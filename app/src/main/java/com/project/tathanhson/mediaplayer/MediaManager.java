package com.project.tathanhson.mediaplayer;

import android.media.MediaPlayer;

public class MediaManager {
    private MediaPlayer mediaPlayer;
    private static MediaManager instance;

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public void createMediaPlayer(int song, boolean isLooping) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.reset();
        } else {
            this.mediaPlayer = MediaPlayer.create(MyApplication.getInstance(), song);
            this.mediaPlayer.setLooping(isLooping);
            this.mediaPlayer.start();
        }
    }

    public void playMediaLayer() {
        if (this.mediaPlayer != null && !this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.start();
        }
    }

    public void pauseMediaLayer() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
    }

    public void stopMediaPlayer() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.reset();
            this.mediaPlayer = null;
        }
    }

}
