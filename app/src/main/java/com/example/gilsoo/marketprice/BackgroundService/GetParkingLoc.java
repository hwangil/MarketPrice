package com.example.gilsoo.marketprice.BackgroundService;

import android.content.Context;

import com.example.gilsoo.marketprice.MainActivity;
import com.example.gilsoo.marketprice.R;
import com.example.gilsoo.marketprice.data.CommonData;
import com.example.gilsoo.marketprice.data.ParkingLoc;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-10-29.
 */
public class GetParkingLoc extends BackGroundWorker{

    Context context;

    public GetParkingLoc(Context context){
        this.context = context;
    }
    @Override
    protected void onPostExecute(String s) {
        GoogleMap map = MainActivity.getMap();
        ArrayList<ParkingLoc> list = CommonData.parkList;
        for(int i=0; i<list.size(); i++){
            for(int k=0; k<CommonData.parkInfo.size(); k++) {
                if (list.get(i).getParkName().equals(CommonData.parkInfo.get(k).getName())) {
                    list.get(i).setLat(CommonData.parkInfo.get(k).getLat());
                    list.get(i).setLng(CommonData.parkInfo.get(k).getLng());
                    break;
                }
            }
        }

//        Geocoder gc = new Geocoder(context, Locale.KOREA);
////        Log.d("gilsoo", "size : " + list.size());
//        for(int i=0; i<list.size(); i++){
//            try {
//                List<Address> addr = null;
//                addr =  gc.getFromLocationName(list.get(i).getParkAddress(), 5);
//                if(addr.size() >= 1){
////                    Log.d("gilsoo", ""+i);
//                    list.get(i).setLat(addr.get(0).getLatitude());
////                    Log.d("gilsoo", "" + list.get(i).getLat());
//                    list.get(i).setLng(addr.get(0).getLongitude());
////                    Log.d("gilsoo", "" + list.get(i).getLng());
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        LatLng loc = null;
        MarkerOptions options = null;
        Marker marker = null;

        for(int i=0; i<list.size(); i++) {
            loc = new LatLng(list.get(i).getLat(), list.get(i).getLng());
            options = new MarkerOptions();
            options.position(loc);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            options.snippet(list.get(i).getParkName());
            options.title("주차장");
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.parking));
            marker = map.addMarker(options);
            marker.showInfoWindow();
        }
        CommonData.increaseSyncFinish();

    }

    @Override
    protected String doInBackground(String... params) {

        parseParkingInfo(params[0]);
        return null;
    }

    public void parseParkingInfo(String requestUrl) {
        try {
            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");

            String parkName = null;      // 파싱할 주차장 이름
            String parkAddress = null;       // 파싱할 주차장 주소
            String max_parkingCnt = null;       // 파싱할 주차장 총 공간( 차 대수)
            String parkingCnt = null;       // 파싱할 주차장 현재 차 대수

            int parserEvent = parser.getEventType();
            String tag = null;
            int identifier = -1;

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("PARK_NAME"))
                            identifier = 1;
                        else if (tag.equals("PARK_ADDRESS"))
                            identifier = 2;
                        else if (tag.equals("MAX_PARKING_CNT"))
                            identifier = 3;
                        else if (tag.equals("PARKING_CNT"))
                            identifier = 4;
                        break;

                    case XmlPullParser.END_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        if (identifier == 1) {
                            parkName = parser.getText();
//                            Log.d("gilsoo", "parkName : " + parser.getText());
                        } else if (identifier == 2) {
                            parkAddress = parser.getText();
//                            Log.d("gilsoo", "parkAddress : " + parser.getText());
                        } else if (identifier == 3) {
                            max_parkingCnt = parser.getText();
//                            Log.d("gilsoo", "max_parkingCnt : " + parser.getText());
                        } else if (identifier == 4) {
                            parkingCnt = parser.getText();
//                            Log.d("gilsoo", "parkingCnt : " + parser.getText());
                        }

                        identifier = 0;
                        break;
                }
                if (parkName != null && parkAddress != null && max_parkingCnt != null && parkingCnt != null) {
                    CommonData.parkList.add(new ParkingLoc(parkName, parkAddress, max_parkingCnt, parkingCnt));
                    parkName = null;
                    parkAddress = null;
                    max_parkingCnt = null;
                    parkingCnt = null;
                }
                parserEvent = parser.next();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
