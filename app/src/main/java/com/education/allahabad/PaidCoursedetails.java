package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaidCoursedetails extends AppCompatActivity {
    String idget,idz;
    RecyclerView recyclerView;
    ArrayList<Modelclass>arrayList;
    Paidcouredetailadapter paidcouredetailadapter;
    String youtubee;
    ImageView imageView;

    String get_user_idd,get_courseid,getpurchaseid,getalreadypurchid,pur_statuss,purchId,
    purchasStatus,alreadypurch;

    TextView price,coursename, des,adddes,vediox,pdfx;
    ImageView imageViewz;
    WebView webView;
    Button buynow,statuss;
    LinearLayout linearLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_coursedetails);
        imageView=findViewById(R.id.back);

        price=findViewById(R.id.pricee);
        buynow=findViewById(R.id.buynow);
        linearLayout=findViewById(R.id.linear);
        vediox = findViewById(R.id.vediosx);
        statuss = findViewById(R.id.statuss);

        coursename=findViewById(R.id.text_namee);
        des=findViewById(R.id.dess);
        adddes=findViewById(R.id.adddesc);
        imageViewz=findViewById(R.id.imgdesz );
        webView=findViewById(R.id.youtube_player);
        pdfx = findViewById(R.id.pdfx);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_idd = sharedPreferences.getString("userid",null);

        arrayList=new ArrayList<>();
        idget= getIntent().getStringExtra("idsend");
       /* getpurchaseid= getIntent().getStringExtra("purchaseid");
        getalreadypurchid= getIntent().getStringExtra("alreadypurchid");
        pur_statuss= getIntent().getStringExtra("pur_status");
*/
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/index.php?p=getPaidCourseDetail";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                 //   Log.d("iddddddddddddddddd",response);
                    //   Toast.makeText(Freecoursedescription.this, response + "", Toast.LENGTH_SHORT).show();
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        Log.d("ddd",getdata);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String idd = jsonObject.getString("id");

                            youtubee = jsonObject.getString("singleLink");
                            String pricex = jsonObject.getString("price");
                            String coursenames = jsonObject.getString("courseName");
                            String des1 = jsonObject.getString("description");
                            String dess2 = jsonObject.getString("additionalDescription");
                            String pdfimg = jsonObject.getString("thumbnail");
                            purchId = jsonObject.getString("purchasedId");
                            alreadypurch = jsonObject.getString("alreadyPurchased");
                            Log.d("sksahh",alreadypurch);
                             purchasStatus=jsonObject.getString("purchasedStatus");

                            if(alreadypurch.equals("1")){
                                if(purchasStatus.equals("0")){
                                    linearLayout.setVisibility(View.GONE);
                                    buynow.setVisibility(View.GONE);
                                    statuss.setVisibility(View.VISIBLE);
                                    statuss.setText("Pending");
                                } else if (purchasStatus.equals("1")){
                                    linearLayout.setVisibility(View.VISIBLE);
                                    buynow.setVisibility(View.GONE);
                                    statuss.setVisibility(View.GONE); vediox.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(), Purchase_vedio.class);
                                            intent.putExtra("id",purchId);
                                            //   intent.putExtra("course_name",arrayList.get(position).getTxt());
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            vediox.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.signup));
                                            pdfx.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.white_background));
                                        }
                                    });

                                    pdfx.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(), purchaase_pdf.class);
                                            intent.putExtra("id",purchId);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                            pdfx.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.signup));
                                            vediox.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.white_background));


                                        }
                                    });

                                } else if (purchasStatus.equals("2")){
                                    linearLayout.setVisibility(View.GONE);
                                    buynow.setVisibility(View.VISIBLE);
                                    statuss.setVisibility(View.GONE);
                                    buynow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Handle Buy Now button click
                                            Intent intent = new Intent(getApplicationContext(), Purchase_Course.class);
                                            intent.putExtra("id", idd);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                //buynow.setVisibility(View.GONE);


                            } else if(alreadypurch.equals("0")){
                                buynow.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);

                                buynow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Handle Buy Now button click
                                        Intent intent = new Intent(getApplicationContext(), Purchase_Course.class);
                                        intent.putExtra("id", idd);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });

                            }


                            coursename.setText(coursenames);

                           price.setText("₹ "+pricex);
                           des.setText(Html.fromHtml(des1));
                           adddes.setText(Html.fromHtml(dess2));

                            String img_uri2 = "https://techcanopus.in/study/assets/upload/"+pdfimg;
                            Glide.with(getApplicationContext()).load(img_uri2).into(imageViewz);

                          webView.getSettings().setJavaScriptEnabled(true);
                            String youtubeUrl = youtubee;

                            String videoId = extractVideoId(youtubeUrl);

                            String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                            try {
                                // Load the embed HTML into the WebView
                               webView.loadData(html, "text/html", "utf-8");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
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
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", idget);
                params.put("userId", get_user_idd);

                return params;
            }

        };
        queue.add(stringRequest);

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