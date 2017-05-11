package com.example.gilsoo.marketprice.BackgroundService;

import com.example.gilsoo.marketprice.data.CommonData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gilsoo on 2016-10-24.
 */
public class GetMarketInfo extends BackGroundWorker {
    @Override
    protected String doInBackground(String... params) {
        parseMarketPriceInfo(params[0]);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.d("synchro", "onPost");
        CommonData.increaseSyncNum();

    }

    public void parseMarketPriceInfo(String requestUrl) {
//        Log.d("synchro", "parsing");
        try {
            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");

            String m_name = null;       // 파싱할 시장 이름
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
                        if (tag.equals("M_NAME"))
                            identifier = 1;
//                        else if (tag.equals("A_NAME"))
//                            identifier = 2;
//                        else if (tag.equals("A_UNIT"))
//                            identifier = 3;
//                        else if (tag.equals("A_PRICE"))
//                            identifier = 4;
                        break;

                    case XmlPullParser.END_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        if (identifier == 1) {
                            m_name = parser.getText();
//                            Log.d("gilsoo", "m_name : " + parser.getText());
                        }
//                         else if (identifier == 2) {
//                            a_name = parser.getText();
////                            Log.d("gilsoo", "a_name : " + parser.getText());
//                        } else if (identifier == 3) {
//                            a_unit = parser.getText();
////                            Log.d("gilsoo", "a_unit : " + parser.getText());
//                        } else if (identifier == 4) {
//                            a_price = parser.getText();
////                            Log.d("gilsoo", "a_price : " + parser.getText());
//                        }

                        identifier = 0;
                        break;
                }
                if (m_name != null) {
                    int i = 0;
                    for (i = 0; i < CommonData.marketInfo.size(); i++) {
                        if (m_name.equals(CommonData.marketInfo.get(i)))
                            break;
                    }
                    if (i == CommonData.marketInfo.size())
                        CommonData.marketInfo.add(m_name);

//                    CommonData.marketPrice.add(new MarketPrice(m_name, a_name, a_unit, a_price));
//                    m_name = null;
//                    a_name = null;
//                    a_unit = null;
//                    a_price = null;
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
