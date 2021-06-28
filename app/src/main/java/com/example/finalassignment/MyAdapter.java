package com.example.finalassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<ModelDatabase,MyAdapter.myviewholder> {

    public MyAdapter(@NonNull  FirebaseRecyclerOptions<ModelDatabase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapter.myviewholder holder, int position, @NonNull ModelDatabase model) {
        holder.district.setText("District : "+model.getDistrict());
        holder.thana.setText("Thana : "+model.getThana());
        holder.address.setText("Address : "+model.getAddress());
        holder.floor.setText("Floor & flat : "+model.getFloor());
        holder.area.setText("Area(sq. ft) : "+model.getArea());
        holder.description.setText("Number of rooms & details : "+model.getDetails());
        holder.amount.setText("Rent amount : "+model.getAmount());
        holder.advance.setText("Advance payment :"+model.getAdvance());
        holder.phone.setText("Phone number :"+model.getPhone());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView district,thana,address,floor,area,description,amount,advance,phone;
        public myviewholder( View itemView) {
            super(itemView);
            district = (TextView)itemView.findViewById(R.id.districtViewId);
            thana = (TextView)itemView.findViewById(R.id.thanaViewId);
            address = (TextView)itemView.findViewById(R.id.addressViewId);
            floor = (TextView)itemView.findViewById(R.id.floorViewId);
            area = (TextView)itemView.findViewById(R.id.areaViewId);
            description = (TextView)itemView.findViewById(R.id.descViewId);
            amount = (TextView)itemView.findViewById(R.id.amountViewId);
            advance = (TextView)itemView.findViewById(R.id.advViewId);
            phone = (TextView)itemView.findViewById(R.id.phoneViewId);

        }
    }
}
