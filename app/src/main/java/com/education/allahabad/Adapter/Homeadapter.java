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

import com.education.allahabad.FreeCourse;
import com.education.allahabad.Modelclass2;
import com.education.allahabad.PDF;
import com.education.allahabad.PaidCourse;
import com.education.allahabad.R;
import com.education.allahabad.Syllabus;
import com.education.allahabad.TestSeries;

import java.util.ArrayList;

public class Homeadapter extends RecyclerView.Adapter<Homeadapter.MyViewHolder> {
    public Homeadapter(Context context, ArrayList<Modelclass2> modelclasses) {
        this.context = context;
        this.modelclasses = modelclasses;
    }

    Context context;
    ArrayList<Modelclass2> modelclasses;


    @NonNull
    @Override
    public Homeadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customhome,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Homeadapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(modelclasses.get(position).getImg());
        holder.textView.setText(modelclasses.get(position).getText());

        if (modelclasses.get(position).getText().equals("Free Course")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  //  Toast.makeText(context, modelclasses.get(position).getText()+"", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, FreeCourse.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }else if (modelclasses.get(position).getText().equals("Free Test Series")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(context, modelclasses.get(position).getText()+"", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, TestSeries.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            });

            }else if (modelclasses.get(position).getText().equals("Free PDF")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context, modelclasses.get(position).getText()+"", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PDF.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        } else if (modelclasses.get(position).getText().equals("Paid Course")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context, modelclasses.get(position).getText()+"", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PaidCourse.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }  else if (modelclasses.get(position).getText().equals("Syllabus")){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Toast.makeText(context, modelclasses.get(position).getText()+"", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Syllabus.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

        }
    }

    @Override
    public int getItemCount() {
        return modelclasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imghome);
            textView=itemView.findViewById(R.id.txt1);


        }
    }
}
