package brhymes.app;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import brhymes.app.player.MediaPlayerHolder;
import brhymes.app.player.PlaybackListener;
import brhymes.app.player.PlayerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomAppBar bottomAppBar;
    private AnimatedVectorDrawable play;
    private AnimatedVectorDrawable pause;
    private boolean playtopause = true;
    private FloatingActionButton fab;

    public static PlayerAdapter mPlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        play = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_play_pause);
        pause = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_pause_play);
        bottomAppBar = findViewById(R.id.bar);

        initializePlaybackController();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:

                animatePlayIcon();
                if (mPlayerAdapter.isPlaying()) {
                    mPlayerAdapter.pause();
                } else {
                    mPlayerAdapter.play();
                }
                break;
        }
    }

    public void animatePlayIcon() {
        bottomAppBar.setFabAttached(!bottomAppBar.isFabAttached());
        AnimatedVectorDrawable drawable = playtopause ? play : pause;
        fab.setImageDrawable(drawable);
        drawable.start();
        playtopause = !playtopause;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlayerAdapter.loadMedia(R.raw.balance);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isChangingConfigurations() && !mPlayerAdapter.isPlaying()) {
            mPlayerAdapter.release();
        }
    }

    private void initializePlaybackController() {
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        mMediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener(this));
        mPlayerAdapter = mMediaPlayerHolder;
    }
}
