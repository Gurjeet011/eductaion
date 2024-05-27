package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Slideradapter;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Banner extends AppCompatActivity {
    Slideradapter slideradapter;
    SliderView sliderView;
    ArrayList<Modelclass> arrayList;
    RequestQueue queue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        queue = Volley.newRequestQueue(getApplicationContext());
        sliderView = findViewById(R.id.baner);
       // omg = findViewById(R.id.omg);
        arrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://techcanopus.in/study/api/index.php?p=getBanner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "responce" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String get_status = jsonObject.getString("status");
                    if (get_status.equals("200")) {
                        String get_data = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(get_data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String imgg = jsonObject1.getString("image");
                            Modelclass modelclass = new Modelclass();
                            modelclass.setImg(imgg);
                            arrayList.add(modelclass);

                            slideradapter = new Slideradapter(getApplicationContext(), arrayList);
                            sliderView.setSliderAdapter(slideradapter);
                            sliderView.setScrollTimeInSec(2);
                            sliderView.setAutoCycle(true);
                          /*  String img_uri = "https://techcanopus.in/study/assets/upload/" + imgg;

                            Glide.with(getApplicationContext()).load(img_uri).into(omg);*/
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "responce" + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }
}