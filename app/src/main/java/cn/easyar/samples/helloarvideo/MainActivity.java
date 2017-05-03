/**
* Copyright (c) 2015-2016 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
* EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
* and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
*/

package cn.easyar.samples.helloarvideo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import cn.easyar.engine.EasyAR;


public class MainActivity extends ActionBarActivity{

    private static final String TAG = "MainActivity";
    /*
        * Steps to create the key for this sample:
        *  1. login www.easyar.com
        *  2. create app with
        *      Name: HelloARVideo
        *      Package Name: cn.easyar.samples.helloarvideo
        *  3. find the created item in the list and show key
        *  4. set key string bellow
        */
    static String key = "MEPegtdGLtVQLQRKuOzGeFaI7tUVnrB7HuREGMzkf6GuVg4nAyRQzSr7IyCToN7sLlTu6uuG9Nu93OBe9Enluk86dK8BjYf8Ie0A4426e888309483da5ac2312c1a271144rh3hPcHKRSfhk3yOFQ6XxHvB5rKpUSaLiM8DjQEU5v8AmVlAqZ7ZcU8jQP80ass4H4Ga";

    static {
        System.loadLibrary("EasyAR");
        System.loadLibrary("HelloARVideoNative");
    }

    public static native void nativeInitGL();
    public static native void nativeResizeGL(int w, int h);
    public static native void nativeRender();
    private native boolean nativeInit();
    private native void nativeDestory();
    private native void nativeRotationChange(boolean portrait);
    private native boolean nativeInitJson(String jsonStr);//为了替代无参数的此方法存在；可以从java层传递数据参数到C++里面执行；
    private native String nativeGetVideoUrl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent =  getIntent();
        String jsonStr = intent.getStringExtra("data");//targets.json;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//隐藏状态栏
        EasyAR.initialize(this, key);
        if (jsonStr != null && jsonStr.length() > 13) {//为空时最长13个字节长度
            //初始化AR的 target s 信息
            nativeInitJson(jsonStr);
        } else {
            nativeInit();
        }

        GLView glView = new GLView(this);
        glView.setRenderer(new Renderer());
        glView.setZOrderMediaOverlay(true);

        ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nativeDestory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EasyAR.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EasyAR.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //视频全屏播放设置
    public void actionAllScreenPlay(View v){
        Log.e(TAG,"视频全屏播放 按钮点击了");
        Intent intent = new Intent();
        intent.setClass(this,VideoViewActivity.class);
        String strUrl = nativeGetVideoUrl();
        Log.e(TAG,"获取C++代码中的播放地址："+strUrl);
        if(strUrl == null || strUrl.length() == 0){
            Toast.makeText(this, "视频地址为空", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"视频地址从底层代码获取失败");
            return;
        }
        intent.putExtra("urlStr",strUrl);
//        intent.putExtra("urlStr","/storage/emulated/0/Download/福田水围小学生绘画培训班【青瑞小孩画画培训】_超清.flv");
//        intent.putExtra("urlStr","/storage/emulated/0/Download/欢乐好声音BD1280高清国英双语官方中英双字.mkv");
        startActivity(intent);
    }


}
