package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    String mark,tot_mark,correct,incorrect,total,attempt;

    TextView result1,result3,result,result2;
    TextView viewdetail;
    ImageView timgee;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        timgee=findViewById(R.id.timg);
        timgee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Homepage.class));
            }
        });
        viewdetail=findViewById(R.id.viewdetal);
        viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),View_details.class));
            }
        });
        result1 = findViewById(R.id.result1);
        result3 = findViewById(R.id.result3);
        result = findViewById(R.id.result);
        result2 = findViewById(R.id.result2);



        mark = getIntent().getStringExtra("mark");
        tot_mark = getIntent().getStringExtra("tot_mark");
        correct = getIntent().getStringExtra("correct");
        incorrect = getIntent().getStringExtra("incorrect");
        attempt = getIntent().getStringExtra("attempt");
        total = getIntent().getStringExtra("total");


        result.setText(attempt+ "/"+total);
        result1.setText(correct);
        result2.setText(incorrect);
        result3.setText(mark+"/"+tot_mark);

    }


}