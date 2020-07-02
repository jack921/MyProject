package com.amico.im.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Map<Integer,View> cacheView=new HashMap<>();
    private LayoutInflater layoutInflater;

    public MainAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            return new HeaderView(layoutInflater.inflate(R.layout.layout_header_view,parent,false));
        }else{
            return new MainView(layoutInflater.inflate(R.layout.layout_main_item,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==1){
            cacheView.put(position,holder.itemView);
        }else{
            MainView mainView=(MainView)holder;
            mainView.mainItem.setText("item"+position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class MainView extends RecyclerView.ViewHolder{
        public TextView mainItem;
        public MainView(@NonNull View itemView) {
            super(itemView);
            mainItem=itemView.findViewById(R.id.main_item);
        }
    }

    class HeaderView extends RecyclerView.ViewHolder{
        public HeaderView(@NonNull View itemView) {
            super(itemView);
        }
    }

}
