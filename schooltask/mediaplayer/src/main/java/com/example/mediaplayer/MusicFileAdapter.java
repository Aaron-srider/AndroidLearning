package com.example.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Data;

public class MusicFileAdapter extends ArrayAdapter<File> {
    int resource;

    Music music;

    List<File> musicList;

    public MusicFileAdapter(@NonNull Context context, int resource, @NonNull File[] objects, Music music) {
        super(context, resource, objects);
        this.resource = resource;
        this.musicList = Arrays.asList(objects.clone());
        this.music = music;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        File item = getItem(position);
        String name = item.getName();
        TextView textView = view.findViewById(R.id.item);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                music.startFromBegin(position);
            }
        });

        textView.setText(name);
        return view;
    }


}



