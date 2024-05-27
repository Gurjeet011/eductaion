package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Description extends AppCompatActivity {
    TextView t1,min,mark;
    AppCompatButton agrre;
    String getname,getminute,getmark,getid;
    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        imageView=findViewById(R.id.ghg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        agrre=findViewById(R.id.agree);
        t1=findViewById(R.id.t1x);
        min=findViewById(R.id.min);
        mark=findViewById(R.id.totalmarks);
        getname=getIntent().getStringExtra("testName");
        getminute=getIntent().getStringExtra("totalMinute");
        getmark=getIntent().getStringExtra("totalMarks");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("id", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("userid", null);


        getid=getIntent().getStringExtra("id");

        t1.setText(getname);
        min.setText(getminute+" min ");
        mark.setText(getmark);

      //  Toast.makeText(this, getname+"", Toast.LENGTH_SHORT).show();

        agrre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptid(getid,user_id);

            }
        });

    }







    void attemptid(String id, String user_id) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=startTest";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d("shrjhhsw",response);
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String attemptTestId = jsonObject1.getString("attemptTestId");
//
                        Intent intent= new Intent(getApplicationContext(), Mocktestquestionss.class);
                        // intent.putExtra("subid",arrayList.get(position).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("count", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mocktestid",getid);
                        editor.putString("att_id",attemptTestId);
                        editor.putInt("countss",1);
                        editor.apply();
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("testId", id);
                params.put("userId", user_id);
                return params;
            }
        };
        queue.add(stringRequest);
    }

}