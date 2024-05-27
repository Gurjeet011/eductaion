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

import com.education.allahabad.Freepdffiles;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Coursepdfadapter extends RecyclerView.Adapter<Coursepdfadapter.viewholder> {
    Context context;
    ArrayList<Modelclass>arrayList;

    public Coursepdfadapter(Context context, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Coursepdfadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pdfcustom,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Coursepdfadapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
                   holder.t.setText(arrayList.get(position).getTxtttttttt());

                   holder.t.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent = new Intent(context, Freepdffiles.class);
                           //  intent.putExtra("iD",arrayList.get(position).getId());
                           intent.putExtra("pdf",arrayList.get(position).getPdf_file());
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });

        String file_url = "https://techcanopus.in/study/assets/upload/"+arrayList.get(position).getPdf_file();

        holder.down.setOnClickListener(new View.OnClickListener() {
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
        TextView t;
        ImageView pdficon,down;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            t=itemView.findViewById(R.id.pdfname);
            pdficon=itemView.findViewById(R.id.pdfimg);
            down=itemView.findViewById(R.id.pdfdown);

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
