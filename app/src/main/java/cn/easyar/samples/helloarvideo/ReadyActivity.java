package cn.easyar.samples.helloarvideo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cn.easyar.adapter.ReadyRecyclerAdapter;
import cn.easyar.photogallery.photo.widget.PickConfig;


/**
 *
 * @author lipengjun 2017-04-26
 */
public class ReadyActivity extends ActionBarActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    ReadyRecyclerAdapter readyRecyclerAdapter;
    Button buttonAdd,buttonStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonStart = (Button) findViewById(R.id.button_start);

        buttonAdd.setOnClickListener(this);
        buttonStart.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
    int id = v.getId();
        switch (id){
            case R.id.button_add:
                new  PickConfig.Builder(ReadyActivity.this)
                        .actionBarcolor(Color.parseColor("#E91E63"))
                        .statusBarcolor(Color.parseColor("#D81B60"))
                        .maxPickSize(9)
                        .pickMode(PickConfig.MODE_MULTIP_PICK).build();
                break;
            case R.id.button_start:
                Intent intent = new Intent();
                intent.setClass(this,MainActivity.class);
                //TODO 传递参数 需要加载的 JSON 数据

                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE){
            //在data中返回 选择的图片列表
            ArrayList<String> paths=data.getStringArrayListExtra("data");
            //TODO recyclerview 设置adapter
            readyRecyclerAdapter = new ReadyRecyclerAdapter(this,paths);
            recyclerView.setAdapter(readyRecyclerAdapter);
            Log.e("lists", "onActivityResult: "+paths.toString());
        }

        // 选取图片的返回值
        if (requestCode == 110) {
            //
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                 String imgNo = cursor.getString(0); // 路径
//                String v_path = cursor.getString(1);
//                String v_size = cursor.getString(2);
//                String v_name = cursor.getString(3);
                Log.e("lujing", "onActivityResult: "+imgNo);
//                Log.e("lujing", "onActivityResult: "+v_path);
//                Log.e("lujing", "onActivityResult: "+v_size);
//                Log.e("lujing", "onActivityResult: "+v_name);
                readyRecyclerAdapter.setVideoUrl(imgNo);
            }
        }
    }

}
