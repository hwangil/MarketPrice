package com.example.gilsoo.marketprice.BackgroundService;

import android.widget.ArrayAdapter;

import com.example.gilsoo.marketprice.MainActivity;
import com.example.gilsoo.marketprice.R;
import com.example.gilsoo.marketprice.data.CommonData;
import com.example.gilsoo.marketprice.data.MarketLoc;
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
 * Created by gilsoo on 2016-10-24.
 */
public class GetMarketLoc extends BackGroundWorker {
    ArrayList<String> marketNameList = null;
    ArrayAdapter  searchTextAdapter = null;
    public GetMarketLoc(ArrayList<String> marketNameList, ArrayAdapter searchTextAdapter){
        this.marketNameList = marketNameList;
        this.searchTextAdapter = searchTextAdapter;
    }
    @Override
    protected String doInBackground(String... params) {
        parseMarketInfo(params[0]);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        while(true) {
            if(CommonData.syncNum == 3) {
                GoogleMap map = MainActivity.getMap();
                ArrayList<MarketLoc> list = CommonData.marketList;
                ArrayList<MarketLoc> tempList = new ArrayList<>();
                ArrayList<String> marketInfo = CommonData.marketInfo;
                LatLng loc = null;
                MarkerOptions options = null;
                Marker marker = null;
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < marketInfo.size(); j++) {
//                Log.d("gilsoo", list.get(i).getName() + " == " + marketInfo.get(j));
                        if (list.get(i).getName().equals(marketInfo.get(j))) {
                            tempList.add(list.get(i));
                            loc = new LatLng(list.get(i).getLat(), list.get(i).getLng());
                            options = new MarkerOptions();
                            options.position(loc);
                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                            options.snippet(list.get(i).getName());
                            options.title("시장");
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.store));
                            marker = map.addMarker(options);
                            marker.showInfoWindow();
                            break;
                        }

                    }
                }
                CommonData.increaseSyncFinish();
                CommonData.marketList = tempList;
                for(int i=0; i<CommonData.marketList.size(); i++)
                    marketNameList.add(CommonData.marketList.get(i).getName());
                searchTextAdapter.notifyDataSetChanged();
                CommonData.syncNum = 0;
                break;
            }
        }

    }

    public void parseMarketInfo(String requestUrl) {
        try {
            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");

            String marketName = null;      // 파싱할 시장 이름
            String marketLat = null;       // 파싱할 시장 위도
            String marketLng = null;       // 파싱할 시장 경도

            int parserEvent = parser.getEventType();
            String tag = null;
            int identifier = -1;

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("M_NAME"))
                            identifier = 1;
                        else if (tag.equals("LAT"))
                            identifier = 2;
                        else if (tag.equals("LNG"))
                            identifier = 3;
                        break;

                    case XmlPullParser.END_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        if (identifier == 1) {
                            marketName = parser.getText();
//                            Log.d("gilsoo", "m_name : " + parser.getText());
                        } else if (identifier == 2) {
                            marketLat = parser.getText();
//                            Log.d("gilsoo", "a_name : " + parser.getText());
                        } else if (identifier == 3) {
                            marketLng = parser.getText();
//                            Log.d("gilsoo", "a_unit : " + parser.getText());
                        }

                        identifier = 0;
                        break;
                }
                if (marketName != null && marketLat != null && marketLng != null) {

                    //** 시간이 없으니 어쩔수 없다.!
                    if(marketName.equals("암사종합시장") || marketName.equals("둔촌역전통시장")){
                        ParkingLoc parkingLoc = new ParkingLoc("천호역주차장", null, null, null);
                        parkingLoc.setLat(37.545915);
                        parkingLoc.setLng(127.125493);
                        CommonData.marketList.add(new MarketLoc(Double.valueOf(marketLat), Double.valueOf(marketLng), marketName, parkingLoc));
                    }else
                        CommonData.marketList.add(new MarketLoc(Double.valueOf(marketLat), Double.valueOf(marketLng), marketName));
                    marketName = null;
                    marketLat = null;
                    marketLng = null;

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
