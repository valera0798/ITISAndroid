package ru.itis.android.alarmclock.utils;

import android.app.Notification;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

import ru.itis.android.alarmclock.R;

/**
 * Created by Users on 26.10.2017.
 */

public class MusicPlayerManager {
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_ALARM = RingtoneManager.TYPE_ALARM;

    private static MusicPlayerManager musicPlayerManager;

    private Context appContext;
    private MediaPlayer mediaPlayer;
    private Uri music;

    public static MusicPlayerManager getInstance(Context appContext) {
        if (musicPlayerManager == null)
            musicPlayerManager = new MusicPlayerManager(appContext);
        return musicPlayerManager;
    }
    private MusicPlayerManager(Context appContext) {
        this.appContext = appContext;
    }

    public Uri getMusic(int typeAlarm) {
        switch (typeAlarm) {
            case TYPE_DEFAULT:
                music = Uri.parse("android.resource://"
                        + appContext.getPackageName() + "/" + R.raw.alarm_sound);
                break;
            case TYPE_ALARM:
                music = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                break;
        }
        return music;
    }

    private MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(appContext, R.raw.alarm_sound);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(100, 100);
        }
        return mediaPlayer;
    }

//------------------------------------------------------------------------------------------------//
    public void setMusic(Notification notification, Uri music) {    // для приёмников, работающих с Notification
        notification.sound = music;
    }

    public void start() {
        RingtoneManager.getRingtone(appContext, getMusic(TYPE_DEFAULT))
                .play();
    }

    public void stop() {
        initResources();
        mediaPlayer.stop();
    }
//------------------------------------------------------------------------------------------------//
    private void initResources() {
        music = getMusic(TYPE_DEFAULT);
        mediaPlayer = getMediaPlayer();
    }
}
