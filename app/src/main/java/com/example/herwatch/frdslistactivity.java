package com.example.herwatch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class frdslistactivity extends AppCompatActivity {

    private FloatingActionButton readd;
    private EditText search;
    private ImageView sos;
    private ProgressBar p;
    private RecyclerView recyclerView;

    private String uid, sphn, message;
    private String url = config.baseurl + "frdslist.php";

    private ArrayList<frdsmodel> dataModelArrayList;
    private frdsadapter rvvAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_frdslistactivity);

        recyclerView = findViewById(R.id.cycle1231bookf);
        p = findViewById(R.id.bar123bookf);
//        sos = findViewById(R.id.sos);

        HashMap<String, String> rev = new Sessionmanager(this).getUserDetails();
        sphn = rev.get("phone");

        search = findViewById(R.id.search123f);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                filterList(text.toString());
            }
        });

//        sos.setOnClickListener(view -> sos1());
        fetchingJSON();
    }

    private void fetchingJSON() {
        p.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    p.setVisibility(View.GONE);
                    try {
                        dataModelArrayList = new ArrayList<>();
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject dataobj = array.getJSONObject(i);
                            dataModelArrayList.add(new frdsmodel(
                                    dataobj.getString("id"),
                                    dataobj.getString("name"),
                                    dataobj.getString("phonenumber"),
                                    dataobj.getString("fname"),
                                    dataobj.getString("category"),
                                    dataobj.getString("fphonenumber")
                            ));
                        }
                        setupRecycler();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    p.setVisibility(View.GONE);
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phonenumber", sphn);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupRecycler() {
        rvvAdapter = new frdsadapter(this, dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void filterList(String query) {
        ArrayList<frdsmodel> filteredList = new ArrayList<>();
        for (frdsmodel model : dataModelArrayList) {
            if (model.getFnmae().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(model);
            }
        }
        rvvAdapter.filterList(filteredList);
    }
}

