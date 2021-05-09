package com.example.app.Match;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.app.R;

public class MyPersonalMatchAdapter extends MyAdapter{
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

                });

                cancel.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                return true;
            }
        });
    }
}
