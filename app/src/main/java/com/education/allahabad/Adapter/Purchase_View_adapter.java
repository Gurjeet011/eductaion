package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.education.allahabad.Modelclass;
import com.education.allahabad.Purchase_vedio;
import com.education.allahabad.R;
import com.education.allahabad.purchaase_pdf;

import java.util.ArrayList;

public class Purchase_View_adapter extends RecyclerView.Adapter<Purchase_View_adapter.holder> {
    Context context;

    public Purchase_View_adapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass>arrayList;
    @NonNull
    @Override
    public Purchase_View_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customx,parent,false);

        return new Purchase_View_adapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Purchase_View_adapter.holder holder, @SuppressLint("RecyclerView") int position) {


        holder.coursename.setText(arrayList.get(position).getCoursname());
        String img_uri2 = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg2();
        Glide.with(context).load(img_uri2).into(holder.img);

        holder.webView.getSettings().setJavaScriptEnabled(true);
        String youtubeUrl = arrayList.get(position).getGet_link();
        String videoId = extractVideoId(youtubeUrl);
        String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        try {
            // Load the embed HTML into the WebView
            holder.webView.loadData(html, "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.vediox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Purchase_vedio.class);
                intent.putExtra("id", arrayList.get(position).getId());
                //   intent.putExtra("course_name",arrayList.get(position).getTxt());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                holder.vediox.setBackground(context.getResources().getDrawable(R.drawable.signup));
                holder.pdfx.setBackground(context.getResources().getDrawable(R.drawable.white_background));
            }
        });
        holder.pdfx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, purchaase_pdf.class);
                intent.putExtra("id", arrayList.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                holder.pdfx.setBackground(context.getResources().getDrawable(R.drawable.signup));
                holder.vediox.setBackground(context.getResources().getDrawable(R.drawable.white_background));


            }
        });


    }

    private String extractVideoId(String youtubeUrl) {
        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getLastPathSegment();
        if (videoId != null && videoId.contains("?")) {
            // If there are query parameters, remove them
            videoId = videoId.substring(0, videoId.indexOf("?"));
        }
        return videoId;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView img;
        WebView webView;
        TextView coursename,vediox,pdfx;
        public holder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgdesz);
            webView=itemView.findViewById(R.id.youtube_player_viewx);
            coursename=itemView.findViewById(R.id.text_namex);
            vediox=itemView.findViewById(R.id.vediosx);
            pdfx=itemView.findViewById(R.id.pdfx);

        }


    }
}
