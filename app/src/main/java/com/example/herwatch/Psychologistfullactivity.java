package com.example.herwatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Psychologistfullactivity extends AppCompatActivity {

    TextView docName,docEmail,docCont,docWork,docSpec,docExp;
    Button bookNow;
    String dname,demail,dcont,dwork,dspec,dexp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_psychologistfullactivity);

        docName=findViewById(R.id.details_doc_name);
        docEmail=findViewById(R.id.details_doc_mail);
        docCont=findViewById(R.id.details_doc_cont);
        docWork=findViewById(R.id.details_doc_work);
        docSpec=findViewById(R.id.details_doc_spect);
        docExp=findViewById(R.id.details_doc_exp);

        bookNow=findViewById(R.id.details_doc_butn);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Psychologistfullactivity.this, DocBooking.class);
                i.putExtra("name",dname);
                i.putExtra("phone",dcont);
                startActivity(i);
            }
        });

            Intent intent = getIntent();
            dname = intent.getStringExtra("name");
            docName.setText(dname);

            demail = intent.getStringExtra("email");
            docEmail.setText(demail);

            dcont = intent.getStringExtra("phone");
            docCont.setText(dcont);

            dwork = intent.getStringExtra("work");
            docWork.setText(dwork);

            dspec = intent.getStringExtra("specialization");
            docSpec.setText(dspec);

            dexp = intent.getStringExtra("experience");
            docExp.setText(dexp);
    }
}