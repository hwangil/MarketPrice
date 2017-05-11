package com.example.gilsoo.marketprice.data;

import com.example.gilsoo.marketprice.R;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-10-24.
 */
public class CommonData {
    public static int syncNum = 0;
    public static int syncFinish = 0;
    public static final String KEY = "6b5772496664637437346467664564";     // 서울시 공공데이터 api key
    public static final String TMKEY = "241cb710-87c3-39f2-b094-959d662ab723";
    public static final String marketLocUrl = "http://openAPI.seoul.go.kr:8088/" + KEY + "/xml/ListTraditionalMarket/1/1000/";

    public static final String marketPriceUrl = "http://openapi.seoul.go.kr:8088/" + KEY + "/xml/ListNecessariesPricesService/1/1/";
    public static final String marketPriceUrl1 = "http://openapi.seoul.go.kr:8088/" + KEY + "/xml/ListNecessariesPricesService/1/1000/";
    public static final String marketPriceUrl2 = "http://openapi.seoul.go.kr:8088/" + KEY + "/xml/ListNecessariesPricesService/1001/2000/";
    public static final String marketPriceUrl3 = "http://openapi.seoul.go.kr:8088/" + KEY + "/xml/ListNecessariesPricesService/2001/2600/";

    public static final String parkingLocUrl = "http://openAPI.seoul.go.kr:8088/" + KEY + "/xml/PublicParkingAvaliable/1/26/";

    public static final String[] PRODUCT_NAME = {"사과", "배", "배추", "무", "양파", "상추", "오이", "애호박",
            "쇠고기", "돼지고기", "닭고기", "달걀", "조기", "명태", "오징어", "고등어"};
    public static final int[] PRODUCT_IMAGE = {R.drawable.apple, R.drawable.pear, R.drawable.cabbage, R.drawable.radish, R.drawable.onion
    , R.drawable.salad, R.drawable.cucumber, R.drawable.green_cucumber, R.drawable.meat, R.drawable.pig_meat, R.drawable.chicken
    , R.drawable.eggs, R.drawable.salmon, R.drawable.zander, R.drawable.squid, R.drawable.tuna};


    public static ArrayList<MarketLoc> marketList = new ArrayList<MarketLoc>();         // 서울시 시장 위치 정보
    public static ArrayList<MartLoc> martList = new ArrayList<MartLoc>() {{             // 서울시 마트 위치정보
        add(new MartLoc("신세계백화점", 127.11, 37.33));
        add(new MartLoc("이마트용산점", 126.96, 37.53));
        add(new MartLoc("롯데마트서울역점", 126.97, 37.56));
        add(new MartLoc("이마트미아점", 127.03, 37.61));
        add(new MartLoc("현대백화점미아점", 127.03, 37.61));
        add(new MartLoc("홈플러스영등포점", 126.90, 37.52));
        add(new MartLoc("이마트여의도점", 126.93, 37.52));
        add(new MartLoc("이마트창동점", 127.05, 37.65));
        add(new MartLoc("홈플러스방학점", 127.04, 37.66));
        add(new MartLoc("현대백화점신촌점", 126.94, 37.56));
        add(new MartLoc("홈플러스등촌점", 126.85, 37.56));
        add(new MartLoc("이마트가양점", 126.86, 37.56));
        add(new MartLoc("이마트역삼점", 127.05, 37.50));
        add(new MartLoc("롯데백화점강남점", 127.05, 37.50));
        add(new MartLoc("2001아울렛불광점", 126.93, 37.61));
        add(new MartLoc("이마트은평점", 126.92, 37.60));
        add(new MartLoc("롯데백화점", 126.77, 37.66));
        add(new MartLoc("이마트청계점", 127.02, 37.57));
        add(new MartLoc("농협하나로마트용산점", 126.96, 37.53));
        add(new MartLoc("롯데백화점미아점 ", 127.03, 37.61));
        add(new MartLoc("이마트왕십리점", 127.04, 37.56));
        add(new MartLoc("이마트성수점", 127.05, 37.54));
        add(new MartLoc("이마트자양점", 127.073358, 37.538784));
        add(new MartLoc("롯데마트강변점", 127.10, 37.54));
        add(new MartLoc("홈플러스동대문점", 127.04, 37.57));
        add(new MartLoc("롯데백화점청량리점", 127.05, 37.58));
        add(new MartLoc("이마트상봉점", 127.09, 37.60));
        add(new MartLoc("홈플러스면목점", 127.08, 37.58));
        add(new MartLoc("롯데백화점노원점", 127.06, 37.66));
        add(new MartLoc("홈플러스중계점", 127.07, 37.64));
        add(new MartLoc("행복한세상목동점(하나로마트)", 126.88, 37.53));
        add(new MartLoc("이마트신도림점", 126.89, 37.51));
        add(new MartLoc("애경백화점", 126.8126, 37.45));
        add(new MartLoc("그랜드마트신촌점", 126.94, 37.55));
        add(new MartLoc("홈플러스월드컵점", 126.90, 37.57));
        add(new MartLoc("태평백화점", 126.98, 37.49));
        add(new MartLoc("롯데백화점영등포점", 126.91, 37.52));
        add(new MartLoc("롯데백화점관악점", 126.92, 37.49));
        add(new MartLoc("세이브마트", 126.91, 37.71));
        add(new MartLoc("하나로클럽양재점", 127.04, 37.46));
        add(new MartLoc("롯데백화점잠실점", 127.10, 37.51));
        add(new MartLoc("홈플러스잠실점", 127.10, 37.52));
        add(new MartLoc("이마트명일점", 127.16, 37.55));
        add(new MartLoc("홈플러스강동점", 127.14, 37.55));
        add(new MartLoc("뉴코아아울렛강남점", 127.01, 37.51));
        add(new MartLoc("하나로클럽미아점", 127.03, 37.62));
        add(new MartLoc("이마트목동점", 126.87, 37.53));
        add(new MartLoc("신세계백화점강남점", 127.00, 37.50));
        add(new MartLoc("롯데슈퍼", 128.62, 38.08));
        add(new MartLoc("홈플러스독산점", 126.90, 37.47));

    }};
    public static ArrayList<ParkingLoc> parkList = new ArrayList<ParkingLoc>();               // 서울시 주차장 정보

