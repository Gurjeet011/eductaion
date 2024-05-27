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
import com.education.allahabad.pdfListcategory;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class pdfcatrgory extends RecyclerView.Adapter<pdfcatrgory.viewholder> {
    Context context;

    public pdfcatrgory(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;

    @NonNull
    @Override
    public pdfcatrgory.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pdf,parent,false);

        return new pdfcatrgory.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pdfcatrgory.viewholder holder, @SuppressLint("RecyclerView") int position) {
        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageView);
        holder.textView.setText(arrayList.get(position).getCat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, pdfListcategory.class);
                intent.putExtra("iD",arrayList.get(position).getId());
                //  intent.putExtra("course_name",arrayList.get(position).getTxt());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.pdFCAT);
            imageView=itemView.findViewById(R.id.imgc);

        }
    }
}
