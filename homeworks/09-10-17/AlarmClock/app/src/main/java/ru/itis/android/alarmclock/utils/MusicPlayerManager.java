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

//------------------------------------------------------------------------------------------------//
    public void start() {
        //            initResources();
//            mediaPlayer.prepare();
//            mediaPlayer.start();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(appContext, notification);
        r.play();
    }

    public void configureAlarmSound(Notification alarmNotification) {
        alarmNotification.sound = Uri.parse("android.resource://"
                + appContext.getPackageName() + "/" + R.raw.alarm_sound);
    }

    public void stop() {
        initResources();
        mediaPlayer.stop();
    }
//------------------------------------------------------------------------------------------------//
    private void initResources() {
        //  music = getMusic(RingtoneManager.TYPE_ALARM);
        // music = getMusic(R.raw.alarm_sound);
        mediaPlayer = getMediaPlayer();
    }
    private Uri getMusic(int typeAlarm) {
        if (music == null) music = RingtoneManager.getDefaultUri(typeAlarm);
        return music;
    }
    private MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(appContext, R.raw.alarm_sound);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(100, 100);
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
        }
        return mediaPlayer;
    }
}
