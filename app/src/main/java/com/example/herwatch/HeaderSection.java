package com.example.herwatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Header;

public class HeaderSection extends AppCompatActivity {
CardView psychologist,bookings,laws,frnd,self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_header_section);
        psychologist=findViewById(R.id.UsermenuPsy);
        bookings=findViewById(R.id.UsermenuBookings);
        laws=findViewById(R.id.UsermenuLaw);
        frnd=findViewById(R.id.Usermenutrustedpeop);
        self=findViewById(R.id.UsermenuSelfdef);


        psychologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HeaderSection.this,PsychologistActivity.class);
                startActivity(i);
            }
        });
        frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HeaderSection.this,Friendpage.class);
                startActivity(i);
            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HeaderSection.this, confirmationfeelistactivity.class);
                startActivity(in);

            }
        });
        laws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HeaderSection.this,Laws.class);
                startActivity(in);

            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HeaderSection.this, selfpage.class);
                startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}