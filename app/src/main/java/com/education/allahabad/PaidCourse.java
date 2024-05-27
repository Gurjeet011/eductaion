package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Paidcourseadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaidCourse extends AppCompatActivity {
    Paidcourseadapter paidcourseadapter;
    ArrayList<Modelclass>arrayList;
    RecyclerView recyclerView;
    ImageView imageView;
    String get_user_idd;
  //  String purchasStatus;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_course);
        recyclerView=findViewById(R.id.paidrc);
        imageView= findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        get_user_idd = sharedPreferences.getString("userid",null);

       // Toast.makeText(this, get_user_idd+"", Toast.LENGTH_SHORT).show();
        arrayList=new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://techcanopus.in/study/api/?p=getPaidCourseList";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JsonResponce = new JSONObject(response);
                    String status = JsonResponce .getString("status");
                    if(status.equals("200")){
                        String getdata = JsonResponce .getString("data");
                        Log.d("aaaa",getdata);
                        JSONArray jsonArray = new JSONArray(getdata);
                        for(int i=0; i< jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String expiredate = jsonObject.getString("expiryDate");
                            String name=jsonObject.getString("courseName");
                            String photo = jsonObject.getString("thumbnail");
                            String id1 = jsonObject.getString("id");
                            String purchId = jsonObject.getString("purchasedId");
                            String alreadypurch = jsonObject.getString("alreadyPurchased");
                            Log.d("sksahh",alreadypurch);
                           String purchasStatus=jsonObject.getString("purchasedStatus");
                           // Toast.makeText(PaidCourse.this, alreadypurch+"", Toast.LENGTH_SHORT).show();
                            Modelclass modelclass = new Modelclass();
                            modelclass.setExpdate(expiredate);
                            modelclass.setImg(photo);
                            modelclass.setTxt(name);
                            modelclass.setId(id1);
                           /* modelclass.setPurchase_id(purchId);
                            modelclass.setAlready_purchase(alreadypurch);
                            modelclass.setPurchase_status(purchasStatus);
*/
                            arrayList.add(modelclass);
                            paidcourseadapter = new Paidcourseadapter(getApplicationContext(), arrayList);
                            recyclerView.setAdapter(paidcourseadapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(PaidCourse.this, error+"error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", get_user_idd);

                return params;
            }

        };




        queue.add(stringRequest);
    }
}