package com.example.herwatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herwatch.R;
import com.example.herwatch.selfdefmodel;

import java.util.ArrayList;

public class selfdefadapter extends RecyclerView.Adapter<selfdefadapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<selfdefmodel> dataModelArrayList;
    private Context c;

    public selfdefadapter(Context ctx, ArrayList<selfdefmodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.selfdeflist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        selfdefmodel currentModel = dataModelArrayList.get(position);

        holder.pi.setText(currentModel.getTitle());
        holder.health.setText(currentModel.getDescription());

        // Set the video URL to TextView
//        if (!currentModel.getVideo().isEmpty()) {
//            String videourl = "http://192.168.1.36/HERWATCH/selfdefence/" + currentModel.getVideo();
//            holder.watchVideoButton.setText(videourl);
//        } else {
//            holder.watchVideoButton.setText("No video available for download");
//        }

        // Set up the Watch Video button click listener
        holder.watchVideoButton.setOnClickListener(v -> {
            String videoUrl = currentModel.getVideo();
            if (!videoUrl.isEmpty()) {
                // Create an Intent to open a new activity with the video URL
                Intent intent = new Intent(c, VideoPlayerActivity.class);
                intent.putExtra("VIDEO_URL", "http://192.168.126.112/HERWATCH/selfdefence/" + videoUrl);
                c.startActivity(intent);
            } else {
                // Handle case when no video URL is available
                Toast.makeText(c, "No video available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    // Filter the list and update the adapter
    public void filterList(ArrayList<selfdefmodel> filteredList) {
        this.dataModelArrayList = filteredList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView1, pi, health;
        Button watchVideoButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.patlistId1);
            pi = itemView.findViewById(R.id.patlistname1);
            health = itemView.findViewById(R.id.patlistdate1);
            watchVideoButton = itemView.findViewById(R.id.details1);
        }
    }
}
