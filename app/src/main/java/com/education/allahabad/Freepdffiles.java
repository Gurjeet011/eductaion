package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Freepdffiles extends AppCompatActivity {
    String fileget;
    WebView webView;
    ImageView imageView;
    ProgressDialog progressDialog; // Added ProgressBar reference

    private float originalWidth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freepdffiles);
        webView = findViewById(R.id.rrrrpdf);
        imageView= findViewById(R.id.zzzz);
        progressDialog = new ProgressDialog(Freepdffiles.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // Enable JavaScript for the WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        fileget = getIntent().getStringExtra("pdf");
        // Load the PDF file into the WebView
        webView.loadUrl("https://docs.google.com/viewer?url=https://techcanopus.in/study/assets/upload/"+fileget);

        // Set a WebViewClient to handle page loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //progressBar.setVisibility(View.GONE);
                // Dismiss the ProgressDialog when page is loaded
                progressDialog.dismiss();

                // Get the original width of the PDF content after the page is loaded
                webView.evaluateJavascript("document.body.scrollWidth", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        originalWidth = Float.parseFloat(value);
                    }
                });
            }
        });

        // Add a JavaScript interface to the WebView to communicate with Java code
        webView.addJavascriptInterface(new WebViewInterface(), "Android");
        // Show ProgressDialog when activity starts loading
        progressDialog.show();

    }

    // Define a JavaScript interface to handle communication between JavaScript and Java code
   private class WebViewInterface {
        @JavascriptInterface
        public void onResize(int width, int height) {
            // Adjust PDF zoom level based on the new size
            float zoomScale = width / originalWidth;
            webView.loadUrl("javascript:document.body.style.zoom='" + zoomScale + "';");
        }
    }
}
