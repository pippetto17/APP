package com.example.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {
    private TextView textName;
    private TextView textSurname;
    private TextView textPhone;
    private TextView textUser;
    private TextView textPass;
    private TextView textConfirmPass;
    private Button reg_back;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        textName = findViewById(R.id.textName);
        textSurname = findViewById(R.id.textSurname);
        textPhone = findViewById(R.id.textPhone);
        textUser = findViewById(R.id.textUser);
        textPass = findViewById(R.id.textPass);
        textConfirmPass = findViewById(R.id.textConfirmPass);
        reg_back = findViewById(R.id.cardViewButton);
    }
}


