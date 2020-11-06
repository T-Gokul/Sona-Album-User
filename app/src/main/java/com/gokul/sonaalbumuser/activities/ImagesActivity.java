package com.gokul.sonaalbumuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;

import com.gokul.sonaalbumuser.R;
import com.gokul.sonaalbumuser.adapter.AdapterImage;
import com.gokul.sonaalbumuser.model.ModelImage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity implements AdapterImage.OnItemClickListener {

    RecyclerView imagesRv;
    DatabaseReference myRef;
    ArrayList<ModelImage> imageArrayList;
    AdapterImage adapterImage;

    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        setTitle("All Images");

        imagesRv = findViewById(R.id.imagesRv);
        //et_search = findViewById(R.id.et_search);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        imagesRv.setLayoutManager(layoutManager);
        imagesRv.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference();
        
        imageArrayList = new ArrayList<>();
        
        clearAll();
        
        GetDataFromFirebase();

        /*et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });*/
    }

    private void filter(String text) {

        ArrayList<ModelImage> filterList = new ArrayList<>();

        for (ModelImage item : imageArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }

        adapterImage.filteredList(filterList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void GetDataFromFirebase() {

        Query query = myRef.child("Images");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelImage modelImage = new ModelImage();

                    modelImage.setName(dataSnapshot.child("name").getValue().toString());
                    modelImage.setImage(dataSnapshot.child("image").getValue().toString());

                    imageArrayList.add(modelImage);
                }
                adapterImage = new AdapterImage(getApplicationContext(), imageArrayList);
                imagesRv.setAdapter(adapterImage);
                adapterImage.setOnItemClickListener(ImagesActivity.this);
                adapterImage.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if (imageArrayList != null){
            imageArrayList.clear();
            if (adapterImage != null){
                adapterImage.notifyDataSetChanged();
            }
        }
        imageArrayList = new ArrayList<>();
        
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, ImagesDetailActivity.class);
        ModelImage imageItem = imageArrayList.get(position);

        detailIntent.putExtra("name", imageItem.getName());
        detailIntent.putExtra("image", imageItem.getImage());

        startActivity(detailIntent);
    }
}