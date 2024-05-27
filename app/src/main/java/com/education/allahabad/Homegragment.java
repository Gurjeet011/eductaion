package com.education.allahabad;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.education.allahabad.Adapter.Homeadapter;
import com.education.allahabad.Adapter.Slideradapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Homegragment extends Fragment {
    Slideradapter slideradapter;
    SliderView sliderView;
    ArrayList<Modelclass> arrayList;
    ArrayList<Modelclass2> arrayList2;
    RequestQueue queue;
    Homeadapter homeadapter;
    RecyclerView recyclerView;
    String nam;
    TextView text;
    ImageView omg;
    String getid;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homegragment, container, false);
        queue = Volley.newRequestQueue(getContext());
        recyclerView = view.findViewById(R.id.rc1);
        sliderView = view .findViewById(R.id.baner);
        progressBar = view.findViewById(R.id.progress_bar); // Initialize ProgressBar
       progressBar.setVisibility(View.VISIBLE); // Show ProgressBar

        //  text= view.findViewById(R.id.username);
       //usernamefetch
      /*  SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", MODE_PRIVATE);
        getid = sharedPreferences.getString("userid",null);

        String get_name = text.getText().toString();*/

      /*  RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://techcanopus.in/study/api/index.php?p=getProfile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponce = new JSONObject(response);
                    Log.d ("ufbvrbv",response);
                    String get_status = jsonResponce.getString("status");
                    if (get_status.equals("200")) {
                        String get_data = jsonResponce.getString("data");
                        JSONArray jsonArray = new JSONArray(get_data);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String get_name = jsonObject.getString("name");

                            text.setText(get_name);


                        }
                        // String get_data = jsonResponce.getString("message");



                      //  Toast.makeText(Homegragment, "sucessfully update profile", Toast.LENGTH_SHORT).show();

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

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",getid);












                return params;
            }
        };
        queue.add(stringRequest);

*/
        //  omg = view.findViewById(R.id.omg);
        arrayList = new ArrayList<>();
        StringRequest stringRequestz = new StringRequest(Request.Method.GET, "https://techcanopus.in/study/api/index.php?p=getBanner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

                          /*  String img_uri = "https://techcanopus.in/study/assets/upload/" + imgg;

                            Glide.with(getContext()).load(img_uri).into(omg);*/
                        }
                    }
                    slideradapter = new Slideradapter(getActivity(), arrayList);
                    sliderView.setSliderAdapter(slideradapter);
                    sliderView.setScrollTimeInSec(3);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM);
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);


                    progressBar.setVisibility(View.GONE); // Hide ProgressBar after receiving the response

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               //// Toast.makeText(getContext(), "responce" + error, Toast.LENGTH_SHORT).show();
               progressBar.setVisibility(View.GONE); // Hide ProgressBar in case of VolleyError

            }
        });


        arrayList2 = new ArrayList<>();
        arrayList2.add(new Modelclass2("Free Course",R.drawable.man));
        arrayList2.add(new Modelclass2("Paid Course",R.drawable.freepdf));
        arrayList2.add(new Modelclass2("Free Test Series",R.drawable.freetest));
        arrayList2.add(new Modelclass2("Free PDF",R.drawable.paidcourse));
        arrayList2.add(new Modelclass2("Syllabus",R.drawable.syllabus));

        homeadapter = new Homeadapter(getActivity(),arrayList2);
        recyclerView.setAdapter(homeadapter);
        GridLayoutManager layoutManagerr = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManagerr);





         queue.add(stringRequestz);

        return view;
    }
}