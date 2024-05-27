package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Testsubadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestseriesSUB extends AppCompatActivity {
    RecyclerView recyclerView;
    Testsubadapter testsubadapter;
    ArrayList<Modelclass>arrayList;
    String getide;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testseries_sub);

        recyclerView = findViewById(R.id.testrc);
        imageView= findViewById(R.id.imgd);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList= new ArrayList<>();
        getide = getIntent().getStringExtra("id");
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getTestSubcategory";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d("asdjwaj",response);
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                             String iddn = jsonObject.getString("id");
                             String catidx = jsonObject.getString("cat");
                            String namee = jsonObject.getString("subCatName");
                            String imgv = jsonObject.getString("image");


                            Log.d("fbfsfsbrebre",getdata);

                            Modelclass modelclass = new Modelclass();
                            modelclass.setId(iddn);
                            modelclass.setCatidxzx(catidx);
                            modelclass.setCat(namee);
                            modelclass.setImg(imgv);
                            arrayList.add(modelclass);


                        }


                        testsubadapter = new Testsubadapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(testsubadapter);
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
               // Toast.makeText(TestseriesSUB.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", getide);
                return params;
            }

        };



        queue.add(stringRequest);


    }
}