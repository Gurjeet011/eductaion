package com.education.allahabad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.education.allahabad.Adapter.SyllbusSubadaterr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SyallubusSUb extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageViewzx;
    ArrayList<Modelclass> arrayList;
    SyllbusSubadaterr syllbusSubadaterr;
    String syllbusid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syallubus_sub);
        recyclerView = findViewById(R.id.testrcx);
        imageViewzx= findViewById(R.id.imgdx);
        imageViewzx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList= new ArrayList<>();
        syllbusid = getIntent().getStringExtra("sylid");
        Log.d("ddddddddddddd",syllbusid);

        // getide = getIntent().getStringExtra("id");
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url ="https://techcanopus.in/study/api/?p=getSyllabusByCategory";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d("asdjwaj",response);
                    String getstatus = jsonObject1.getString("status");
                    if (getstatus.equals("200")) {
                        String getdata = jsonObject1.getString("data");
                        JSONArray jsonArray = new JSONArray(getdata);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String iddnx = jsonObject.getString("id");
                            String nameex = jsonObject.getString("name");
                            String imgvx = jsonObject.getString("image");
                            String pdf_filex = jsonObject.getString("pdf");


                            Log.d("vvvvvvvvvvv",getdata);

                            Modelclass modelclass = new Modelclass();
                            modelclass.setId(iddnx);
                            modelclass.setCat(nameex);
                            modelclass.setImg(imgvx);
                            modelclass.setPdf_file(pdf_filex);

                            arrayList.add(modelclass);


                        }


                        syllbusSubadaterr = new SyllbusSubadaterr(getApplicationContext(), arrayList, new SyllbusSubadaterr.click() {
                            @Override
                            public void download_click(String fileUrl) {
                                if (ContextCompat.checkSelfPermission(SyallubusSUb.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SyallubusSUb.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    downloadPdf(fileUrl);
                                }else {
                                    ActivityCompat.requestPermissions(SyallubusSUb.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
                                }
                            }
                        });
                        recyclerView.setAdapter(syllbusSubadaterr);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(SyallubusSUb.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", syllbusid);
                return params;
            }

        };
        queue.add(stringRequest);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==11){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadPdf(String fileUrl) {

        // Download PDF
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));

        // Save the downloaded file to the Download directory
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloaded.pdf");

        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            Toast.makeText(getApplicationContext(), "PDF download started", Toast.LENGTH_SHORT).show();
            // Update download status of PdfItem and notify adapter
        } else {
            Toast.makeText(getApplicationContext(), "Failed to start PDF download", Toast.LENGTH_SHORT).show();
        }
    }

}