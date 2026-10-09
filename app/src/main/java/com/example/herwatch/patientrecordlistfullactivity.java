package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class patientrecordlistfullactivity extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    ImageView img;
    String id1,st1,st2,st3,st4,st5,st6,st7,st8,st9,st10,st11,st12,simg,gender,status,message,url=config.baseurl+"approve.php";
    RadioGroup regen;
    Button b1;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patientrecordlistfullactivity);

        t1=findViewById(R.id.cdocname1);
        t2=findViewById(R.id.cdocemail1);
        t4=findViewById(R.id.cusername1);
        t5=findViewById(R.id.cuserphonenumber1);
        t6=findViewById(R.id.cpetname1);

        t10=findViewById(R.id.date123);
        t11=findViewById(R.id.time123);
        regen = findViewById(R.id.gen1);
        b1 = findViewById(R.id.sub);


        Intent intent = getIntent();
        id1 = intent.getStringExtra("id");

        st1 = intent.getStringExtra("docname");
        t1.setText(st1);

        st2 = intent.getStringExtra("doc_contact");
        t2.setText( st2);


        st4 = intent.getStringExtra("patientname");
        t4.setText( st4);

        st5 = intent.getStringExtra("patientnum");
        t5.setText( st5);

        st6 = intent.getStringExtra("patientage");
        t6.setText( st6);


        st10 = intent.getStringExtra("dateselect");
        t10.setText( st10);

        st11 = intent.getStringExtra("timeselect");
        t11.setText( st11);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve();
            }
        });
    }

    private void approve()
    {
        int id = regen.getCheckedRadioButtonId();
        if (id == -1) {
            // No radio button is checked, show an error message
            Toast.makeText(this, "Please select a one option", Toast.LENGTH_SHORT).show();
            return; // Exit the method if no button is selected
        } else {
            RadioButton radioButton = regen.findViewById(id);
            gender = radioButton.getText().toString();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(patientrecordlistfullactivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            message = c.getString("message");
                            checklogin();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(patientrecordlistfullactivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", id1);

                params.put("docname", st1);
                params.put("doc_contact",st2);

                params.put("patientname",st4);
                params.put("patientnum", st6);
                params.put("patientage",st5);
                params.put("dateselect",st10);
                params.put("timeselect",st11);
                params.put("status", gender);
                return params;
            }
        };

        // Execute the request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checklogin() {
        if (status.equals("0")) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Submit successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(patientrecordlistfullactivity.this, PsycologistHome.class);
            startActivity(i);
            finish();
        }
    }
}