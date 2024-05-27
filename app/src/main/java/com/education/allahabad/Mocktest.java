package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import com.education.allahabad.Adapter.Mocktestadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mocktest extends AppCompatActivity {
    RecyclerView recyclerView;
    Mocktestadapter mocktestadapter;
    ArrayList<Modelclass>arrayList;
    String idcat,idsub;
    ImageView imageView;
  //  String getids;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freemocktest);
        recyclerView=findViewById(R.id.rcttx);
        imageView= findViewById(R.id.timg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList= new ArrayList<>();
     //   getidx = getIntent().getStringExtra("id");

        idcat = getIntent().getStringExtra("catid");
        idsub = getIntent().getStringExtra("subid");
        Log.d("rrrrrrrrrrrrr",idcat);
        Log.d("yyyyyyyyyyyy",idsub);
    //  Toast.makeText(this, idcat+"aaaaaaaaaa", Toast.LENGTH_SHORT).show();
    //    Toast.makeText(this, idsub+"zzzzzzzzzzz", Toast.LENGTH_SHORT).show();

       // getids=getIntent().getStringExtra("idx");

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/index.php?p=getTestData";
        StringRequest stringRequestz = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                  //  Toast.makeText(Mocktest.this, response+"", Toast.LENGTH_SHORT).show();
                    Log.d("zzzzzzzzzzzzzz",response);
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        Log.d("kjsbkjdsbvd",getstatus);
                        String getdata = jsonObject1.getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String iddnzx = jsonObject.getString("id");
                            String que = jsonObject.getString("totalQuestions");
                            String mar = jsonObject.getString("totalMarks");
                            String min = jsonObject.getString("totalMinute");
                            String nam = jsonObject.getString("testName");


                            Log.d("fbfsfsbrebre",getdata);

                            Modelclass modelclass = new Modelclass();
                            modelclass.setId(iddnzx);
                            modelclass.setQuenames(nam);
                            modelclass.setMarks(mar);
                            modelclass.setMinutes(min);
                            modelclass.setQuestion(que);

                            arrayList.add(modelclass);


                        }


                        mocktestadapter = new Mocktestadapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(mocktestadapter);
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
               // Toast.makeText(Mocktest.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("subCat", idsub);
                params.put("cat", idcat);

                //  params.put("id", getidx);


                return params;
            }

        };



        queue.add(stringRequestz);
    }
}