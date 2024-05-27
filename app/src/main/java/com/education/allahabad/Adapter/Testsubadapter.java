package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.education.allahabad.Mocktest;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Testsubadapter extends RecyclerView .Adapter<Testsubadapter.viewholder> {
    Context context;

    public Testsubadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;

    @NonNull
    @Override
    public Testsubadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.full_test,parent,false);

        return new Testsubadapter.viewholder(view);    }


    @Override
    public void onBindViewHolder(@NonNull Testsubadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(arrayList.get(position).getCat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Mocktest.class);
              //  intent.putExtra("id",arrayList.get(position).getId());
                //intent.putExtra("idx", arrayList.get(position).getId());

                intent.putExtra("catid",arrayList.get(position).getCatidxzx());
                intent.putExtra("subid",arrayList.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageViewm);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageView btnn;
        ImageView imageViewm;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.textsub);
            btnn=itemView.findViewById(R.id.viewdetails);
            imageViewm=itemView.findViewById(R.id.img);

        }
    }
}
