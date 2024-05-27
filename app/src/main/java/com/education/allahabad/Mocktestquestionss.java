package com.education.allahabad;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.Locale;
import java.util.Map;

public class Mocktestquestionss extends AppCompatActivity implements View.OnClickListener{
    String iD;
    TextView question;
    TextView optiona;
    TextView optionb;
    TextView optionc;
    TextView optiond;
    TextView Timer;
    TextView selectedAnsawr;

    AppCompatButton saveandnext,finish_btn;

    int count;

    int get_count;
    int timerrr;
    String iddn, user_id, questionid, anser, attemptTestId,q_id;
    CountDownTimer countDownTimer;

    SharedPreferences sharedPreferences;
    RelativeLayout rl1,rl2,rl3,rl4;
    //  int defaultStrokeColor;

    String selectedOption = "";
    ImageView imageView;

    String id,get_testId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktestquestionss);
        imageView=findViewById(R.id.timg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //   saveandnextapi();
        rl1=findViewById(R.id.a);
        rl2=findViewById(R.id.b);
        rl3=findViewById(R.id.c);
        rl4=findViewById(R.id.d);

        //  iD = getIntent().getStringExtra("");
        question = findViewById(R.id.testque);
        optiona = findViewById(R.id.ta);
        optionb = findViewById(R.id.tb);
        optionc = findViewById(R.id.tc);
        optiond = findViewById(R.id.td);
        Timer = findViewById(R.id.timer);
        saveandnext = findViewById(R.id.saveandnext);
        finish_btn = findViewById(R.id.finish_btn);

        sharedPreferences = getSharedPreferences("count", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("mocktestid", null);
        iD = sharedPreferences.getString("att_id", null);
        count = sharedPreferences.getInt("countss", 0);

        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid", null);

        getQuationApi();


        saveandnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedOption.equals("")) {
                    selectedOption = "";
                    fetchNextQuestion();

                } else {
                    // Call fetchNextQuestion here to move to the next question
                    fetchNextQuestion();

                }

            }
        });
    }

    void saveSelectedAnswer() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=saveQuestion";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdkflhr",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String get_status = jsonObject.getString("status");

                    if (get_status.equals("200")){

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Do nothing here, as we will move to the next question when "Save & Next" is clicked
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(Mocktestquestionss.this, error+"", Toast.LENGTH_SHORT).show();

                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("testId", get_testId);
                params.put("attemptTestId", iD);
                params.put("userId", user_id);
                params.put("questionId", q_id);
                params.put("selectedAnswer", selectedOption);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    void fetchNextQuestion() {
        count += 1;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("countss", count);
        editor.apply();
        Log.d("vherth",count+"");
        Log.d("hrhse",get_count+"");
        // Always call saveSelectedAnswer if count is less than or equal to get_count
        if (count <=get_count) {
            Intent intent = new Intent(getApplicationContext(),Mocktestquestionss.class);
            startActivity(intent);
            finish();
            saveSelectedAnswer();
        }
//        if (count==get_count){
//            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//        }
//        // When count exceeds get_count, update the button to say "Finish"
//        // and set its click listener to call finish_quation_api()
//        if (count>get_count) {
//            saveandnext.setText("Finish");
//            saveandnext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    saveSelectedAnswer();
//                    finish_quation_api();
//                }
//            });
//        }
    }



    private void finish_quation_api() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=finalAttempt";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dkghsd",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String get_status = jsonObject.getString("status");

                    if (get_status.equals("200")){
                        String get_totalMarkObtain = jsonObject.getString("totalMarkObtain");
                        String get_totalMarksFrom = jsonObject.getString("totalMarksFrom");
                        String get_correct = jsonObject.getString("correct");
                        String get_incorrect = jsonObject.getString("incorrect");
                        String attemptQuestions = jsonObject.getString("attemptQuestions");
                        String totalQuestions = jsonObject.getString("totalQuestions");

                        Intent intent = new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("mark",get_totalMarkObtain);
                        intent.putExtra("tot_mark",get_totalMarksFrom);
                        intent.putExtra("correct",get_correct);
                        intent.putExtra("incorrect",get_incorrect);
                        intent.putExtra("attempt",attemptQuestions);
                        intent.putExtra("total",totalQuestions);
                        startActivity(intent);
                        finish();
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("testId", id);
                params.put("attemptTestId", iD);
                params.put("userId", user_id);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void startTimer(int seconds) {
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                Timer.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                Timer.setText("00:00");

                fetchNextQuestion();

            }

        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    void getQuationApi(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/index.php?p=getQuestions";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String getstatus = jsonObject1.getString("status");
                    Log.d("slfjsr",response);
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            questionid = jsonObject.getString("question");
                            get_testId = jsonObject.getString("testId");
                            q_id = jsonObject.getString("id");
                            anser = jsonObject.getString("answer");
                            String a = jsonObject.getString("optionA");
                            String b = jsonObject.getString("optionB");
                            String c = jsonObject.getString("optionC");
                            String d = jsonObject.getString("optionD");

                            question.setText(Html.fromHtml(questionid));
                            optiona.setText("A. "+a);
                            optionb.setText("B. "+b);
                            optionc.setText("C. "+c);
                            optiond.setText("D. "+d);
                        }

                        timerrr = Integer.parseInt(jsonObject1.getString("time"));
                        startTimer(Integer.parseInt(String.valueOf(timerrr)));
                        iddn = jsonObject1.getString("total");
                        get_count = Integer.parseInt(iddn);


                        if (get_count==count){
                            saveandnext.setVisibility(View.GONE);
                            finish_btn.setVisibility(View.VISIBLE);
                            finish_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    saveSelectedAnswer();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish_quation_api();
                                        }
                                    },1000);

                                }
                            });
                        }else {
                            saveandnext.setVisibility(View.VISIBLE);
                            finish_btn.setVisibility(View.GONE);
                        }

                        optiona.setOnClickListener(Mocktestquestionss.this);
                        optionb.setOnClickListener(Mocktestquestionss.this);
                        optionc.setOnClickListener(Mocktestquestionss.this);
                        optiond.setOnClickListener(Mocktestquestionss.this);


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
                params.put("page", String.valueOf(count));
                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        if (selectedAnsawr !=null){
            selectedAnsawr.setBackground(getResources().getDrawable(R.drawable.clickcolorchange));
        }

        TextView clickedTextView = (TextView) view;
        clickedTextView.setBackground(getResources().getDrawable(R.drawable.testtttt));

        selectedAnsawr = clickedTextView;

        // Get the selected option value
        if (selectedAnsawr==optiona){
            selectedOption = "A";
        }else if (selectedAnsawr==optionb){
            selectedOption = "B";
        }else if (selectedAnsawr==optionc){
            selectedOption = "C";
        }else if (selectedAnsawr==optiond){
            selectedOption = "D";
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}