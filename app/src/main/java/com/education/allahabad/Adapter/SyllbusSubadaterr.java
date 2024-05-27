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
import com.education.allahabad.Freepdffiles;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class SyllbusSubadaterr extends RecyclerView.Adapter<SyllbusSubadaterr.viewholder> {
    Context context;

    click clicks;

    public SyllbusSubadaterr(Context context, ArrayList<Modelclass> arrayList,click clicks) {
        this.context = context;
        this.arrayList = arrayList;
        this.clicks = clicks;
    }

    ArrayList<Modelclass> arrayList;

    @NonNull
    @Override
    public SyllbusSubadaterr.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabussubcustom, parent, false);

        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SyllbusSubadaterr.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtnamex.setText(arrayList.get(position).getCat());
        holder.txtnamex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Freepdffiles.class);
                //  intent.putExtra("iD",arrayList.get(position).getId());
                intent.putExtra("pdf", arrayList.get(position).getPdf_file());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        String fileUrl = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getPdf_file();
        holder.downimgx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clicks.download_click(fileUrl);


            }
        });

        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.iconimgx);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class viewholder extends RecyclerView.ViewHolder {
        ImageView iconimgx,downimgx;
        TextView txtnamex;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iconimgx = itemView.findViewById(R.id.imgcbusxxzxz);
            downimgx = itemView.findViewById(R.id.downlodessyllx);
            txtnamex = itemView.findViewById(R.id.textsubb);
        }
    }
    public interface click{
        void download_click(String fileUrl);
    }
}
