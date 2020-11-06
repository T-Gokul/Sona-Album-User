package com.gokul.sonaalbumuser.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gokul.sonaalbumuser.R;

public class MainActivity extends AppCompatActivity {

    CardView viewImageCard, viewVideoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sona Album");

        viewImageCard = findViewById(R.id.viewImageCard);
        viewVideoCard = findViewById(R.id.viewVideoCard);

        viewImageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImagesActivity.class));
            }
        });

        viewVideoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideosActivity.class));
            }
        });
    }
}