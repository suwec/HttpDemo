package com.example.httpdemo;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static String[] videoList =
            {
                    "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4",
                    "http://jzvd.nathen.cn/63f3f73712544394be981d9e4f56b612/69c5767bb9e54156b5b60a1b6edeb3b5-5287d2089db37e62345123a1be272f8b.mp4",
                    "http://jzvd.nathen.cn/b201be3093814908bf987320361c5a73/2f6d913ea25941ffa78cc53a59025383-5287d2089db37e62345123a1be272f8b.mp4",
                    "http://jzvd.nathen.cn/d2438fd1c37c4618a704513ad38d68c5/68626a9d53ca421c896ac8010f172b68-5287d2089db37e62345123a1be272f8b.mp4",
                    "http://jzvd.nathen.cn/25a8d119cfa94b49a7a4117257d8ebd7/f733e65a22394abeab963908f3c336db-5287d2089db37e62345123a1be272f8b.mp4",
                    "http://jzvd.nathen.cn/7512edd1ad834d40bb5b978402274b1a/9691c7f2d7b74b5e811965350a0e5772-5287d2089db37e62345123a1be272f8b.mp4",
                    "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        OkGo.<String>get("https://www.baidu.com/?tn=02003390_30_hao_pg").headers(new HttpHeaders("demo","test")).execute(new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                Log.i("TAG",response.body());
//            }
//        });
        recyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);
        List<Video> datas = new ArrayList<>();
        for (int i = 0; i < videoList.length; i++) {
            String videoUrl = videoList[i];
            String title = "测试视频";
            long time = 60000;
            String imageUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2069336097,1305246815&fm=11&gp=0.jpg";
            Video video = new Video(title,time,imageUrl,videoUrl);
            datas.add(video);
        }
        VideoAdapter adapter = new VideoAdapter(this, datas);
        recyclerView.setAdapter(adapter);
//注意：下面这个方法不能漏掉
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                VideoPlayer videoPlayer = ((VideoAdapter.VideoViewHolder) holder).mVideoPlayer;
                if (videoPlayer == VideoPlayerManager.instance().getCurrentVideoPlayer()) {
                    VideoPlayerManager.instance().releaseVideoPlayer();
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = layout.findFirstVisibleItemPosition();

                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到要更新的item的view
                VideoAdapter.VideoViewHolder viewHolder  = (VideoAdapter.VideoViewHolder) recyclerView.findViewHolderForAdapterPosition(1);
                if (null != viewHolder){
                    //do something
                    //开始播放
                    VideoPlayer mVideoPlayer = viewHolder.mVideoPlayer;
                    if (mVideoPlayer.isIdle()) {
                        mVideoPlayer.start();
                    }else if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()) {
                        mVideoPlayer.pause();
                    } else if (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused()) {
                        mVideoPlayer.restart();
                    }
                }
            }
        },5000);
    }
}
