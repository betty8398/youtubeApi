package com.example.youtubeapi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBarListener;

import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private String videoID ="FuWpTXZR2bo"; // 接續在https://www.youtube.com/watch?v= 後面的短網址
    private YouTubePlayerSeekBar youTubePlayerSeekBar;
    private YouTubePlayerTracker tracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化 Youtube Player
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                //自動播放影片 從第120秒
                youTubePlayer.loadVideo(videoID, 120);
                //不自動播放畫面 停在封面圖片 按下播放從第120開始
                //youTubePlayer.cueVideo(videoID,120);
                //TODO 解決旋轉重新播放問題
                //防止影片在背景播放
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer,
//                        getLifecycle(),
//                        videoID,
//                        120
//                );
                tracker = new YouTubePlayerTracker();
                youTubePlayer.addListener(tracker);




            }

        });



        //監控時間
        //String.format(TimeUtilities, 10);
        youTubePlayerSeekBar=findViewById(R.id.SeekBar);
        //TODO 解決seekbar 沒辦法符合影片長度問題
        youTubePlayerSeekBar.setYoutubePlayerSeekBarListener(new YouTubePlayerSeekBarListener() {
            @Override
            public void seekTo(float time) {
                //youTubePlayer是youTubePlayerView的原件 用來控制播放時間
                youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                    youTubePlayer.seekTo(time);

                    //影片狀態追蹤器

                    Log.d("tracker", "tracker getState = "+tracker.getState()); //在播放還是暫停
                    Log.d("tracker", "tracker getCurrentSecond = "+tracker.getCurrentSecond()); //上個播放時間
                    Log.d("tracker", "tracker getVideoDuration = "+tracker.getVideoDuration()); //影片總長度
                    Log.d("tracker", "tracker getVideoId = "+tracker.getVideoId()); //影片ID
                    //TODO 將treak的秒數轉換成MM:SS
                    Toast.makeText(MainActivity.this, "目前影片時間"+tracker.getCurrentSecond(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}