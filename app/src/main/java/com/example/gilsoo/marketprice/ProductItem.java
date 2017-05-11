package com.example.gilsoo.marketprice;

/**
 * Created by gilsoo on 2016-10-25.
 */
public class ProductItem {
    private String a_seq;
    private String a_name;
    private String a_unit;
    private String a_price;
    private boolean lowestFlag;

    public ProductItem(){
        this.a_name = "제품이 없습니다.";
        this.lowestFlag = false;
        this.a_price = "999999";
    }
    public ProductItem(String a_name){
        this.a_name = a_name;
        a_unit = null;
        a_price = null;
        lowestFlag = false;
    }
    public ProductItem(String a_seq, String a_name, String a_unit, String a_price){
        this.a_seq = a_seq;
        this.a_name = a_name;
        this.a_unit = a_unit;
        this.a_price = a_price;
        lowestFlag = false;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_unit() {
        return a_unit;
    }

    public void setA_unit(String a_unit) {
        this.a_unit = a_unit;
    }

    public String getA_price() {
        return a_price;
    }

    public void setA_price(String a_price) {
        this.a_price = a_price;
    }

    public String getA_seq() {
        return a_seq;
    }

    public void setA_seq(String a_seq) {
        this.a_seq = a_seq;
    }


    public boolean isLowestFlag() {
        return lowestFlag;
    }

    public void setLowestFlag(boolean lowestFlag) {
        this.lowestFlag = lowestFlag;
    }

}
