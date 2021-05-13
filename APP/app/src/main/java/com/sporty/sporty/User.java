package com.sporty.sporty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sporty.sporty.Match.CreatorAdapter;
import com.sporty.sporty.Match.CreatorMatchModel;
import com.sporty.sporty.Match.MatchModel;
import com.sporty.sporty.Match.MyPersonalMatchAdapter;
import com.sporty.sporty.Match.SplitCreatorMatch;
import com.sporty.sporty.Match.SplitData;
import com.sporty.sporty.Match.SplitSavedMatch;
import com.sporty.sporty.Match.SplitUserData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class User extends AppCompatActivity {

    private TextView logout;
    public TextView nome, email, eta;
    Dialog dialog1;
    RecyclerView recycle;
    public CreatorAdapter matchAdapter;
    ArrayList<CreatorMatchModel> list;
    TextView textNone;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String[] arrayMatchPersonali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        nome = findViewById(R.id.nome_u);
        email = findViewById(R.id.mail_u);
        eta = findViewById(R.id.Eta);

        dialog1 = new Dialog(User.this);
        dialog1.setContentView(R.layout.logout_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }

        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(false);

        CardView logout_btn = dialog1.findViewById(R.id.logout_btn);
        CardView cancel = dialog1.findViewById(R.id.cancel_btn);

        logout_btn.setOnClickListener(v -> userLogOut());

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> dialog1.show());

        cancel.setOnClickListener(v -> dialog1.dismiss());

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

                    case R.id.navigation_fav:
                        startActivity(new Intent(getApplicationContext(), FavMatch.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_add:
                        startActivity(new Intent(getApplicationContext(), AddMatch.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_user:
                        return true;
                }

                return false;
            }
        });

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
                PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/userdatametodo.php", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();

                        String a[] = SplitUserData.returnUserData(result);

                        nome.setText(a[0]);
                        email.setText(a[1]);
                        eta.setText(a[2]);
                    }
                }
            }
        });

        setListStyle();
        loadList(arrayMatchPersonali);
    }

    public void setListStyle() {
        recycle = findViewById(R.id.create_recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        matchAdapter = new CreatorAdapter(this, list, "Calcio", this);
        recycle.setAdapter(matchAdapter);
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
                        PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/matchcreatormetodo.php ", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();

                                CreatorMatchModel[] ArrayMatchRecuperati = SplitCreatorMatch.returnCreatorMatch(result);

                                //Istanziamneto item nella recycleView per ogni match caricato
                                for (int i = 0; i < ArrayMatchRecuperati.length; i++) {

                                    CreatorMatchModel Model = new CreatorMatchModel();
                                    Model.giorno = ArrayMatchRecuperati[i].giorno;
                                    Model.citta = ArrayMatchRecuperati[i].citta;
                                    Model.fasciaOraria = ArrayMatchRecuperati[i].fasciaOraria;
                                    Model.modalita = ArrayMatchRecuperati[i].modalita;
                                    Model.sport = ArrayMatchRecuperati[i].sport;
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(User.this, AddMatch.class));
        finish();

    }
}