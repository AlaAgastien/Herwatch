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

public class PsychologistAdapter extends RecyclerView.Adapter<PsychologistAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<Psychologistmodel> dataModelArrayList;
    private Context c;


    public PsychologistAdapter(Context ctx, ArrayList<Psychologistmodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.patientrecordlist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


//the things u need to show in the card
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.psylistname.setText("Psychologist Name :" +dataModelArrayList.get(position).getName());
        holder.psylistspec.setText("Specialization :" +dataModelArrayList.get(position).getSpecialization());
//        holder.psylistexp.setText("Experience :" +dataModelArrayList.get(position).getExperience());
//        holder.psylistwork.setText("Work :" +dataModelArrayList.get(position).getWork());



//        holder.psychobooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(c, PsychologistfullActivity.class);
//                c.startActivity(i);
//            }
//        });


//        holder.book.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(c, docbooking.class);
//                intent.putExtra("name",dataModelArrayList.get(position).getName());
//                intent.putExtra("email", dataModelArrayList.get(position).getEmail());
//                c.startActivity(intent);
//            }
//        });

        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(c, Psychologistfullactivity.class);
                intent.putExtra("name",dataModelArrayList.get(position).getName());
                intent.putExtra("email", dataModelArrayList.get(position).getEmail());
                intent.putExtra("phone",dataModelArrayList.get(position).getPhone());
                intent.putExtra("work",dataModelArrayList.get(position).getWork());
                intent.putExtra("specialization",dataModelArrayList.get(position).getSpecialization());
                intent.putExtra("experience",dataModelArrayList.get(position).getExperience());




                c.startActivity(intent);
            }

        });
        //sms nte code




//
//      holder.cardView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               BookingDataModel p = dataModelArrayList.get(position);
//             String crop = p.getBloodgp();
//           //    String img = p.getImage();
//              Intent i = new Intent(c, Bookingdonorslist.class);
//               i.putExtra("crop", crop);
//               c.startActivity(i);
//            }
//       });

    }


    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }


    public void filterList(ArrayList<Psychologistmodel> filteredSongs) {
        this.dataModelArrayList = filteredSongs;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView psylistname,psylistspec,psylistexp,psylistwork;
        Button viewdetails;



        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.patlistId);
            psylistname = itemView.findViewById(R.id.patlistname);
            psylistspec = itemView.findViewById(R.id.patlistdate);
//            psylistexp = itemView.findViewById(R.id.psylistexp);
//            psylistwork = itemView.findViewById(R.id.psylistwork);
            viewdetails = itemView.findViewById(R.id.details);

        }

    }
}
