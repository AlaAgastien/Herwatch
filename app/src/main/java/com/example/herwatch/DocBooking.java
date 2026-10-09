//package com.example.herwatch;
//
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.w3c.dom.Text;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DocBooking extends AppCompatActivity {
//
//    TextView docName,docCont;
//    EditText patName,patAge,patCont,dateSelect;
//    Spinner time;
//    Button book;
//    String t[]={"Select your booking time", "10:00 - 10:30 AM", "10:30 - 11:00 AM", "11:00 - 11:30 AM", "2:30 - 3:00 PM", "3:00 - 3:30 PM", "3:30 - 4:00 Pm"};
//
//    String dname,dcont,pname,page,pcont,dateS,timesp,status,message;
//
//    String url=config.baseurl+"psybooking.php";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_doc_booking);
//
//        docName=findViewById(R.id.book_doc_name);
//        docCont=findViewById(R.id.book_doc_cont);
//
//        patName=findViewById(R.id.book_patient_name);
//        patAge=findViewById(R.id.book_patient_age);
//        patCont=findViewById(R.id.book_patient_cont);
//        dateSelect=findViewById(R.id.book_select_date);
//
//        time=findViewById(R.id.sp_time);
//
//        book=findViewById(R.id.book_now_id);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, t);
////spinner object.setAdapter(adapter);
//        time.setAdapter(adapter);
//
//
//        HashMap<String,String> data=new Sessionmanager(DocBooking.this).getUserDetails();
//        pname=data.get("name");
//        pcont=data.get("phone");
//        patName.setText(pname);
//        patCont.setText(pcont);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        book.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                book1();
//            }
//        });
//        dateSelect.setOnClickListener(v -> showDatePicker());
//
//
//
//        Intent intent =getIntent();
//        dname = intent.getStringExtra("name");
//        docName.setText(dname);
//
//        dcont = intent.getStringExtra("phone");
//        docCont.setText(dcont);
//
//    }
//    private void showDatePicker() {
//        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                DocBooking.this,
//                (view, selectedYear, selectedMonth, selectedDay) -> {
//                    dateSelect.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
//                },
//                year, month, day
//        );
//        datePickerDialog.show();
//
//    }
//
//    private void book1() {
////        dname=docName.getText().toString();
////        dcont=docCont.getText().toString();
////        pname=patName.getText().toString();
////        pcont=patCont.getText().toString();
//        page=patAge.getText().toString();
//        dateS=dateSelect.getText().toString();
//        timesp=time.getSelectedItem().toString();
//
//
////        if (TextUtils.isEmpty(dname)){
////            docName.requestFocus();
////            docName.setError("Enter the patient name!");
////            return;
////        }
////        if (TextUtils.isEmpty(dcont)){
////            docCont.requestFocus();
////            docCont.setError("Enter the contact number!");
////            return;
////        }
////        if (TextUtils.isEmpty(page)){
////            patAge.requestFocus();
////            patAge.setError("Enter the patient age!");
////            return;
////        }
////        if (TextUtils.isEmpty(dateS)){
////            dateSelect.requestFocus();
////            dateSelect.setError("Select date!");
////            return;
////        }
////
////        if (timesp.equals("Select Time")) {
////            // Show a validation message
////            Toast.makeText(DocBooking.this, "Please select a valid time!", Toast.LENGTH_SHORT).show();
////        } else {
////            // Proceed with booking
////            Toast.makeText(DocBooking.this, "Time selected: " + timesp, Toast.LENGTH_SHORT).show();
////            // Add further processing logic here
////        }
//
//        StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//                        //  Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
//                        try {
//                            JSONObject c = new JSONObject(response);
//                            status = c.getString("status");
//                            message = c.getString("message");
//                            checklogin();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //run cheyikkumbo error indo ennu nokkan
//                        Toast.makeText(DocBooking.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
//                    }
//
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("docname", dname);
//                params.put("doc_contact", dcont);
//                params.put("patientname",pname);
//                params.put("patientnum",pcont);
//                params.put("patientage", page);
//                params.put("dateselect", dateS);
//                params.put("timeselect", timesp);
//
//
//                return params;
//            }
//
//
//        };
//
//        //string reqt ne execute cheyan aanu requestqueue
//        Volley volley =  null;
//        RequestQueue requestQueue = volley.newRequestQueue(this);
//        requestQueue.add(StringRequest);
//    }
//    private void checklogin() {
//        if (status.equals("0")){
//            Toast.makeText(this, "Invalied", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "Booked successfully", Toast.LENGTH_SHORT).show();
//            Intent i =new Intent(DocBooking.this,HeaderSection.class);
//            startActivity(i);
//            finish();
//        }
//
//    }
//
//}


package com.example.herwatch;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
//import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DocBooking extends AppCompatActivity {
    TextView docName,docCont;
    EditText patName,patCont,patAge,dateSelect;
    Spinner time;
    Button book;

    String dname,dcont,pname,pcont,page,dateS,timesp,status,message;

    String url=config.baseurl+"psybooking.php";
    String dis[]={"Select your Time", "9 : 00 AM ", "10 : 00 AM", "11 : 00 AM", "3 : 00 PM", "4 :00 PM", "5 : 00 PM "};


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doc_booking);
        docName=findViewById(R.id.book_doc_name);
        docCont=findViewById(R.id.book_doc_cont);

        patName=findViewById(R.id.book_patient_name);
        patAge=findViewById(R.id.book_patient_age);
        patCont=findViewById(R.id.book_patient_cont);
        dateSelect=findViewById(R.id.book_select_date);
        time=findViewById(R.id.sp_time);
        book=findViewById(R.id.book_now_id);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dis);
