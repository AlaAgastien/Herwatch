package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class approvedlistfullactivity extends AppCompatActivity {
    TextView docName, docCont, patName, patCont, patAge, dateSelected, timeSelected;
    String n1, n2, n3, n4, n5, n6, n7;
    ImageView img;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvedlistfullactivity);

        // Initialize views
        docName = findViewById(R.id.booked_detail_docname123);
        docCont = findViewById(R.id.booked_detail_docCont123);
        patName = findViewById(R.id.booked_detail_patName123);
        patCont = findViewById(R.id.booked_detail_patCont123);
        patAge = findViewById(R.id.booked_detail_patAge123);
        dateSelected = findViewById(R.id.booked_details_date123);
        timeSelected = findViewById(R.id.booked_detail_time123);
        img = findViewById(R.id.videocall); // Initialize your ImageView here

        // Set the click listener for the ImageView
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // You must replace this with actual values from your model or user
                String phoneNumber = "userPhoneNumber";  // Replace with actual phone number
                String userEmail = "userEmail@example.com";  // Replace with actual email
                String userName = n3;  // Replace with actual user name

                // WhatsApp video call URI scheme (just as an example)
                String videoCallUri = "https://meet.google.com/mzu-defx-xda" + phoneNumber + "?text=Let's%20have%20a%20video%20call!";

                // Create an email intent
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{userEmail}); // Recipient's email
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me for a Video Call");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi " + userName + ",\n\n" +
                        "I would like to have a video call with you. Please click the link below to join:\n" +
                        videoCallUri + "\n\nBest regards,\nHerWatch");

                // Start the email activity
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                } catch (Exception e) {
                    Toast.makeText(approvedlistfullactivity.this, "No email client found on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Receive data from Intent
        Intent intent = getIntent();

        n1 = intent.getStringExtra("docname");
        docName.setText(n1);

        n2 = intent.getStringExtra("doc_contact");
        docCont.setText(n2);

        n3 = intent.getStringExtra("patientname");
        patName.setText(n3);

        n4 = intent.getStringExtra("patientnum");
        patAge.setText(n4);

        n5 = intent.getStringExtra("patientage");
        patCont.setText(n5);

        n6 = intent.getStringExtra("dateselect");
        dateSelected.setText(n6);

        n7 = intent.getStringExtra("timeselect");
        timeSelected.setText(n7);

        // Set padding to avoid overlap with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
