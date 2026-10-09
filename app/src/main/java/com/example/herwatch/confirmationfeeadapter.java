package com.example.herwatch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class confirmationfeeadapter extends RecyclerView.Adapter<confirmationfeeadapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<confirmationfeemodel> dataModelArrayList;
    private Context c;

    public confirmationfeeadapter(Context ctx, ArrayList<confirmationfeemodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.approvelist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final confirmationfeemodel omodel = dataModelArrayList.get(position);

        holder.docname.setText("Doctor name:"+dataModelArrayList.get(position).getDocname());
        holder.petname.setText( "Date:"+dataModelArrayList.get(position).getDateselect());
        holder.date.setText("Username:"+dataModelArrayList.get(position).getPatientname());
        holder.status.setText(dataModelArrayList.get(position).getStatus());




        String statusText = omodel.getStatus();
        if ("Approve".equalsIgnoreCase(statusText)) {
            holder.status.setTextColor(ContextCompat.getColor(c, R.color.green));  // Green color for "Approve"
        } else if ("Disapprove".equalsIgnoreCase(statusText)) {
            holder.status.setTextColor(ContextCompat.getColor(c, R.color.red));  // Red color for "Disapprove"
        } else {
            holder.status.setTextColor(ContextCompat.getColor(c, R.color.black));  // Default color if status is neither "Approve" nor "Disapprove"
        }


        // Check if the status is "approved", and show/hide the payment button accordingly
        if ("Approve".equalsIgnoreCase(omodel.getStatus())) {
            holder.pay.setVisibility(View.VISIBLE);  // Show the payment button
        } else {
            holder.pay.setVisibility(View.GONE);  // Hide the payment button
        }

        // Handle the payment button click
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // You can handle payment logic here, like navigating to a payment screen or processing payment
                Toast.makeText(c, "Proceeding to payment for " + omodel.getDocname(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c, confirmationfeefullactivity.class);
                intent.putExtra("id",dataModelArrayList.get(position).getId());
                intent.putExtra("docname",dataModelArrayList.get(position).getDocname() );
                intent.putExtra("doc_contact", dataModelArrayList.get(position).getDoc_contact());
                intent.putExtra("patientname", dataModelArrayList.get(position).getPatientname());
                intent.putExtra("patientnum",dataModelArrayList.get(position).getPatientnum());
                intent.putExtra("patientage",dataModelArrayList.get(position).getPatientage());
                intent.putExtra("dateselect",dataModelArrayList.get(position).getDateselect());
                intent.putExtra("timeselect",dataModelArrayList.get(position).getTimeselect());
                intent.putExtra("status",dataModelArrayList.get(position).getStatus());




                c.startActivity(intent);


                // Intent intent = new Intent(c, PaymentActivity.class);
                // intent.putExtra("docId", omodel.getId());
                // c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public void filterList(ArrayList<confirmationfeemodel> filteredSongs) {
        this.dataModelArrayList = filteredSongs;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView producta;
        TextView docname, petname, date, time, status;
        ImageView image, delete;
        Button pay;
        String id, cname;

        public MyViewHolder(View itemView) {
            super(itemView);
            producta = itemView.findViewById(R.id.bookcardfee);
            docname = itemView.findViewById(R.id.bookdocfee);
            petname = itemView.findViewById(R.id.bookpetfee);
            date = itemView.findViewById(R.id.bookdatefee);
            image = itemView.findViewById(R.id.bookimagefee);
            status = itemView.findViewById(R.id.statusfee);
            pay = itemView.findViewById(R.id.pay);
        }
    }
}
