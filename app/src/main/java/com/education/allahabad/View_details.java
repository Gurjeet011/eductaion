package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.education.allahabad.Adapter.Veiw_details_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_details extends AppCompatActivity {
    String attemptestidget;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<Modelclass>arrayList;
    Veiw_details_adapter veiw_details_adapter;
    ImageView timg;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        timg=findViewById(R.id.timg);
        timg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.veiwdrc);
        arrayList=new ArrayList<>();
        sharedPreferences = getSharedPreferences("count",MODE_PRIVATE);

        attemptestidget = sharedPreferences.getString("att_id", null);



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=viewAttemptTestDetail";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dkghsdzzz",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    String get_status = jsonObject.getString("status");

                    if (get_status.equals("200")) {
                        String getdata = jsonObject.getString("data");
                        Log.d("sssssssssss",getdata);
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String get_question = jsonObject1.getString("question");
                            String correct_ans = jsonObject1.getString("correctAnswer");
                            String wrong_ans = jsonObject1.getString("selectedAnswer");

                            Modelclass modelclass = new Modelclass();
                            modelclass.setViewdetailsquestio(get_question);
                            modelclass.setCorrect_ans(correct_ans);
                            modelclass.setWrong_ans(wrong_ans);


                            arrayList.add(modelclass);


                        }


                        veiw_details_adapter = new Veiw_details_adapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(veiw_details_adapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Do nothing here, as we will move to the next question when "Save & Next" is clicked
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("attemptTestId", attemptestidget);
                return params;
            }
        };

        queue.add(stringRequest);
    }


    }
