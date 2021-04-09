package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdvertS extends AppCompatActivity {

    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_adv_soccer);

        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(AdvertS.this, Search.class));
        finish();

    }
}
