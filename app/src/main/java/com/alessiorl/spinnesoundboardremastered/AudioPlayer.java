package com.alessiorl.spinnesoundboardremastered;

import android.content.Context;
import android.media.MediaPlayer;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

public class AudioPlayer {

    private SimpleExoPlayer player;

    private void stopSound() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void playSound(Context context, int sounRes, float volume)
    {
        stopSound();
        player = ExoPlayerFactory.newSimpleInstance(context);
        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(sounRes));
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(context);
        try {
            rawResourceDataSource.open(dataSpec);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }

        DataSource.Factory factory = () -> rawResourceDataSource;
        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource
                .Factory(factory)
                .createMediaSource(rawResourceDataSource.getUri());


        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        player.setVolume(volume);

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    player.release();
                }
            }
        });
    }


}