package com.example.mediaplayer;

import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Data;


class Music implements Observable {
    public MainActivity mainActivity;
    public Observer observer;
    public Timer timer = new Timer();

    public List<File> fileList;

    public Integer currentIndex = 0;

    public  MediaPlayer player = new MediaPlayer();

    public Integer status = STOP;

    public  Integer playMode = SEQUENCE;


    public static final Integer PLAYING = 0;
    public static final Integer STOP = 1;
    public static final Integer PAUSE = 2;
    public static final Integer START = 3;
    public static final Integer SEQUENCE = 10;
    public static final Integer RANDOM = 11;

    public Music(List<File> list) {
        this.fileList = list;
    }

    /**
     * 从头开始播放指定音乐
     *
     * @param position 音乐在列表中的位置
     */
    public void startFromBegin(int position) {

        //如果播放器为空，重新创建
        if (player == null) {
            player = new MediaPlayer();
        }

        //重置播放器
        player.reset();

        //必须在reset后注册，否则被reset置空
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                status = STOP;
                next();
                startFromBegin(currentIndex);
                //timer.cancel();
                //timer.purge();
            }
        });

        try {
            //设置音乐文件来源
            player.setDataSource(fileList.get(position).getPath());

            //准备（缓冲文件）
            player.prepare();

            //播放开始
            player.start();

            //更新状态
            currentIndex = position;

            //更新状态
            status = START;


            notifyObservers();


            //为进度条设置时间监听器，负责将歌曲进度同步到进度条
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!mainActivity.isSeekbarChanging()) {
                        //必须判断播放是否结束，如果不判断，那么可能在mediaPlayer初始化的时候getDuration错误调用onCompletion
                        if (!isStop()) {
                            notifyObservers();
                        }
                    }
                }
            }, 0, 50);
//
            status = PLAYING;
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void next() {
        if (playMode.equals(SEQUENCE)) {
            if (currentIndex < fileList.size() - 1) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }
        }

        if (playMode.equals(RANDOM)) {
            currentIndex = (int) (Math.random() * (fileList.size()));
        }
    }

    public void previous() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = fileList.size() - 1;
        }
    }

    public void pause() {
        if (isPause()) {
            status = PLAYING;
            player.start();
        } else if (isPlay()) {
            status = PAUSE;
            player.pause();
        }
    }

    public boolean isPlay() {
        return status.equals(PLAYING);
    }

    public boolean isPause() {

        return status.equals(PAUSE);
    }

    public boolean isStart() {

        return status.equals(START);
    }

    public boolean isStop() {

        return status.equals(STOP);
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        observer.update(this);
    }
}
