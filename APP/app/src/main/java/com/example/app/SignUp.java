package com.example.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    EditText EditTextName, EditTextSurname, EditTextEta, EditTextEmail, EditTextPassword;
    CardView SignUpButton;
    Button buttonImage;
    CircleImageView img;
    TextView helpText;
    Uri imagePath = null;
    Bitmap bitmap;

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
        SignUpButton = findViewById(R.id.SignUpButton);
        buttonImage = findViewById(R.id.buttonImage);
        img = findViewById(R.id.imageSelected);
        helpText = findViewById(R.id.helpText);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Modifica Foto")
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setCropMenuCropButtonTitle("Fatto")
                        .setFixAspectRatio(true)
                        .start(SignUp.this);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome, cognome, eta, email, password;
                nome = String.valueOf(EditTextName.getText());
                cognome = String.valueOf(EditTextSurname.getText());
                eta = String.valueOf(EditTextEta.getText());
                email = String.valueOf(EditTextEmail.getText()).toLowerCase();
                password = String.valueOf(EditTextPassword.getText());

                if (!nome.isEmpty() && !cognome.isEmpty() && !eta.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
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
                else {
                    Toast.makeText(getApplicationContext(), "Sono richiesti tutti i campiooo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                img.setImageURI(result.getUri());

                 imagePath = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                

                helpText.setVisibility(View.GONE);
                Toast.makeText(
                        this, "Immagine caricata correttamente", Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Immagine non modificata: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SignUp.this, Login.class));
        finish();

    }
}