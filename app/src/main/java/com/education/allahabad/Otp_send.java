package com.education.allahabad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Otp_send extends AppCompatActivity {

    String Otp;
    Button btn;
    PinView pinView;
    TextView timerTextView,resendotp;
    CountDownTimer countDownTimer;
    String getotp,getnumber;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_send);
        Otp = getIntent().getStringExtra("otp");
        getnumber=getIntent().getStringExtra("mobile_number");
        btn= findViewById(R.id.Verifyotp);
        pinView= findViewById(R.id.firstPinView);
        timerTextView=findViewById(R.id.timerresend);
        resendotp=findViewById(R.id.qq);
       // mobileotp();
        startResendTimer(30000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String veriftotp = pinView.getText().toString();
                 if(veriftotp.isEmpty()){
                     Toast.makeText(Otp_send.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                 }else if(veriftotp.length()<4 || veriftotp.length()>4){
                     Toast.makeText(Otp_send.this, "Enter valid Otp", Toast.LENGTH_SHORT).show();

                 }else if(!veriftotp.equals(Otp)){
                     Toast.makeText(Otp_send.this, "Enter correct Otp", Toast.LENGTH_SHORT).show();

                 } else if (veriftotp.equals(Otp)){
                     startActivity(new Intent(getApplicationContext(),Signup.class));
                }

            }
        });




        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://techcanopus.in/study/api/?p=sendOtpSignup";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(Otp_send.this, response + "", Toast.LENGTH_SHORT).show();
                        Log.d("vjsbvjsvj",response);
                        try {
                            JSONObject jsonResponce = new JSONObject(response);

                            String get_status = jsonResponce.getString("status");
                            if (get_status.equals("200")) {
                                Otp = jsonResponce.getString("otp");
                                Toast.makeText(Otp_send.this, "OTP send successfully.", Toast.LENGTH_SHORT).show();
                               /* Intent intent = new Intent(getApplicationContext(),Otp_send.class);
                                intent.putExtra("otp",getotp);
                                startActivity(intent);
*/


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params = new HashMap<>();
                        params.put("mobile",getnumber);

                        return params;
                    }
                };
                queue.add(stringRequest);




                // Here, you can add your resend logic
                // Restart the timer after resend action
                startResendTimer(30000);
            }

        });

    }



    private void startResendTimer(long timeInMilliseconds) {
        resendotp.setEnabled(false); // Disable resend button

        countDownTimer = new CountDownTimer(timeInMilliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                // Update UI
                long seconds = (millisUntilFinished / 1000) % 60;
                long minutes = (millisUntilFinished / (1000 * 60)) % 60;
                String timeFormatted = String.format("%02d:%02d", minutes, seconds);
                timerTextView.setText(timeFormatted);
            }

            public void onFinish() {
                // Enable the resend button and reset the timer text
                resendotp.setEnabled(true);
                timerTextView.setText("00:00");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    }
