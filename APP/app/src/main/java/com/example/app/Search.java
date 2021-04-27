package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.Match.Match;
import com.example.app.Match.MatchModel;
import com.example.app.Match.MyAdapter;
import com.example.app.Match.SplitData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    //Item recycleView
    RecyclerView recycle;
    MyAdapter matchAdapter;
    ArrayList<MatchModel> list;

    //Array background
    int imagesSport[] = {R.drawable.ic_item_soccer, R.drawable.ic_item_tennis, R.drawable.ic_item_paddle, R.drawable.ic_item_basket};

    //Spinner filtri sport
    Spinner filterBy;

    //URL basico per la SELECT
    final String urlServer = "http://93.43.208.27/carletti/sportydb/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.search_wallpaper);

        setListStyle();

        filterBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Rimozione elementi dalla recycleView
                list.clear();
                matchAdapter.notifyDataSetChanged();

                //Set background per la nuova lista in base allo sport scelto
                matchAdapter.setChoosedSport(filterBy.getSelectedItem().toString().trim());

                //Caricamento nuovi match
                loadList(filterBy.getSelectedItem().toString().trim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //NAVIGATION BAR
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()) {
                    case R.id.navigation_search:
                        return true;

                    case R.id.navigation_add:
                        startActivity(new Intent(getApplicationContext(), Add.class));
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

    /**
     * Author: Luca Orlandi
     * Carica i dati in base allo sport scelto
     * Viene passato come parametro lo sport
     * @param sportDaCercare
     */
    public void loadList( String sportDaCercare ){

        //Url specifico per lo sport
        String urlSport = urlServer;

        //Modifica dello url in base allo sport scelto
        switch (sportDaCercare) {
            case "Calcio":
                urlSport += "ItemSoccer.php";
                break;

            case "Tennis":
                urlSport += "ItemTennis.php";
                break;

            case "Basket":
                urlSport += "ItemBasket.php";
                break;

            case "Paddle":
                urlSport += "ItemPaddle.php";
                break;
        }


        //Caricamento dati
        FetchData fetchData = new FetchData(urlSport);
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {

                //Risultato SELECT
                String result = fetchData.getResult();

                if(!result.isEmpty()){

                    //Array match recuperati
                    MatchModel[] ArrayMatchRecuperati = SplitData.returnInfoMatch(result);

                    //Istanziamneto item nella recycleView per ogni match caricato
                    for(int i = 0; i < ArrayMatchRecuperati.length; i++){

                        MatchModel Model = new MatchModel();
                        Model.giorno = ArrayMatchRecuperati[i].giorno;
                        Model.citta = ArrayMatchRecuperati[i].citta;
                        Model.fasciaOraria = ArrayMatchRecuperati[i].fasciaOraria;
                        Model.emailCreatore = ArrayMatchRecuperati[i].emailCreatore;
                        Model.nomeCognomeCreatore = ArrayMatchRecuperati[i].nomeCognomeCreatore;
                        Model.info = ArrayMatchRecuperati[i].info;
                        Model.modalita = ArrayMatchRecuperati[i].modalita;
                        Model.eta = ArrayMatchRecuperati[i].eta;
                        list.add(Model);
                    }

                    //Notifica l'adapter dei nuovi dati caricati
                    matchAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(Search.this, "Nessun match trovato", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    /**
     * Author: Luca Orlandi
     * Setta lo stile della lista e della recycleView
     */
    public void setListStyle(){
        filterBy = findViewById(R.id.filterBy);
        recycle = findViewById(R.id.recyclerView);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        matchAdapter = new MyAdapter(this, list, filterBy.getSelectedItem().toString().trim());
        recycle.setAdapter(matchAdapter);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Search.this, Search.class));
        finish();

    }
}