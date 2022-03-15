package com.bookkeeping.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.base.BaseFragment;
import com.bookkeeping.myapplication.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @author : qiuyiyang
 * @date : 2022/1/10  16:43
 * @desc :
 */
public class VideoFragment extends BaseFragment{

    private static final String TAG = "ImageLight";
    @BindView(R.id.movie_palyer_videoview)
    VideoView moviePalyerVideoview;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.tv_previous)
    Button tvPrevious;
    @BindView(R.id.tv_before)
    Button tvBefore;
    @BindView(R.id.et_step)
    EditText etStep;
    @BindView(R.id.tv_after)
    Button tvAfter;
    @BindView(R.id.tv_next)
    Button tvNext;

    private int PICK_VIDEO_REQUEST = 0x2;
    private String file_path;
    private int frameRate=25;
    private boolean user=false;
    private Runnable onEverySecond=new Runnable() {
        @Override
        public void run() {
            if(seekBar != null) {
                seekBar.setProgress(moviePalyerVideoview.getCurrentPosition());
            }
            if(moviePalyerVideoview.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
                if(moviePalyerVideoview.getDuration() <= seekBar.getProgress()) {
                    moviePalyerVideoview.seekTo(0);
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int initLayout() {
        return R.layout.act_video;
    }

    @Override
    public void initData(View v) {

    }

    @OnClick({R.id.tv_choose,R.id.tv_previous,R.id.tv_before,R.id.tv_after,R.id.tv_next,R.id.tv_recording})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_VIDEO_REQUEST);
                break;
            case R.id.tv_previous:
                seekBar.incrementProgressBy(-1000/frameRate);
                user=true;
                break;
            case R.id.tv_before:
                if (!StringUtil.isEmpty(etStep.getText().toString()) && !etStep.getText().toString().equals("0")) {
                    seekBar.incrementProgressBy(-Integer.parseInt(etStep.getText().toString())*1000/frameRate);
                    user=true;
                } else {
                    Toast.makeText(context, "请输入帧数", Toast.LENGTH_LONG);
                }
                break;
            case R.id.tv_after:
                if (!StringUtil.isEmpty(etStep.getText().toString()) && !etStep.getText().toString().equals("0")) {
                    seekBar.incrementProgressBy(Integer.parseInt(etStep.getText().toString())*1000/frameRate);
                    user=true;
                } else {
                    Toast.makeText(context, "请输入帧数", Toast.LENGTH_LONG);
                }
                break;
            case R.id.tv_next:
                seekBar.incrementProgressBy(1000/frameRate);
                user=true;
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            file_path = cursor.getString(columnIndex);
            moviePalyerVideoview.setMediaController(null);
            moviePalyerVideoview.setVideoPath(file_path);
            moviePalyerVideoview.start();
            moviePalyerVideoview.requestFocus();
            moviePalyerVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    user=false;
                    seekBar.setMax(moviePalyerVideoview.getDuration());
                    Log.i(TAG, "onBufferingUpdate: "+moviePalyerVideoview.getDuration());
                    seekBar.postDelayed(onEverySecond, 1000/frameRate);

                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser||user){
                        moviePalyerVideoview.seekTo(progress);
                        moviePalyerVideoview.pause();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            cursor.close();
        }
    }
}