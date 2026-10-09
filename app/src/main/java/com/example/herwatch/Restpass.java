package com.example.herwatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.herwatch.Login;
import com.example.herwatch.R;
import com.example.herwatch.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Restpass extends AppCompatActivity {
    EditText newpassword,confirm;
    Button confirmbutton;
    String password,cpassword,name,status,message,url = config.baseurl+"resetpass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restpass);
        newpassword = findViewById(R.id.resetnewpass);
        confirm = findViewById(R.id.resetconfirm);
        confirmbutton = findViewById(R.id.resetbutton);

        Intent intent = getIntent();
        name = intent.getStringExtra("otp");

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword();
            }
        });

    }

    private void newPassword() {
        password = newpassword.getText().toString();
        cpassword = confirm.getText().toString();

        if (TextUtils.isEmpty(password)){
            newpassword.setError("Required");
            newpassword.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(cpassword))
        {
            confirm.setError("Required");
            confirm.requestFocus();
            return;
        }
        else if (!password.equals(cpassword))
        {
            Toast.makeText(this, "password mismatch", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("1".equals(status)) {
                    Toast.makeText(Restpass.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                } else {
                    Toast.makeText(Restpass.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Restpass.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("password",password);
                map.put("otp",name);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
