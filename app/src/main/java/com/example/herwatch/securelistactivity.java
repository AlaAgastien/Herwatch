package com.example.herwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class securelistactivity extends AppCompatActivity {

    FloatingActionButton readd;

    EditText search;
    Button edit;
    String uid,sphn;

    private String url = config.baseurl+"securelist.php";
    private ArrayList<securemodel> dataModelArrayList;
    private secureadapter rvvAdapter;
    private RecyclerView recyclerView;
    private ProgressBar p;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securelistactivity);

        recyclerView = findViewById(R.id.cycle1231book5);
        p = findViewById(R.id.bar123book5);
        // readd=findViewById(R.id.addpetbook5);


//        edit=findViewById(R.id.campbutton);
        HashMap<String,String> rev=new PsySessionManager(securelistactivity.this).getUserDetails();

        sphn=rev.get("phone");



        fetchingJSON();
    }



    private void fetchingJSON() {

        p.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            p.setVisibility(View.GONE);

                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);


                                if (dataobj.getString("fee").equals("200")) {
                                    dataModelArrayList.add(new securemodel(
                                            dataobj.getString("id"),
                                            dataobj.getString("docname"),
                                            dataobj.getString("doc_contact"),
                                            dataobj.getString("patientname"),
                                            dataobj.getString("patientage"),
                                            dataobj.getString("patientnum"),
                                            dataobj.getString("dateselect"),
                                            dataobj.getString("timeselect"),
                                            dataobj.getString("status"),
                                            dataobj.getString("fee"),
                                            dataobj.getString("feedate")
                                    ));
                                }
                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("doc_contact", sphn);
                return params;
            }


        };



        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) {
                p.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void setupRecycler(){
        rvvAdapter = new secureadapter(this, dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

}