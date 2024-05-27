package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
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

public class Login extends AppCompatActivity {
    EditText editTextmob, editTextpass;
    Button btn,btn2;
    TextView forgot;
    RequestQueue queue;
    ImageView eye4,hideeye4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextmob = findViewById(R.id.mobileee);
        editTextpass = findViewById(R.id.passeordd);
        eye4=findViewById(R.id.eye4);
        hideeye4=findViewById(R.id.hideeye4);
        forgot = findViewById(R.id.forgotpassword);
        SpannableString spannableString = new SpannableString("Forgot Password? Reset");

// Set color for the first part of the text (e.g., "Hello")
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.black));
        spannableString.setSpan(colorSpan1, 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set color for the second part of the text (e.g., "World")
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.bluex));
        spannableString.setSpan(colorSpan2, 16, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the SpannableString to the TextView
        forgot.setText(spannableString);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Forgot_password.class));
            }
        });

        eye4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextpass.setTransformationMethod(new PasswordTransformationMethod());
                eye4.setVisibility(View.GONE);
                hideeye4.setVisibility(View.VISIBLE);
            }
        });
        editTextpass.addTextChangedListener(new TextWatcher() {
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
                    hideeye4.setVisibility(View.GONE);
                }else {
                    hideeye4.setVisibility(View.VISIBLE);
                }
            }
        });

        hideeye4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextpass.setTransformationMethod(null);
                hideeye4.setVisibility(View.GONE);
                eye4.setVisibility(View.VISIBLE);
            }
        });
        btn = findViewById(R.id.login);
        btn2 = findViewById(R.id.Registerw);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Mobile_no.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getmobile = editTextmob.getText().toString();
                String getpass = editTextpass.getText().toString();

                if (getmobile.isEmpty() || getpass.isEmpty()) {
                    Toast.makeText(Login.this, "please enter all details", Toast.LENGTH_SHORT).show();

                } else if (getmobile.length()<10 || getmobile.length()>10){
                    Toast.makeText(Login.this, "Enter 10 digits number", Toast.LENGTH_SHORT).show();

                }






                else{
                    login(getmobile, getpass);
                }

            }


        });
    }

    private void login(String getmobile, String getpass) {
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/?p=login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              // Toast.makeText(Login.this, response+"", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonResponce = new JSONObject(response);

                    String get_statuss = jsonResponce.getString("status");
                    if (get_statuss.equals("200")) {
                        String get_data = jsonResponce.getString("data");
                       String mess = jsonResponce.getString("message");
                        Toast.makeText(Login.this, mess+"", Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = new JSONArray(get_data);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String get_id = object.getString("id");
                           // Toast.makeText(Login.this, get_id+"", Toast.LENGTH_SHORT).show();
                          //  Log.d("aaddfxgfxfxfx",get_id);

                           // Toast.makeText(Login.this, "sucessfully login", Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(getApplicationContext(), Login.class));
                            SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Intent intent = new Intent(getApplicationContext(),Homepage.class);
                            startActivity(intent);
                            finish();
                            editor.putString("userid", get_id);
                            editor.commit();
                        }





                    }
                    else {
                        // Show toast message for invalid credentials
                        String errorMessage = jsonResponce.getString("message");
                        Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(Login.this, error+"error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", getmobile);

                params.put("password", getpass);

                return params;
            }
        };
        queue.add(stringRequest);


    }
}