    public static ArrayList<ParkingLatln> parkInfo = new ArrayList<ParkingLatln>(){{
        add(new ParkingLatln("잠실역주차장", 127.09299, 37.51800));
        add(new ParkingLatln("창동역주차장", 127.04906, 37.65437));
        add(new ParkingLatln("구로디지털단지역주차장", 126.90171, 37.48489));
        add(new ParkingLatln("개화산역주차장", 126.80520, 37.57223));
        add(new ParkingLatln("수서역북주차장", 127.09974, 37.48801));
        add(new ParkingLatln("수서역남주차장", 127.09974, 37.48801));
        add(new ParkingLatln("복정역주차장", 127.12858, 37.47045));
        add(new ParkingLatln("한강진역주차장", 127.00253, 37.53950));
        add(new ParkingLatln("수락산역주차장", 127.05590, 37.67902));
        add(new ParkingLatln("봉화산역주차장", 127.09419, 37.60622));
        add(new ParkingLatln("화랑대역주차장", 127.08077, 37.61580));
        add(new ParkingLatln("구파발역주차장", 127.03608, 37.57162));
        add(new ParkingLatln("영등포구청역주차장", 126.89516, 37.52490));
        add(new ParkingLatln("학여울역주차장", 127.06989, 37.49515));
        add(new ParkingLatln("사당노외주차장", 126.98376, 37.47530));
        add(new ParkingLatln("종묘주차장", 126.99503, 37.57150));
        add(new ParkingLatln("개화역주차장", 126.79821, 37.57789));
        add(new ParkingLatln("마포유수지주차장", 126.94267, 37.53889));
        add(new ParkingLatln("서울역관광버스주차장", 126.97026, 37.55783));
        add(new ParkingLatln("신천유수지", 127.10286, 37.52237));
        add(new ParkingLatln("천호역주차장", 127.12511, 37.53849));
        add(new ParkingLatln("용산주차빌딩", 126.96519, 37.53436));
        add(new ParkingLatln("남부여성주차장", 126.90605, 37.46326));
        add(new ParkingLatln("세종로주차장", 126.97595, 37.57340));
        add(new ParkingLatln("동대문주차장", 127.01365, 37.55789));
        add(new ParkingLatln("신대방역주차장", 127.04069, 37.56732));
    }};
    public static ArrayList<String> marketInfo = new ArrayList<String>();


    synchronized public static void increaseSyncNum() {
        syncNum++;
    }
    synchronized public static void increaseSyncFinish() {
        syncFinish++;
    }

}
