package com.example.httpdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private List<Video> mVideoList;

    VideoAdapter(Context context, List<Video> videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_test_my_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(itemView);
        //创建视频播放控制器，主要只要创建一次就可以呢
        MyVideoController controller = new MyVideoController(mContext);
        holder.setController(controller);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        holder.bindData(video);
    }

    @Override
    public int getItemCount() {
        return mVideoList==null ? 0 : mVideoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        MyVideoController mController;
        VideoPlayer mVideoPlayer;

        VideoViewHolder(View itemView) {
            super(itemView);
            mVideoPlayer = (VideoPlayer) itemView.findViewById(R.id.nice_video_player);
            // 将列表中的每个视频设置为默认16:9的比例
            ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
            // 宽度为屏幕宽度
            params.width = itemView.getResources().getDisplayMetrics().widthPixels;
            // 高度为宽度的9/16
            params.height = (int) (params.width * 9f / 16f);
            mVideoPlayer.setLayoutParams(params);
            itemView.setTag(R.id.nice_video_player,mVideoList);
        }

        /**
         * 设置视频控制器参数
         * @param controller            控制器对象
         */
        void setController(MyVideoController controller) {
            mController = controller;
            mVideoPlayer.setController(mController);
            mController.setOnPlayComplieteListener(new MyVideoController.OnPlayComplieteListener() {
                @Override
                public void onCompliete() {
                    Log.i("TAG","setOnPlayComplieteListener");
                }
            });
        }

        void bindData(Video video) {
            mController.setTitle(video.getTitle());
            mController.setLength(video.getLength());
            Glide.with(itemView.getContext())
                    .load(video.getImageUrl())
                    .placeholder(R.drawable.image_default)
                    .crossFade()
                    .into(mController.imageView());
            mVideoPlayer.setUp(video.getVideoUrl(), null);
            mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
            mVideoPlayer.continueFromLastPosition(true);
        }
    }
}