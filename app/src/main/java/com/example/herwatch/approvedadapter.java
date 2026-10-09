package com.example.herwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class approvedadapter extends RecyclerView.Adapter<approvedadapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<securemodel> dataModelArrayList;
    private Context c;

    public approvedadapter(Context ctx, ArrayList<securemodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.approvedlist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final securemodel omodel = dataModelArrayList.get(position);
//        Picasso.get().load(config.imgurl1 + omodel.getDocimage()).into(holder.image);


        holder.username.setText( "Name:"+dataModelArrayList.get(position).getPatientname());
        holder.date.setText("Date:"+ dataModelArrayList.get(position).getDateselect());
        holder.time.setText( "time:"+dataModelArrayList.get(position).getTimeselect());




        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, approvedlistfullactivity.class);
                intent.putExtra("docname",dataModelArrayList.get(position).getDocname() );
                intent.putExtra("doc_contact", dataModelArrayList.get(position).getDoc_contact());
                intent.putExtra("patientname", dataModelArrayList.get(position).getPatientname());
                intent.putExtra("patientnum",dataModelArrayList.get(position).getPatientnum());
                intent.putExtra("patientage",dataModelArrayList.get(position).getPatientage());
                intent.putExtra("dateselect",dataModelArrayList.get(position).getDateselect());
                intent.putExtra("timeselect",dataModelArrayList.get(position).getTimeselect());
                intent.putExtra("status",dataModelArrayList.get(position).getStatus());
                intent.putExtra("fee",dataModelArrayList.get(position).getFee());
                intent.putExtra("feedate",dataModelArrayList.get(position).getFeedate());

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


    public void filterList(ArrayList<securemodel> filteredSongs) {
        this.dataModelArrayList = filteredSongs;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        public
        CardView producta;
        TextView docname,username,petname,date,time;
        ImageView image,delete;
        Button view;
        String id,cname;


        public MyViewHolder(View itemView) {
            super(itemView);
            producta =itemView.findViewById(R.id.bookcard213);
            username =itemView.findViewById(R.id.bookdoc213);
            date = itemView.findViewById(R.id.bookdate213);
            time= itemView.findViewById(R.id.booktime213);
            view= itemView.findViewById(R.id.viewdetails13);

        }

    }
}