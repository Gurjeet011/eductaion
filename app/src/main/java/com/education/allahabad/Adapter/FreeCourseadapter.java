package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.education.allahabad.Freecoursedescription;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class FreeCourseadapter extends RecyclerView.Adapter<FreeCourseadapter.Myviewholder> {
    Context context;

    public FreeCourseadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass> arrayList;
    @NonNull
    @Override
    public FreeCourseadapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom,parent,false);
        return new FreeCourseadapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeCourseadapter.Myviewholder holder, @SuppressLint("RecyclerView") int position) {
               holder.textView2.setText("Expire Date : "+arrayList.get(position).getExpdate());
        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageView);

       // Glide.with(context).load(arrayList.get(position).getImg()).into(holder.imageView);

               holder.textView.setText(Html.fromHtml(arrayList.get(position).getTxt()));


                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Freecoursedescription.class);
                        intent.putExtra("id1", arrayList.get(position).getId());
                        intent.putExtra("course_name",arrayList.get(position).getTxt());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,textView2;
        Button btn;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img1);
            textView = itemView.findViewById(R.id.t1);
            textView2 = itemView.findViewById(R.id.t2);

            btn = itemView.findViewById(R.id.Readmore);


        }
    }
}
