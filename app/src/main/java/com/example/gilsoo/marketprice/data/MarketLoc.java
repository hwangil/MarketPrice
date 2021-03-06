package com.example.gilsoo.marketprice.data;

/**
 * Created by gilsoo on 2016-10-24.
 */
public class  MarketLoc {
    private double lat;
    private double lng;
    private String name;
    private ParkingLoc nearParking;
    public MarketLoc(double lat, double lng, String name){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.nearParking = null;
    }
    public MarketLoc(double lat, double lng, String name, ParkingLoc nearParking){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.nearParking = nearParking;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkingLoc getNearParking() {
        return nearParking;
    }

    public void setNearParking(ParkingLoc nearParking) {
        this.nearParking = nearParking;
    }
}
