package com.example.herwatch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class securefullactivity extends AppCompatActivity {
    TextView docName,docCont,patName,patCont,patAge,dateSelected,timeSelected;
    String n1,n2,n3,n4,n5,n6,n7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_securefullactivity);
        docName=findViewById(R.id.booked_detail_docname12);
        docCont=findViewById(R.id.booked_detail_docCont12);
        patName=findViewById(R.id.booked_detail_patName12);
        patCont=findViewById(R.id.booked_detail_patCont12);
        patAge=findViewById(R.id.booked_detail_patAge12);
        dateSelected=findViewById(R.id.booked_details_date12);
        timeSelected=findViewById(R.id.booked_detail_time12);

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}