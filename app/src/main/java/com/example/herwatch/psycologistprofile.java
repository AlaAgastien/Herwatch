package com.example.herwatch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class psycologistprofile extends AppCompatActivity {
    EditText psyproname,psyproemail,psyprophone,psypropassword,psyprospecialization,psyprowork,psyproexp;
    Button psyproupdate;
    String psyid,n1, n2, n3, n4,n5,n6,n7,status,message,url= config.baseurl+"psyprofileupdate.php";;
    String psyproname2,psyproemail2,psyprophone2,psyprowork2,psyprospecialization2,psyproexp2,psypropassword2;
    TextView psyhome1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_psycologistprofile);

        psyproname = findViewById(R.id.psyproname);
        psyproemail = findViewById(R.id.psyproemail);
        psyprospecialization=findViewById(R.id.psyprospecial);
        psyprophone = findViewById(R.id.psyprophone);
        psypropassword = findViewById(R.id.psypropass);
        psyproupdate = findViewById(R.id.psyproupdate);
        psyprowork=findViewById(R.id.psyprowork);
        psyproexp=findViewById(R.id.psyproexp);
        psyhome1=findViewById(R.id.prohome12);


        HashMap<String,String> data=new PsySessionManager(psycologistprofile.this).getUserDetails();

        psyid=data.get("id");
        psyproname2=data.get("name");
        psyproemail2=data.get("email");
        psyprophone2=data.get("phone");
        psyprowork2=data.get("work");
        psyprospecialization2=data.get("specialization");
        psyproexp2=data.get("experience");
        psypropassword2=data.get("password");

        psyproname.setText(psyproname2);
        psyproemail.setText(psyproemail2);
        psyprophone.setText(psyprophone2);
        psyprowork.setText(psyprowork2);
        psyprospecialization.setText(psyprospecialization2);
        psyproexp.setText(psyproexp2);
        psypropassword.setText(psypropassword2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        psyproupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nn();
            }
        });
        psyhome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout1();
            }
        });
    }

    private void nn() {
        n1 = psyproname.getText().toString();
        n2 = psyproemail.getText().toString();
        n3 = psyprophone.getText().toString();
        n4 = psyprowork.getText().toString();
        n5 = psyprospecialization.getText().toString();
        n6 = psyproexp.getText().toString();
        n7 = psypropassword.getText().toString();

        if (TextUtils.isEmpty(n1)) {
            psyproname.requestFocus();
            psyproname.setError("Enter your name !!!");
        }

        if (TextUtils.isEmpty(n2)) {
            psyproemail.requestFocus();
            psyproemail.setError("Enter your Email ID !!!");
        }

        if (TextUtils.isEmpty(n3)) {
            psyprophone.requestFocus();
            psyprophone.setError("Enter your contact number !!!");
        }

        if (n3.length() > 10) {
            psyprophone.requestFocus();
            psyprophone.setError("Please enter a valid phone number");
        }
        if (n3.length() < 10) {
            psyprophone.requestFocus();
            psyprophone.setError("Please enter a valid phone number");
        }

        if (TextUtils.isEmpty(n7)) {
            psypropassword.requestFocus();
            psypropassword.setError("Please enter password !!!");
        }

        if (n7.length() < 6) {
            psypropassword.requestFocus();
            psypropassword.setError("Password should be atleast 6 characters !!!");
        }
        if (n7.length() >12 ) {
            psypropassword.requestFocus();
            psypropassword.setError("Password should be less than 12 characters !!!");
        }


        if(TextUtils.isEmpty(n5)){
            psyprospecialization.requestFocus();
            psyprospecialization.setError("Enter your specialization !!!");
        }

        if(TextUtils.isEmpty(n4)){
            psyprowork.requestFocus();
            psyprowork.setError("Please enter your work place !!!");
        }

        if(TextUtils.isEmpty(n6)){
            psyproexp.requestFocus();
            psyproexp.setError("Enter your experience !!!");
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
                        Toast.makeText(psycologistprofile.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //table name should be these values in the quotes!!!
                params.put("id", psyid);
                params.put("name", psyproname2);
                params.put("email", psyproemail2 );
                params.put("phone",psyprophone2);
                params.put("work", psyprowork2);
                params.put("specialization", psyprospecialization2);
                params.put("experience", psyproexp2);
                params.put("password", psypropassword2);


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
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(psycologistprofile.this,PsycologistHome.class);
            startActivity(i);
            finish();
        }

    }
    private void logout1() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getApplicationContext(),Choose_user.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
