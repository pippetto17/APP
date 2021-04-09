package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[], data2[];

    int images[];

    Context context;

    public MyAdapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.creator_name.setText(data1[position]);
        holder.creator_mod.setText(data2[position]);
        holder.myImage.setImageResource(images[position]);

        holder.buttonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, AdvertS.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView creator_name, creator_mod;
        ImageView myImage;
        Button buttonItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            creator_name = itemView.findViewById(R.id.textViewName);
            creator_mod = itemView.findViewById(R.id.textViewMod);
            myImage = itemView.findViewById(R.id.imageView);

            buttonItem = itemView.findViewById(R.id.buttonItem);
        }
    }
}
