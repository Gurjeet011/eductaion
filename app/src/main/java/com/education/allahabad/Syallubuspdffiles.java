package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Syallubuspdffiles extends AppCompatActivity {
    String filezget;
    WebView webViewx;
    private float originalWidth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syallubuspdffiles);
        webViewx = findViewById(R.id.rrrrpdfxx);

        // Enable JavaScript for the WebView
        WebSettings webSettings = webViewx.getSettings();
        webSettings.setJavaScriptEnabled(true);

        filezget = getIntent().getStringExtra("pdf");
        // Load the PDF file into the WebView
        webViewx.loadUrl("https://docs.google.com/viewer?url=https://techcanopus.in/study/assets/upload/"+filezget);

        // Set a WebViewClient to handle page loading
        webViewx.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Get the original width of the PDF content after the page is loaded
                webViewx.evaluateJavascript("document.body.scrollWidth", new android.webkit.ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        originalWidth = Float.parseFloat(value);
                    }
                });
            }
        });

        // Add a JavaScript interface to the WebView to communicate with Java code
        webViewx.addJavascriptInterface(new WebViewInterface(), "Android");
    }

    // Define a JavaScript interface to handle communication between JavaScript and Java code
    private class WebViewInterface {
        @JavascriptInterface
        public void onResize(int width, int height) {
            // Adjust PDF zoom level based on the new size
            float zoomScale = width / originalWidth;
            webViewx.loadUrl("javascript:document.body.style.zoom='" + zoomScale + "';");
        }
    }
}