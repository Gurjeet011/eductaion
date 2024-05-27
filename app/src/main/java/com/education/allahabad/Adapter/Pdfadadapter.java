package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.education.allahabad.Freepdffiles;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Pdfadadapter extends RecyclerView.Adapter<Pdfadadapter.viewholder> {
    Context context;

    public Pdfadadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Modelclass> arrayList;
    @NonNull
    @Override
    public Pdfadadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ssc,parent,false);

        return new Pdfadadapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pdfadadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.pdf.setText(arrayList.get(position).getPdftext());

        holder.pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Freepdffiles.class);
             //  intent.putExtra("iD",arrayList.get(position).getId());
                intent.putExtra("pdf",arrayList.get(position).getPdf_file());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        String img_uri = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getImg();
        Glide.with(context).load(img_uri).into(holder.imageView);

        String file_url = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getPdf_file();

        holder.downlodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPdf(file_url);
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView pdf;
        ImageView imageView,downlodes;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            pdf = itemView.findViewById(R.id.pdftxt);
            imageView = itemView.findViewById(R.id.img);
            downlodes = itemView.findViewById(R.id.downlodes);


        }
        }

        private void downloadPdf(String fileUrl) {

            // Download PDF
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));

            // Save the downloaded file to the Download directory
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloaded.pdf");

            DownloadManager downloadManager = (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
                Toast.makeText(context.getApplicationContext(), "PDF download started", Toast.LENGTH_SHORT).show();
                // Update download status of PdfItem and notify adapter
            } else {
                Toast.makeText(context.getApplicationContext(), "Failed to start PDF download", Toast.LENGTH_SHORT).show();
            }
        }


}
