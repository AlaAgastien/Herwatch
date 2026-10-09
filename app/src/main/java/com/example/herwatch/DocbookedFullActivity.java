package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DocbookedFullActivity extends AppCompatActivity {
    TextView docName,docCont,patName,patCont,patAge,dateSelected,timeSelected;
    String n1,n2,n3,n4,n5,n6,n7;

    Button payNowButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_docbooked_full);

        docName=findViewById(R.id.booked_detail_docname);
        docCont=findViewById(R.id.booked_detail_docCont);
        patName=findViewById(R.id.booked_detail_patName);
        patCont=findViewById(R.id.booked_detail_patCont);
        patAge=findViewById(R.id.booked_detail_patAge);
        dateSelected=findViewById(R.id.booked_details_date);
        timeSelected=findViewById(R.id.booked_detail_time);




        // Receiving data from intent
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
