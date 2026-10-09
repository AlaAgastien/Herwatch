package com.example.herwatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Random;

public class Forgotpassword extends AppCompatActivity {
    Button sub;
    EditText contact;
    String otpnew,username,status,message,url = config.baseurl+"forgotpassword.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        sub = findViewById(R.id.sub);
        contact = findViewById(R.id.contact);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserName();
            }
        });
    }

    private void checkUserName() {
        username = contact.getText().toString();
        if (TextUtils.isEmpty(username))
        {
            contact.setError("Required");
            contact.requestFocus();
            return;
        }
        Random r = new Random();
        int otp = r.nextInt((9999 - 1000) + 1) + 1000;
        otpnew = "IC"+ otp;

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse>>",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("1".equals(status)) {
//                    Toast.makeText(ForgotActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),EnterOtp.class);

                    SmsManager sms = SmsManager.getDefault();
                    Intent in = new Intent();
                    PendingIntent pi = PendingIntent.getActivity(Forgotpassword.this, 0, in, PendingIntent.FLAG_IMMUTABLE);
                    sms.sendTextMessage(username, null, "Your otp for changing password is " + otpnew, pi, null);
                    Toast.makeText(Forgotpassword.this, "OTP sent via SMS successfully", Toast.LENGTH_SHORT).show();

                    intent.putExtra("otp",username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Forgotpassword.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Forgotpassword.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("phone",username);
                map.put("otp",otpnew);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}