package com.sporty.sporty;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText EditTextName, EditTextSurname, EditTextEta, EditTextEmail, EditTextPassword, EditTextConfPassword;
    CardView SignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.def_wallpaper);

        setContentView(R.layout.activity_sign_up);

        EditTextName = findViewById(R.id.nome);
        EditTextSurname = findViewById(R.id.cognome);
        EditTextEta = findViewById(R.id.eta);
        EditTextEmail = findViewById(R.id.emailReg);
        EditTextPassword = findViewById(R.id.passwordReg);
        EditTextConfPassword = findViewById(R.id.confermaPasswordRegistration);
        SignUpButton = findViewById(R.id.SignUpButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome, cognome, eta, email, password, confPassword;
                nome = String.valueOf(EditTextName.getText());
                cognome = String.valueOf(EditTextSurname.getText());
                eta = String.valueOf(EditTextEta.getText());
                email = String.valueOf(EditTextEmail.getText()).toLowerCase();
                password = String.valueOf(EditTextPassword.getText());
                confPassword = String.valueOf(EditTextConfPassword.getText());

                if (nome.isEmpty()) {
                    EditTextName.setError("Questo campo è obbliatorio");
                    EditTextName.requestFocus();
                    return;
                }

                if (cognome.isEmpty()) {
                    EditTextSurname.setError("Questo campo è obbliatorio");
                    EditTextSurname.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    EditTextEmail.setError("Questo campo è obbliatorio");
                    EditTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EditTextEmail.setError("Questa mail non è valida");
                    EditTextEmail.requestFocus();
                    return;
                }

                if (eta.isEmpty()) {
                    EditTextEta.setError("Questo campo è obbliatorio");
                    EditTextEta.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    EditTextPassword.setError("Questo campo è obbliatorio");
                    EditTextPassword.requestFocus();
                    return;
                } else if (password.length() < 8 || password.length() > 16) {
                    EditTextPassword.setError("La password deve avere: \n almeno 8, massimo 16 caratteri");
                    EditTextPassword.requestFocus();
                    return;
                }

                if (!password.equals(confPassword)) {
                    EditTextConfPassword.setError("Le password non coincidono");
                    EditTextConfPassword.requestFocus();
                    return;
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[5];
                        field[0] = "nome";
                        field[1] = "cognome";
                        field[2] = "eta";
                        field[3] = "email";
                        field[4] = "password";


                        String[] data = new String[5];
                        data[0] = nome;
                        data[1] = cognome;
                        data[2] = eta;
                        data[3] = email;
                        data[4] = password;
                        PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/signup.php", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Account creato")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUp.this, Login.class));
        finish();

    }
}