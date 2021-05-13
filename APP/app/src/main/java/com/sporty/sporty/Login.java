package com.sporty.sporty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {


    EditText EditTextEmail, EditTextPassword;
    CardView buttonLogin;
    TextView textViewSignup;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.def_wallpaper);

        setContentView(R.layout.activity_login);

        EditTextEmail = findViewById(R.id.emailLogin);
        EditTextPassword = findViewById(R.id.passwordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Vengono estrapolati mail e psw dalle textView e viene chiamato il metodo di Login
                String email = String.valueOf(EditTextEmail.getText());
                String password = String.valueOf(EditTextPassword.getText());
                userLogin(email, password);
            }
        });
    }


    //Effettua il login e scrive le shared preferences
    public void userLogin(String email, String password) {

        if (!email.isEmpty() && !password.isEmpty()) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "password";

                    String[] data = new String[2];
                    data[0] = email;
                    data[1] = password;
                    PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/login.php", "POST", field, data);

                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Login riuscito")) {
                                Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();


                                //Scrive le shared prefrences
                                sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("emailLogin", email);
                                editor.putString("passwordLogin", password);
                                editor.commit();

                                Intent intent = new Intent(Login.this, Search.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        } else {
            Toast.makeText(Login.this, "Sono richiesti tutti i campi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);

    }
}