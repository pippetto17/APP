package com.example.app.Match;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class MyPersonalMatchAdapter extends MyAdapter{
    public MyPersonalMatchAdapter(Context pContext, ArrayList<MatchModel> pList, String pChoosedSport, Activity activity) {
        super(pContext, pList, pChoosedSport, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.buttonitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ctx, "PREMUTO PER UN PO", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
