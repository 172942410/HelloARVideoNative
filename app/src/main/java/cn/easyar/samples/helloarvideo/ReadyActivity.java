package cn.easyar.samples.helloarvideo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import cn.easyar.bean.JsonDataBean;
import cn.easyar.db.SharedPreferencesUtil;
import cn.easyar.photogallery.photo.widget.PickConfig;
import cn.easyar.utils.UriUtils;


/**
 *
 * @author lipengjun 2017-04-26
 */
public class ReadyActivity extends ActionBarActivity implements View.OnClickListener{

    private static final String TAG = "ReadyActivity";
    RecyclerView recyclerView;
    ReadyRecyclerAdapter readyRecyclerAdapter;
    Button buttonAdd,buttonStart;
    SharedPreferencesUtil spUtil;
    JsonDataBean jsonDataBean;
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
        readyRecyclerAdapter = new ReadyRecyclerAdapter(this);
        recyclerView.setAdapter(readyRecyclerAdapter);
        spUtil = SharedPreferencesUtil.getInstance(this);
        String localData = spUtil.getLocalData();//读取本地数据
        if(localData != null && localData.length()>0) {
            jsonDataBean = JsonDataBean.createObject(localData);
        }
        if(jsonDataBean != null) {
            readyRecyclerAdapter.setData(jsonDataBean.images);
        }
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
        switch (id) {
            case R.id.button_add:
                new PickConfig.Builder(ReadyActivity.this)
                        .actionBarcolor(Color.parseColor("#E91E63"))
                        .statusBarcolor(Color.parseColor("#D81B60"))
                        .maxPickSize(9)
                        .pickMode(PickConfig.MODE_MULTIP_PICK).build();
                break;
            case R.id.button_start:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                // 传递参数 需要加载的 JSON 数据
                if (jsonDataBean != null) {
                    String jsonStr = jsonDataBean.toJSON().toString();
                    Log.e(TAG, "jsonStr:" + jsonStr);
                    intent.putExtra("data", jsonStr);
                }
                startActivity(intent);
                break;

        }
    }

    /**
     * 第一次target list 的数据封装
     */
    private JsonDataBean list2Bean(ArrayList<String> paths) {
        JsonDataBean jsonDataBean = new JsonDataBean();
        jsonDataBean.images = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            JsonDataBean.Target target = new JsonDataBean.Target();
            target.image = paths.get(i);
            jsonDataBean.images.add(target);
        }
        return jsonDataBean;
    }

//    private JsonDataBean data2Json(ArrayList<String> paths){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonChildObject;
//        try {
//            for(int i = 0;i<paths.size();i++){
//                jsonChildObject = new JSONObject();
//                jsonChildObject.put("image",paths.get(i));
//
//                jsonArray.put(jsonChildObject);
//            }
//            jsonObject.put("images",jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE){
            //在data中返回 选择的图片列表
            ArrayList<String> paths=data.getStringArrayListExtra("data");
            //TODO recyclerview 设置adapter
//            readyRecyclerAdapter = new ReadyRecyclerAdapter(this,paths);
//            recyclerView.setAdapter(readyRecyclerAdapter);
            jsonDataBean = list2Bean(paths);
            readyRecyclerAdapter.setData(jsonDataBean.images);
            Log.e("lists", "onActivityResult: "+paths.toString());
        }

        // 选取视频的返回值
        if (requestCode == 110) {
            //
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String pathStr;
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
//                    imgNo = cursor.getString(2);//
                    pathStr =  UriUtils.getPath(this,uri);
                    Log.e("路径", "pathStr onActivityResult: "+pathStr);
                    jsonDataBean.images.get(0).uid = pathStr;//TODO 先默认只是第一个；之后要对应住每一个item
                }else {
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    pathStr = cursor.getString(0); // 路径

                    String v_path = cursor.getString(1);
                    String v_size = cursor.getString(2);
                    String v_name = cursor.getString(3);
                    String v_4 = cursor.getString(4);
                    String v_5 = cursor.getString(5);
//                String v_6 = cursor.getString(6);
//                String v_7 = cursor.getString(7);
                    Log.e("lujing", "pathStr onActivityResult: " + pathStr);
                    Log.e("lujing", "v_path onActivityResult: " + v_path);
                    Log.e("lujing", "v_size onActivityResult: " + v_size);
                    Log.e("lujing", "v_name onActivityResult: " + v_name);
                    Log.e("lujing", "v_4 onActivityResult: " + v_4);
                    Log.e("lujing", "v_5 onActivityResult: " + v_5);
//                Log.e("lujing", "v_6 onActivityResult: "+v_6);
//                Log.e("lujing", "v_7 onActivityResult: "+v_7);
                }
                readyRecyclerAdapter.setData(jsonDataBean.images);
//                readyRecyclerAdapter.setVideoUrl(pathStr);
            }
        }
    }

}
