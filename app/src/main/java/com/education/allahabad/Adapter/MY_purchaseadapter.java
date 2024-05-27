package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.allahabad.Modelclass;
import com.education.allahabad.PaidCoursedetails;
import com.education.allahabad.Purchasehistory;
import com.education.allahabad.R;
import com.education.allahabad.TestseriesSUB;

import java.util.ArrayList;

public class MY_purchaseadapter extends RecyclerView.Adapter<MY_purchaseadapter.holder> {
    Context context;

    public MY_purchaseadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;
    @NonNull
    @Override
    public MY_purchaseadapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customz,parent,false);

        return new MY_purchaseadapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MY_purchaseadapter.holder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(arrayList.get(position).getCoursname());
     //  holder.status.setText(arrayList.get(position).getStaatus());
        if(arrayList.get(position).getStaatus().equals("0")){

            holder.status.setText("Pending");
            holder.status.setTextColor(context.getResources().getColor(R.color.bluex ));
         //   Toast.makeText(context, "Status Pending", Toast.LENGTH_SHORT).show();
        } else if  (arrayList.get(position).getStaatus().equals("1")){
            holder.status.setText("Success");
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PaidCoursedetails.class);
                    intent.putExtra("idsend", arrayList.get(position).getCourseidx());
                    //   intent.putExtra("catid",arrayList.get(position).getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
          //  Toast.makeText(context, "payment success", Toast.LENGTH_SHORT).show();

        } else if (arrayList.get(position).getStaatus().equals("2")){
            holder.status.setText("Reject");
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
           // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }






    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView textView,status;
        public holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.coursename);
            status=itemView.findViewById(R.id.status);

        }
    }
}
