package com.project.tathanhson.mediaplayer.views.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tathanhson.mediaplayer.Mp3Player;
import com.project.tathanhson.mediaplayer.R;
import com.project.tathanhson.mediaplayer.databinding.ActivityMainBinding;
import com.project.tathanhson.mediaplayer.models.ItemMusic;
import com.project.tathanhson.mediaplayer.viewmodels.MainViewModel;
import com.project.tathanhson.mediaplayer.views.adapter.MusicAdapter;
import com.project.tathanhson.mediaplayer.views.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    public static final int REQUEST_CODE = 1;
    boolean isREAD_EXTERNAL_STORAGE;
    boolean isWRITE_EXTERNAL_STORAGE;
    public static final String TAG = "AAAAAAAAAAAAAAAA";

    private ImageView ivPlay;
    RecyclerView recyclerView;

    @NonNull
    @Override
    public Class<MainViewModel> initViewModelClass() {
        return MainViewModel.class;
    }

    @NonNull
    @Override
    public ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initViews() {
        checkPer();
        Mp3Player.getInstance().loadOffline();
        initAdapter();
        actionMusic();
    }

    private void actionMusic() {
        binding.iclMusic.btnControl.setOnClickListener(view -> {
            Mp3Player.getInstance().playMusic();
            updateUI();
        });
        binding.iclMusic.btnBack.setOnClickListener(view -> {
            Mp3Player.getInstance().backMusic();
            updateUI();
        });
        binding.iclMusic.btnNext.setOnClickListener(view -> {
            Mp3Player.getInstance().nextMusic();
            updateUI();
        });
    }

    private void updateUI() {
        if (Mp3Player.getInstance().getState()==Mp3Player.STATE_PLAYING){
            binding.iclMusic.btnControl.setImageResource(R.drawable.ic_music_outline_pause);
        }else {
            binding.iclMusic.btnControl.setImageResource(R.drawable.ic_music_outline_play);
        }

    }

    private void initAdapter() {
        binding.rcvMusic.setAdapter(new MusicAdapter(Mp3Player.getInstance().getListMusic(), this, view-> {
            doClickItemSong((ItemMusic)view.getTag());
        }));
        binding.rcvMusic.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doClickItemSong(ItemMusic tag) {

    }


    private void checkPer() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            isREAD_EXTERNAL_STORAGE = true;
        }else {
            isREAD_EXTERNAL_STORAGE = false;
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            isWRITE_EXTERNAL_STORAGE =true;
        }else {
            isWRITE_EXTERNAL_STORAGE =false;
        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            Log.e(TAG, "Build.VERSION.SDK_INT: "+Build.VERSION.SDK_INT );
            if (Environment.isExternalStorageManager()) {
                Log.e(TAG, "checkPer: "+ Environment.isExternalStorageManager());
            }else{
                Log.e(TAG, "checkPer: "+ Environment.isExternalStorageManager());

                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse(String.format("package:%s", MainActivity.this.getPackageName())));
                startActivity(intent);
            }
        }else {
            Log.e(TAG, "Build.VERSION.SDK_INT: "+Build.VERSION.SDK_INT );
            if (isREAD_EXTERNAL_STORAGE && isWRITE_EXTERNAL_STORAGE){
                Log.e(TAG, "checkPer: "+isREAD_EXTERNAL_STORAGE +isWRITE_EXTERNAL_STORAGE);
            }else{
                Log.e(TAG, "checkPer: "+isREAD_EXTERNAL_STORAGE +isWRITE_EXTERNAL_STORAGE);
                String[] strArr = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, strArr, REQUEST_CODE);
            }
        }
    }



}