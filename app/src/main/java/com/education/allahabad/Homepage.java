package com.education.allahabad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Homepage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
       androidx.appcompat.widget.Toolbar toolbar;
       SharedPreferences sharedPreferences;
       boolean press_back=false;
       TextView textView2,textView3,username;
       Toast toast;
       String getid,text;

       TextView no,yes;

    NavigationView navigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

         toolbar= findViewById(R.id.toolbar);
         username=findViewById(R.id.usernamee);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("id", MODE_PRIVATE);
        getid = sharedPreferences.getString("userid",null);

        String get_name = username.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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

                            username.setText(get_name);


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




        navigationView = findViewById(R.id.nav_view);

      /*  relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Update_password.class));
            }
        });*/

       View view =  navigationView.getHeaderView(0);

        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();
        navigationView.setItemIconTintList(null);

        SharedPreferences sharedPreferencess = getSharedPreferences("id", MODE_PRIVATE);
        getid = sharedPreferencess.getString("userid",null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here
                int intent = item.getItemId();
                if (intent == R.id.profie) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));

                }
                if (intent == R.id.history) {

                    startActivity(new Intent(getApplicationContext(), MY_purchase.class));

                }
                if (intent == R.id.Share) {

                    Intent intent1 = new Intent(Intent.ACTION_SEND);

                    intent1.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.education.allahabad ");
                    intent1.setType("text/pain");
                    Intent chooser = Intent.createChooser(intent1, "Share via");

// Verify that the intent will resolve to at least one activity
                    if (intent1.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooser);
                    }
                }
                if (intent == R.id.feedback) {
                    startActivity(new Intent(getApplicationContext(), Help_Feedback.class));

                }
                if (intent == R.id.Conditions) {
                    startActivity(new Intent(getApplicationContext(), Terms_condition.class));

                }
                if (intent == R.id.policy) {
                    startActivity(new Intent(getApplicationContext(), Privacy_policy.class));

                }



                if (intent == R.id.logout) {
                    // startActivity(new Intent(getApplicationContext(), Logout.class));

                    AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialouge_logout, null);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    no=dialogView.findViewById(R.id.no);
                    yes=dialogView.findViewById(R.id.yes);
                    //  TextView messageTextView = dialogView.findViewById(R.id.dialog_message);
                    //  messageTextView.setText("Are you sure you want to logout?");

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences sharedPreferences1 = getSharedPreferences("id", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.clear();
                            editor.apply();
                            Intent intent1 = new Intent(getApplicationContext(),Login.class);
                            startActivity(intent1);
                            finish();
                            dialog.dismiss(); // Dismiss dialog when "Yes" is clicked
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss(); // Dismiss the dialog when "No" is clicked
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.show();
                }



                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.fragment_container, new Homegragment()).commit();

        // Bottom Navigation View

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle bottom navigation item clicks here
                int itemId = item.getItemId();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (itemId == R.id.home) {
                    transaction.replace(R.id.fragment_container,new Homegragment());
                    transaction.commit();
                    return true;
                } else if (itemId == R.id.about) {

                  //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homegragment ()).addToBackStack(null).commit();
                    startActivity(new Intent(Homepage.this, About.class));

                    return true;
                } else if (itemId == R.id.MyCourses) {

                   // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletFragment()).addToBackStack(null).commit();
                    startActivity(new Intent(Homepage.this, My_Course.class));

                    return true;
                } else if (itemId == R.id.Notification) {

                   // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserBlankFragment()).addToBackStack(null).commit();
                    startActivity(new Intent(Homepage.this, Notification.class));

                    return true;
                }

                return false;
            }
        });

        update();

    }
    private void update() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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
                            String get_number = jsonObject.getString("mobile");
                            String get_email = jsonObject.getString("email");
                            String get_distic = jsonObject.getString("district");

                            textView2.setText(get_name);
                            textView3.setText(get_number);

                        }
                        // String get_data = jsonResponce.getString("message");

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
    }

    @Override
    public void onBackPressed() {
        if (press_back){
            finishAffinity();
            toast.cancel();
        }else {
            press_back = true;
            toast = Toast.makeText(getApplicationContext(),"press again to exist",Toast.LENGTH_SHORT);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    press_back = false;
                }
            },1000);
        }

    }
}

