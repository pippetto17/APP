package com.sporty.sporty;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class MatchActivity extends AppCompatActivity {

    EditText EditInfo, EditCity;
    Spinner Mod, Ora;

    SharedPreferences sharedPreferences;

    TextView date_text;
    CardView show_dialog;
    Calendar calendar;

    CardView create_ad;

    String giorno = null;


    int year, month, day;

    String choosedSport = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.def_wallpaper);

        setContentView(R.layout.create_match);

        choosedSport = getIntent().getStringExtra("sport_name");

        //getting calendar instance
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //accessing EditText and Button
        date_text = (TextView) findViewById(R.id.date_text);
        show_dialog = (CardView) findViewById(R.id.show_dialog);

        //dati da passare al onClick per la creazione del match sul DB
        Ora = findViewById(R.id.fascia_oraria);
        Mod = findViewById(R.id.modalita);
        EditCity = findViewById(R.id.city_field);
        EditInfo = findViewById(R.id.info_box);
        create_ad = findViewById(R.id.create_ad);

        //metodo per visualizzare datepicker
        show_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MatchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //sets date in EditText
                        date_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        giorno = (dayOfMonth + "/" + (month + 1) + "/" + year).toString();
                    }
                }, year, month, day);
                //shows DatePickerDialog
                datePickerDialog.show();

            }
        });

        switch (choosedSport) {
            case "calcio":
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                        R.array.soccer_mod, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Mod.setAdapter(adapter1);
                break;

            case "tennis":
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                        R.array.tennis_mod, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Mod.setAdapter(adapter2);
                break;

            case "basket":
                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                        R.array.basket_mod, android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Mod.setAdapter(adapter3);
                break;
            case "paddle":
                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                        R.array.paddle_mod, android.R.layout.simple_spinner_item);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Mod.setAdapter(adapter4);
                break;
        }

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.fascia_giornata, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Ora.setAdapter(adapter5);

        //metodo per inviare dati al db
        create_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String citta, modalita, fascia_oraria, info_box;


                fascia_oraria = Ora.getSelectedItem().toString().trim();
                modalita = Mod.getSelectedItem().toString().trim();

                info_box = String.valueOf(EditInfo.getText());
                citta = String.valueOf(EditCity.getText());

                //riprendere email del creatore
                sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
                String email = sharedPreferences.getString("emailLogin", "");

                if (choosedSport.isEmpty()) {
                    return;
                }

                if (citta.isEmpty()) {
                    EditCity.setError("Questo campo è obbliatorio");
                    EditCity.requestFocus();
                    return;
                }

                if (fascia_oraria.isEmpty()) {
                    return;
                }

                if (modalita.isEmpty()) {
                    return;
                }

                if (info_box.isEmpty()) {
                    EditInfo.setError("Questo campo è obbliatorio");
                    EditInfo.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    return;
                }

                if (giorno == null) {
                    Toast.makeText(getApplicationContext(), "Inserire la data", Toast.LENGTH_SHORT).show();
                    return;
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[7];
                        field[0] = "giorno";
                        field[1] = "fascia_oraria";
                        field[2] = "citta";
                        field[3] = "modalita";
                        field[4] = "email_match";
                        field[5] = "info";
                        field[6] = "sport";

                        String[] data = new String[7];
                        data[0] = giorno;
                        data[1] = fascia_oraria;
                        data[2] = citta;
                        data[3] = modalita;
                        data[4] = email;
                        data[5] = info_box;
                        data[6] = choosedSport;
                        PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/createMatch.php", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Annuncio creato")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Search.class);
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
}