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

//import com.mashood.kaudisorders.R;
//import com.mashood.kaudisorders.disorder.DisorderListActivity;
//import com.squareup.picasso.Picasso;

//import com.example.wecan.ui.dashboard.DashboardFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frdsadapter extends RecyclerView.Adapter<frdsadapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<frdsmodel> dataModelArrayList;
    private Context c;

    public frdsadapter(Context ctx, ArrayList<frdsmodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frdslist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final frdsmodel omodel = dataModelArrayList.get(position);
        holder.docname.setText( "Name:"+dataModelArrayList.get(position).getFnmae());
        holder.petname.setText("Phonenumber:"+ dataModelArrayList.get(position).getFphonenumber());
        holder.date.setText( dataModelArrayList.get(position).getCategory());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace "tx6" with the actual phone number string
                String phoneNumber = dataModelArrayList.get(position).getFphonenumber();

                // Create an Intent to initiate a call
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));

                // Use the context object to start the activity
                c.startActivity(intent);
            }
        });




//        holder.producta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(c, bookingfullactivity.class);
//
//                // Passing the specific fields
//                intent.putExtra("id", dataModelArrayList.get(position).getId());
//                intent.putExtra("username", dataModelArrayList.get(position).getUsername());
//                intent.putExtra("emailid", dataModelArrayList.get(position).getEmailid());
//                intent.putExtra("phonenumber", dataModelArrayList.get(position).getPhonenumber());
//                intent.putExtra("reason", dataModelArrayList.get(position).getReason());
//                intent.putExtra("startingdate", dataModelArrayList.get(position).getStartingdate());
//                intent.putExtra("endingdate", dataModelArrayList.get(position).getEndingdate());
//                intent.putExtra("guideusername", dataModelArrayList.get(position).getGuideusername());
//                intent.putExtra("guideemailid", dataModelArrayList.get(position).getGuideemailid());
//                intent.putExtra("guidephonenumber", dataModelArrayList.get(position).getGuidephonenumber());
//                intent.putExtra("guideage", dataModelArrayList.get(position).getGuideage());
//                intent.putExtra("guidestate", dataModelArrayList.get(position).getGuidestate());
//                intent.putExtra("guidegender", dataModelArrayList.get(position).getGuidegender());
//                intent.putExtra("guideexperience", dataModelArrayList.get(position).getGuideexperience());
//                intent.putExtra("guideimage", dataModelArrayList.get(position).getGuideimage());
//
//                // Starting the activity
//                c.startActivity(intent);
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }


    public void filterList(ArrayList<frdsmodel> filteredSongs) {
        this.dataModelArrayList = filteredSongs;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        public
        CardView producta;
        TextView docname,petname,date,time;
        ImageView call,delete;

        String id,cname;


        public MyViewHolder(View itemView) {
            super(itemView);
            producta =itemView.findViewById(R.id.frds);
            docname = itemView.findViewById(R.id.frdsname);
            petname= itemView.findViewById(R.id.frdsnumber);
            date= itemView.findViewById(R.id.frdstype);
            call=itemView.findViewById(R.id.fcall);



        }

    }
}