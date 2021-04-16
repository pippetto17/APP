package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import de.hdodenhof.circleimageview.CircleImageView;

public class User extends AppCompatActivity {

    private TextView logout;
    private TextView modifica_img;
    private TextView citta_match;
    private TextView modalita_match;
    private TextView giorno_match;
    Dialog dialog1;
    Dialog dialog2;
    CircleImageView img;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        img = findViewById(R.id.U_img_user);

        // QUI IL FETCH DATA PRENDE LA STRINGA CHE RICHIEDO CHE CONTIENE QUESTI 3 VALORI QUA SOTTO
        citta_match = findViewById(R.id.citta_match);
        modalita_match = findViewById(R.id.modalita_match);
        giorno_match = findViewById(R.id.giorno_match);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                FetchData fetchData = new FetchData("http://93.43.208.27/carletti/sportydb/ItemSoccer.php");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {

                        //QUI RESTITUITO IL RESULT LO PASSO A STRINGA E IN QUESTO CASO LO SETTO INTEGRALMENTE A CITTA_MATCH
                        String result = fetchData.getResult();

                        citta_match.setText(result);
                        //End ProgressBar (Set visibility to GONE)
                        Log.i("FetchData", result);
                    }
                }
            }
        });

        dialog1 = new Dialog(User.this);
        dialog1.setContentView(R.layout.logout_dialog);

        dialog2 = new Dialog(User.this);
        dialog2.setContentView(R.layout.change_image_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
            dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }

        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(false);

        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);

        CardView logout_btn = dialog1.findViewById(R.id.logout_btn);
        CardView cancel = dialog1.findViewById(R.id.cancel_btn);

        CardView change_btn = dialog2.findViewById(R.id.change_btn);
        CardView cancel2 = dialog2.findViewById(R.id.cancel_btn);

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Modifica Foto")
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setCropMenuCropButtonTitle("Fatto")
                        .setFixAspectRatio(true)
                        .start(User.this);

                dialog2.dismiss();
            }
        });

        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogOut();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
            }
        });

        modifica_img = findViewById(R.id.U_help_text);
        modifica_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_user);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()) {
                    case R.id.navigation_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_add:
                        startActivity(new Intent(getApplicationContext(), Add.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_user:
                        return true;
                }

                return false;
            }
        });
    }

    private void userLogOut() {

        //Reset shared prefrences
        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        //Messaggio di logout
        Toast.makeText(User.this, "Logout", Toast.LENGTH_SHORT).show();
        dialog1.dismiss();

        //Apertura pagina di login
        Intent intent = new Intent(User.this, Login.class);
        startActivity(intent);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                img.setImageURI(result.getUri());

                Toast.makeText(
                        this, "Immagine caricata correttamente", Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Immagine non modificata: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
}