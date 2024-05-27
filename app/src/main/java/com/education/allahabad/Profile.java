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
import android.widget.EditText;
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

public class Profile extends AppCompatActivity {
    EditText name,number,email,district;
    Button btn;

    TextView updatepassword;
    String get_user_id;
    String getname;
    ImageView imageView;


    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView=findViewById(R.id.aroowback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        name=findViewById(R.id.edtname);
        number=findViewById(R.id.edtnumber);
        email=findViewById(R.id.edtmail);
        district=findViewById(R.id.edtdistrict);
        btn=findViewById(R.id.updateprofile);
        updatepassword=findViewById(R.id.qq);
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_id = sharedPreferences.getString("userid",null);

        getprofile();

        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Update_password.class));
            }
       });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String get_name = name.getText().toString();
              //  String get_number = number.getText().toString();
                String get_email = email.getText().toString();
                String get_district = district.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://techcanopus.in/study/api/index.php?p=updateProfile";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponce = new JSONObject(response);
                            Log.d ("ufbvrbv",response);
                          //  Toast.makeText(Profile.this, response+"", Toast.LENGTH_SHORT).show();

                            String get_status = jsonResponce.getString("status");
                            if (get_status.equals("200")) {
                                String get_message = jsonResponce.getString("message");
                                Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),Homepage.class));
                                finish();
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
                        params.put("userId",get_user_id);
                        params.put("email",get_email);
                        params.put("name",get_name);
                        params.put("district",get_district);

                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });

                String get_name = name.getText().toString();
                String get_number = number.getText().toString();
                String get_email = email.getText().toString();
                String get_district = district.getText().toString();
                if (get_name.isEmpty() || get_number.isEmpty() || get_email.isEmpty() || get_district.isEmpty()) {
                 //   Toast.makeText(Profile.this, "please enter all details", Toast.LENGTH_SHORT).show();
                } else {

                }
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

                            name.setText(get_name);
                            number.setText(get_number);
                            number.setFocusable(false);
                            email.setText(get_email);
                            district.setText(get_distic);

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
