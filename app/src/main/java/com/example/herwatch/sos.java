package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class sos extends AppCompatActivity {

    EditText name, phn;
    Button b1,frdslist;
    Spinner type;
    String sname, sphn, status, message,phonenumber,username,stype;
    String url = config.baseurl+"frds.php";
    String dis[] = {"Select category", "Family", "Friends"};



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sos);
        name = findViewById(R.id.frident);
        phn = findViewById(R.id.number);
        b1 = findViewById(R.id.frientssubmit);
        type = findViewById(R.id.famfrds);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dis);
        //spinner object.setAdapter(adapter);
        type.setAdapter(adapter);

//        frdslist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in =new Intent(sos.this,frdslistactivity.class);
//                startActivity(in);
//            }
//        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1.setOnClickListener(view -> fridents());
        HashMap<String, String> rev = new Sessionmanager(sos.this).getUserDetails();
        username = rev.get("name");
        phonenumber = rev.get("phone");

    }

    private void fridents() {
        sname = name.getText().toString().trim();
        sphn = phn.getText().toString().trim();
        stype = type.getSelectedItem().toString();

        if (sname.isEmpty()) {
            name.setError("Name cannot be empty");
            name.requestFocus();
            return;
        }

        if (sphn.isEmpty()) {
            phn.setError("Phone number cannot be empty");
            phn.requestFocus();
            return;
        }

        if (!sphn.matches("\\d{10}")) { // Validates a 10-digit phone number
            phn.setError("Enter a valid 10-digit phone number");
            phn.requestFocus();
            return;
        }

// Assuming the first item in Spinner is a placeholder like "Select type"
        if (type.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a valid type", Toast.LENGTH_SHORT).show();
            return;
        }


        StringRequest str = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(sos.this, response, Toast.LENGTH_SHORT).show();


                try {
                    JSONObject json = new JSONObject(response);
                    status = json.getString("status");
                    message = json.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("0".equals(status)) {
                    Toast.makeText(sos.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(sos.this, "updation successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(sos.this, HeaderSection.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(sos.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("phonenumber", phonenumber);
                params.put("fname", sname);
                params.put("category", stype);
                params.put("fphonenumber", sphn);

                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(sos.this);
        rq.add(str);
    }


}