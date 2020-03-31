package com.example.smartcarparking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ViewHolder> {
    ArrayList<ParkAttr> parkAttrs;
    Context context;
    Activity activity;

    public ParkingAdapter(ArrayList<ParkAttr> parkAttrs, Context context, Activity activity) {
        this.context = context;
        this.parkAttrs = parkAttrs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parkitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.slots.setText(parkAttrs.get(position).getSlots());
        holder.available.setText(parkAttrs.get(position).getAvailable());
        holder.price.setText(parkAttrs.get(position).getPrice());
        holder.title.setText(parkAttrs.get(position).getTitle());
        Picasso.get().load(parkAttrs.get(position).getPic()).into(holder.profile);
        final String id = parkAttrs.get(position).getId();
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setMessage("Are you sure to remove parking?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Parkings").child(id).setValue(null);
                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return parkAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView deleteBtn, profile;
        TextView slots, price,available,title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = (ImageView) itemView.findViewById(R.id.imgDelete);
            profile = (ImageView) itemView.findViewById(R.id.imgProfile);
            slots = (TextView) itemView.findViewById(R.id.txtSlots);
            available = (TextView) itemView.findViewById(R.id.txtAvailable);
            price = (TextView) itemView.findViewById(R.id.txtPrice);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }
}
