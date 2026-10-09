package com.example.herwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageButton playButton, pauseButton;
    private SeekBar progressBar;
    private Handler handler = new Handler();
    private Runnable updateSeekBarRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.player_view);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        progressBar = findViewById(R.id.progress_bar);

        // Get the video URL passed from the intent
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        if (videoUrl != null) {
            videoView.setVideoPath(videoUrl);  // Set the video URL to the VideoView
        }

        // Initially hide the Pause button
        pauseButton.setVisibility(ImageButton.INVISIBLE);

        // Set click listener for Play button
        playButton.setOnClickListener(v -> {
            videoView.start();  // Start video playback
            playButton.setVisibility(ImageButton.INVISIBLE);  // Hide Play button
            pauseButton.setVisibility(ImageButton.VISIBLE);  // Show Pause button
            startUpdatingSeekBar();
        });

        // Set click listener for Pause button
        pauseButton.setOnClickListener(v -> {
            videoView.pause();  // Pause video playback
            pauseButton.setVisibility(ImageButton.INVISIBLE);  // Hide Pause button
            playButton.setVisibility(ImageButton.VISIBLE);  // Show Play button
            stopUpdatingSeekBar();
        });

        // Set the SeekBar max value once the video is prepared
        videoView.setOnPreparedListener(mp -> {
            int duration = videoView.getDuration();  // Get video duration
            progressBar.setMax(duration);  // Set max value for SeekBar
        });

        // Allow user to seek the video using the SeekBar
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);  // Seek to the specified position
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // Method to update the SeekBar during playback
    private void startUpdatingSeekBar() {
        updateSeekBarRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = videoView.getCurrentPosition();
                progressBar.setProgress(currentPosition);  // Update SeekBar

                // Repeat every 1000 milliseconds
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateSeekBarRunnable);
    }

    // Stop updating the SeekBar
    private void stopUpdatingSeekBar() {
        if (updateSeekBarRunnable != null) {
            handler.removeCallbacks(updateSeekBarRunnable);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop the video and release resources
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        stopUpdatingSeekBar();
    }
}
