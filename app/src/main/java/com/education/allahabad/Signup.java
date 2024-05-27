package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Signup extends AppCompatActivity {
    EditText names,mobile,email,district,confrimpassword,password;
    Button btn;
    RequestQueue queue;
    ImageView eye5,eye6,hideeye5,hideeye6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        eye5=findViewById(R.id.eye5);
        eye6=findViewById(R.id.eye6);
        hideeye5=findViewById(R.id.hideeye5);
        hideeye6=findViewById(R.id.hideeye6);

        btn = findViewById(R.id.signup);
        names = findViewById(R.id.names);
        mobile = findViewById(R.id.mobileno);
        email = findViewById(R.id.emaill);
        district = findViewById(R.id.districtt);
        password = findViewById(R.id.passwordy);
        confrimpassword = findViewById(R.id.confirmpassword);
        eye5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(new PasswordTransformationMethod());
                eye5.setVisibility(View.GONE);
                hideeye5.setVisibility(View.VISIBLE);
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String get_click = editable.toString();
                if (get_click.isEmpty()){
                    hideeye5.setVisibility(View.GONE);
                }else {
                    hideeye5.setVisibility(View.VISIBLE);
                }
            }
        });

        hideeye5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(null);
                hideeye5.setVisibility(View.GONE);
                eye5.setVisibility(View.VISIBLE);
            }
        });
        eye6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confrimpassword.setTransformationMethod(new PasswordTransformationMethod());
                eye6.setVisibility(View.GONE);
                hideeye6.setVisibility(View.VISIBLE);
            }
        });
        confrimpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String get_click = editable.toString();
                if (get_click.isEmpty()){
                    hideeye6.setVisibility(View.GONE);
                }else {
                    hideeye6.setVisibility(View.VISIBLE);
                }
            }
        });

        hideeye6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confrimpassword.setTransformationMethod(null);
                hideeye6.setVisibility(View.GONE);
                eye6.setVisibility(View.VISIBLE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = names.getText().toString();
                String mobilee = mobile.getText().toString();
                String emaill = email.getText().toString();
                String districtt = district.getText().toString();
                String conpassword = confrimpassword.getText().toString();
                String pass = password.getText().toString();
 
                if (name.isEmpty() || mobilee.isEmpty() || emaill.isEmpty() || districtt.isEmpty() || conpassword.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Signup.this, "please enter all details", Toast.LENGTH_SHORT).show();
                } else if (!emaill.contains("@gmail.com")) {
                    Toast.makeText(Signup.this, "Please enter a valod email id.", Toast.LENGTH_SHORT).show();
                } else if (!conpassword.equals(pass )) {
                    Toast.makeText(Signup.this, "Your confirm password doesnot match password", Toast.LENGTH_SHORT).show();

                } else if (mobilee.length()<10||mobilee.length()>10){
                    Toast.makeText(Signup.this, "Enter 10 digits number", Toast.LENGTH_SHORT).show();

                }


                else {
                    Signup_api(name, mobilee, emaill, districtt, conpassword, pass);

                }


            }
        });
    }
    private void Signup_api(String name, String mobilee, String emaill, String districtt, String conpassword, String pass) {
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/?p=signup";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponce = new JSONObject(response);

                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {
                        Toast.makeText(Signup.this, "sucessfully register", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        String id = jsonResponce.getString("userId");
                        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userid", id);
                        editor.commit();

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
                params.put("mobile", mobilee);
                params.put("name", name);
                params.put("email", emaill);
                params.put("district", districtt);
                params.put("confirmPassword", conpassword);
                params.put("password", pass);

                return params;
            }
        };
        queue.add(stringRequest);
    }
}