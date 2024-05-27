package com.education.allahabad.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.allahabad.Free_Video_Modal;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Purchase_vedio_adapter extends RecyclerView.Adapter<Purchase_vedio_adapter.holder> {

    public Purchase_vedio_adapter(Context context, ArrayList<Free_Video_Modal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    Context context;
    ArrayList<Free_Video_Modal>arrayList;

    @NonNull
    @Override
    public Purchase_vedio_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hello, parent, false);


        return new Purchase_vedio_adapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Purchase_vedio_adapter.holder holder, int position) {
        holder.webView.getSettings().setJavaScriptEnabled(true);
        String youtubeUrl = arrayList.get(position).getUrl();

        String videoId = extractVideoId(youtubeUrl);

        String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

// Load the embed HTML into the WebView
        try {
            // Load the embed HTML into the WebView
            holder.webView.loadData(html, "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //  holder.youTubePlayerVieww.getSettings().setJavaScriptEnabled(true);
        //    holder.youTubePlayerVieww.loadData(html, "text/html", "UTF-8");


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
        WebView webView;
        public holder(@NonNull View itemView) {
            super(itemView);
            webView=itemView.findViewById(R.id.youtube);
        }
    }
}
