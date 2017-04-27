package cn.easyar.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cn.easyar.bean.JsonDataBean;
import cn.easyar.samples.helloarvideo.R;

/**
 * Created by lipengjun on 2017/4/26.
 */

public class ReadyRecyclerAdapter extends RecyclerView.Adapter<ReadyRecyclerAdapter.ItemHolder> {
    private static final String TAG = "ReadyRecyclerAdapter";
    Activity activity;
//    private ArrayList<String> photoList;
    private ItemHolder itemHolder;
    ArrayList<JsonDataBean.Target> targetList;

    public ReadyRecyclerAdapter(Activity activity){
        this.activity = activity;
        targetList = new ArrayList<>();
    }
//    public ReadyRecyclerAdapter(Activity activity, ArrayList<String> photoList){
//        this.activity = activity;
//        this.photoList = photoList;
//    }
    public void setData(ArrayList<JsonDataBean.Target> targetList){

        if(targetList == null || targetList.size() == 0){
            Log.e(TAG,"adapter数据targetList 为空");
            return;
        }
        Log.e(TAG,"设置adapter数据");
        this.targetList.clear();
        this.targetList.addAll(targetList);
        notifyDataSetChanged();
    }
    public void addData(ArrayList<JsonDataBean.Target> targetList){
        if(targetList == null || targetList.size() == 0){
            return;
        }
        if( this.targetList != null){
            this.targetList.addAll(targetList);
            notifyDataSetChanged();
        }
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
        displayImage(targetList.get(position).image, itemHolder.imageView);
        itemHolder.addBtn.setText(targetList.get(position).image);
        if(targetList.get(position).uid != null && targetList.get(position).uid.length()>0) {
            itemHolder.videoBtn.setText(targetList.get(position).uid);
        }
//        itemHolder.videoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectVideo(v,position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (targetList==null || targetList.size()==0){
            return 0;
        }
        return targetList.size();
    }

    private void selectVideo(View view){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent,110);
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
            videoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectVideo(v);
                }
            });
        }

    }
}
