package cn.easyar.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.easyar.bean.JsonDataBean;
import cn.easyar.db.SharedPreferencesUtil;
import cn.easyar.photogallery.photo.widget.PickConfig;
import cn.easyar.samples.helloarvideo.R;

/**
 * Created by lipengjun on 2017/4/26.
 */

public class ReadyRecyclerAdapter extends RecyclerView.Adapter<ReadyRecyclerAdapter.ItemHolder> {
    private static final String TAG = "ReadyRecyclerAdapter";
    Activity activity;
    JsonDataBean jsonDataBean;
    SharedPreferencesUtil spUtil;
    public ReadyRecyclerAdapter(Activity activity){
        this.activity = activity;
        spUtil = SharedPreferencesUtil.getInstance(activity);
        String localData = spUtil.getLocalData();//读取本地数据
        if(localData != null && localData.length()>0) {
            jsonDataBean = JsonDataBean.createObject(localData);
        }
    }
    public JsonDataBean getData(){
        return jsonDataBean;
    }
    public  JsonDataBean addData(JsonDataBean jsonDataBean){
        if(this.jsonDataBean == null && jsonDataBean != null){
            this.jsonDataBean = jsonDataBean;
        }else if(this.jsonDataBean != null && jsonDataBean != null) {
            if(this.jsonDataBean.images == null){
                this.jsonDataBean.images = jsonDataBean.images;
            }else {
                this.jsonDataBean.images.addAll(jsonDataBean.images);
            }
            jsonDataBean = this.jsonDataBean;
        }
        notifyDataSetChanged();
        spUtil.putLocalData(this.jsonDataBean.toJSON().toString());
        return  this.jsonDataBean;
    }
//    public  JsonDataBean setData(JsonDataBean jsonDataBean){
//
//        if(jsonDataBean == null || jsonDataBean.images == null){
//            Log.e(TAG,"adapter数据targetList 为空");
//            return jsonDataBean;
//        }
//        Log.e(TAG,"设置adapter数据");
//        this.jsonDataBean = jsonDataBean;
//        notifyDataSetChanged();
//        return  jsonDataBean;
//    }
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_ready, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder itemHolder, final int position) {
//        itemHolder = holder;
        JsonDataBean.Target target = jsonDataBean.images.get(position);
        itemHolder.setBindData(target);
        // 显示缩略图
        displayImage(target.image, itemHolder.imageView);
        itemHolder.addBtn.setText(target.image);
        itemHolder.videoBtn.setText("对应视频地址.");
        if(target.uid != null && target.uid.length()>0) {
            itemHolder.videoBtn.setText(target.uid);
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
        if (jsonDataBean==null || jsonDataBean.images == null){
            return 0;
        }
        return jsonDataBean.images.size();
    }
    /**
     * 记录当前跳出去的对象
     */
    private JsonDataBean.Target curTarget;

    private void selectVideo(JsonDataBean.Target target){
        //去系统或者第三方图库相册需要在本地记录位置和数据；
        curTarget = target;
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent,110);
    }


    public void setVideoUrl(String url){
        curTarget.uid = url;
        Log.e(TAG,"jsonDataBean:"+jsonDataBean.toString());
        spUtil.putLocalData(jsonDataBean.toJSON().toString());
        notifyDataSetChanged();
    }

    public void selectImage(JsonDataBean.Target target){
        curTarget = target;
        new PickConfig.Builder(activity)
                .actionBarcolor(Color.parseColor("#E91E63"))
                .statusBarcolor(Color.parseColor("#D81B60"))
                .maxPickSize(1)
                .isSinglePick(true)
                .pickMode(PickConfig.MODE_SINGLE_PICK).build();
    }

    public void setImageUrl(String url){
        curTarget.image = url;
        Log.e(TAG,"jsonDataBean:"+jsonDataBean.toString());
        spUtil.putLocalData(jsonDataBean.toJSON().toString());
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
        public ImageButton imageButtonDelete;
        JsonDataBean.Target bindData;
        public ItemHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            addBtn = (Button) itemView.findViewById(R.id.button_add);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 更换图片地址
                    selectImage(bindData);
//                    changeImage(bindData);
//                    addBtn.setText(bindData.image);
                }
            });
            videoBtn = (Button) itemView.findViewById(R.id.button_start);
            videoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 更换视频地址
                    selectVideo(bindData);
//                    videoBtn.setText(bindData.uid);
                }
            });
            imageButtonDelete = (ImageButton) itemView.findViewById(R.id.image_button_delete);
            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 删除当前一组数据
                    jsonDataBean.images.remove(bindData);
                    // 需要从数据存储中移除；
                    spUtil.putLocalData(jsonDataBean.toJSON().toString());
                    notifyDataSetChanged();
                }
            });
        }

        public void setBindData(JsonDataBean.Target bindData) {
            this.bindData = bindData;
        }
    }
}
