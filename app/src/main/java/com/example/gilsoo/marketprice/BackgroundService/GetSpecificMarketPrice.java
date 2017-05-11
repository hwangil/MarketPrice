package com.example.gilsoo.marketprice.BackgroundService;

import android.util.Log;

import com.example.gilsoo.marketprice.ProductItem;
import com.example.gilsoo.marketprice.ProductListAdapter;
import com.example.gilsoo.marketprice.data.CommonData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-10-25.
 */
public class GetSpecificMarketPrice extends BackGroundWorker {
    ArrayList<ArrayList<ProductItem>>  list;
    String market;
    ProductListAdapter listAdapter;
    int index;
    int length;
    CallBackEvent callBackEvent;

    public GetSpecificMarketPrice(ArrayList<ArrayList<ProductItem>> list, ProductListAdapter listAdapter, String market, int index, int length, CallBackEvent callBackEvent){
        this.list = list;
        this.listAdapter = listAdapter;
        this.market = market;
        this.index = index;
        this.length = length;
        this.callBackEvent = callBackEvent;
    }

    @Override
    protected void onPostExecute(String s) {
//        for(int i=0; i<list.get(index).size()-1; i++){            // 의미 없음 -> 상품끼리 상품번호가 같지 않은 경우가 있음
//            for(int j=i+1; j<list.get(index).size(); j++){
//                ProductItem tempItem;
//                if(Integer.parseInt(list.get(index).get(i).getA_seq()) > Integer.parseInt(list.get(index).get(j).getA_seq())){
//                    tempItem = new ProductItem(list.get(index).get(j).getA_seq(), list.get(index).get(j).getA_name()
//                    ,list.get(index).get(j).getA_unit(), list.get(index).get(j).getA_price());
//                    list.get(index).set(j, list.get(index).get(i));
//                    list.get(index).set(i, tempItem);
//                }
//            }
//        }
        if(index == length-1) {             //*** 사실 여기 동기화 해줘야함
            for(int k=0; k<16; k++) {
                int lowestPriceIndex = 0;
                for (int i = 0; i < list.size() - 1; i++) {

                    if (Integer.valueOf(list.get(lowestPriceIndex).get(k).getA_price()) > Integer.valueOf(list.get(i+1).get(k).getA_price())) {
                        lowestPriceIndex = i + 1;
                    }
                }
                list.get(lowestPriceIndex).get(k).setLowestFlag(true);
            }
            listAdapter.notifyDataSetChanged();
            callBackEvent.stopProgress();
        }

    }

    @Override
    protected String doInBackground(String... params) {
        for(int i=0; i<16; i++) {
            parseSpecificMarketPriceInfo(params[0] + "/"+ CommonData.PRODUCT_NAME[i]);
        }
//        parseSpecificMarketPriceInfo(params[0]);
        Log.d("synchro", "specificMarketParsing : #" + index);
        return null;
    }

     public void parseSpecificMarketPriceInfo(String requestUrl) {
         Log.d("gilsoo", requestUrl);
        try {
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(is, "UTF-8");

                String a_seq = null;
                String a_name = null;      // 파싱할 물품이름
                String a_unit = null;       // 파싱할 물품단위
                String a_price = null;       // 파싱할 물품가격


                int parserEvent = parser.getEventType();
                String tag = null;
                int identifier = -1;

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if (tag.equals("A_SEQ"))
                                identifier = 0;
                            else if (tag.equals("A_NAME"))
                                identifier = 1;
                            else if (tag.equals("A_UNIT"))
                                identifier = 2;
                            else if (tag.equals("A_PRICE"))
                                identifier = 3;
                            else if (tag.equals("CODE"))
                                identifier = 4;

                            break;

                        case XmlPullParser.END_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            if (identifier == 0) {
                                a_seq = parser.getText();
//                                Log.d("gilsoo", "" + index);
//                                Log.d("gilsoo", "a_seq : " + a_seq);

                            } else if (identifier == 1) {
                                a_name = parser.getText();
//                                Log.d("gilsoo", "a_name : " + parser.getText());
                            } else if (identifier == 2) {
                                a_unit = parser.getText();
//                                Log.d("gilsoo", "a_unit : " + parser.getText());
                            } else if (identifier == 3) {
                                a_price = parser.getText();
//                                Log.d("gilsoo", "a_price : " + parser.getText());
                            } else if (identifier == 4){
                                if(parser.getText().equals("INFO-200"))
                                    list.get(index).add(new ProductItem());
                            }

                            identifier = -1;
                            break;
                    }
                    if (a_seq != null && a_name != null && a_unit != null && a_price != null) {

                        list.get(index).add(new ProductItem(a_seq, a_name, a_unit, a_price));
                        a_seq = null;
                        a_name = null;
                        a_unit = null;
                        a_price = null;
                    }
                    parserEvent = parser.next();
                }

        } catch (MalformedURLException e) {
            Log.d("gilsoo", "Malformed : "+e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.d("gilsoo", "Xml : "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("gilsoo", "IO : " +e.getMessage());
            e.printStackTrace();
        }
    }
}
