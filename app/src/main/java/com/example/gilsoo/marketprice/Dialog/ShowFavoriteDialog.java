package com.example.gilsoo.marketprice.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilsoo.marketprice.MarketListAdapter;
import com.example.gilsoo.marketprice.R;
import com.example.gilsoo.marketprice.data.CommonData;
import com.example.gilsoo.marketprice.data.SelectMarketInfo;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-11-20.
 */
public class ShowFavoriteDialog extends Dialog {
    ShowFavoriteDialog showaFavoriteDialog;
    ListView showFavoriteList;
    ShowFavoriteListAdapter adapter;
    ArrayList<String> favoriteMarket;
    int selectMarketPosition = -1;


    public ShowFavoriteDialog(Context context, final ArrayList<String> favoriteMarket, final ArrayList<SelectMarketInfo> selectMarket
            ,MarketListAdapter selectMarketAdapter ) {
        super(context);
        final Context finContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_favorite_dialog);
        showaFavoriteDialog = this;
        this.favoriteMarket = favoriteMarket;
        final ArrayList<SelectMarketInfo> finSelectMarket = selectMarket;
        final MarketListAdapter finSelectMarketAdapter = selectMarketAdapter;

        showFavoriteList = (ListView) findViewById(R.id.showFavoriteList);
        adapter = new ShowFavoriteListAdapter(context, favoriteMarket);

        showFavoriteList.setAdapter(adapter);

        showFavoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMarketPosition = position;
            }
        });
        Button dialog_cancelBtn = (Button) findViewById(R.id.dialog_cancelBtn);
        dialog_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button addMarket = (Button) findViewById(R.id.addMarket);
        addMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectMarketPosition == -1){                     // 아무것도 선택을 하지 않았을 시
                    Toast toast = Toast.makeText(finContext, "'시장/마켓'을 선택해주세요.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(23);
                    toast.show();
                    return;
                }
                for(int i=0; i<selectMarket.size(); i++){                   // 중복으로 추가시
                    if(selectMarket.get(i).getName().equals(favoriteMarket.get(selectMarketPosition))){
                        Toast toast = Toast.makeText(finContext, "이미 선택되었습니다.", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(23);
                        toast.show();
                        return;
                    }
                }
                int i=0;
                for(i=0; i<CommonData.marketList.size(); i++){
                    if(CommonData.marketList.get(i).getName().equals(favoriteMarket.get(selectMarketPosition))){
                        finSelectMarket.add(new SelectMarketInfo(CommonData.marketList.get(i).getName(), CommonData.marketList.get(i).getLat(), CommonData.marketList.get(i).getLng()));
                        finSelectMarketAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                if(i>=CommonData.marketList.size()) {
                    for (i = 0; i < CommonData.martList.size(); i++) {
                        if (CommonData.martList.get(i).getName().equals(favoriteMarket.get(selectMarketPosition))) {
                            finSelectMarket.add(new SelectMarketInfo(CommonData.martList.get(i).getName(), CommonData.martList.get(i).getLat(), CommonData.martList.get(i).getLng()));
                            finSelectMarketAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
                selectMarketPosition = -1;
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        selectMarketPosition = -1;
        super.onBackPressed();
    }
}

class ShowFavoriteListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> favoriteMarket;

    public ShowFavoriteListAdapter(Context context, ArrayList<String> favoriteMarket) {
        this.context = context;
        this.favoriteMarket = favoriteMarket;
    }

    @Override
    public int getCount() {
        return favoriteMarket.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteMarket.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.find_road_row, null);
        }
        TextView textVIew = (TextView) view.findViewById(R.id.findRoadText);
        textVIew.setText(favoriteMarket.get(position));

        return view;
    }
}
