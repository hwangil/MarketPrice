package com.example.gilsoo.marketprice.data;

/**
 * Created by gilsoo on 2016-11-26.
 */
public class ParkingLatln {
    private String name;
    private double lat;
    private double lng;

    public ParkingLatln(String name, double lng, double lat){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
