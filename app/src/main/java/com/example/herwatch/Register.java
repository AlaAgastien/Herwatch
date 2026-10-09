package com.example.herwatch;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
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

public class Register extends AppCompatActivity {
//object
    EditText Name,Email,Phonenumber,Guardiannumber,Password,Confirm;
    Button SignUp;
    TextView Regis;
    String n1,n2,n3,n4,n5,n6, status,message;


   String url=config.baseurl+"register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        //id binding

        Name=findViewById(R.id.RegName);
        Email=findViewById(R.id.RegEmail);
        Phonenumber=findViewById(R.id.Regphone);
        Guardiannumber=findViewById(R.id.Regguard);
        Password=findViewById(R.id.RegPass);
        SignUp=findViewById(R.id.RegButton);
        Regis=findViewById(R.id.RegLog);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(Register.this,Login.class);
                startActivity(l);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login1();
            }
        });
    }

            private void Login1() {
                n1=Name.getText().toString();
                n2=Email.getText().toString();
                n3=Phonenumber.getText().toString();
                n4=Guardiannumber.getText().toString();
                n5=Password.getText().toString();
               // n6=Confirm.getText().toString();

               if(TextUtils.isEmpty(n1)){
                    Name.requestFocus();
                    Name.setError("Enter your name!!!");
                    return;
                }

               if(TextUtils.isEmpty(n2)){
                    Email.requestFocus();
                    Email.setError("Enter your email id!!! ");
                    return;
                }

                if(TextUtils.isEmpty(n3)) {
                    Phonenumber.requestFocus();
                    Phonenumber.setError("Enter your phone number!!");
                    return;
                }

                if (n3.length()> 10) {
                    Phonenumber.setError( "Enter Valid Phone Number");
                    return;
                }

                if (n3.length()< 10) {
                    Phonenumber.setError( "Enter Valid Phone Number");
                    return;
                }

                if (TextUtils.isEmpty(n4)) {
                    Guardiannumber.requestFocus();
                    Guardiannumber.setError("Enter phone number");
                    return;
                }

                if (n4.length()> 10) {
                    Guardiannumber.setError( "Enter Valid Phone Number");
                    return;
                }

                if (n4.length()<10) {
                    Guardiannumber.setError( "Enter Valid Phone Number");
                    return;
                }

                if (TextUtils.isEmpty(n5)) {
                    Password.requestFocus();
                    Password.setError("Enter your password");
                    return;
                }

// Check if the password length exceeds 6 characters
                if (n5.length() < 6) {
                    Password.requestFocus();
                    Password.setError("Password must be atleast 6 characters");
                    return;
                }

                if (n5.length() > 12) {
                    Password.requestFocus();
                    Password.setError("Password must be less than 12 characters");
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
                                Toast.makeText(Register.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }

                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", n1);
                        params.put("email", n2 );
                        params.put("phone",n3);
                        params.put("guardiannumber",n4);
                        params.put("password", n5);

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
            Intent i =new Intent(Register.this,Login.class);
            startActivity(i);
            finish();
        }

    }
}
