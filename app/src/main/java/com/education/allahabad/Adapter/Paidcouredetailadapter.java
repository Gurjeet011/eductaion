package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.education.allahabad.About;
import com.education.allahabad.Modelclass;
import com.education.allahabad.Purchase_Course;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Paidcouredetailadapter extends RecyclerView.Adapter<Paidcouredetailadapter.viewholder> {
    Context context;

    public Paidcouredetailadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;
    @NonNull
    @Override
    public Paidcouredetailadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.paiddes,parent,false);

        return new Paidcouredetailadapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Paidcouredetailadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {

       /* String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageView);*/
        String img_uri2 = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg2();
        Glide.with(context).load(img_uri2).into(holder.imageViewz);

        holder.webView.getSettings().setJavaScriptEnabled(true);
        String youtubeUrl = arrayList.get(position).getGet_link();
        // Toast.makeText(context, arrayList.get(position).getVedio_id()+"", Toast.LENGTH_SHORT).show();

// Extract the video ID from the URL
        String videoId = extractVideoId(youtubeUrl);

// Construct the embed HTML to load in the WebView
        String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

// Load the embed HTML into the WebView
        try {
            // Load the embed HTML into the WebView
            holder.webView.loadData(html, "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Toast.makeText(context, arrayList.get(position).getStaatus()+"", Toast.LENGTH_SHORT).show();
//        if (arrayList.get(position).getStaatus().equals("0")){
//            holder.status.setVisibility(View.VISIBLE);
//            holder.status.setText("Pending");
//            holder.buynow.setVisibility(View.GONE);
//        }else if (arrayList.get(position).getStaatus().equals("1")){
//            holder.status.setVisibility(View.VISIBLE);
//            holder.status.setText("Success");
//            holder.buynow.setVisibility(View.GONE);
//        }else if (arrayList.get(position).getStaatus().equals("2")){
//            holder.status.setVisibility(View.VISIBLE);
//            holder.status.setText("Reject");
//            holder.buynow.setVisibility(View.GONE);
//        }
//
//        holder.buynow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(context, Purchase_Course.class);
//                intent.putExtra("id",arrayList.get(position).getId());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(intent);
//            }
//        });
//
//
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView price,coursename, des,adddes;
        ImageView imageViewz;
        WebView webView;
        Button buynow,status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.pricee);
            buynow=itemView.findViewById(R.id.buynow);
            status=itemView.findViewById(R.id.status);

            coursename=itemView.findViewById(R.id.text_namee);
            des=itemView.findViewById(R.id.dess);
            adddes=itemView.findViewById(R.id.adddesc);
            imageViewz=itemView.findViewById(R.id.imgdesz );
            webView=itemView.findViewById(R.id.youtube_player);


        }
    }
    private String extractVideoId(String youtubeUrl) {
        // Extract the video ID from the URL
        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getLastPathSegment();
        if (videoId != null && videoId.contains("?")) {
            // If there are query parameters, remove them
            videoId = videoId.substring(0, videoId.indexOf("?"));
        }
        return videoId;
    }
}

