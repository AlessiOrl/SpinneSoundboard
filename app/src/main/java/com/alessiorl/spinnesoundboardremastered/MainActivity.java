package com.alessiorl.spinnesoundboardremastered;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AudioPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new AudioPlayer();
        final Sound[] soundArray = SoundStore.getSounds(this);

        Button buttonX = (Button) findViewById(R.id.btn_random);
        buttonX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Random rand = new Random();
                Sound sound = soundArray[rand.nextInt(soundArray.length)];
                player.playSound(MainActivity.this, sound.getResourceId(), 1);
            }
        });

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);

        RecyclerView.Adapter myAdapter = new RecyclerViewAdapter(MainActivity.this, soundArray, player, 10);

        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
    }



}
