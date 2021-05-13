package com.sporty.sporty.Match;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sporty.sporty.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    ArrayList<MatchModel> matchList;
    Context context;
    String choosedSport;
    Activity ctx;
    TextView Nome;
    TextView Eta;
    TextView Giorno;
    TextView Modalita;
    TextView Fascia;
    TextView Info;
    TextView Citta;
    CardView Banner;
    ImageView Player;
    ImageView Ball;
    ToggleButton SaveButton;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public MyAdapter(Context pContext, ArrayList<MatchModel> pList, String pChoosedSport, Activity activity) {
        this.matchList = pList;
        this.context = pContext;
        this.choosedSport = pChoosedSport;
        this.ctx = activity;
    }


    public void setChoosedSport(String choosedSport) {
        this.choosedSport = choosedSport;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MatchModel matchListModel = matchList.get(position);

        //Set campi e immagine di sfondo
        holder.giorno.setText(matchListModel.giorno);
        holder.fasciaOraria.setText(matchListModel.fasciaOraria);
        holder.citta.setText(matchListModel.citta);
        holder.modalita.setText(matchListModel.modalita);
        holder.nomeCognome.setText(matchListModel.nomeCognomeCreatore);
        holder.eta.setText(matchListModel.eta);

        switch (matchListModel.kindSport) {
            case "calcio":
                holder.bgItem.setImageResource(R.drawable.ic_item_soccer);
                break;

            case "tennis":
                holder.bgItem.setImageResource(R.drawable.ic_item_tennis);
                break;

            case "basket":
                holder.bgItem.setImageResource(R.drawable.ic_item_basket);
                break;
            case "paddle":
                holder.bgItem.setImageResource(R.drawable.ic_item_paddle);
                break;
        }


        holder.buttonitem.setOnClickListener(v -> {
            openItem(matchListModel.nomeCognomeCreatore, matchListModel.eta, matchListModel.giorno, matchListModel.modalita, matchListModel.fasciaOraria, matchListModel.citta, matchListModel.info, matchListModel.idMatch);
        });

    }

    public void openItem(String nome, String eta, String giorno, String modalita, String fascia, String citta, String info, String idMatch) {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();

        int DeviceTotalWidth = metrics.widthPixels;
        int DeviceTotalHeight = metrics.heightPixels;

        final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.open_adv);

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
        SaveButton = dialog.findViewById(R.id.save_button);


        Nome.setText(nome);
        Eta.setText(eta);
        Giorno.setText(giorno);
        Modalita.setText(modalita);
        Fascia.setText(fascia);
        Citta.setText(citta);
        Info.setText(info);

        SaveButton.setOnClickListener(v -> {

            //Recupero id match
            String idMatchRecuperato = idMatch;
            idMatchRecuperato += "$";

            SaveButton.setChecked(true);

            //Recupero email
            sharedPreferences = ctx.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
            String emailRecuperata = sharedPreferences.getString("emailLogin", "");

            saveMatch(idMatchRecuperato, emailRecuperata);

        });

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

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView giorno, citta, fasciaOraria, eta, modalita, nomeCognome;
        ImageView bgItem;
        Button buttonitem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            citta = itemView.findViewById(R.id.cittaT);
            fasciaOraria = itemView.findViewById(R.id.fasciaT);
            giorno = itemView.findViewById(R.id.giornoT);
            eta = itemView.findViewById(R.id.etaT);
            modalita = itemView.findViewById(R.id.modalitaT);
            nomeCognome = itemView.findViewById(R.id.nomeCognomeT);
            bgItem = itemView.findViewById(R.id.backgroundItem);
            buttonitem = itemView.findViewById(R.id.buttonItem);


        }
    }

    public void saveMatch(String idMatchP, String emailP) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "id_match";
                field[1] = "email";


                String[] data = new String[2];
                data[0] = idMatchP;
                data[1] = emailP;
                PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/saved.php", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("Annuncio salvato")) {
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
