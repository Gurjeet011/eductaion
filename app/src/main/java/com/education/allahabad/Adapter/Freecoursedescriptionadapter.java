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
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.education.allahabad.CoursePdf;
import com.education.allahabad.Freevedios;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Freecoursedescriptionadapter extends RecyclerView.Adapter<Freecoursedescriptionadapter.viewholder> {
    Context context;

    public Freecoursedescriptionadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass> arrayList;

    @NonNull
    @Override
    public Freecoursedescriptionadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.freecoursedescription,parent,false);

        return new Freecoursedescriptionadapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Freecoursedescriptionadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt2.setText(Html.fromHtml(arrayList.get(position).getDes()));
        holder.txt3.setText(Html.fromHtml(arrayList.get(position).getDes2()));
        holder.txtname.setText(arrayList.get(position).getTxt());
       /* String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageView);*/
        String img_uri2 = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg2();
        Glide.with(context).load(img_uri2).into(holder.imageView);

        holder.youTubePlayerView.getSettings().setJavaScriptEnabled(true);
        String youtubeUrl = arrayList.get(position).getGet_link();
       // Toast.makeText(context, arrayList.get(position).getVedio_id()+"", Toast.LENGTH_SHORT).show();

// Extract the video ID from the URL
        String videoId = extractVideoId(youtubeUrl);

// Construct the embed HTML to load in the WebView
        String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

// Load the embed HTML into the WebView
        try {
            // Load the embed HTML into the WebView
            holder.youTubePlayerView.loadData(html, "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.vediobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Freevedios.class);
              intent.putExtra("idd", arrayList.get(position).getId());
             //   intent.putExtra("course_name",arrayList.get(position).getTxt());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                holder.vediobtn.setBackground(context.getResources().getDrawable(R.drawable.signup));
                holder.pdfbtn.setBackground(context.getResources().getDrawable(R.drawable.white_background));
            }
        });
        holder.pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CoursePdf.class);
                intent.putExtra("idd", arrayList.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                holder.pdfbtn.setBackground(context.getResources().getDrawable(R.drawable.signup));
                holder.vediobtn.setBackground(context.getResources().getDrawable(R.drawable.white_background));


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtname,txt2,txt3;
        TextView vediobtn,pdfbtn;

        WebView youTubePlayerView;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgdes);
            vediobtn=itemView.findViewById(R.id.vedios);
            pdfbtn=itemView.findViewById(R.id.pdf);
            txtname=itemView.findViewById(R.id.text_name);

            //  imageView2=itemView.findViewById(R.id.imgdes2);
            txt2=itemView.findViewById(R.id.des);
            txt3=itemView.findViewById(R.id.summary);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);

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
