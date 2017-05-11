package com.example.gilsoo.marketprice;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gilsoo.marketprice.data.CommonData;
import com.example.gilsoo.marketprice.data.CommonFunc;
import com.example.gilsoo.marketprice.data.SelectMarketInfo;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-10-25.
 */
public class ProductListAdapter extends BaseAdapter {
    Context context;
    ArrayList<SelectMarketInfo> selectMarket;
    ArrayList<ArrayList<ProductItem>> productList;
    RecyclerView productRecyclerVIew;
    ProductRecyclerAdapter productRecyclerAdapter;

    final ArrayList<RecyclerView> list = new ArrayList<RecyclerView>();

//    long posX = 0;
    int posY = 0;

    ProductListAdapter(Context context, ArrayList<ArrayList<ProductItem>> productList, ArrayList<SelectMarketInfo> selectMarket) {
        this.context = context;
        this.productList = productList;
        this.selectMarket = selectMarket;
    }

    @Override
    public int getCount() {
        return selectMarket.size();
    }

    @Override
    public Object getItem(int position) {
        return selectMarket.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_listview_row, null);
        }
        TextView marketName = (TextView) view.findViewById(R.id.marketName);
        TextView lowestCount = (TextView) view.findViewById(R.id.lowestCount);
        marketName.setText(selectMarket.get(position).getName());
        int count = 0;
        for(int i=0; i<productList.get(position).size(); i++){
            if(productList.get(position).get(i).isLowestFlag())
                count++;
        }
        lowestCount.setText("최저상품 : " + String.valueOf(count) + "개");

        productRecyclerVIew = (RecyclerView) view.findViewById(R.id.productRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        productRecyclerVIew.setLayoutManager(linearLayoutManager);
        productRecyclerAdapter = new ProductRecyclerAdapter(productList.get(position));
        productRecyclerVIew.setAdapter(productRecyclerAdapter);
        productRecyclerAdapter.notifyDataSetChanged();
        if(list.size() < selectMarket.size())
            list.add(productRecyclerVIew);

        list.get(0).addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
//                    posX += posX + dx;
//                    posY += posY + dy;
//                    Log.d("gilsoo", "dX = " + dx + ", dY = " + dy);
                }
            }
        });

        final ToggleButton favoriteToggleBtn = (ToggleButton)view.findViewById(R.id.favoriteToggleBtn);

        favoriteToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoriteToggleBtn.isChecked()){
                    CommonFunc.savePreferences(context, selectMarket.get(position).getName());
                    Toast toast = Toast.makeText(context, "'즐겨찾기'에 추가되었습니다.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(23);
                    toast.show();
//                    CommonFunc.getPreferences(context, selectMarket.get(position).getName());
                }else{
                    CommonFunc.removePreferences(context, selectMarket.get(position).getName());
                    Toast toast = Toast.makeText(context, "'즐겨찾기'에서 삭제되었습니다.", Toast.LENGTH_SHORT);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(23);
                    toast.show();
//                    CommonFunc.getPreferences(context, selectMarket.get(position).getName());
                }
            }
        });
        if(CommonFunc.getPreferences(context, selectMarket.get(position).getName()))
            favoriteToggleBtn.setChecked(true);
        else
            favoriteToggleBtn.setChecked(false);



        return view;
    }

    // 리스뷰 안에 리사이클러뷰 (가로방향)
    class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {
        ArrayList<ProductItem> list;

        public ProductRecyclerAdapter(ArrayList<ProductItem> list) {
            this.list = list;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recyclerview_row, parent, false);
            ProductViewHolder viewHolder = new ProductViewHolder(layoutView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            holder.a_name.setText(list.get(position).getA_name());
            holder.a_unit.setText(list.get(position).getA_unit());
            holder.a_image.setImageResource(CommonData.PRODUCT_IMAGE[position]);
            if(list.get(position).getA_price().equals("999999")){
                holder.a_price.setText(" ");
            }
            else {
                holder.a_price.setText(list.get(position).getA_price() +"원");
            }

            if(position>=0 && position <2){
                if(list.get(position).isLowestFlag())
                    holder.a_layout.setBackgroundResource(R.drawable.border_yellow);
                else
                    holder.a_layout.setBackgroundColor(Color.rgb(229, 227, 92));
            }
            else if(position >=2 && position <8){
                if(list.get(position).isLowestFlag())
                    holder.a_layout.setBackgroundResource(R.drawable.border_green);
                else
                    holder.a_layout.setBackgroundColor(Color.rgb(188, 229, 92));
            }else if(position>=8 && position<12){
                if(list.get(position).isLowestFlag())
                    holder.a_layout.setBackgroundResource(R.drawable.border_orange);
                else
                    holder.a_layout.setBackgroundColor(Color.rgb(242, 182, 138));
            }else{
                if(list.get(position).isLowestFlag())
                    holder.a_layout.setBackgroundResource(R.drawable.border_blue);
                else
                    holder.a_layout.setBackgroundColor(Color.rgb(92, 209, 229));
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView a_name;
            TextView a_unit;
            TextView a_price;
            LinearLayout a_layout;
            ImageView a_image;

            public ProductViewHolder(View itemView) {
                super(itemView);
                a_name = (TextView) itemView.findViewById(R.id.a_name);
                a_unit = (TextView) itemView.findViewById(R.id.a_unit);
                a_price = (TextView) itemView.findViewById(R.id.a_price);
                a_layout = (LinearLayout)itemView.findViewById(R.id.productRecyclerLayout);
                a_image = (ImageView)itemView.findViewById(R.id.a_image);
            }
        }
    }

    public void nextScrollEvent(){
        int posX = list.get(0).computeHorizontalScrollOffset();
        for(int i=0; i<list.size(); i++){
            int dx = posX - list.get(i).computeHorizontalScrollOffset();        // 첫번째 스크롤과 차이 구함
            list.get(i).smoothScrollBy(dx+521, 0);
        }
    }
    public void prevScrollEvent(){
        int posX = list.get(0).computeHorizontalScrollOffset();
        for(int i=0; i<list.size(); i++){
            int dx = posX - list.get(i).computeHorizontalScrollOffset();        // 첫번째 스크롤과 차이 구함
            list.get(i).smoothScrollBy(dx-521, 0);
        }
    }
    public void syncScrollEvent() {         // 비교모드로 하나의 스크롤을 넘기면 다른 곳도 스크롤 됨  ** 안씀!

        for(int i=0; i<list.size(); i++) {
//            list.get(i).scrollToPosition(0);
            final int index = i;


            list.get(i).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
//                        Log.d("gilsoo", "list size : " + list.size());
//                        Log.d("gilsoo", "selectMarket size : " + selectMarket.size());
                        for (int j = 0; j < list.size(); j++) {
                            if (index == j)
                                continue;
                            list.get(j).scrollBy(dx, dy);
                        }
                    }
                }
            });
            list.get(i).scrollBy(30, 0);

        }
    }
    public void unsyncScrollEvent(){
        for(int i=0; i<list.size(); i++)
            list.get(i).clearOnScrollListeners();
    }
}
