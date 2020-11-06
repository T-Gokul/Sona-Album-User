package com.gokul.sonaalbumuser.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gokul.sonaalbumuser.adapter.AdapterVideo;
import com.gokul.sonaalbumuser.model.ModelImage;
import com.gokul.sonaalbumuser.model.ModelVideo;
import com.gokul.sonaalbumuser.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity {

    RecyclerView videosRv;
//    private ArrayList<ModelVideo> videoArrayList;
//    private AdapterVideo adapterVideo;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        setTitle("All Videos");

        videosRv = findViewById(R.id.videosRv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        videosRv.setHasFixedSize(true);
        videosRv.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Videos");

       // loadVideosFromFirebase();
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ModelVideo, AdapterVideo> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelVideo, AdapterVideo>(
                        ModelVideo.class,
                        R.layout.row_video,
                        AdapterVideo.class,
                        reference
                ) {
                    @Override
                    protected void populateViewHolder(AdapterVideo adapterVideo, ModelVideo modelVideo, int i) {
                        adapterVideo.setVideo(getApplication(), modelVideo.getTitle(), modelVideo.getVideoUrl());
                    }
                };
        videosRv.setAdapter(firebaseRecyclerAdapter);

    }

    /* private void loadVideosFromFirebase() {
        videoArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Videos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ModelVideo modelVideo = ds.getValue(ModelVideo.class);
                    videoArrayList.add(modelVideo);
                }
                adapterVideo = new AdapterVideo(VideosActivity.this, videoArrayList);
                videosRv.setAdapter(adapterVideo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}