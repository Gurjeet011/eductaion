package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.education.allahabad.Adapter.Freecoursedescriptionadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Freecoursedescription extends AppCompatActivity {
    Freecoursedescriptionadapter freecoursedescriptionadapter;
    ArrayList<Modelclass> arrayList;
    RecyclerView recyclerView;
    String getid,get_name;
   // YouTubePlayerView youTubePlayerView;
   // TextView text_name;
    String youtube;
    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freecoursedescriptions);
        recyclerView=findViewById(R.id.desrc);
        imageView= findViewById(R.id.img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       // text_name = findViewById(R.id.text_name);

        arrayList = new ArrayList<>();
        getid = getIntent().getStringExtra("id1");
      //  get_name = getIntent().getStringExtra("course_name");

     //   text_name.setText(get_name);

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/index.php?p=getFreeCourseDetail";
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
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String idd = jsonObject.getString("id");

                            youtube = jsonObject.getString("singleLink");
                            Log.d("jnvkjdbsvbs",youtube);
                            String coursename = jsonObject.getString("courseName");
                            String des1 = jsonObject.getString("description");
                            String dess2 = jsonObject.getString("additionalDescription");
                            String pdfimg = jsonObject.getString("thumbnail");
                            Modelclass modelclass = new Modelclass();
                            modelclass.setId(idd);
                            modelclass.setDes(des1);
                            modelclass.setDes2(dess2);
                            modelclass.setTxt(coursename);
                            modelclass.setImg2(pdfimg);
                            modelclass.setGet_link(youtube);
                            arrayList.add(modelclass);
                        }


                        freecoursedescriptionadapter = new Freecoursedescriptionadapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(freecoursedescriptionadapter);
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
                params.put("id", getid);

                return params;
            }

        };
        queue.add(stringRequest);
    }
}