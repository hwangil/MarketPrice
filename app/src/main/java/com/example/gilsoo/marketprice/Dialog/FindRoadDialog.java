package com.example.gilsoo.marketprice.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.marketprice.MainActivity;
import com.example.gilsoo.marketprice.R;
import com.example.gilsoo.marketprice.data.SelectMarketInfo;
import com.skp.Tmap.TMapPoint;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-11-20.
 */
public class FindRoadDialog extends Dialog {
    FindRoadDialog findRoadDialog;
    ArrayList<SelectMarketInfo> selectMarket;
    ListView findRoadListView;
    FindRoadListAdapter adapter;
    Button findCarRoad;
    Button findPublicRoad;
    int flag = 0;
    int selectMarketPosition = -1;



    public FindRoadDialog(Context context, final ArrayList<SelectMarketInfo> selectMarket, int flag) {
        super(context);
        final Context finContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.find_road_dialog);
        findRoadDialog = this;
        this.selectMarket = selectMarket;
        this.flag = flag;
        findCarRoad = (Button) findViewById(R.id.findCarRoad);
        findPublicRoad = (Button) findViewById(R.id.findPublicRoad);
        findRoadListView = (ListView) findViewById(R.id.findRoadListView);
        adapter = new FindRoadListAdapter(context, selectMarket);

        findRoadListView.setAdapter(adapter);

        findPublicRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectMarketPosition == -1){
                    Toast toast = Toast.makeText(finContext, "'시장/마켓'을 선택해주세요.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(23);
                    toast.show();
                    return;
                }
                double startLat, startLng;
                TMapPoint tpoint = MainActivity.getTmapgps().getLocation();
                if(tpoint != null) {
                    startLat = tpoint.getLatitude();
                    startLng = tpoint.getLongitude();
                }else{
                    startLat = 37.5407667;
                    startLng = 127.0771541;
                }
                Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=" + startLat + ", " + startLng + " &daddr="
                        + selectMarket.get(selectMarketPosition).getLat() + ", " + selectMarket.get(selectMarketPosition).getLng() + "&hl=ko");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                finContext.startActivity(it);
            }
        });

        findCarRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectMarketPosition == -1){
                    Toast toast = Toast.makeText(finContext, "'시장/마켓'을 선택해주세요.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(23);
                    toast.show();
                    return;
                }
                Log.d("gilsoo", "lat,lng : " + selectMarket.get(selectMarketPosition).getName() + ", " + selectMarket.get(selectMarketPosition).getLat() + ", " + selectMarket.get(selectMarketPosition).getLng());
                if(MainActivity.getTmaptapi().isTmapApplicationInstalled())
                    MainActivity.getTmaptapi().invokeRoute(selectMarket.get(selectMarketPosition).getName()
                            , (float) selectMarket.get(selectMarketPosition).getLng(), (float) selectMarket.get(selectMarketPosition).getLat());
                else
                    Toast.makeText(getContext(), "TMap 어플리케이션을 깔아주세요 ", Toast.LENGTH_SHORT).show();

            }
        });
        findRoadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMarketPosition = position;
                Log.d("gilsoo", "" + selectMarketPosition);
            }
        });
        Button dialog_cancelBtn = (Button)findViewById(R.id.dialog_cancelBtn);
        dialog_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        selectMarketPosition = -1;
        super.onBackPressed();
    }
}

class FindRoadListAdapter extends BaseAdapter {
    Context context;
    ArrayList<SelectMarketInfo> selectMarket;

    public FindRoadListAdapter(Context context, ArrayList<SelectMarketInfo> selectMarket) {
        this.context = context;
        this.selectMarket = selectMarket;
    }

    @Override
    public int getCount() {
        return selectMarket.size();
    }

    @Override
    public Object getItem(int position) {
        return selectMarket.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.find_road_row, null);
        }
        TextView textVIew = (TextView) view.findViewById(R.id.findRoadText);
        textVIew.setText(selectMarket.get(position).getName());

        return view;
    }
}
