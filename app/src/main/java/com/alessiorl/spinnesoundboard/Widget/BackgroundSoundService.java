package com.alessiorl.spinnesoundboard.Widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alessiorl.spinnesoundboard.Helpers.AudioPlayer;
import com.alessiorl.spinnesoundboard.Helpers.Sound;
import com.alessiorl.spinnesoundboard.Helpers.SoundStore;

import java.util.ArrayList;
import java.util.Random;

public class BackgroundSoundService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private ArrayList<Sound> soundItems;
    private AudioPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        this.soundItems = SoundStore.getSounds(this);
        this.player = new AudioPlayer();
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private void releasePlayer() {
        player.stopSound();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public AudioPlayer getplayerInstance() {
        if (player != null) {
            startPlayer();
        }
        return player;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (player != null) {
            startPlayer();
        }
        return START_STICKY;
    }

    private void startPlayer() {
        final Context context = this;
        Random rand = new Random();
        Sound sound = this.soundItems.get(rand.nextInt(soundItems.size()));
        player.playSound(this, sound.getResourceId(), 1);
        Toast.makeText(this, "Playing: " + sound.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public class LocalBinder extends Binder {
        public BackgroundSoundService getService() {
            return BackgroundSoundService.this;
        }
    }
}