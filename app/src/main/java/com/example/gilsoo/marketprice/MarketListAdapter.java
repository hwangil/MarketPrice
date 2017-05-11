package com.example.gilsoo.marketprice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gilsoo.marketprice.data.SelectMarketInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-10-25.
 */
public class MarketListAdapter extends BaseAdapter {
    ArrayList<SelectMarketInfo> list;
    Context context;
    ListView thatList;

    public MarketListAdapter(Context context, ArrayList<SelectMarketInfo> objects, ListView thatList) {
        list = objects;
        this.context = context;
        this.thatList = thatList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.select_market_row, null);
        }
        TextView selectMarketText = (TextView)view.findViewById(R.id.selectMarketText);
        final String name = selectMarketText.getText().toString();
        selectMarketText.setOnClickListener(new View.OnClickListener() {            // 리스트뷰의 텍스트 누르면 해당 위치로 이동
            @Override
            public void onClick(View v) {
                 MainActivity.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(position).getLat(), list.get(position).getLng()), 14));
            }
        });
        Button delSelectMarket = (Button)view.findViewById(R.id.delSelectMarket);
        delSelectMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delSelectMarketEvent(v, position);
            }
        });

        selectMarketText.setText(list.get(position).getName());
        return view;
    }
    public void delSelectMarketEvent(View v, int position){
        list.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        thatList.setSelection(getCount()-1);
    }
}
