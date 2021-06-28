package com.example.finalassignment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OwnerInfoRetrieve extends ArrayAdapter<ModelDatabase> {
    private Activity context;
    private List<ModelDatabase>propertyList;
    public OwnerInfoRetrieve(Activity context,List<ModelDatabase>propertyList)
    {
        super(context,R.layout.list_item,propertyList);
        this.context=context;
        this.propertyList = propertyList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item,null,true);

        TextView textViewDist =(TextView) listViewItem.findViewById(R.id.districtViewId);
        TextView textViewThana =(TextView) listViewItem.findViewById(R.id.thanaViewId);
        TextView textViewAdd =(TextView) listViewItem.findViewById(R.id.addressViewId);
        TextView textViewFloor =(TextView) listViewItem.findViewById(R.id.floorViewId);
        TextView textViewDesc =(TextView) listViewItem.findViewById(R.id.descViewId);
        TextView textViewArea =(TextView) listViewItem.findViewById(R.id.areaViewId);
        TextView textViewAmount =(TextView) listViewItem.findViewById(R.id.amountViewId);
        TextView textViewAdv =(TextView) listViewItem.findViewById(R.id.advViewId);
        TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.phoneViewId);

        ModelDatabase modelDatabase = propertyList.get(position);

        textViewDist.setText("District : "+modelDatabase.getDistrict());
        textViewThana.setText("Thana : "+modelDatabase.getThana());
        textViewAdd.setText("Address : "+modelDatabase.getAddress());
        textViewArea.setText("Floor & flat : "+modelDatabase.getArea());
        textViewFloor.setText("Area(sq. ft) : "+modelDatabase.getFloor());
        textViewDesc.setText("Number of rooms & details : "+modelDatabase.getDetails());
        textViewAmount.setText("Rent amount : "+modelDatabase.getAmount());
        textViewAdv.setText("Advance payment :"+modelDatabase.getAdvance());
        textViewPhone.setText("Phone number :"+modelDatabase.getPhone());

        return listViewItem;
    }
}
