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
import com.education.allahabad.Adapter.Syllabusadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Syllabus extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageView;
    ArrayList<Modelclass>arrayList;
    Syllabusadapter syllabusadapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        recyclerView=findViewById(R.id.syllbusrcv);
        imageView= findViewById(R.id.timgs);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList=new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getSyllabusCategory";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                  //  Toast.makeText(Syllabus.this, response + "", Toast.LENGTH_SHORT).show();
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        Log.d("hsfdskbfkb",getdata);

                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String idd = jsonObject.getString("id");

                            String  sytxt = jsonObject.getString("catName");
                            String  sylimg = jsonObject.getString("image");

//                            JSONArray js = new JSONArray(pdf);
//                            for (int v=0;v<js.length();v++){
//                                String pdffile = js.getString(v);
//
//                                Modelclass modelclass = new Modelclass();
//                                modelclass.setPdftext(pdffile);
//                                arrayList.add(modelclass);
//                                Log.d("rssdbudyyuv",pdf);
//
//                            }

                            Modelclass modelclass = new Modelclass();
                            modelclass.setId(idd);
                            modelclass.setCat(sytxt);
                            modelclass.setImg(sylimg);
                            arrayList.add(modelclass);


                        }


                        syllabusadapter = new Syllabusadapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(syllabusadapter);
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
               // Toast.makeText(Syllabus.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);



    }
}