package com.example.herwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.herwatch.Psychologistmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PsychologistActivity extends AppCompatActivity {


    EditText search1;
    String uid;
    ImageView img;
    private String url = config.baseurl+"PsychologistList.php";
    private ArrayList<Psychologistmodel> dataModelArrayList;
    private PsychologistAdapter rvvAdapter;
    private RecyclerView recyclerView1;
    private ProgressBar p1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist2);

        recyclerView1 = findViewById(R.id.Psyactivity);
        p1 = findViewById(R.id.Psyactivityprogress);
        img = findViewById(R.id.booking);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(PsychologistActivity.this,docbookedlistactivity.class);
                startActivity(in);
            }
        });


        search1=findViewById(R.id.Psyactivitysrch);
        search1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable text) {
                //new array list that will hold the filtered data
                ArrayList<Psychologistmodel> filteredSongs = new ArrayList<>();

                if (dataModelArrayList != null && !dataModelArrayList.isEmpty()) {
                    //looping through existing elements
                    for (Psychologistmodel  s1: dataModelArrayList) {
                        //if the existing elements contains the search input
                        if (s1.getSpecialization().toLowerCase().contains(text.toString().toLowerCase())) {
                            //adding the element to filtered list
                            filteredSongs.add(s1);
                        }
                    }
                }

                if (rvvAdapter != null) {
                    //calling a method of the adapter class and passing the filtered list
                    rvvAdapter.filterList(filteredSongs);
                }
            }
        });

        fetchingJSON();
    }



    private void fetchingJSON() {

        p1.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            p1.setVisibility(View.GONE);

                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);



                                dataModelArrayList.add(new Psychologistmodel(
                                        dataobj.getString("id"),
                                        dataobj.getString("name"),
                                        dataobj.getString("email"),
                                        dataobj.getString("phone"),
                                        dataobj.getString("work"),
                                        dataobj.getString("specialization"),
                                        dataobj.getString("experience"),
                                        dataobj.getString("password")





                                ));
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
                        p1.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
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
                p1.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void setupRecycler(){
        rvvAdapter = new PsychologistAdapter(this, dataModelArrayList);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(rvvAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

}
