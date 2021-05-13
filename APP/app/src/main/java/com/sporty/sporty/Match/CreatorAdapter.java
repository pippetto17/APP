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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sporty.sporty.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class CreatorAdapter extends RecyclerView.Adapter<CreatorAdapter.MyViewHolder> {


    ArrayList<CreatorMatchModel> matchList;
    Context context;
    String choosedSport;
    Activity ctx;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public CreatorAdapter(Context pContext, ArrayList<CreatorMatchModel> pList, String pChoosedSport, Activity activity) {
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

        View v = LayoutInflater.from(context).inflate(R.layout.creator_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CreatorMatchModel matchListModel = matchList.get(position);

        //Set campi e immagine di sfondo
        holder.giorno.setText(matchListModel.giorno);
        holder.fasciaOraria.setText(matchListModel.fasciaOraria);
        holder.citta.setText(matchListModel.citta);
        holder.modalita.setText(matchListModel.modalita);

        switch (matchListModel.sport) {
            case "calcio":
                holder.bgItem.setImageResource(R.drawable.c_item_soccer);
                break;

            case "tennis":
                holder.bgItem.setImageResource(R.drawable.c_item_tennis);
                break;

            case "basket":
                holder.bgItem.setImageResource(R.drawable.c_item_basket);
                break;
            case "paddle":
                holder.bgItem.setImageResource(R.drawable.c_item_paddle);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView giorno, citta, fasciaOraria, modalita;
        ImageView bgItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            citta = itemView.findViewById(R.id.cittaC);
            fasciaOraria = itemView.findViewById(R.id.fasciaC);
            giorno = itemView.findViewById(R.id.giornoC);
            modalita = itemView.findViewById(R.id.modalitaC);
            bgItem = itemView.findViewById(R.id.bg_creator_match);


        }
    }
}