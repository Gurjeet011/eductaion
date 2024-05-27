package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class Terms_condition extends AppCompatActivity {
    ImageView imageView;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        t=findViewById(R.id.term);
        imageView=findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/?p=getSiteData";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dkghsd",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    String get_status = jsonObject.getString("status");

                    if (get_status.equals("200")) {
                        String getdata = jsonObject.getString("data");
                        Log.d("sssssssssss",getdata);
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String abouts = jsonObject1.getString("terms");
                            t.setText(Html.fromHtml(abouts));
                        }



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
        });

        queue.add(stringRequest);
    }
}