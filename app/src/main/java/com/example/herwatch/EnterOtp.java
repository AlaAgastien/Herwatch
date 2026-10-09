package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EnterOtp extends AppCompatActivity {

    EditText otp;
    Button b1;
    String n1,status,message,url=config.baseurl+"otp.php",username;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_otp);

        otp=findViewById(R.id.idOTP);
        b1=findViewById(R.id.sub1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp();
            }
        });
    }

            private void otp() {
                 n1 = otp.getText().toString();

                if (TextUtils.isEmpty(n1)) {
                    otp.requestFocus();
                    otp.setError("Required !!!");
                } else if (!n1.matches("^IC\\d{4}$")) {
                    // Regex: ^IC\\d{4}$
                    // Explanation: "IC" followed by exactly four digits
                    otp.requestFocus();
                    otp.setError("OTP must follow the format ICXXXX !");
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
                                Toast.makeText(EnterOtp.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }

                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("otp", n1);
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
            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(EnterOtp.this,Restpass.class);
            i.putExtra("otp",n1);

            startActivity(i);
            finish();
        }

    }
}


