package com.example.herwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class docbookedlistactivity extends AppCompatActivity {


    EditText search1;
    String uid;

    private String url = config.baseurl+"docbookedlist.php";
    private ArrayList<docbookedmodel> dataModelArrayList;
    private docbookedAdapter rvvAdapter;
    private RecyclerView recyclerView1;
    private ProgressBar p1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docbookedlistactivity);

        recyclerView1 = findViewById(R.id.BookinglistActivity);
        p1 = findViewById(R.id.bookingactivityprogress);




        search1=findViewById(R.id.previousSearch);
        search1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable text) {
                //new array list that will hold the filtered data
                ArrayList<docbookedmodel> filteredSongs = new ArrayList<>();

                if (dataModelArrayList != null && !dataModelArrayList.isEmpty()) {
                    //looping through existing elements
                    for (docbookedmodel  s1: dataModelArrayList) {
                        //if the existing elements contains the search input
                        if (s1.getDocname().toLowerCase().contains(text.toString().toLowerCase())) {
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



                                dataModelArrayList.add(new docbookedmodel(
                                        dataobj.getString("id"),
                                        dataobj.getString("docname"),
                                        dataobj.getString("doc_contact"),
                                        dataobj.getString("patientname"),
                                        dataobj.getString("patientnum"),
                                        dataobj.getString("patientage"),
                                        dataobj.getString("dateselect"),
                                        dataobj.getString("timeselect")





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
        rvvAdapter = new docbookedAdapter(this, dataModelArrayList);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(rvvAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }}