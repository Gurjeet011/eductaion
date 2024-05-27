package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Help_Feedback extends AppCompatActivity {
    ImageView imageView;
    TextView n, no, note;
    Button btn;

    String name,number,get_user_id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        imageView = findViewById(R.id.back);
        note = findViewById(R.id.note);
        btn = findViewById(R.id.sumbit);


        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_id = sharedPreferences.getString("userid",null);


        getprofile();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feebback = note.getText().toString();


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://techcanopus.in/study/api/?p=submitFeedback";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponce = new JSONObject(response);

                            String get_status = jsonResponce.getString("status");
                            if (get_status.equals("200")) {
                                startActivity(new Intent(getApplicationContext(),Help_Feedback.class));
                                finish();
                                Toast.makeText(Help_Feedback.this, "successfully submitted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("mobile", number);
                        params.put("name", name);
                        params.put("feedback", feebback);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
    private void getprofile() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=getProfile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponce = new JSONObject(response);
                    Log.d ("ufbvrbv",response);

                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {
                        String get_data = jsonResponce.getString("data");

                        JSONArray jsonArray = new JSONArray(get_data);

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String get_name = jsonObject.getString("name");
                            String get_number = jsonObject.getString("mobile");
                            String get_email = jsonObject.getString("email");
                            String get_distic = jsonObject.getString("district");

                            name = get_name;
                            number = get_number;

                        }
                        // String get_data = jsonResponce.getString("message");



                        //   Toast.makeText(Profile.this, "sucessfully update profile", Toast.LENGTH_SHORT).show();

                        // startActivity(new Intent(getApplicationContext(), Login.class));
                        // startActivity(new Intent(getApplicationContext(), Homepage.class));

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
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",get_user_id);



                return params;
            }
        };
        queue.add(stringRequest);
    }
}

