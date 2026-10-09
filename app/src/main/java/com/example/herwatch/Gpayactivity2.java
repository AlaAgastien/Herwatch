package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.herwatch.Home;
import com.example.herwatch.R;
import com.example.herwatch.Sessionmanager;
import com.example.herwatch.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Gpayactivity2 extends AppCompatActivity {
    private EditText doctorNameEditText, patientNameEditText, upiVirtualIdEditText;
    private Button sendButton;
    private String TAG = "Gpayactivity2";
    private final int UPI_PAYMENT = 0;

    private String serverUrl = config.baseurl + "gpayamount2.php";
    private String userName, userPhone,datee,timee,petamee;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpayactivity2);

        // Initialize views
        doctorNameEditText = findViewById(R.id.dnameu);
        patientNameEditText = findViewById(R.id.pname11ur);
        upiVirtualIdEditText = findViewById(R.id.upi_idcu);
        sendButton = findViewById(R.id.sendcu);

        // Get user details from session
        HashMap<String, String> userDetails = new Sessionmanager(Gpayactivity2.this).getUserDetails();
        userName = userDetails.get("name");
        patientNameEditText.setText(userName);

        // Get data from Intent
        Intent intent = getIntent();
        doctorNameEditText.setText(intent.getStringExtra("docname"));
        userPhone = intent.getStringExtra("patientnum");
//        petamee = intent.getStringExtra("reason");
////        datee = intent.getStringExtra("date");
//        timee = intent.getStringExtra("time");

        // Set click listener for the send button
        sendButton.setOnClickListener(v -> {
            if (validateInputs()) {
                recordTransaction();  // Save the payment details to the server
                initiateUPIPayment(); // Initiate UPI payment
            }
        });
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(doctorNameEditText.getText().toString().trim())) {
            Toast.makeText(this, "Doctor Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(patientNameEditText.getText().toString().trim())) {
            Toast.makeText(this, "Patient Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(upiVirtualIdEditText.getText().toString().trim())) {
            Toast.makeText(this, "UPI ID is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initiateUPIPayment() {
        String name = doctorNameEditText.getText().toString().trim();
        String upiId = upiVirtualIdEditText.getText().toString().trim();
        String note = "Booking Payment for " + patientNameEditText.getText().toString().trim();
        String amount = "200";

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId) // Payee UPI ID
                .appendQueryParameter("pn", name) // Payee name
                .appendQueryParameter("tn", note) // Transaction note
                .appendQueryParameter("am", amount) // Amount
                .appendQueryParameter("cu", "INR") // Currency
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if (chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app found. Please install one to proceed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void recordTransaction() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        if ("0".equals(status)) {
                            Toast.makeText(Gpayactivity2.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Gpayactivity2.this, "Payment Recorded Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Gpayactivity2.this, Home.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Gpayactivity2.this, "Server Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Gpayactivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("feedate", currentDate);
                params.put("fee", "200");
                params.put("patientnum", userPhone);
//                params.put("reason", petamee);
////                params.put("date", datee);
//                params.put("time", timee);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT) {
            ArrayList<String> dataList = new ArrayList<>();
            if (data != null) {
                dataList.add(data.getStringExtra("response"));
            } else {
                dataList.add("nothing");
            }
            handleUPIResponse(dataList);
        }
    }

    private void handleUPIResponse(ArrayList<String> data) {
        if (!isConnectionAvailable()) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }

        String transactionResponse = data.get(0);
        String status = "";
        String approvalRefNo = "";

        if (transactionResponse != null) {
            String[] responseParts = transactionResponse.split("&");
            for (String part : responseParts) {
                String[] keyValue = part.split("=");
                if (keyValue.length >= 2) {
                    if ("Status".equalsIgnoreCase(keyValue[0])) {
                        status = keyValue[1].toLowerCase();
                    } else if ("ApprovalRefNo".equalsIgnoreCase(keyValue[0]) || "txnRef".equalsIgnoreCase(keyValue[0])) {
                        approvalRefNo = keyValue[1];
                    }
                }
            }

            if ("success".equals(status)) {
                Toast.makeText(this, "Transaction Successful. Ref: " + approvalRefNo, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Payment Successful: " + approvalRefNo);
            } else {
                Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Payment Failed: " + approvalRefNo);
            }
        } else {
            Toast.makeText(this, "No Response from UPI App", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }
}