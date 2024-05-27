package com.education.allahabad.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.allahabad.Free_Video_Modal;
import com.education.allahabad.Modelclass;
import com.education.allahabad.R;

import java.util.ArrayList;

public class Freevediosadapter extends RecyclerView.Adapter<Freevediosadapter.viewholder> {
    Context context;

    public Freevediosadapter(Context context, ArrayList<Free_Video_Modal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Free_Video_Modal> arrayList;

    @NonNull
    @Override
    public Freevediosadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_freevedios, parent, false);
        return new Freevediosadapter.viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        // Get the YouTube video URL

        holder.youTubePlayerVieww.getSettings().setJavaScriptEnabled(true);
        String youtubeUrl = arrayList.get(position).getUrl();

// Extract the video ID from the URL
        String videoId = extractVideoId(youtubeUrl);

// Construct the embed HTML to load in the WebView
        String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

// Load the embed HTML into the WebView
        try {
            // Load the embed HTML into the WebView
            holder.youTubePlayerVieww.loadData(html, "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //  holder.youTubePlayerVieww.getSettings().setJavaScriptEnabled(true);
        //    holder.youTubePlayerVieww.loadData(html, "text/html", "UTF-8");


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        WebView youTubePlayerVieww;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerVieww = itemView.findViewById(R.id.youtube_player_view);
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

//        private class CustomWebViewClient extends WebViewClient {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // Add condition to check if the URL is from YouTube
//                if (url.contains("youtube.com")) {
//                    // Return true to indicate that the URL should not be loaded
//                    return true;
//                }
//                // For all other URLs, allow the WebView to navigate normally
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//        }
    }


