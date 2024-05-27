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
import com.education.allahabad.Adapter.Freevediosadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Freevedios extends AppCompatActivity {
    Freevediosadapter freevediosadapter;
    ArrayList<Free_Video_Modal> arrayList;
    RecyclerView recyclerViewz;
    String getttd;
    String youtubee;
    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freevedios);
        recyclerViewz=findViewById(R.id.vediorc);
        imageView= findViewById(R.id.mg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList = new ArrayList<>();
        getttd = getIntent().getStringExtra("idd");

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/index.php?p=getFreeCourseDetail";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                 //   Toast.makeText(Freevedios.this, response + "", Toast.LENGTH_SHORT).show();
                    String getstatus = jsonObject1.getString("status");
                    Log.d("dfjdhshr",response);
                    if (getstatus.equals("200")) {

                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String idd = jsonObject.getString("id");
                            String names = jsonObject.getString("courseName");
                            Log.d("sdlkrhaw",names);

                            String get_video = jsonObject.getString("youtubeLink");

                            Log.d("askjrjhw", get_video.length()+"");
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


                        freevediosadapter = new Freevediosadapter(getApplicationContext(),arrayList);
                        recyclerViewz.setAdapter(freevediosadapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerViewz.setLayoutManager(layoutManager);
                        freevediosadapter.notifyDataSetChanged();


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
                params.put("id", getttd);

                return params;
            }

        };
        queue.add(stringRequest);


    }
}