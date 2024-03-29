package com.sporty.sporty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddMatch extends AppCompatActivity {

    CardView soccer, basket, paddle, tennis;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.sfondo);

        setContentView(R.layout.activity_add);

        soccer = findViewById(R.id.chooseSoccer);
        basket = findViewById(R.id.chooseBasket);
        paddle = findViewById(R.id.choosePaddle);
        tennis = findViewById(R.id.chooseTennis);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMatch.this, MatchActivity.class);
                intent.putExtra("sport_name", "calcio");
                startActivity(intent);
            }
        });

        tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMatch.this, MatchActivity.class);
                intent.putExtra("sport_name", "tennis");
                startActivity(intent);
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMatch.this, MatchActivity.class);
                intent.putExtra("sport_name", "basket");
                startActivity(intent);
            }
        });

        paddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMatch.this, MatchActivity.class);
                intent.putExtra("sport_name", "paddle");
                startActivity(intent);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_add);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()) {
                    case R.id.navigation_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_fav:
                        startActivity(new Intent(getApplicationContext(), FavMatch.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_add:
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddMatch.this, Search.class));
        finish();

    }
}