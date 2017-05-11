package com.example.gilsoo.marketprice.data;

/**
 * Created by gilsoo on 2016-10-24.
 */
public class MarketPrice {
    private String a_name;     // 물품이름
    private String a_unit;       // 물품 단위
    private String a_price;      // 물품 가격
    private String m_name;    // 시장이름

    public MarketPrice( String m_name, String a_name, String a_unit, String a_price){
        this.a_name = a_name;
        this.a_unit = a_unit;
        this.a_price = a_price;
        this.m_name = m_name;
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

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }
}
