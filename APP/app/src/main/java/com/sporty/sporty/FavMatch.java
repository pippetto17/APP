package com.sporty.sporty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sporty.sporty.Match.MatchModel;
import com.sporty.sporty.Match.MyPersonalMatchAdapter;
import com.sporty.sporty.Match.SplitData;
import com.sporty.sporty.Match.SplitSavedMatch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class FavMatch extends AppCompatActivity {

    //Item recycleView
    RecyclerView recycle;
    public MyPersonalMatchAdapter matchAdapter;
    ArrayList<MatchModel> list;
    TextView textNone;

    //URL basico per la SELECT
    final String urlServer = "http://93.43.208.27/carletti/sportydb/";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String[] arrayIDMatchSalvati;
    int x = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.search_wallpaper);

        textNone = findViewById(R.id.text_none);

        setListStyle();

        requestFavourites();

        //NAVIGATION BAR
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_fav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()) {
                    case R.id.navigation_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_fav:
                        return true;

                    case R.id.navigation_add:
                        startActivity(new Intent(getApplicationContext(), AddMatch.class));
                        overridePendingTransition(0, 0);
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

    public void setListStyle() {
        recycle = findViewById(R.id.fav_recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        matchAdapter = new MyPersonalMatchAdapter(this, list, "Calcio", this);
        recycle.setAdapter(matchAdapter);
    }

    /**
     * Recupera la lista dei match salvati dell'utente connesso e li carica nella recycleview
     */
    public void requestFavourites() {

        //Recupero email dalle shared preferences
        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        String emailRecuperata = sharedPreferences.getString("emailLogin", "");

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";
                String[] data = new String[1];
                data[0] = emailRecuperata;
                PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/salvametodo.php ", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();

                        //Array con gli ID dei match salvati
                        arrayIDMatchSalvati = SplitSavedMatch.returnSavedMatches(result);

                        loadList(arrayIDMatchSalvati);

                    }
                }
            }
        });
    }

    public void loadList(String[] array) {


        //Controlla se c'è almeno un match salvato
        if (array.length > 0 && !array[0].isEmpty()) {

            textNone.setVisibility(View.GONE);

            //Per ogni match recupera i dati
            for (String f : array) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[1];
                        field[0] = "id_match";
                        String[] data = new String[1];
                        data[0] = f;
                        PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/salvaIdmetodo.php ", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();

                                MatchModel[] ArrayMatchRecuperati = SplitData.returnInfoMatch(result);

                                //Istanziamneto item nella recycleView per ogni match caricato
                                for (int i = 0; i < ArrayMatchRecuperati.length; i++) {

                                    MatchModel Model = new MatchModel();
                                    Model.giorno = ArrayMatchRecuperati[i].giorno;
                                    Model.citta = ArrayMatchRecuperati[i].citta;
                                    Model.fasciaOraria = ArrayMatchRecuperati[i].fasciaOraria;
                                    Model.emailCreatore = ArrayMatchRecuperati[i].emailCreatore;
                                    Model.nomeCognomeCreatore = ArrayMatchRecuperati[i].nomeCognomeCreatore;
                                    Model.info = ArrayMatchRecuperati[i].info;
                                    Model.modalita = ArrayMatchRecuperati[i].modalita;
                                    Model.eta = ArrayMatchRecuperati[i].eta;
                                    Model.idMatch = ArrayMatchRecuperati[i].idMatch;
                                    Model.kindSport = ArrayMatchRecuperati[i].kindSport;
                                    list.add(Model);
                                }
                                matchAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

            }


        } else {
            textNone.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FavMatch.this, FavMatch.class));
        finish();

    }
}
