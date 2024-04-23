package com.project.tathanhson.mediaplayer.models;

public class ItemMusic {
    public String title, path, album, artist;

    public ItemMusic(String title, String path, String album, String artist) {
        this.title = title;
        this.path = path;
        this.album = album;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "ItemMusic{" + "title='" + title + '\'' + '}';
    }
}