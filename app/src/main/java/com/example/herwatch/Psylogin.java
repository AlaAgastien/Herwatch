package com.example.herwatch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Psylogin extends AppCompatActivity {
    EditText email,pass;
    Button login;
    TextView RegPsy,forgot;
    String n1,n2,n3,n4,n5,n6,n7,status,message,id;
    String url=config.baseurl+"Psylogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_psylogin);

        email=findViewById(R.id.LogEmailPsy);
        pass=findViewById(R.id.LogPassPsy);
        login=findViewById(R.id.LogButtonPsy);
        forgot=findViewById(R.id.forgotpass);
        RegPsy=findViewById(R.id.RegbackPsy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass = new Intent(Psylogin.this,forgotPass_psy.class);
                startActivity(pass);
            }
        });

        RegPsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(Psylogin.this,Psychologist.class);
                startActivity(l);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {login2();


            }
        });
    }

    private void login2() {
        n1=email.getText().toString();
        n2=pass.getText().toString();

        if (TextUtils.isEmpty(n1)){
            email.requestFocus();
            email.setError("Enter email id!!!");
            return;
        }

        if (TextUtils.isEmpty(n2)){
            pass.requestFocus();
            pass.setError("Enter password!!!");
            return;

        }
        StringRequest StringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            message = c.getString("message");

                            id=c.getString("id");
                            n3=c.getString("name");
                            n1=c.getString("email");
                            n4=c.getString("phone");
                            n5=c.getString("work");
                            n6=c.getString("specialization");
                            n7=c.getString("experience");
                            n2=c.getString("password");
                            checklogin();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Psylogin.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", n1);
                params.put("password", n2 );
                return params;
            }


        };


        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }


    private void checklogin() {
        if (status.equals("0")){
            Toast.makeText(this, "Invalied", Toast.LENGTH_SHORT).show();
        }else {
            new PsySessionManager(Psylogin.this).createLoginSession(id,n3,n1,n4,n5,n6,n7,n2);
            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(Psylogin.this,PsycologistHome.class);
            startActivity(i);
            finish();
        }
    }

}
