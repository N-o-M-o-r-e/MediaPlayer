package com.project.tathanhson.mediaplayer;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import com.project.tathanhson.mediaplayer.models.ItemMusic;

import java.io.IOException;
import java.util.ArrayList;

public class Mp3Player {
    public static  int STATE_IDEL = 1;
    public static  int STATE_PLAYING = 2;
    public static  int STATE_PAUSED = 3;

    public int getState() {
        return state;
    }

    //state trạng thái
    private int state = STATE_IDEL;



    private int currentMusic;
    public int getCurrentMusic() {
        return currentMusic;
    }

    public ArrayList<ItemMusic> getListMusic() {
        return listMusic;
    }
    ArrayList<ItemMusic> listMusic = new ArrayList<>();
    MediaPlayer player = new MediaPlayer();
    private static Mp3Player instance;

    public static Mp3Player getInstance() {
        if (instance == null) {
            instance = new Mp3Player();
        }
        return instance;
    }

    private Mp3Player() {
        //for singleton
    }


    public void loadOffline() {
        Cursor cursor = MyApplication.getInstance().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        MediaStore.Audio.Media.TITLE + " ASC");


        cursor.moveToFirst();
        int cTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int cPath = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int cAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int cArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        listMusic.clear();

        while (!cursor.isAfterLast()) {
            String title = cursor.getString(cTitle);
            String path = cursor.getString(cPath);
            String album = cursor.getString(cAlbum);
            String artist = cursor.getString(cArtist);

            ItemMusic itemMusic = new ItemMusic(title, path, album, artist);
            listMusic.add(itemMusic);

            cursor.moveToNext();
        }
        cursor.close();
        Log.i("AAAAAAAAAA", "load item music: "+listMusic);
    }

    public void playMusic(){
        if(state==STATE_IDEL){
            player.reset();
            try{
                player.setDataSource(listMusic.get(currentMusic).path);
                player.prepare();
                player.start();
                state = STATE_PLAYING;
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if (state == STATE_PAUSED){
            player.start();
            state  = STATE_PLAYING;
        }else {
            player.pause();
            state = STATE_PAUSED;
        }
    }
    public void nextMusic(){
        currentMusic++;
        if (currentMusic==listMusic.size()){
            currentMusic = 0;
        }
        state = STATE_IDEL;
        playMusic();
    }

    public void backMusic(){
        currentMusic--;
        if (currentMusic==0){
            currentMusic = listMusic.size()-1;
        }
        state = STATE_IDEL;
        playMusic();
    }


}
