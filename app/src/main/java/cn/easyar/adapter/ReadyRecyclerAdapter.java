package cn.easyar.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.easyar.samples.helloarvideo.R;

/**
 * Created by lipengjun on 2017/4/26.
 */

public class ReadyRecyclerAdapter extends RecyclerView.Adapter<ReadyRecyclerAdapter.ItemHolder> {
    Activity activity;
    public ReadyRecyclerAdapter(Activity activity){
        this.activity = activity;
    }
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_ready, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public class ItemHolder extends RecyclerView.ViewHolder{

        public ItemHolder(View itemView) {
            super(itemView);
        }

    }
}
