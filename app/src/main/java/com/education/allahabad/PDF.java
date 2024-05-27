package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.education.allahabad.Adapter.pdfcatrgory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDF extends AppCompatActivity {
     ArrayList<Modelclass> arrayList;
    pdfcatrgory freepdfcatrgory;
 //  String dd;
    String Id;
    RecyclerView recyclerView;
    ImageView imageView;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        recyclerView=findViewById(R.id.r);
        imageView= findViewById(R.id.pimg);
      /*  progressDialog = new ProgressDialog(PDF.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);*/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
           arrayList= new ArrayList<>();
        Id = getIntent().getStringExtra("iD");
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getFreePdfCategory";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                   // Log.d("asdjwaj",response);
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                           String iddn = jsonObject.getString("id");
                            String namee = jsonObject.getString("catName");
                            String catimg = jsonObject.getString("image");

                            Log.d("fbfsfsbrebre",getdata);

                            Modelclass modelclass = new Modelclass();
                            modelclass.setId(iddn);
                            modelclass.setCat(namee);
                            modelclass.setImg(catimg);
                            arrayList.add(modelclass);


                        }


                        freepdfcatrgory = new pdfcatrgory(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(freepdfcatrgory);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                       // progressDialog.dismiss();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(PDF.this, error+"", Toast.LENGTH_SHORT).show();
              //  progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", Id);
                return params;
            }

        };

      //  progressDialog.show();


        queue.add(stringRequest);
    }
}