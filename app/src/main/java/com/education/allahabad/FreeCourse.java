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
import com.education.allahabad.Adapter.FreeCourseadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FreeCourse extends AppCompatActivity {
    RecyclerView recyclerView;
    FreeCourseadapter freeCourse;
  //  String get_statuss;
    ArrayList<Modelclass>arrayList;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_course);
        recyclerView= findViewById(R.id.rrr);
        imageView= findViewById(R.id.backarrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        arrayList = new ArrayList<>();
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        String url="https://techcanopus.in/study/api/index.php?p=getFreeCourseList";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JsonResponce = new JSONObject(response);
                    String status = JsonResponce .getString("status");
                  //  Toast.makeText(FreeCourse.this, status+"", Toast.LENGTH_SHORT).show();

                    if(status.equals("200")){
                     String getdata = JsonResponce .getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        for(int i=0; i< jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String expiredate = jsonObject.getString("expiryDate");
                        String name=jsonObject.getString("courseName");
                        String photo = jsonObject.getString("thumbnail");
                        String id1 = jsonObject.getString("id");

                        Modelclass modelclass = new Modelclass();
                        modelclass.setExpdate(expiredate);
                        modelclass.setImg(photo);
                        modelclass.setTxt(name);
                        modelclass.setId(id1);
                        arrayList.add(modelclass);
                            freeCourse = new FreeCourseadapter(getApplicationContext(), arrayList);
                            recyclerView.setAdapter(freeCourse);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(FreeCourse.this, error+"error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}