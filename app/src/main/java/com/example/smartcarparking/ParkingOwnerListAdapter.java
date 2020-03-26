package com.example.smartcarparking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParkingOwnerListAdapter extends RecyclerView.Adapter<ParkingOwnerListAdapter.ViewHolder> {
    ArrayList<String> parkingLocation,ownerName,totalSpace,filledSpace,freeSpace;

    public ParkingOwnerListAdapter(ArrayList<String> parkingLocation, ArrayList<String> ownerName, ArrayList<String> totalSpace, ArrayList<String> filledSpace, ArrayList<String> freeSpace) {
        this.filledSpace=filledSpace;
        this.parkingLocation=parkingLocation;
        this.ownerName=ownerName;
        this.totalSpace=totalSpace;
        this.freeSpace=freeSpace;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parking_owner_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.totalSpace.setText(totalSpace.get(position));
        holder.filledSpace.setText(filledSpace.get(position));
        holder.freeSpace.setText(freeSpace.get(position));
        holder.ownerName.setText(ownerName.get(position));
        holder.parkingLocation.setText(parkingLocation.get(position));
    }

    @Override
    public int getItemCount() {
        return ownerName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView parkingLocation,ownerName,freeSpace,filledSpace,totalSpace;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingLocation =(TextView) itemView.findViewById(R.id.parkingLocation);
            ownerName=(TextView) itemView.findViewById(R.id.ownerName);
            freeSpace=(TextView) itemView.findViewById(R.id.freeSpace);
            filledSpace=(TextView) itemView.findViewById(R.id.filledSpace);
            totalSpace=(TextView) itemView.findViewById(R.id.totalSpace);
        }
    }
}
