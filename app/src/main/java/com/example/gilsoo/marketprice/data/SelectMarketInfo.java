package com.example.gilsoo.marketprice.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilsoo on 2016-11-24.
 */
public class SelectMarketInfo implements Parcelable {
    private String name;
    private double lat, lng;

    public SelectMarketInfo(String name, double lat, double lng){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    protected SelectMarketInfo(Parcel in) {
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<SelectMarketInfo> CREATOR = new Creator<SelectMarketInfo>() {
        @Override
        public SelectMarketInfo createFromParcel(Parcel in) {
            return new SelectMarketInfo(in);
        }

        @Override
        public SelectMarketInfo[] newArray(int size) {
            return new SelectMarketInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
