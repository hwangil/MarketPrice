package com.example.gilsoo.marketprice.data;

/**
 * Created by gilsoo on 2016-10-29.
 */
public class ParkingLoc {
    private String parkName = null;      // 파싱할 주차장 이름
    private String parkAddress = null;       // 파싱할 주차장 주소
    private String max_parkingCnt = null;       // 파싱할 주차장 총 공간( 차 대수)
    private String parkingCnt = null;
    private double lat;
    private double lng;

    public ParkingLoc(String parkName, String parkAddrss, String max_parkingCnt, String parkingCnt){
        this.parkName = parkName;
        this.parkAddress = parkAddrss;
        this.max_parkingCnt = max_parkingCnt;
        this.parkingCnt = parkingCnt;
        lat = -1;
        lng = -1;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getParkAddress() {
        return parkAddress;
    }

    public void setParkAddress(String parkAddress) {
        this.parkAddress = parkAddress;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getMax_parkingCnt() {
        return max_parkingCnt;
    }

    public void setMax_parkingCnt(String max_parkingCnt) {
        this.max_parkingCnt = max_parkingCnt;
    }

    public String getParkingCnt() {
        return parkingCnt;
    }

    public void setParkingCnt(String parkingCnt) {
        this.parkingCnt = parkingCnt;
    }
}
