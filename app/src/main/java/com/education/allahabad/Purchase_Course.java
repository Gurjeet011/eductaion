package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.education.allahabad.Adapter.Paidcouredetailadapter;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Purchase_Course extends AppCompatActivity {
    ImageView imageView,imgback,showimage;
    String idpaidcourse;
    RelativeLayout selectimg;

    Uri select_img;
    TextView downloadQR,upiTextView;
    String base64,get_user_id,imageUrl;
    AppCompatButton paynow;
    String   upi_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_course);
        ImageView copyImageView = findViewById(R.id.copyImageView);
        upiTextView = findViewById(R.id.upiid);
        imageView = findViewById(R.id.imgpayment);
        selectimg = findViewById(R.id.selectimage);
        showimage = findViewById(R.id.showiamge);
        paynow = findViewById(R.id.paynow);
        downloadQR = findViewById(R.id.downloadqr);

        imgback = findViewById(R.id.ghg);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        qrcode();

      //  String upiId = "123456789@abc";

        // Purchase_Course_api();
        copyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Copy the text to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("UPI ID", upi_id);
                clipboard.setPrimaryClip(clip);

              //  Toast.makeText(getApplicationContext(), "UPI ID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        idpaidcourse = getIntent().getStringExtra("id");
        // Toast.makeText(this, idpaidcourse+"", Toast.LENGTH_SHORT).show();
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Purchase_Course.this)
                        .compress(1024)
                        .crop()
                        .maxResultSize(1080, 1080)
                        .start();
            }

        });
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_id = sharedPreferences.getString("userid", null);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://techcanopus.in/study/api/index.php?p=purchaseCourse";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            String getstatus = jsonObject1.getString("status");
                            // Toast.makeText(Purchase_Course.this, getstatus+"", Toast.LENGTH_SHORT).show();
                            if (getstatus.equals("200")) {
                                Toast.makeText(Purchase_Course.this, "purchase successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), Homepage.class));
                                finish();                              /*  String getdata = jsonObject1.getString("data");
                                Toast.makeText(Purchase_Course.this, "bkl", Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = new JSONArray(getdata);
                                Log.d("ddd", getdata);

                                for (int i = 0; i < jsonArray.length(); i++) {*/
                                //  JSONObject jsonObject = jsonArray.getJSONObject(i);
                                   /* String idd = jsonObject.getString("id");


                                    String des1 = jsonObject.getString("description");

                                    Modelclass modelclass = new Modelclass();
                                    modelclass.setId(idd);*/


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(Freecoursedescription.this, error+"", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", get_user_id);
                        params.put("courseId", idpaidcourse);
                        params.put("screenshot", base64);


                        return params;
                    }

                };
                queue.add(stringRequest);


            }
        });

        downloadQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Get the file name from the URL
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));

                    // Save the downloaded file to the Download directory
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                    DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    if (downloadManager!=null){
                        Toast.makeText(Purchase_Course.this, "Download Started", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "No image available to download", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No image available to download", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void qrcode() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/?p=getSiteData";
        StringRequest stringRequesta = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dkghsd", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    String get_status = jsonObject.getString("status");

                    if (get_status.equals("200")) {
                        String getdata = jsonObject.getString("data");
                        Log.d("cccccccccccc", getdata);
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                             upi_id = jsonObject1.getString("upi");

                            upiTextView.setText("UPI ID :"+upi_id);


                            String qrcodeimg = jsonObject1.getString("qrcode");
                            imageUrl = "https://techcanopus.in/study/assets/upload/" + qrcodeimg;

                            Glide.with(getApplicationContext())
                                    .load(imageUrl)
                                    .into(imageView);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });

        queue.add(stringRequesta);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            select_img = data.getData();
            showimage.setVisibility(View.VISIBLE);

            showimage.setImageURI(select_img);
            base64 = com.education.allahabad.ImagePicker.convertImageToBase64(showimage, select_img);
        }

    }
}
