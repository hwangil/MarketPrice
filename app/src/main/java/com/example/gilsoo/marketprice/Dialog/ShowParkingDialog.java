package com.example.gilsoo.marketprice.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.gilsoo.marketprice.R;
import com.example.gilsoo.marketprice.data.ParkingLoc;

/**
 * Created by gilsoo on 2016-11-12.
 */
public class ShowParkingDialog extends Dialog {
    ShowParkingDialog showParkingDialog;
    ParkingLoc parkingLoc;
    TextView parkingName, parkingAddress, max_parkingCnt, parkingCnt, parkingOk;

    public ShowParkingDialog(Context context, ParkingLoc parkingLoc){
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_parking_dialog);
        showParkingDialog = this;
        this.parkingLoc = parkingLoc;
        init();
        parkingName.setText(parkingLoc.getParkName());
        parkingAddress.setText("주소 : " + parkingLoc.getParkAddress());
        max_parkingCnt.setText("총 주차 공간 : " + parkingLoc.getMax_parkingCnt() + " 대");
        int remainCnt = Integer.parseInt(parkingLoc.getMax_parkingCnt()) - Integer.parseInt(parkingLoc.getParkingCnt());
        parkingCnt.setText(String.valueOf(remainCnt));
        parkingCnt.setTextColor(Color.rgb(241,88,147));

        parkingOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowParkingDialog.this.dismiss();
            }
        });
    }

    public void init(){
        parkingName = (TextView)findViewById(R.id.parkingName);
        parkingAddress = (TextView)findViewById(R.id.parkingAddress);
        max_parkingCnt = (TextView)findViewById(R.id.max_parkingCnt);
        parkingCnt = (TextView)findViewById(R.id.parkingCnt);
        parkingOk = (TextView)findViewById(R.id.parkingOk);

    }
}
