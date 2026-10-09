package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.mashood.kaudisorders.R;
//import com.mashood.kaudisorders.disorder.DisorderListActivity;
//import com.squareup.picasso.Picasso;


import com.example.herwatch.R;
import com.example.herwatch.Psychologistmodel;

import java.util.ArrayList;

public class patientrecordadapter extends RecyclerView.Adapter<patientrecordadapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<patientrecordmodel> dataModelArrayList;
    private Context c;

    public patientrecordadapter(Context ctx, ArrayList<patientrecordmodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.patientrecord, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final patientrecordmodel omodel = dataModelArrayList.get(position);
//        Picasso.get().load(config.imgurl1 + omodel.getDocimage()).into(holder.image);


        holder.username.setText( "Name:"+dataModelArrayList.get(position).getPatientname());
        holder.date.setText("Date:"+ dataModelArrayList.get(position).getDateselect());
        holder.time.setText( "time:"+dataModelArrayList.get(position).getTimeselect());





        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, patientrecordlistfullactivity.class);
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

//                        if (!dataModelArrayList.get(position).getImage().equals("")) {
//            Picasso.get.load(config.imgurl+dataModelArrayList.get(position).getImage()).into(holder.image);
            }

        });

    }


    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }


    public void filterList(ArrayList<patientrecordmodel> filteredSongs) {
        this.dataModelArrayList = filteredSongs;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        public
        Button view;
        TextView username,date,time;
        ImageView image,delete;

        String id,cname;


        public MyViewHolder(View itemView) {
            super(itemView);
            username =itemView.findViewById(R.id.bookdoc2);
            date = itemView.findViewById(R.id.bookdate2);
            time= itemView.findViewById(R.id.booktime2);
            view= itemView.findViewById(R.id.viewdetails);
        }

    }
}