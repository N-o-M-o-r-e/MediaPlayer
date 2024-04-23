package com.project.tathanhson.mediaplayer.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tathanhson.mediaplayer.R;
import com.project.tathanhson.mediaplayer.databinding.ItemMusicBinding;
import com.project.tathanhson.mediaplayer.models.ItemMusic;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private final View.OnClickListener event;
    private ArrayList<ItemMusic> listMusics = new ArrayList<>();
    private Context context;
    private int currentMusic;

    public MusicAdapter(@NonNull ArrayList<ItemMusic> itemMusics, @NonNull Context context, View.OnClickListener event) {
        this.listMusics = itemMusics;
        this.context = context;
        this.event = event;
    }

    @NonNull
    @Override
    public MusicAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMusicBinding binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MusicHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicHolder holder, int position) {
        ItemMusic currentItem = listMusics.get(position);
        if (position ==  currentMusic){
            holder.binding.itMusic.setBackgroundResource(R.color.cl_item_select);
        }else {
            holder.binding.itMusic.setBackgroundResource(R.color.white);
        }


        holder.binding.tvItemMusic.setText(currentItem.title);
        holder.binding.tvItemMusic.setTag(currentItem);
    }

    @Override
    public int getItemCount() {
        return listMusics.size();
    }

    public void updataUI(int currentMusic){
        this.currentMusic = currentMusic;
        notifyItemRangeChanged(0, listMusics.size());
    }


    public  class MusicHolder extends RecyclerView.ViewHolder {
        final ItemMusicBinding binding;

        public MusicHolder(@NonNull ItemMusicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.itMusic.setOnClickListener(event);
        }

    }
}
