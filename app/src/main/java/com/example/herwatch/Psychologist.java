package com.example.herwatch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;

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

public class Psychologist extends AppCompatActivity {
    EditText name, email, phone,work,experience, pass;
    Button Signup;
    TextView log;
    String n1,n2,n3,n4,n5,n6,n7,status,message;
    String url=config.baseurl+"psyreg.php";
    Spinner specialize;
    String dis[]={"Select your specialization", "Clinical psychology", "Neuropsychology", "Industrial-organizational psychology", "Forensic psychology", "Educational psychology", "Applied behavior analysis", "School and career counseling", "Counseling psychology","Social psychology", " Health psychology"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_psychologist);

        name = findViewById(R.id.RegNamePsy);
        email = findViewById(R.id.RegEmailPsy);
        phone = findViewById(R.id.RegphonePsy);
        work = findViewById(R.id.Regworkpsy);
        specialize = findViewById(R.id.Regspecpsy);
        experience = findViewById(R.id.Regexppsy);
        pass = findViewById(R.id.RegpassPsy);
        Signup = findViewById(R.id.RegSignPsy);
        log = findViewById(R.id.tologinPsy);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dis);
//spinner object.setAdapter(adapter);
        specialize.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(Psychologist.this, Psylogin.class);
                startActivity(l);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {login1();
                Intent l = new Intent(Psychologist.this, Psylogin.class);
                startActivity(l);
            }

        });
    }
    private void login1() {
        n1=name.getText().toString();
        n2=email.getText().toString();
        n3=phone.getText().toString();
        n4=work.getText().toString();
        n5=specialize.getSelectedItem().toString();
        n6=experience.getText().toString();
        n7=pass.getText().toString();



        if (TextUtils.isEmpty(n1)){
            name.requestFocus();
            name.setError("Enter your name!!!!");
            return;
        }

        if(TextUtils.isEmpty(n2)){
            email.requestFocus();
            email.setError("Enter your email address!!!");
            return;
        }

        if (TextUtils.isEmpty(n3)){
            phone.requestFocus();
            phone.setError("Enter your phone number!!!");
            return;
        }

        if (n3.length() > 10){
            phone.requestFocus();
            phone.setError("Enter valid number!!!");
            return;
        }

        if (n3.length() < 10){
            phone.requestFocus();
            phone.setError("Enter valid number!!!");
            return;
        }

        if (TextUtils.isEmpty(n4)) {
            work.requestFocus();
            work.setError("Enter the work place !!!");
        }
        if(n5.equals("Select your district")){
            Toast.makeText(Psychologist.this, "Select your district", Toast.LENGTH_SHORT).show();
        }
//        if(TextUtils.isEmpty(n5)) {
//            specialize.requestFocus();
//            specialize.setError("Enter your specialization!!!");
//        }

        if (TextUtils.isEmpty(n6)) {
            experience.requestFocus();
            experience.setError("Enter your experience!!!");
        }

        if (TextUtils.isEmpty(n7)){
            pass.requestFocus();
            pass.setError("Enter Password!!!");
            return;
        }
        if (n7.length()<6){
            pass.requestFocus();
            pass.setError("Password should be atleast 6 characters!!");
            return;
        }
        if (n7.length()>6){
            pass.requestFocus();
            pass.setError("Password shouldn't exceed 6 characters!!");
            return;
        }

        StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
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
                        //run cheyikkumbo error indo ennu nokkan
                        Toast.makeText(Psychologist.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", n1);
                params.put("email", n2 );
                params.put("phone",n3);
                params.put("work",n4);
                params.put("specialization",n5);
                params.put("experience",n6);
                params.put("password", n7);

                return params;
            }


        };

        //string reqt ne execute cheyan aanu requestqueue
        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }


    private void checklogin() {
        if (status.equals("0")){
            Toast.makeText(this, "Invalied", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(Psychologist.this,Psylogin.class);
            startActivity(i);
            finish();
        }

    }
}

