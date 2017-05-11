package com.example.gilsoo.marketprice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.marketprice.BackgroundService.GetMarketInfo;
import com.example.gilsoo.marketprice.BackgroundService.GetMarketLoc;
import com.example.gilsoo.marketprice.BackgroundService.GetParkingLoc;
import com.example.gilsoo.marketprice.Dialog.ShowFavoriteDialog;
import com.example.gilsoo.marketprice.Dialog.ShowParkingDialog;
import com.example.gilsoo.marketprice.data.CommonData;
import com.example.gilsoo.marketprice.data.CommonFunc;
import com.example.gilsoo.marketprice.data.ParkingLoc;
import com.example.gilsoo.marketprice.data.SelectMarketInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapTapi;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    MapFragment mapFrament = null;
    private static GoogleMap map = null;
    ListView selectMarketList = null;
    MarketListAdapter selectMarketAdapter = null;
    ArrayList<SelectMarketInfo> selectMarket = null;
    ArrayList<String> marketNameList = null;
    MenuItem searchMarket = null;
    MenuItem goMarket = null;
    Toolbar main_toolbar, search_toolbar;
    Button goToolBtn, backToolBtn = null;
    MultiAutoCompleteTextView searchEditText = null;
    ArrayAdapter<String> searchTextAdapter = null;
    long backKeyPressedTime = 0;
    static TMapView tmapview = null;
    static TMapTapi tmaptapi = null;
    static TMapGpsManager tmapgps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this, SplashActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (CommonData.syncFinish == 2) {
                        SplashActivity.splashActivity.finish();
                        break;
                    }
                }
            }
        }).start();

        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        search_toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(main_toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //**                **//
        init();
        mapFrament = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrament);
        mapFrament.getMapAsync(this);

        new GetMarketInfo().execute(CommonData.marketPriceUrl1);
        new GetMarketInfo().execute(CommonData.marketPriceUrl2);
        new GetMarketInfo().execute(CommonData.marketPriceUrl3);
        new GetMarketLoc(marketNameList, searchTextAdapter).execute(CommonData.marketLocUrl);

        new GetParkingLoc(this).execute(CommonData.parkingLocUrl);
        tmapview = new TMapView(this);
        tmapview.setSKPMapApiKey(CommonData.TMKEY);
        tmapview.setTrackingMode(true);
        tmapgps = new TMapGpsManager(this);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(5);
        tmapgps.setProvider(tmapgps.GPS_PROVIDER);
        tmapgps.OpenGps();
        tmaptapi = new TMapTapi(this);
        tmaptapi.setSKPMapAuthentication(CommonData.TMKEY);
        tmaptapi.setOnAuthenticationListener(new TMapTapi.OnAuthenticationListenerCallback() {
            @Override
            public void SKPMapApikeySucceed() {
                Log.d("gilsoo", "SKMapApikeySucced()");
            }

            @Override
            public void SKPMapApikeyFailed(String s) {
                Log.d("gilsoo", "SKMapApikeySucced()");
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);        //*** 맵타일 유형 설정

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    99);

            return;
        }

        //** 현재 위치 컨트롤러 표시
        map.setMyLocationEnabled(true);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setRotateGesturesEnabled(false);
        final LatLng loc = new LatLng(37.5407667, 127.0771541);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14));
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14));

        LatLng martLoc = null;
        MarkerOptions options = null;
        Marker marker = null;
        for (int i = 0; i < CommonData.martList.size(); i++) {           // 마트 정보 받아오기 (미리 저장해두었음)
//            Log.d("gilsoo" , "martList.size() : " + CommonData.martList.size());
            martLoc = new LatLng(CommonData.martList.get(i).getLat(), CommonData.martList.get(i).getLng());
            options = new MarkerOptions();
            options.position(martLoc);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            options.snippet(CommonData.martList.get(i).getName());
            options.title("마트");
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.cart2));
            marker = map.addMarker(options);

            marker.showInfoWindow();
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {               // 마커 클릭하면 시장정보 리스트뷰에 추가
                if (marker.getTitle().equals("시장") || marker.getTitle().equals("마트")) {
                    String snippet = marker.getSnippet();
                    int i = 0;
                    for (i = 0; i < selectMarket.size(); i++) {
                        if (snippet.equals(selectMarket.get(i).getName()))
                            break;
                    }
                    if (i == selectMarket.size()) {
                        selectMarket.add(new SelectMarketInfo(snippet, marker.getPosition().latitude, marker.getPosition().longitude));
                        selectMarketAdapter.notifyDataSetChanged();
                    }

//                //  ToDo. 현재위치 가져오기 --- 시간되면 Tmap으로 바꾸기, 주차장은 대중교통 정보가 필요 없음
//                    // 구글맵 길찾기 url -> 대중교통밖에 안됨.
//                    LatLng start = null;
//                    LatLng des = marker.getPosition();
//                    Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=건대역&daddr=" + des.latitude + ", " + des.longitude + "&hl=ko");
//
//                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(it);
                    return false;
                } else if (marker.getTitle().equals("주차장")) {
                    String snippet = marker.getSnippet();
                    ParkingLoc selectParkingLoc = null;
                    for (int i = 0; i < CommonData.parkList.size(); i++) {
                        if (snippet.equals(CommonData.parkList.get(i).getParkName())) {
                            selectParkingLoc = CommonData.parkList.get(i);
                            break;
                        }
                    }
                    new AsyncTask<ParkingLoc, Void, ParkingLoc>() {
                        @Override
                        protected ParkingLoc doInBackground(ParkingLoc... params) {
                            ParkingLoc selectParkingLoc = params[0];
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return selectParkingLoc;
                        }

                        @Override
                        protected void onPostExecute(ParkingLoc parkingLoc) {
                            super.onPostExecute(parkingLoc);
                            new ShowParkingDialog(MainActivity.this, parkingLoc).show();
                        }
                    }.execute(selectParkingLoc);
                    return false;
                }

                return false;
            }
        });

    }

    public static GoogleMap getMap() {
        return map;
    }

    public static TMapView getTMap() {
        return tmapview;
    }

    public static TMapTapi getTmaptapi() {
        return tmaptapi;
    }

    public static TMapGpsManager getTmapgps() {
        return tmapgps;
    }

    public void init() {
        selectMarketList = (ListView) findViewById(R.id.selectMarketList);
        selectMarket = new ArrayList<SelectMarketInfo>();
        selectMarketAdapter = new MarketListAdapter(this, selectMarket, selectMarketList);
        selectMarketList.setAdapter(selectMarketAdapter);

        final Button goToolBtn = (Button)findViewById(R.id.goToolBtn);
        final Button backToolBtn = (Button)findViewById(R.id.backToolBtn);

        marketNameList = new ArrayList<String>();
        for(int i=0; i<CommonData.martList.size(); i++)
            marketNameList.add(CommonData.martList.get(i).getName());
        searchEditText = (MultiAutoCompleteTextView)findViewById(R.id.searchEditText);
        searchTextAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, marketNameList);
        searchEditText.setAdapter(searchTextAdapter);
        searchEditText.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
            @Override
            public int findTokenStart(CharSequence text, int cursor) {
                return 0;
            }

            @Override
            public int findTokenEnd(CharSequence text, int cursor) {
                return 0;
            }

            @Override
            public CharSequence terminateToken(CharSequence text) {
                return text;
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    goToolBtn.setEnabled(true);
                else
                    goToolBtn.setEnabled(false);
            }
        });

    }

    public void onToolbarBtnClick(View v) {
        switch (v.getId()) {
            case R.id.goToolBtn:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);   // 키보드 내리는거
                imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                int i=0;
                String name = null;
                for(i=0; i<marketNameList.size(); i++){
                    if(marketNameList.get(i).equals(searchEditText.getText().toString())){

                        name = marketNameList.get(i);
                        break;
                    }
                }
                if(i >= marketNameList.size()){
                    Toast.makeText(this, "찾으시는 '시장/마트' 정보가 없습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                for(int j=0; j<CommonData.marketList.size(); j++)
                    if(CommonData.marketList.get(j).getName().equals(name)){
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CommonData.marketList.get(j).getLat()
                                , CommonData.marketList.get(j).getLng()), 14));
                        return;
                    }

                for(int j=0; j<CommonData.martList.size(); j++)
                    if(CommonData.martList.get(j).getName().equals(name)){
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CommonData.martList.get(j).getLat()
                                , CommonData.martList.get(j).getLng()), 14));
                        return;
                    }

                break;
            case R.id.searchToolBtn:
                main_toolbar.setVisibility(View.GONE);
                search_toolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.backToolBtn:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                searchEditText.setText("");
                main_toolbar.setVisibility(View.VISIBLE);
                search_toolbar.setVisibility(View.GONE);
                break;
        }
    }

    public void onGoCompareBtn(View v) {
        if (selectMarket.size() > 0) {
            Intent intent = new Intent(this, CompareActivity.class);
            intent.putParcelableArrayListExtra("selectMarket", selectMarket);
            startActivity(intent);
        }
    }
    public void onGoFavoriteBtn(View v){
        Map<String, Boolean> map = CommonFunc.getAllPreferences(this);
        ArrayList<String> favoriteMarket = new ArrayList<>();
        for(String mapkey : map.keySet()){
            Log.d("gilsoo", "in preferences -  key : " + mapkey +", value : " + map.get(mapkey));
            favoriteMarket.add(mapkey);
        }
        new ShowFavoriteDialog(this, favoriteMarket, selectMarket, selectMarketAdapter).show();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();

                /*
                 *  Toast message 크기 조정
                 */
                Toast toast = Toast.makeText(getApplicationContext(), "한번더 누르면 종료합니다", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(25);
                toast.show();
            } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//                Toast.makeText(getApplicationContext(), "한번더 누르면 종료합니다", Toast.LENGTH_SHORT).cancel();
                moveTaskToBack(true);
                ActivityCompat.finishAffinity(this);        // 안드로이드 프로세스 종료시키는 법
                System.runFinalizersOnExit(true);           // 방법에 대해 말이 많음, 일단 이렇게 종료시켰음. 연구 필요!
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        searchMarket = menu.findItem(R.id.searchMarket);        // **** findItem. getItem 차이는??
//        goMarket = menu.findItem(R.id.goMarket);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.searchMarket) {
//            searchMarket.setVisible(false);
//            goMarket.setVisible(true);
//            main_toolbar.setVisibility(View.GONE);
//            search_toolbar.setVisibility(View.VISIBLE);
//        }
//      else if(id == R.id.goMarket){
//            searchMarket.setVisible(true);
//            goMarket.setVisible(false);
//            main_toolbar.setVisibility(View.VISIBLE);
//            search_toolbar.setVisibility(View.GONE);
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_send) {
//            Uri uri = Uri.parse("dolce0629@naver.com");
//            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
