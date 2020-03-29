package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ParkingOwnerList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_owner_list);
        RecyclerView recyclerView;
        ArrayList<String> parkingLocation=new ArrayList<>();
        ArrayList<String> ownerName = new ArrayList<>();
        ArrayList<String> totalSpace=new ArrayList<>();
        ArrayList<String> filledSpace=new ArrayList<>();
        ArrayList<String> freeSpace=new ArrayList<>();
        getSupportActionBar().setTitle("Owners List");
        // setContentView(R.layout.activity_parking_owner_list);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parkingLocation.add("Uni Road Abbottabad");
        parkingLocation.add("Kakul Road Abbottabad");
        ownerName.add("Hamza Khan");
        ownerName.add("Yaseen Shah");
        totalSpace.add("100");
        totalSpace.add("50");
        freeSpace.add("40");
        freeSpace.add("10");
        filledSpace.add("50");
        filledSpace.add("40");



        recyclerView.setAdapter(new ParkingOwnerListAdapter(parkingLocation,ownerName,totalSpace,filledSpace,freeSpace));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_parking_owner_list;
    }
}
