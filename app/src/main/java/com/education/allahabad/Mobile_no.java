package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Mobile_no extends AppCompatActivity {

    Button btn;
    RequestQueue queue;
    EditText editText;
    String getotp,get_data;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn=findViewById(R.id.button1);
        editText=findViewById(R.id.edittext);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_number = editText.getText().toString();
                if(get_number==null){
                    Toast.makeText(Mobile_no.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if(get_number.length()<10 || get_number.length()>10){
                    Toast.makeText(Mobile_no.this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                } else {
                    mobileotp(get_number);
                }

            }

        });



    }

    private void mobileotp(String get_number) {
       queue = Volley.newRequestQueue(getApplicationContext());
           String url ="https://techcanopus.in/study/api/?p=sendOtpSignup";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               //Toast.makeText(Mobile_no.this, response + "", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonResponce = new JSONObject(response);

                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {
                     //   get_data = jsonResponce.getString("data");
                     //   String messz = jsonResponce.getString("message");
                     //   Toast.makeText(Mobile_no.this, messz+"", Toast.LENGTH_SHORT).show();

                        getotp = jsonResponce.getString("otp");
                        Intent intent = new Intent(getApplicationContext(),Otp_send.class);
                        intent.putExtra("otp",getotp);
                        intent.putExtra("mobile_number",get_number);
                        startActivity(intent);



                    }else if (get_status.equals("404")){
                        String messz = jsonResponce.getString("message");
                        Toast.makeText(Mobile_no.this, messz+"", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //    Toast.makeText(Mobile_no.this, error + "", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams(){
             Map<String,String> params = new HashMap<>();
             params.put("mobile",editText.getText().toString());

             return params;
            }
        };
          queue.add(stringRequest);
    }
}