package com.example.gilsoo.marketprice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.marketprice.BackgroundService.CallBackEvent;
import com.example.gilsoo.marketprice.BackgroundService.GetSpecificMarketPrice;
import com.example.gilsoo.marketprice.Dialog.FindParkingDialog;
import com.example.gilsoo.marketprice.Dialog.FindRoadDialog;
import com.example.gilsoo.marketprice.data.CommonData;
import com.example.gilsoo.marketprice.data.SelectMarketInfo;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class CompareActivity extends Activity {
    ProgressDialog progressDialog;
    ListView productListView;
    ProductListAdapter productListAdapter;
    ArrayList<SelectMarketInfo> selectMarket;
//    ArrayList<ProductItem> productList;
    ArrayList<ArrayList<ProductItem>> productList;
    boolean syncFlag = false;

    Button comparePrevBtn, compareNextBtn;
    FabSpeedDial floatingBtn;

    @Override
    public void onBackPressed() {
        if(progressDialog != null)
            progressDialog.dismiss();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        init();
        for(int i=0; i<selectMarket.size(); i++){
            String market = selectMarket.get(i).getName();

            String requestUrl = CommonData.marketPriceUrl  + market ;
            productList.add(new ArrayList<ProductItem>());
            new GetSpecificMarketPrice(productList, productListAdapter, market, i, selectMarket.size(), new CallBackEvent() {
                @Override
                public void stopProgress() {
                    progressDialog.dismiss();
                }
            }).execute(requestUrl);

        }
    }


    public void init(){
        progressDialog = ProgressDialog.show(new ContextThemeWrapper(this, R.style.DialogCustom), "", "잠시만 기다려주세요 ", false);
        selectMarket = getIntent().getParcelableArrayListExtra("selectMarket");
        productListView = (ListView)findViewById(R.id.productListView);
        productList = new ArrayList<ArrayList<ProductItem>>();
        productListAdapter = new ProductListAdapter(this, productList, selectMarket);
        productListView.setAdapter(productListAdapter);

        comparePrevBtn = (Button)findViewById(R.id.comparePrevBtn);
        compareNextBtn = (Button)findViewById(R.id.compareNextBtn);

        floatingBtn = (FabSpeedDial)findViewById(R.id.floatingBtn);
        if (floatingBtn != null) {
            floatingBtn.setMenuListener(new SimpleMenuListenerAdapter(){
                @Override
                public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                    super.onPrepareMenu(navigationMenu);
                    /**
                     *   floating button 에서 menu의 item 을 가져와서 텍스트 크기 설정해주는 부분(SpannableString 사용!)
                     * */
                    for(int i=0; i<navigationMenu.size(); i++){
                        MenuItem item = navigationMenu.getItem(i);
                        SpannableString spannableString = new SpannableString(item.getTitle().toString());
                        spannableString.setSpan(new RelativeSizeSpan(2.0f), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        item.setTitle(spannableString);
                    }

                    return true;
                }

                @Override
                public boolean onMenuItemSelected(MenuItem menuItem) {
                    switch(menuItem.getItemId()){

                        case R.id.menu_road:
                            new FindRoadDialog(CompareActivity.this, selectMarket, 0).show();       // flag = 0; -> 시장 길찾기
                            Toast toast = Toast.makeText(getApplicationContext(), "'시장/마트'까지의 길안내 서비스입니다.", Toast.LENGTH_LONG);
                            LinearLayout toastLayout = (LinearLayout) toast.getView();
                            TextView toastTV = (TextView) toastLayout.getChildAt(0);
                            toastTV.setTextSize(22);
                            toast.show();

                            break;
                        case R.id.menu_parking:
                            new FindParkingDialog(CompareActivity.this, selectMarket, 1).show();       // flag = 1; -> 주차장 길찾기
                            toast = Toast.makeText(getApplicationContext(), "'시장/마트'주변 주차장까지의 길안내 서비스입습니다.", Toast.LENGTH_LONG);
                            toastLayout = (LinearLayout) toast.getView();
                            toastTV = (TextView) toastLayout.getChildAt(0);
                            toastTV.setTextSize(22);
                            toast.show();
                            break;
                    }

                    return true;
                }
            });
        }
        Button backToolBtn2 = (Button)findViewById(R.id.backToolBtn2);
        backToolBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    public void onCompareBtn(View v){
        switch(v.getId()){
            case R.id.comparePrevBtn:
                productListAdapter.prevScrollEvent();
                break;
            case R.id.compareNextBtn:
                productListAdapter.nextScrollEvent();
                break;

        }
    }

    public void setSyncScrollBtn(View v){
        if(!syncFlag) {
            productListAdapter.syncScrollEvent();
            syncFlag = !syncFlag;
        }
        else {
            productListAdapter.unsyncScrollEvent();
            syncFlag = !syncFlag;
        }
    }


}
