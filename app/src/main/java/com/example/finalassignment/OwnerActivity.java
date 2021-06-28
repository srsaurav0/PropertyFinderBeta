package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner district;
    EditText thana,address,floor,area,details,amount,advance,phoneNum;
    String dist_str,thnstr,addstr,floorstr,areastr,detstr,amostr,advstr,phoneStr;
    Button add;
    TextView propertyView;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Personal_Property");
    DatabaseReference allRef = database.getReference("All_Property");

    FirebaseAuth myAuth=FirebaseAuth.getInstance();
    String id=myAuth.getCurrentUser().getUid();
    DatabaseReference userRef=myRef.child(id);

    int count=0,all_count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Toolbar toolbar = findViewById(R.id.myToolbar2);
        setSupportActionBar(toolbar);

        thana = (EditText)findViewById(R.id.editTextThanaId);
        address = (EditText)findViewById(R.id.editTextAddressId);
        area = (EditText)findViewById(R.id.editTextAreaId);
        details = (EditText)findViewById(R.id.editTextRoomsId);
        amount = (EditText)findViewById(R.id.editTextRentAmountId);
        advance = (EditText)findViewById(R.id.editTextAdvanceAmountId);
        add = (Button)findViewById(R.id.addButtonId);
        floor=findViewById(R.id.editTextFlatId);
        phoneNum = findViewById(R.id.editTextPhoneNumberId);

        propertyView=findViewById(R.id.propertyViewId);

        district = (Spinner)findViewById(R.id.districtId);
        final String[] options = getResources().getStringArray(R.array.districts);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(OwnerActivity.this,
                android.R.layout.simple_spinner_dropdown_item, options);
        district.setAdapter(adapter);
        district.setOnItemSelectedListener(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thnstr = thana.getText().toString();
                addstr = address.getText().toString();
                floorstr = floor.getText().toString();
                areastr = area.getText().toString();
                detstr = details.getText().toString();
                amostr = amount.getText().toString();
                advstr = advance.getText().toString();
                phoneStr = phoneNum.getText().toString();


                TextView textView = (TextView)findViewById(R.id.propertyViewId);

                if(thnstr.isEmpty()) {
                    thana.setError("Required!");
                    return;
                }
                if(addstr.isEmpty()) {
                    address.setError("Required!");
                    return;
                }
                if(floorstr.isEmpty()) {
                    address.setError("Required!");
                    return;
                }
                if(areastr.isEmpty()) {
                    area.setError("Required!");
                    return;
                }
                if(detstr.isEmpty()) {
                    details.setError("Required!");
                    return;
                }
                if(amostr.isEmpty()) {
                    amount.setError("Required!");
                    return;
                }
                if(advstr.isEmpty()) {
                    advance.setError("Required!");
                    return;
                }
                if(phoneStr.isEmpty()) {
                    phoneNum.setError("Required!");
                    return;
                }

                ModelDatabase ownerDatabase = new ModelDatabase(dist_str,thnstr,addstr,floorstr,areastr,detstr,amostr,advstr,phoneStr);

                String key = myRef.child(id).push().getKey();

                myRef.child(id).child(key).setValue(ownerDatabase);
                allRef.child(key).setValue(ownerDatabase);

                Toast.makeText(OwnerActivity.this,"Property added successfully",Toast.LENGTH_LONG).show();
            }
        });



        propertyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerActivity.this,OwnerViewActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
        dist_str = parent.getSelectedItem().toString();
        //Toast.makeText(OwnerActivity.this,dist_str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(OwnerActivity.this,"Please select a district",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signOut) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to sign out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OwnerActivity.this,MainActivity.class);
                            Toast.makeText(getApplicationContext(),"Logged out successfully!",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        return true;
    }
}