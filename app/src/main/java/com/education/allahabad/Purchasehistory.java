package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Purchase_View_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Purchasehistory extends AppCompatActivity {
    ImageView imageView;
    String getida;
    Purchase_View_adapter purchase_view_adapter;
    ArrayList<Modelclass> arrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasehistory);
        recyclerView=findViewById(R.id.r1);

        imageView=findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getida=getIntent().getStringExtra("id");
        arrayList = new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getMyPurchaseDetail";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    //  Toast.makeText(Freecoursedescription.this, response + "", Toast.LENGTH_SHORT).show();
                    String getstatus = jsonObject1.getString("status");
                    Log.d("skdjskjrh",response);
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        Toast.makeText(Purchasehistory.this, getdata+"", Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String idd = jsonObject.getString("id");
                            String cname = jsonObject.getString("courseName");
                            String webvieww = jsonObject.getString("singleLink");
                            String thumbnaill = jsonObject.getString("thumbnail");

                            Modelclass modelclass = new Modelclass();
                            modelclass.setCoursname(cname);
                            modelclass.setImg2(thumbnaill);
                            modelclass.setGet_link(webvieww);
                            modelclass.setId(idd);

                            arrayList.add(modelclass);


                        }


                        purchase_view_adapter = new Purchase_View_adapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(purchase_view_adapter);
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
                //  Toast.makeText(Freecoursedescription.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", getida);

                return params;
            }

        };
        queue.add(stringRequest);

    }
}