package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OwnerViewActivity extends AppCompatActivity {
    ListView personalProperty;
    List<ModelDatabase> propertyList;

    String[] keys = new String[10000];

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getCurrentUser().getUid();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Personal_Property").child(uid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view);

        personalProperty = (ListView)findViewById(R.id.listViewId2);
        propertyList = new ArrayList<>();

        personalProperty.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ModelDatabase modelDatabase = propertyList.get(position);
//                Toast.makeText(OwnerViewActivity.this,keys[position],Toast.LENGTH_LONG).show();
                ownerUpdateDialog(keys[position],modelDatabase.getDistrict(),modelDatabase.getThana(),modelDatabase.getAddress(),modelDatabase.getArea(),modelDatabase.getFloor(),modelDatabase.getDetails(),modelDatabase.getAmount(),modelDatabase.getAdvance(),modelDatabase.getPhone());
                return false;
            }
        });

    }

    private void ownerUpdateDialog(String key, String district, String thana, String address, String area, String floor, String details, String amount, String advance, String phone) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View updateView = inflater.inflate(R.layout.owner_update_dialog,null);
        builder.setView(updateView);

        EditText newAmount = (EditText) updateView.findViewById(R.id.editTextAmountUpdateId);
        EditText newAdvance = (EditText) updateView.findViewById(R.id.editTextAdvanceUpdateId);
        EditText newPhone = updateView.findViewById(R.id.editTextPhoneUpdateId);
        Button update = (Button)updateView.findViewById(R.id.buttonOwnerUpdateId);
        Button delete = (Button)updateView.findViewById(R.id.buttonOwnedDeleteId);

        builder.setTitle("Update and delete window");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amo = newAmount.getText().toString();
                String adv = newAdvance.getText().toString();
                String pho = newPhone.getText().toString();

                if(TextUtils.isEmpty(amo) && TextUtils.isEmpty(adv) && TextUtils.isEmpty(pho))
                {
                    Toast.makeText(getApplicationContext(),"Please enter a field to update!",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(amo)){
                    amo = amount;
                }
                if(TextUtils.isEmpty(adv)){
                    adv = advance;
                }
                if(TextUtils.isEmpty(pho)){
                    pho = phone;
                }

                updateOwnerData(key,district,thana,address,area,floor,details,amo,adv,pho);
                alertDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddress(key);
            }
        });
    }

    private boolean deleteAddress(String key) {
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Personal_Property").child(uid).child(key);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("All_Property").child(key);

        ref1.removeValue();
        ref2.removeValue();

        Toast.makeText(getApplicationContext(),"Address deleted successfully",Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean updateOwnerData(String key, String district, String thana, String address, String area, String floor, String details, String amo, String adv,String phone) {
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Personal_Property").child(uid).child(key);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("All_Property").child(key);

        ModelDatabase modelDatabase = new ModelDatabase(district,thana,address,area,floor,details,amo,adv,phone);
        ref1.setValue(modelDatabase);
        ref2.setValue(modelDatabase);

        Toast.makeText(getApplicationContext(),"Owner details updated successfully",Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(getApplicationContext(),"Long press for more options!",Toast.LENGTH_LONG).show();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                propertyList.clear();
                int i=0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    ModelDatabase database = snapshot.getValue(ModelDatabase.class);
                    propertyList.add(database);
                    keys[i] = snapshot.getKey();
                    i++;
                }
                OwnerInfoRetrieve adapter_list = new OwnerInfoRetrieve(OwnerViewActivity.this,propertyList);
                personalProperty.setAdapter(adapter_list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}