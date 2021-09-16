package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class MainActivity extends AppCompatActivity implements Observer {

    Music music;

    SeekBar seekbar;

    boolean isSeekbarChanging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.next();
                music.startFromBegin(music.getCurrentIndex());
            }
        });

        Button previous = findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.previous();
                music.startFromBegin(music.getCurrentIndex());
            }
        });

        Button pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.pause();
            }
        });


        Button random = findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.setPlayMode(Music.RANDOM);
            }
        });

        Button sequence = findViewById(R.id.sequence);
        sequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.setPlayMode(Music.SEQUENCE);
            }
        });

        seekbar = findViewById(R.id.seekbar);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                int duration2 = mediaPlayer.getDuration() / 1000;//获取音乐总时长
//                int position = mediaPlayer.getCurrentPosition();//获取当前播放的位置
//                tv_start.setText(calculateTime(position / 1000));//开始时间
//                tv_end.setText(calculateTime(duration2));//总时长
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarChanging = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekbarChanging = false;
                //在当前位置播放
                music.getPlayer().seekTo(seekBar.getProgress());
//                tv_start.setText(calculateTime(mediaPlayer.getCurrentPosition() / 1000));
            }
        });

        music = createMusic();

        music.setMainActivity(this);
        music.registerObserver(this);

        File[] array2 = music.getFileList().toArray(new File[music.getFileList().size()]);

        MusicFileAdapter adapter = new MusicFileAdapter(this, R.layout.musiclist, array2, music);

        ListView musicList = findViewById(R.id.musicList);
        musicList.setAdapter(adapter);

    }


    public List<String> getMusicNames(List<File> fileList) {

        List<String> musicNames = new ArrayList<>();
        for (File file : fileList) {
            String name = file.getName();
            musicNames.add(name);
        }
        return musicNames;
    }

    public Music createMusic() {
        File[] files = loadMusicFromSdCard();
        return new Music(Arrays.asList(files.clone()));
    }

    public File[] loadMusicFromSdCardTemp() {
        File file1 = new File("/sdcard/Music/trytoforget.mp3");
        File file2 = new File("/sdcard/Music/goodthing.mp3");
        File file3 = new File("/sdcard/Music/gawaada.mp3");
        File file4 = new File("/sdcard/Music/latetomeet.mp3");
        File file5 = new File("/sdcard/Music/shadow.mp3");
        List<File> musicList = new ArrayList<>();
        File[] files = new File[5];

        files[0] = file1;
        files[1] = file2;
        files[2] = file3;
        files[3] = file4;
        files[4] = file5;
        return files;
    }

    public File[] loadMusicFromSdCard() {
        //读取歌曲存成列表
        File musicFolder = new File("/sdcard/Music");
        System.out.println(musicFolder.exists());

        if (!musicFolder.exists()) {
            throw new RuntimeException("Music文件夹不存在");
        }

        File[] files = musicFolder.listFiles();
        List<File> musicList = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".mp3")) {
                musicList.add(file);
            }
        }

        File[] result = musicList.toArray(new File[]{});
        return result;
    }

    @Override
    public void update(Observable observable) {
        if(observable instanceof Music) {

            Music music = (Music) observable;
            if(music.isStart()) {
                seekbar.setProgress(0);
                seekbar.setMax(music.getPlayer().getDuration());
            } else if(music.isPlay()) {
                seekbar.setProgress(music.getPlayer().getCurrentPosition());
            }
        }
    }
}

