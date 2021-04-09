package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {

    RecyclerView recycle;

    String s1[], s2[];

    int images[] = {R.drawable.ic_item_soccer, R.drawable.ic_item_tennis, R.drawable.ic_item_paddle, R.drawable.ic_item_basket};

    Spinner filterBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        filterBy = findViewById(R.id.filterBy);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.sport_filter, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterBy.setAdapter(adapter5);

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
}