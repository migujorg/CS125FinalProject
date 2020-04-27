package com.example.CS125FinalProject;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.CS125FinalProject.JsonCreator.Workshop;

import java.io.IOException;

import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;
/** Manages running the sketch on Android. */
public class Main extends AppCompatActivity {
    public static PApplet sketch;
    //public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame);

        //sketch = new Sketch(this);
        sketch = new Workshop();
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }
}

