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
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;
import com.education.allahabad.SyallubusSUb;

import java.util.ArrayList;

public class Syllabusadapter extends RecyclerView.Adapter<Syllabusadapter.viewholder> {
    Context context;

    public Syllabusadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;

    @NonNull
    @Override
    public Syllabusadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabuscustom,parent,false);

        return new Syllabusadapter.viewholder(view);    }


    @Override
    public void onBindViewHolder(@NonNull Syllabusadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtname.setText(arrayList.get(position).getCat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, SyallubusSUb.class);
                intent.putExtra("sylid",arrayList.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.iconimg);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView iconimg,arrowimg;
        TextView txtname;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iconimg=itemView.findViewById(R.id.imgsylabus);
           // arrowimg=itemView.findViewById(R.id.arrow);
           txtname =itemView.findViewById(R.id.syllabustxt);

        }
    }
}
