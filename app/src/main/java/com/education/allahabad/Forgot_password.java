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
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class Forgot_password extends AppCompatActivity {
    EditText otp,newpassword,conpassword;
    Button verify;
    EditText mobile;
    Button sendotp;
    RequestQueue queue;
    String getotpx;
    RelativeLayout mobilenorl,forgotrl;
    String get_user_idz;
    TextView t;
    ImageView back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        t=findViewById(R.id.t);
        mobilenorl=findViewById(R.id.r1x);
        forgotrl=findViewById(R.id.forgotpage);
        otp=findViewById(R.id.otpsend);
        newpassword=findViewById(R.id.fornewpassword);
        conpassword=findViewById(R.id.forconpass);
        verify=findViewById(R.id.verifybutton);
        mobile=findViewById(R.id.edittextx);
        sendotp=findViewById(R.id.send_otpx);


        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_idz = sharedPreferences.getString("userid",null);




        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_number = mobile.getText().toString();
                if (get_number == null) {
                    Toast.makeText(Forgot_password.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (get_number.length() < 10 || get_number.length() > 10) {
                    Toast.makeText(Forgot_password.this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                } else {
                    mobileotp();
                    t.setVisibility(View.GONE);

                    sendotp.setVisibility(View.GONE);
                    mobilenorl.setVisibility(View.GONE);
                    verify.setVisibility(View.VISIBLE);
                    forgotrl.setVisibility(View.VISIBLE);
                    verify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String otpx = otp.getText().toString();
                            String new_pass = newpassword.getText().toString();
                            String con_pass = conpassword.getText().toString();

                            if (otpx.isEmpty() || new_pass.isEmpty() || con_pass.isEmpty()) {
                                Toast.makeText(Forgot_password.this, "please enter all details", Toast.LENGTH_SHORT).show();
                            } else if (!con_pass.equals(new_pass)) {
                                Toast.makeText(Forgot_password.this, "confirm password does not match new password", Toast.LENGTH_SHORT).show();
                            } else {
                                updatez(otpx, new_pass, con_pass,get_number);
                               // t.setVisibility(View.GONE);

                            }
                        }


                    });

                }
            }
        });
    }
    private void mobileotp() {
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/?p=sendOtp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zzzzzzzzzz",response);
                // Toast.makeText(Mobile_no.this, response + "", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonResponce = new JSONObject(response);

                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {
                        getotpx = jsonResponce.getString("otp");
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
                params.put("mobile", mobile.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void updatez(String otpx, String new_pass, String con_pass, String get_number) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=forgotPassword";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponce = new JSONObject(response);
                    Log.d("ufbvrbv", response);

                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {
                        String get_message = jsonResponce.getString("message");
                        Toast.makeText(Forgot_password.this, get_message+"", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(), Login.class));

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
                params.put("mobile", get_number);
                params.put("otp", otpx);
                params.put("newPassword", new_pass);
                params.put("confirmPassword", con_pass);


                return params;
            }
        };
        queue.add(stringRequest);


    }

}
