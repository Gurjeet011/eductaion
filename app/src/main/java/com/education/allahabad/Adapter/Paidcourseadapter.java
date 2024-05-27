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
import com.education.allahabad.Modelclass;
import com.education.allahabad.PaidCoursedetails;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Paidcourseadapter extends RecyclerView.Adapter<Paidcourseadapter.viewholder> {
    Context context;

    public Paidcourseadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;

    @NonNull
    @Override
    public Paidcourseadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custompaid,parent,false);
        return new Paidcourseadapter.viewholder(view);    }

    @Override
    public void onBindViewHolder(@NonNull Paidcourseadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.expiredate.setText("Expire Date : "+arrayList.get(position).getExpdate());
        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imgz);

        // Glide.with(context).load(arrayList.get(position).getImg()).into(holder.imageView);
        holder.coursename.setText(Html.fromHtml(arrayList.get(position).getTxt()));

        holder.readmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PaidCoursedetails.class);
                intent.putExtra("idsend",arrayList.get(position).getId());
              /*  intent.putExtra("purchaseid",arrayList.get(position).getPurchase_id());
                intent.putExtra("alreadypurchid",arrayList.get(position).getAlready_purchase());
                intent.putExtra("pur_status",arrayList.get(position).getPurchase_status());
*/
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
        TextView coursename,expiredate;
        Button readmor;
        ImageView imgz;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            coursename=itemView.findViewById(R.id.te1);
            expiredate=itemView.findViewById(R.id.tt2);
            readmor=itemView.findViewById(R.id.Readmoree);
            imgz=itemView.findViewById(R.id.img12);



        }
    }
}
