package com.example.CS125FinalProject;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class SoundPlayer {
    final MediaPlayer player;
    final AssetFileDescriptor descriptor;

    SoundPlayer(final MediaPlayer play, final AssetFileDescriptor descript) {
        player = play;
        descriptor = descript;
    }
}
