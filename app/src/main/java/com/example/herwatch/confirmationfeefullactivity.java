package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class confirmationfeefullactivity extends AppCompatActivity {
    TextView docName,docCont,patName,patCont,patAge,dateSelected,timeSelected;
    String n1,n2,n3,n4,n5,n6,n7;
    CheckBox confirmBookingCheckBox;
    Button payNowButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmationfeefullactivity);

        docName=findViewById(R.id.booked_detail_docname1);
        docCont=findViewById(R.id.booked_detail_docCont1);
        patName=findViewById(R.id.booked_detail_patName1);
        patCont=findViewById(R.id.booked_detail_patCont1);
        patAge=findViewById(R.id.booked_detail_patAge1);
        dateSelected=findViewById(R.id.booked_details_date1);
        timeSelected=findViewById(R.id.booked_detail_time1);
        payNowButton=findViewById(R.id.pay);
        confirmBookingCheckBox = findViewById(R.id.confirmBookingCheckBox);
        payNowButton.setEnabled(false);

        // Enable the Pay Now button if the CheckBox is checked, disable if unchecked
        confirmBookingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            payNowButton.setEnabled(isChecked);  // Enable if checked, disable if unchecked
        });

        // Set click listener for the Pay Now button
        payNowButton.setOnClickListener(view -> {
            if (confirmBookingCheckBox.isChecked()) {
                // Proceed with payment
                Toast.makeText(confirmationfeefullactivity.this, "Proceeding to payment...", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(confirmationfeefullactivity.this, Gpayactivity2.class);

                // Doctor details
                i.putExtra("docname", n1);
                i.putExtra("patientnum", n4);
//                i.putExtra("reason", st6);
                startActivity(i);
                finish();
                // Add your payment processing code here
            } else {
                // Show message if the checkbox is not checked
                Toast.makeText(confirmationfeefullactivity.this, "Please confirm your booking first.", Toast.LENGTH_SHORT).show();
            }
        });


//        payNowButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in=new Intent(confirmationfeefullactivity.this,Gpayactivity2.class);
//                startActivity(in);
//            }
//        });

        Intent intent = getIntent();

        n1 = intent.getStringExtra("docname");
        docName.setText(n1);

        n2 = intent.getStringExtra("doc_contact");
        docCont.setText(n2);

        n3 = intent.getStringExtra("patientname");
        patName.setText(n3);

        n4 = intent.getStringExtra("patientnum");
        patCont.setText(n4);

        n5 = intent.getStringExtra("patientage");
        patAge.setText(n5);

        n6 = intent.getStringExtra("dateselect");
        dateSelected.setText(n6);

        n7 = intent.getStringExtra("timeselect");
        timeSelected.setText(n7);



        // Handling window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
