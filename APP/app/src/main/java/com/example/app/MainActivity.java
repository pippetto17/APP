package com.example.app;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

        fragmentTransaction1.add(R.id.fragment_container, new firstPageActivity());
        fragmentTransaction1.commit();
    }
}