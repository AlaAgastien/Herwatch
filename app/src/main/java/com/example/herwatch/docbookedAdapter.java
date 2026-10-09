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

public class docbookedAdapter extends RecyclerView.Adapter<docbookedAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<docbookedmodel> dataModelArrayList;
    private Context c;


    public docbookedAdapter(Context ctx, ArrayList<docbookedmodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.docbookedlist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    //the things u need to show in the card
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.docbookedname.setText("Psychologist Name :" +dataModelArrayList.get(position).getDocname());
        holder.doccontact.setText("Contact :" +dataModelArrayList.get(position).getDoc_contact());
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

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(c, DocbookedFullActivity.class);
                intent.putExtra("docname",dataModelArrayList.get(position).getDocname());
                intent.putExtra("doc_contact", dataModelArrayList.get(position).getDoc_contact());
                intent.putExtra("patientname", dataModelArrayList.get(position).getPatientname());
                intent.putExtra("patientnum",dataModelArrayList.get(position).getPatientnum());
                intent.putExtra("patientage",dataModelArrayList.get(position).getPatientage());
                intent.putExtra("dateselect",dataModelArrayList.get(position).getDateselect());
                intent.putExtra("timeselect",dataModelArrayList.get(position).getTimeselect());




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


    public void filterList(ArrayList<docbookedmodel> filteredSongs) {
        this.dataModelArrayList = filteredSongs;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView docbookedname,doccontact,psylistexp,psylistwork;
        Button view_details;



        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.psylistId);
            docbookedname = itemView.findViewById(R.id.booked_doc_name);
            doccontact = itemView.findViewById(R.id.contact_doc);
//            psylistexp = itemView.findViewById(R.id.psylistexp);
//            psylistwork = itemView.findViewById(R.id.psylistwork);
            view_details = itemView.findViewById(R.id.booked_ones);

        }

    }
}
