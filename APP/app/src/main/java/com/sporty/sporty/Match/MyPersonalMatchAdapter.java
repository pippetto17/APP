package com.sporty.sporty.Match;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.sporty.sporty.FavMatch;
import com.sporty.sporty.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MyPersonalMatchAdapter extends MyAdapter {

    public MyPersonalMatchAdapter(Context pContext, ArrayList<MatchModel> pList, String pChoosedSport, Activity activity) {
        super(pContext, pList, pChoosedSport, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.remove_match_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(ctx.getDrawable(R.drawable.dialog_bg));
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        CardView remove_btn = dialog.findViewById(R.id.remove_btn);
        CardView cancel = dialog.findViewById(R.id.cancel_btn);

        holder.buttonitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                dialog.show();

                remove_btn.setOnClickListener(v -> {

                    MatchModel matchListModel = matchList.get(position);
                    removeFavouriteMatch(matchListModel.idMatch);
                    dialog.dismiss();
                });

                cancel.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                return true;
            }
        });
    }

    /**
     * Rimuove l'id del match indicato dalla lista dei favoriti
     * Viene passato come parametro l'id del match da rimuovere
     *
     * @param idMatch
     */
    public void removeFavouriteMatch(String idMatch) {

        //Recupero email dalle shared preferences
        sharedPreferences = ctx.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
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

                        //Stringa dei match salvati
                        String result = putData.getResult();

                        //Nuova stringa dei match salvati senza il match appena rimosso
                        String idMatchDeleted = removeFavMatch.returnNewSavedMatch(result, idMatch);

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String[] field = new String[2];
                                field[0] = "string";
                                field[1] = "email";


                                String[] data = new String[2];
                                data[0] = idMatchDeleted;
                                data[1] = emailRecuperata;
                                PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/modsalvatimetodo.php", "POST", field, data);

                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Salvati aggiornati")) {
                                            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                        });


                    }
                }
            }
        });

        //Reload Fav list
        Intent intent = new Intent(ctx, FavMatch.class);
        ctx.finish();
        ctx.startActivity(intent);
    }

    /**
     * Carica l'annuncio aperto ma non da la possibilita di salvare l'annuncio
     *
     * @param nome
     * @param eta
     * @param giorno
     * @param modalita
     * @param fascia
     * @param citta
     * @param info
     * @param idMatch
     */
    @Override
    public void openItem(String nome, String eta, String giorno, String modalita, String fascia, String citta, String info, String idMatch) {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();

        int DeviceTotalWidth = metrics.widthPixels;
        int DeviceTotalHeight = metrics.heightPixels;

        final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.open_adv_fav);

        Nome = dialog.findViewById(R.id.nomeCognomeO);
        Eta = dialog.findViewById(R.id.etaO);
        Giorno = dialog.findViewById(R.id.giornoO);
        Modalita = dialog.findViewById(R.id.modalitaO);
        Fascia = dialog.findViewById(R.id.fasciaO);
        Citta = dialog.findViewById(R.id.cittaO);
        Info = dialog.findViewById(R.id.infoO);
        Banner = dialog.findViewById(R.id.banner);
        Player = dialog.findViewById(R.id.playerO);
        Ball = dialog.findViewById(R.id.ballO);


        Nome.setText(nome);
        Eta.setText(eta);
        Giorno.setText(giorno);
        Modalita.setText(modalita);
        Fascia.setText(fascia);
        Citta.setText(citta);
        Info.setText(info);

        dialog.getWindow().setLayout(DeviceTotalWidth, DeviceTotalHeight);
        dialog.show();

        ImageView back_button = dialog.findViewById(R.id.back_button);

        back_button.setOnClickListener(v -> {
            dialog.dismiss();
        });

        switch (choosedSport) {
            case "Calcio":
                Banner.setCardBackgroundColor(Color.parseColor("#4BA44A"));
                Player.setImageResource(R.drawable.ic_player_soccer);
                Ball.setImageResource(R.drawable.ic_ball_soccer);
                break;

            case "Tennis":
                Banner.setCardBackgroundColor(Color.parseColor("#D5762E"));
                Player.setImageResource(R.drawable.ic_player_tennis);
                Ball.setImageResource(R.drawable.ic_ball_tennisss);
                break;

            case "Basket":
                Banner.setCardBackgroundColor(Color.parseColor("#CA3838"));
                Player.setImageResource(R.drawable.ic_player_basket);
                Ball.setImageResource(R.drawable.ic_ball_basket);
                break;
            case "Paddle":
                Banner.setCardBackgroundColor(Color.parseColor("#3333B7"));
                Player.setImageResource(R.drawable.ic_player_padel);
                Ball.setImageResource(R.drawable.ic_ball_paddle);
                break;
        }
    }
}
