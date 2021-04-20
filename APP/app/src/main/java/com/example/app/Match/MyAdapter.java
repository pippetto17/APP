package com.example.app.Match;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    ArrayList<MatchModel> matchList;
    Context context;
    String choosedSport;

    public MyAdapter(Context pContext, ArrayList<MatchModel> pList, String pChoosedSport) {
        this.matchList = pList;
        this.context = pContext;
        this.choosedSport = pChoosedSport;
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

        switch (choosedSport) {
            case "Calcio":
                holder.bgItem.setImageResource(R.drawable.ic_item_soccer);
                break;

            case "Tennis":
                holder.bgItem.setImageResource(R.drawable.ic_item_tennis);
                break;

            case "Basket":
                holder.bgItem.setImageResource(R.drawable.ic_item_basket);
                break;
            case "Paddle":
                holder.bgItem.setImageResource(R.drawable.ic_item_paddle);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            citta = itemView.findViewById(R.id.cittaT);
            fasciaOraria = itemView.findViewById(R.id.fasciaT);
            giorno = itemView.findViewById(R.id.giornoT);
            eta = itemView.findViewById(R.id.etaT);
            modalita = itemView.findViewById(R.id.modalitaT);
            nomeCognome = itemView.findViewById(R.id.nomeCognomeT);
            bgItem = itemView.findViewById(R.id.backgroundItem);

        }
    }
}
