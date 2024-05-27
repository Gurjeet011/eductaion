package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Pdfadadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pdfListcategory extends AppCompatActivity {
    RecyclerView recyclerViewa;
    Pdfadadapter freePdfadadapter;
    ArrayList<Modelclass> arrayList;
    String pdf;
    String getidd;
    ImageView imageView;

    String[] new_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freepdf);
        recyclerViewa = findViewById(R.id.pdfrc);
        imageView=findViewById(R.id.ghg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList = new ArrayList<>();
        getidd = getIntent().getStringExtra("iD");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://techcanopus.in/study/api/?p=getFreePdfListByCategory";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                   // Toast.makeText(pdfListcategory.this, response + "", Toast.LENGTH_SHORT).show();
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        Log.d("hsfdskbfkb", getdata);

                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            // String iddn = jsonObject.getString("id");
                            String namexe = jsonObject.getString("name");
                            String pdf_file = jsonObject.getString("pdf");
                            String imgf = jsonObject.getString("image");


                            Modelclass modelclass = new Modelclass();
                            modelclass.setPdftext(namexe);
                            modelclass.setImg(imgf);
                            modelclass.setPdf_file(pdf_file);
                            arrayList.add(modelclass);

                            // Log.d("askjrjhw",mul_videos);
                        }


                        freePdfadadapter = new Pdfadadapter(getApplicationContext(), arrayList);
                        recyclerViewa.setAdapter(freePdfadadapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerViewa.setLayoutManager(layoutManager);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(pdfListcategory.this, error + "", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", getidd);

                return params;
            }
        };
        queue.add(stringRequest);


    }
}

//    private JSONArray mergeJSONArrays(JSONArray js, JSONArray jsonArray1) {
//        JSONArray new_array = new JSONArray();
//
//        // Add elements from the first array
//       /* for (int i = 0; i < array1.length(); i++) {
//            try {
//                new_array.put(array1.getJSONObject(i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Add elements from the second array
//        for (int i = 0; i < array2.length(); i++) {
//            try {
//                new_array.put(array2.getJSONObject(i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }*/
//
//        return new_array;
//    }
