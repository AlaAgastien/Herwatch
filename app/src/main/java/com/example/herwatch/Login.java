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
import androidx.activity.result.contract.ActivityResultContracts;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    //object
    EditText Email,Password;
    Button Login;
    TextView Forgot,Reg;
    String id,n1,n2,n3,n4,n5,status,message;

    String url=config.baseurl+"login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        //id binding
        Email=findViewById(R.id.LogEmail);
        Password=findViewById(R.id.LogPass);
        Forgot=findViewById(R.id.LogForgot);
        Login=findViewById(R.id.LogButton);
        Reg=findViewById(R.id.LogReg);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Login.this,Register.class);
                startActivity(in);
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot =new Intent(Login.this, Forgotpassword.class);
                startActivity(forgot);
            }
        });


        //intent

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //method

                Login2();
            }
            });

        }


            private void Login2() {
                n2=Email.getText().toString();
                n5=Password.getText().toString();

                if(TextUtils.isEmpty(n2)){
                    Email.requestFocus();
                    Email.setError("Required !!!");
                    return;
                }
                if(TextUtils.isEmpty(n5)){
                    Password.requestFocus();
                    Password.setError("Enter the password !!!");
                    return;
                }

                if(n5.length()<6){
                    Password.requestFocus();
                    Password.setError("Password should have atleast 6 characters !!!");
                }

                if(n5.length()>12){
                    Password.requestFocus();
                    Password.setError("Password shouldn't exceed 12 characters !!!");
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

                                    id=c.getString("id");
                                    n1=c.getString("name");
                                    n2=c.getString("email");
                                    n3=c.getString("phone");
                                    n4=c.getString("guardiannumber");
                                    n5=c.getString("password");


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
                                Toast.makeText(Login.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }

                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", n2);
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
            new Sessionmanager(Login.this).createLoginSession(id,n1,n2,n3,n4,n5);
            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(Login.this,Home.class);
            startActivity(i);
            finish();
        }

    }
}