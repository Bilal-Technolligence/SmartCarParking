package com.example.smartcarparking;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewParkingSlotsAdapter extends RecyclerView.Adapter<ViewParkingSlotsAdapter.ViewHolder> {
    ArrayList<ParkAttr> parkAttrs;
    private ViewParkingSlots activity;
    private String user;

    public ViewParkingSlotsAdapter(ArrayList<ParkAttr> parkAttrs, ViewParkingSlots activity) {
        this.activity = activity;
        this.parkAttrs = parkAttrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.post, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load( parkAttrs.get( position ).getPic() ).into( holder.serviceImage );
        holder.service.setText( parkAttrs.get( position ).getName() );
        holder.company.setText( parkAttrs.get( position ).getAdmin() );
        //holder.location.setText( parkAttrs.get( position ).getLocation() );
        //holder.close.setText( parkAttrs.get( position ).getCloseTime() );
        //holder.rating.setText( String.valueOf( parkAttrs.get( position ).getRating() ) );
       // holder.ratingBar.setRating( Float.valueOf( parkAttrs.get( position ).getRating() ) );
        //holder.total.setText( String.valueOf( parkAttrs.get( position ).getTotal() ) );
                holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String serviceId = null;
                        serviceId = parkAttrs.get( position ).getId();

                        Intent i = new Intent( activity, ParkingSlotDetail.class );
                        i.putExtra( "Id", serviceId );
                        activity.startActivity( i );

                    }
                } );
    }

    @Override
    public int getItemCount() {
        return parkAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceImage;
        TextView service, location, company, close, total, rating;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            serviceImage = itemView.findViewById( R.id.imgService );
            service = itemView.findViewById( R.id.txtService );
            company = itemView.findViewById( R.id.txtCompany );
            location = itemView.findViewById( R.id.txtLocation );
            close = itemView.findViewById( R.id.txtCloseTime );
            ratingBar = itemView.findViewById( R.id.postRating );
            rating = itemView.findViewById( R.id.txtRating );
            total = itemView.findViewById( R.id.txtTotalRating );

        }
    }
}
