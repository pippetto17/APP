package com.example.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class MatchActivity extends AppCompatActivity {
    EditText EditSport, EditFascia, EditMod, EditInfo;

    SharedPreferences sharedPreferences;

    TextView date_text;
    CardView show_dialog;
    Calendar calendar;

    CardView create_ad;

    String giorno = null;


    int year, month, day;

    String choosedSport;


    public void MatchActivity(String sportName){

        this.choosedSport = sportName.trim();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_match_soccer);
        //getting calendar instance
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //accessing EditText and Button
        date_text = (TextView) findViewById(R.id.date_text);
        show_dialog = (CardView) findViewById(R.id.show_dialog);

        //dati da passare al onClick per la creazione del match sul DB
        EditSport = (EditText) findViewById(R.id.sport);
        EditFascia = (EditText) findViewById(R.id.fascia_oraria);
        EditMod = (EditText) findViewById(R.id.modalita);
        EditInfo = (EditText) findViewById(R.id.info_box);
        create_ad = (CardView) findViewById(R.id.create_ad);

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

        //metodo per inviare dati al db
        create_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sport, fascia_oraria, modalita, info_box;
                sport = String.valueOf(EditSport.getText()).trim();
                fascia_oraria = String.valueOf(EditFascia.getText()).trim();
                modalita = String.valueOf(EditMod.getText()).trim();
                info_box = String.valueOf(EditInfo.getText());

                //riprendere email del creatore
                sharedPreferences  = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
                String email = sharedPreferences.getString("emailLogin", "");



                if (!sport.isEmpty() && !fascia_oraria.isEmpty() && !modalita.isEmpty() && !info_box.isEmpty() && !email.isEmpty() && !giorno.isEmpty()) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String[] field = new String[5];
                            field[0] = "giorno";
                            field[1] = "modalita";
                            field[2] = "email_match";
                            field[3] = "info";
                            field[4] = "sport";

                            String[] data = new String[5];
                            data[0] = giorno;
                            data[1] = modalita;
                            data[2] = email;
                            data[3] = info_box;
                            data[4] = sport;
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
                } else {
                    Toast.makeText(getApplicationContext(), "Sono richiesti tutti i campi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}