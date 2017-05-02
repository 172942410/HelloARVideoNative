
package cn.easyar.samples.helloarvideo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends ActionBarActivity {
    // /storage/emulated/0/Download/福田水围小学生绘画培训班【青瑞小孩画画培训】_超清.flv
    // /storage/emulated/0/Download/欢乐好声音BD1280高清国英双语官方中英双字.mkv

    private static final String TAG = "VideoViewActivity";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_video_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//隐藏状态栏
        Intent intent = getIntent();
        String urlStr = intent.getStringExtra("urlStr");
//        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Test_Movie.m4v");
        Log.e(TAG,"播放的视频地址为："+urlStr);
        if(urlStr == null || urlStr.length() == 0){
            Toast.makeText(this, "播放地址为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse(urlStr);
        VideoView videoView = (VideoView)this.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    public void actionBack(View v){
        onBackPressed();
    }
}