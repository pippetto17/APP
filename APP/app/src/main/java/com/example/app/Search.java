package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recycle;

    String s1[], s2[];

    int images[] = {R.drawable.ic_item_soccer, R.drawable.ic_item_tennis, R.drawable.ic_item_paddle, R.drawable.ic_item_basket};

    Spinner filterBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        filterBy = findViewById(R.id.filterBy);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sport_filter, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        filterBy.setAdapter(adapter);
        filterBy.setOnItemSelectedListener(this);

        recycle = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.nome);
        s2 = getResources().getStringArray(R.array.modalita);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images);
        recycle.setAdapter(myAdapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_search);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()) {
                    case R.id.navigation_search:
                        return true;

                    case R.id.navigation_add:
                        startActivity(new Intent(getApplicationContext(), Add.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_user:
                        startActivity(new Intent(getApplicationContext(), User.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}