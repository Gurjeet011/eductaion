package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Purchase_pdf_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class purchaase_pdf extends AppCompatActivity {
    String getida;
    RecyclerView recyclerView;
    Purchase_pdf_adapter purchase_pdf_adapter;
    ImageView imageViewb;
    ArrayList<Modelclass> arrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaase_pdf);
        getida=getIntent().getStringExtra("id");
        recyclerView=findViewById(R.id.vediorczzzzzss);
        imageViewb= findViewById(R.id.mgc);
        imageViewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList = new ArrayList<>();


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getMyPurchaseDetail";
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
                            String idd = jsonObject.getString("id");

                            JSONArray js = jsonObject.getJSONArray("pdfName");
                            JSONArray jsonArray1 = jsonObject.getJSONArray("pdfFile");
                            for (int v=0;v<js.length();v++){
                                String pdfnames = js.getString(v);
                                Modelclass modelclass = new Modelclass();
                                modelclass.setTxtttttttt(pdfnames);
                                modelclass.setId(idd);
                                arrayList.add(modelclass);

                                if (v<jsonArray1.length()){
                                    String get_pdf_file = jsonArray1.getString(v);
                                    modelclass.setPdf_file(get_pdf_file);
                                    //   Toast.makeText(CoursePdf.this, get_pdf_file+"", Toast.LENGTH_SHORT).show();
                                }
                                //  Log.d("askjrjhw",mul_videos);
                            }


                        }


                        purchase_pdf_adapter = new Purchase_pdf_adapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(purchase_pdf_adapter);
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
                params.put("id", getida);

                return params;
            }

        };
        queue.add(stringRequest);



    }
}