//spinner object.setAdapter(adapter);
        time.setAdapter(adapter);

        HashMap<String,String>rev=new Sessionmanager(DocBooking.this).getUserDetails();
        pname=rev.get("name");
        pcont=rev.get("phone");
        patName.setText(pname);
        patCont.setText(pcont);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datePicker = new DatePickerDialog(DocBooking.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Ensuring proper "DD/MM/YYYY" format
                                String formattedDay = String.format("%02d", dayOfMonth); // Ensures two-digit day
                                String formattedMonth = String.format("%02d", monthOfYear + 1); // Ensures two-digit month
                                String formattedDate = formattedDay + "/" + formattedMonth + "/" + year;
                                dateSelect.setText(formattedDate);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

//        endingdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                DatePickerDialog datePicker = new DatePickerDialog(DocBooking.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int day) {
//                                endingdate.setText(day + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                datePicker.show();
//
//            }
//        });


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booknow1();
            }


        });

        Intent intent = getIntent();
        dname = intent.getStringExtra("name");
        docName.setText(dname);

        dcont = intent.getStringExtra("phone");
        docCont.setText(dcont);


    }

    private void booknow1() {
        dname = docName.getText().toString().trim();
        dcont = docCont.getText().toString().trim();
        pname = patName.getText().toString().trim();
        pcont = patCont.getText().toString().trim();
        page = patAge.getText().toString().trim();
        dateS = dateSelect.getText().toString().trim();
        timesp = time.getSelectedItem().toString();

        if (TextUtils.isEmpty(pname)) {
            patName.setError("Enter Name");
            patName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pcont)) {
            patCont.setError("Enter contact details");
            patCont.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(page)) {
            patAge.setError("Enter Age");
            patAge.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(dateS)) {
            dateSelect.setError("Enter Date");
            dateSelect.requestFocus();
            return;
        }

// Optional: Validate date format (Assuming format is "dd/MM/yyyy")
        if (!dateS.matches("\\d{2}/\\d{2}/\\d{4}")) {
            dateSelect.setError("Enter a valid date (DD/MM/YYYY)");
            dateSelect.requestFocus();
            return;
        }


// Assuming the first item in Spinner is a placeholder like "Select Time"
        if (time.getSelectedItemPosition() == 0) {
            Toast.makeText(DocBooking.this, "Select a valid time", Toast.LENGTH_SHORT).show();
            return;
        }



        com.android.volley.toolbox.StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                          Toast.makeText(DocBooking.this, response, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DocBooking.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("docname", dname);
                params.put("doc_contact", dcont);
                params.put("patientname",pname);
                params.put("patientnum",pcont);
                params.put("patientage", page);
                params.put("dateselect", dateS);
                params.put("timeselect", timesp);
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
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(DocBooking.this,docbookedlistactivity.class);
            startActivity(i);
            finish();
        }

    }
}

