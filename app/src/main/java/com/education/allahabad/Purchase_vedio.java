package com.education.allahabad;

import androidx.annotation.Nullable;
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
import com.education.allahabad.Adapter.Purchase_vedio_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Purchase_vedio extends AppCompatActivity {
    Purchase_vedio_adapter purchase_vedio_adapter;
    ArrayList<Free_Video_Modal> arrayList;
    RecyclerView recyclerViewz;
     String getida;
     ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_vedio);
        recyclerViewz=findViewById(R.id.vediorcx);
        imageView= findViewById(R.id.mg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList = new ArrayList<>();
        getida = getIntent().getStringExtra("id");
        Log.d("kdkjjbvsdbvs",getida);

        video_api();
    }
    void video_api(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getMyPurchaseDetail";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
               //     Toast.makeText(Purchase_vedio.this, response + "", Toast.LENGTH_SHORT).show();
                    String getstatus = jsonObject1.getString("status");
                    Log.d("dfjdhshr",response);
                    if (getstatus.equals("200")) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                              String idd = jsonObject.getString("id");
                            String names = jsonObject.getString("courseName");
                            Log.d("sdlkrhaw",names);

                            // Check if the value associated with the key "youtubeLink" is a string or an array
                            if (jsonObject.has("youtubeLink")) {
                                Object youtubeLinkObject = jsonObject.get("youtubeLink");
                                if (youtubeLinkObject instanceof JSONArray) {
                                    JSONArray js = (JSONArray) youtubeLinkObject;
                                    for (int v = 0; v < js.length(); v++) {
                                        String mul_videos = js.getString(v);
                                        Free_Video_Modal modelclass1 = new Free_Video_Modal();
                                        modelclass1.setUrl(mul_videos);
                                        arrayList.add(modelclass1);
                                        Log.d("askjrjhw", mul_videos);
                                    }
                                } else if (youtubeLinkObject instanceof String) {
                                    // Handle case where "youtubeLink" is a string
                                    String mul_videos = (String) youtubeLinkObject;
                                    Free_Video_Modal modelclass1 = new Free_Video_Modal();
                                    modelclass1.setUrl(mul_videos);
                                    arrayList.add(modelclass1);
                                    Log.d("askjrjhw", mul_videos);
                                }
                            }

                        }


                        purchase_vedio_adapter = new Purchase_vedio_adapter(getApplicationContext(),arrayList);
                        recyclerViewz.setAdapter(purchase_vedio_adapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerViewz.setLayoutManager(layoutManager);
                        purchase_vedio_adapter.notifyDataSetChanged();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams(){

                Map<String,String> params = new HashMap<>();
                params.put("id",getida);
                return params;
            }
        };
        queue.add(request);
    }
}