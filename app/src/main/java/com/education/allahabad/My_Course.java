package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.MY_purchaseadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class My_Course extends AppCompatActivity {
    ImageView imageView;
    MY_purchaseadapter my_purchaseadapter;
    ArrayList<Modelclass> arrayList;
    RecyclerView recyclerView;
    String get_user_id,statuss;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        recyclerView=findViewById(R.id.course);
        imageView=findViewById(R.id.ghg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }



        });
        apifetch();


    }

    private void apifetch() {
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_id = sharedPreferences.getString("userid",null);
        arrayList = new ArrayList<>();
       /* idx = getIntent().getStringExtra("idd");
        Log.d("mmmmmmmm",idx);*/
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getMyPurchase";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        // Toast.makeText(CoursePdf.this, getdata+"", Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String iddq = jsonObject.getString("id");
                            String courseidx = jsonObject.getString("courseId");

                            String xx = jsonObject.getString("status");

                            String ss = jsonObject.getString("courseName");
                            //  JSONArray jsonArray1 = jsonObject.getJSONArray("pdfFile");

                            Modelclass modelclass = new Modelclass();
                            modelclass.setCoursname(ss);
                            modelclass.setStaatus(xx);
                            modelclass.setCourseidx(courseidx);

                            modelclass.setId(iddq);
                            arrayList.add(modelclass);
                        }

                        ArrayList<Modelclass> modelclasses = new ArrayList<>();
                        for (Modelclass modelclass : arrayList){
                            if (modelclass.getStaatus().equals("1")){
                                modelclasses.add(modelclass);
                            }
                        }

                        my_purchaseadapter = new MY_purchaseadapter(getApplicationContext(),modelclasses);
                        recyclerView.setAdapter(my_purchaseadapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Freevedios.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", get_user_id);

                return params;
            }

        };
        queue.add(stringRequest);
    }
}