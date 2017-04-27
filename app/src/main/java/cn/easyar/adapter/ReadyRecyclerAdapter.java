package cn.easyar.adapter;

import android.app.Activity;
import android.content.Intent;
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
    private ItemHolder itemHolder;

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
    public void onBindViewHolder(ItemHolder holder, final int position) {
        itemHolder = holder;
        // 显示缩略图
        displayImage(photoList.get(position), itemHolder.imageView);
        itemHolder.addBtn.setText(photoList.get(position));
        itemHolder.videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideo(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (photoList==null || photoList.size()==0){
            return 0;
        }
        return photoList.size();
    }

    private void selectVideo(View view, int postion){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent,110);
    }

    public void setVideoUrl(String url){
        itemHolder.videoBtn.setText(url);
        notifyDataSetChanged();
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

        public Button videoBtn;
        public ImageView imageView;
        public Button addBtn;

        public ItemHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            addBtn = (Button) itemView.findViewById(R.id.button_add);
            videoBtn = (Button) itemView.findViewById(R.id.button_start);
        }

    }
}
