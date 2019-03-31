package com.example.httpdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.AbsVideoPlayerController;
import org.yczbj.ycvideoplayerlib.utils.VideoPlayerUtils;

public class MyVideoController extends AbsVideoPlayerController {
    private TextView textView;
    private ImageView imageView,image;
    private OnPlayComplieteListener onPlayComplieteListener;

    public void setOnPlayComplieteListener(OnPlayComplieteListener onPlayComplieteListener) {
        this.onPlayComplieteListener = onPlayComplieteListener;
    }

    public MyVideoController(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_controller,this,true);
        textView = view.findViewById(R.id.videoTimeLenght);
        imageView = view.findViewById(R.id.ivVolume);
        image = view.findViewById(R.id.image);
        initAction();
    }

    private void initAction() {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoPlayer.setVolume(0);
            }
        });
    }

    @Override
    public boolean getLock() {
        return false;
    }

    @Override
    public void setCenterPlayer(boolean isVisibility, int image) {

    }

    @Override
    public void setTopVisibility(boolean isVisibility) {

    }

    @Override
    public void setTopPadding(float top) {

    }

    @Override
    public void setTvAndAudioVisibility(boolean isVisibility1, boolean isVisibility2) {

    }

    @Override
    public void setHideTime(long time) {

    }

    @Override
    public void setLoadingType(int type) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setImage(int resId) {

    }

    @Override
    public ImageView imageView() {
        return image;
    }

    @Override
    public void setLength(long length) {
        textView.setText(VideoPlayerUtils.formatTime(length));
    }

    @Override
    public void setLength(String length) {
        textView.setText(length);
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case ConstantKeys.CurrentState.STATE_IDLE:
                break;
            //播放准备中
            case ConstantKeys.CurrentState.STATE_PREPARING:
//                startPreparing();
                image.setVisibility(GONE);
                break;
            //播放准备就绪
            case ConstantKeys.CurrentState.STATE_PREPARED:
                startUpdateProgressTimer();
                //取消缓冲时更新网络加载速度
                cancelUpdateNetSpeedTimer();
                break;
            //正在播放
            case ConstantKeys.CurrentState.STATE_PLAYING:
//                mLoading.setVisibility(View.GONE);
//                mCenterStart.setVisibility(View.GONE);
//                mRestartPause.setImageResource(org.yczbj.ycvideoplayerlib.R.drawable.ic_player_pause);
//                startDismissTopBottomTimer();
                cancelUpdateNetSpeedTimer();
                break;
            //暂停播放
            case ConstantKeys.CurrentState.STATE_PAUSED:
//                mLoading.setVisibility(View.GONE);
//                mCenterStart.setVisibility(mIsCenterPlayerVisibility?View.VISIBLE:View.GONE);
//                mRestartPause.setImageResource(org.yczbj.ycvideoplayerlib.R.drawable.ic_player_start);
//                cancelDismissTopBottomTimer();
                cancelUpdateNetSpeedTimer();
                break;
            //正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
            case ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING:
//                mLoading.setVisibility(View.VISIBLE);
//                mCenterStart.setVisibility(View.GONE);
//                mRestartPause.setImageResource(org.yczbj.ycvideoplayerlib.R.drawable.ic_player_pause);
//                mLoadText.setText("正在准备...");
//                startDismissTopBottomTimer();
                cancelUpdateNetSpeedTimer();
                break;
            //正在缓冲
            case ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED:
//                mLoading.setVisibility(View.VISIBLE);
//                mRestartPause.setImageResource(org.yczbj.ycvideoplayerlib.R.drawable.ic_player_start);
//                mLoadText.setText("正在准备...");
//                cancelDismissTopBottomTimer();
                //开启缓冲时更新网络加载速度
                startUpdateNetSpeedTimer();
                break;
            //播放错误
            case ConstantKeys.CurrentState.STATE_ERROR:
//                stateError();
                break;
            //播放完成
            case ConstantKeys.CurrentState.STATE_COMPLETED:
                if (onPlayComplieteListener != null)
                    onPlayComplieteListener.onCompliete();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlayModeChanged(int playMode) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void updateNetSpeedProgress() {

    }

    @Override
    protected void updateProgress() {
//获取当前播放的位置，毫秒
        long position = mVideoPlayer.getCurrentPosition();
        //获取办法给总时长，毫秒
//        long duration = mVideoPlayer.getDuration();
        textView.setText(VideoPlayerUtils.formatTime(position));
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {

    }

    @Override
    protected void hideChangePosition() {

    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {

    }

    @Override
    protected void hideChangeVolume() {

    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {

    }

    @Override
    protected void hideChangeBrightness() {

    }

    public interface OnPlayComplieteListener{
        void onCompliete();
    }
}
