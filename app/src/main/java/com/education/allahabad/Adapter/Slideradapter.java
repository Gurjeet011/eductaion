package com.education.allahabad.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class Slideradapter extends SliderViewAdapter<Slideradapter.MyViewHolder> {

    Context context;
    ArrayList<Modelclass> arrayList;

    public Slideradapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_banner,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {

        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();

        try {
            Glide.with(context).load(img_uri).into(viewHolder.imageView);
        } catch (Exception e) {
            Log.e("GlideError", "Glide error: " + e.getMessage());
        }
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends ViewHolder {
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bannerimg);
        }

    }
}
