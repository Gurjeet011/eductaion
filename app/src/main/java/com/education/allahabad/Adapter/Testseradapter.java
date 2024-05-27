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
import com.education.allahabad.TestseriesSUB;

import java.util.ArrayList;

public class Testseradapter extends RecyclerView.Adapter<Testseradapter.viewholder> {
    Context context;

    public Testseradapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;
    @NonNull
    @Override
    public Testseradapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test,parent,false);

        return new Testseradapter.viewholder(view);    }

    @Override
    public void onBindViewHolder(@NonNull Testseradapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.tt.setText(arrayList.get(position).getCat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TestseriesSUB.class);
                intent.putExtra("id", arrayList.get(position).getId());
                //   intent.putExtra("catid",arrayList.get(position).getId());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageViewn);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tt;
        ImageView imageViewn;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tt=itemView.findViewById(R.id.pdFtest);
            imageViewn=itemView.findViewById(R.id.imgc);

        }
    }
}
