package com.example.gilsoo.marketprice.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.marketprice.MainActivity;
import com.example.gilsoo.marketprice.R;
import com.example.gilsoo.marketprice.data.SelectMarketInfo;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-12-04.
 */
public class FindParkingDialog extends Dialog{
    FindParkingDialog findParkingDialog;
    ArrayList<SelectMarketInfo> selectMarket;
    ListView findParkingListView;
    FindRoadListAdapter adapter;
    Button findParkingButton;
    int flag = 0;
    int selectMarketPosition = -1;



    public FindParkingDialog(Context context, final ArrayList<SelectMarketInfo> selectMarket, int flag) {
        super(context);
        final Context finContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.find_parking_dialog);
        findParkingDialog = this;
        this.selectMarket = selectMarket;
        this.flag = flag;
        findParkingButton = (Button) findViewById(R.id.findParkingButton);
        findParkingListView = (ListView) findViewById(R.id.findParkingListView);
        adapter = new FindRoadListAdapter(context, selectMarket);

        findParkingListView.setAdapter(adapter);

        findParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectMarketPosition == -1) {
                    Toast toast = Toast.makeText(finContext, "'시장/마켓'을 선택해주세요.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(23);
                    toast.show();
                    return;
                }
                //** 시간이 없으니 어쩔수 없다!
                String selectMarketName = selectMarket.get(selectMarketPosition).getName();
                Log.d("gilsoo" , selectMarketName);
                if (selectMarketName.equals("암사종합시장") || selectMarketName.equals("둔촌역전통시장")) {
                    MainActivity.getTmaptapi().invokeRoute("천호역주차장"
                            , (float) 127.125493, (float) 37.545915);
                } else {
                    Toast toast = Toast.makeText(finContext, "'시장 주변에 주차장이 없습니다.'", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(22);
                    toast.show();
                }


//                int i = 0;
//                ParkingLoc nearParkingLoc = null;
//                for (i = 0; i < CommonData.marketList.size(); i++) {
//                    if (selectMarketName.equals(CommonData.marketList.get(i))) {
//                        Log.d("gilsoo", CommonData.marketList.get(i).getNearParking().getParkName());
//                        Log.d("gilsoo", CommonData.marketList.get(i).getName());
//                        nearParkingLoc = CommonData.marketList.get(i).getNearParking();
//                        break;
//                    }
//                }
//                if (i >= CommonData.marketList.size()) {
//                    for (int j = 0; j < CommonData.martList.size(); j++) {
//                        if (selectMarketName.equals(CommonData.martList.get(j))) {
//                            nearParkingLoc = CommonData.martList.get(j).getNearParking();
//                            break;
//                        }
//                    }
//                }
//                if (nearParkingLoc != null) {
//                    MainActivity.getTmaptapi().invokeRoute(nearParkingLoc.getParkName()
//                            , (float) nearParkingLoc.getLng(), (float) nearParkingLoc.getLat());
//                } else {
//                    Toast toast = Toast.makeText(finContext, "'시장 주변에 주차장이 없습니다.'", Toast.LENGTH_LONG);
//                    LinearLayout toastLayout = (LinearLayout) toast.getView();
//                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
//                    toastTV.setTextSize(22);
//                    toast.show();
//                }
            }
        });


        findParkingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


