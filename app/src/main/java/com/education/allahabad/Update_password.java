package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

public class Update_password extends AppCompatActivity {

    EditText edt1,edt2,edt3;
    Button btn;

    String get_user_id;
    ImageView leftarrow;
   // RelativeLayout rr;
    ImageView imageView;
    ImageView eye,hideeye,eye2,hideeye2,eye3,hideeye3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        eye=findViewById(R.id.eye);
        hideeye= findViewById(R.id.hideeye);
        eye2=findViewById(R.id.eye2);
        hideeye2= findViewById(R.id.hideeye2);
        eye3=findViewById(R.id.eye3);
        hideeye3= findViewById(R.id.hideeye3);
        edt1 = findViewById(R.id.currentpass);

        edt2 = findViewById(R.id.newpassword);
        edt3 = findViewById(R.id.conpass);
        btn = findViewById(R.id.updatepassword);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setTransformationMethod(new PasswordTransformationMethod());
                eye.setVisibility(View.GONE);
                hideeye.setVisibility(View.VISIBLE);
            }
        });
        edt1.addTextChangedListener(new TextWatcher() {
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
                    hideeye.setVisibility(View.GONE);
                }else {
                    hideeye.setVisibility(View.VISIBLE);
                }
            }
        });

        hideeye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setTransformationMethod(null);
                hideeye.setVisibility(View.GONE);
                eye.setVisibility(View.VISIBLE);
            }
        });
        eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt2.setTransformationMethod(new PasswordTransformationMethod());
                eye2.setVisibility(View.GONE);
                hideeye2.setVisibility(View.VISIBLE);
            }
        });
        edt2.addTextChangedListener(new TextWatcher() {
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
                    hideeye2.setVisibility(View.GONE);
                }else {
                    hideeye2.setVisibility(View.VISIBLE);
                }
            }
        });

        hideeye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt2.setTransformationMethod(null);
                hideeye2.setVisibility(View.GONE);
                eye2.setVisibility(View.VISIBLE);
            }
        });
        eye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt3.setTransformationMethod(new PasswordTransformationMethod());
                eye3.setVisibility(View.GONE);
                hideeye3.setVisibility(View.VISIBLE);
            }
        });
        edt3.addTextChangedListener(new TextWatcher() {
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
                    hideeye3.setVisibility(View.GONE);
                }else {
                    hideeye3.setVisibility(View.VISIBLE);
                }
            }
        });

        hideeye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt3.setTransformationMethod(null);
                hideeye3.setVisibility(View.GONE);
                eye3.setVisibility(View.VISIBLE);
            }
        });

        imageView=findViewById(R.id.iiii);
        imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });




        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_id = sharedPreferences.getString("userid",null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current_pass = edt1.getText().toString();
                String new_pass = edt2.getText().toString();
                String con_pass = edt3.getText().toString();


                if (current_pass.isEmpty() || new_pass.isEmpty() || con_pass.isEmpty()) {
                    Toast.makeText(Update_password.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }  else if (!con_pass.equals(new_pass)) {
                    Toast.makeText(Update_password.this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                } else {
                    update(current_pass, new_pass, con_pass);
                }
            }
        });

    }


    private void update(String current_pass, String new_pass, String con_pass) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=changePassword";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(Update_password.this, response+"", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonResponce = new JSONObject(response);
                    Log.d ("ooooooooo",response);
                  //  Toast.makeText(Update_password.this, response+"", Toast.LENGTH_SHORT).show();

                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {

                        Log.d("qqqqqqqqqqq",get_status);

                       // String get_data = jsonResponce.getString("message");



                            Toast.makeText(Update_password.this, "sucessfully update password", Toast.LENGTH_SHORT).show();
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
              //  Toast.makeText(Update_password.this, error+"", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",get_user_id);
                params.put("oldPassword",current_pass );
                params.put("newPassword", new_pass);
                params.put("confirmPassword", con_pass);


                return params;
            }
        };
        queue.add(stringRequest);
    }


    }


