package cn.easyar.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cn.easyar.samples.helloarvideo.R;

/**
 * Created by lipengjun on 2017/4/26.
 */

public class ReadyRecyclerAdapter extends RecyclerView.Adapter<ReadyRecyclerAdapter.ItemHolder> {
    Activity activity;
    private ArrayList<String> photoList;

    public ReadyRecyclerAdapter(Activity activity, ArrayList<String> photoList){
        this.activity = activity;
        this.photoList = photoList;
    }
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_ready, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ItemHolder itemHolder = holder;
        // 显示缩略图
        displayImage(photoList.get(position),itemHolder.imageView);
        itemHolder.addBtn.setText(photoList.get(position));

    }

    @Override
    public int getItemCount() {
        if (photoList==null || photoList.size()==0){
            return 0;
        }
        return photoList.size();
    }

    public void displayImage(String url, ImageView view) {
        Glide.with(activity).load(url).
                centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.loadfaild)
                .into(view);
    }


    public class ItemHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public Button addBtn;

        public ItemHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            addBtn = (Button) itemView.findViewById(R.id.button_add);
        }

    }
}
