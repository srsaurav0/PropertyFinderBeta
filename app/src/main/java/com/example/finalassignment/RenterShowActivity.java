package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RenterShowActivity extends AppCompatActivity {

    ListView allProperty;
    List<ModelDatabase> propertyList;

    String[] keys = new String[10000];

    String district_this,lowerStr,upperStr;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("All_Property");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_show);

        allProperty = (ListView)findViewById(R.id.listViewId);
        propertyList = new ArrayList<>();

        district_this = getIntent().getStringExtra("district_selected");
        lowerStr = getIntent().getStringExtra("lowerStr");
        upperStr = getIntent().getStringExtra("upperStr");

        allProperty.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ModelDatabase mapDatabase = propertyList.get(position);
                String add = mapDatabase.getAddress();
                String phoneNum = mapDatabase.getPhone();
                showMapDialog(add,phoneNum,position);
                return false;
            }
        });

    }

    private void showMapDialog(String add,String phoneNum,int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View mapView = inflater.inflate(R.layout.map_dialog,null);
        dialogBuilder.setView(mapView);

        final Button buttonMap = (Button)mapView.findViewById(R.id.mapShowBtnId);
        final Button call = (Button)mapView.findViewById(R.id.callBtnId);

        dialogBuilder.setTitle("Map");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("Address",add);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "tel:" + phoneNum;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(num));
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(getApplicationContext(),"Long press for more options!",Toast.LENGTH_LONG).show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                propertyList.clear();
                int i = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    ModelDatabase database = snapshot.getValue(ModelDatabase.class);
                    if(lowerStr.isEmpty() && upperStr.isEmpty())
                    {
                        if(district_this.equals(database.district))
                        {
                            propertyList.add(database);
                            keys[i] = snapshot.getKey();
                            i++;
                        }
                    }
                    else if(lowerStr.isEmpty())
                    {
                        if(district_this.equals(database.district) && Integer.valueOf(database.amount)<=Integer.valueOf(upperStr))
                        {
                            propertyList.add(database);
                            keys[i] = snapshot.getKey();
                            i++;
                        }
                    }
                    else if(upperStr.isEmpty())
                    {
                        if(district_this.equals(database.district) && Integer.valueOf(database.amount)>=Integer.valueOf(lowerStr))
                        {
                            propertyList.add(database);
                            keys[i] = snapshot.getKey();
                            i++;
                        }
                    }

                    else
                    {
                        if(district_this.equals(database.district) && Integer.valueOf(database.amount)>=Integer.valueOf(lowerStr) && Integer.valueOf(database.amount)<=Integer.valueOf(upperStr))
                        {
                            propertyList.add(database);
                            keys[i] = snapshot.getKey();
                            i++;
                        }
                    }
                }
                OwnerInfoRetrieve adapter_list = new OwnerInfoRetrieve(RenterShowActivity.this,propertyList);
                allProperty.setAdapter(adapter_list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}