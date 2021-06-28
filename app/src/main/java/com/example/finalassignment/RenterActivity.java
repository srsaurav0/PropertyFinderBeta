package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RenterActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    Spinner district;
    String dist_str;
    Button show;
    EditText lower,upper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter);

        Toolbar toolbar = findViewById(R.id.myToolbar3);
        setSupportActionBar(toolbar);

        show = (Button)findViewById(R.id.showRenterId);
        lower = (EditText)findViewById(R.id.editTextLowerId);
        upper = (EditText)findViewById(R.id.editTextUpperId);

        district = (Spinner)findViewById(R.id.districtRenterId);
        final String[] options_renter = getResources().getStringArray(R.array.districts);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(RenterActivity.this,
                android.R.layout.simple_spinner_dropdown_item, options_renter);
        district.setAdapter(adapter);
        district.setOnItemSelectedListener(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RenterActivity.this,RenterShowActivity.class);
                String upperStr = upper.getText().toString();
                String lowerStr = lower.getText().toString();
                intent.putExtra("district_selected",dist_str);
                intent.putExtra("lowerStr",lowerStr);
                intent.putExtra("upperStr",upperStr);
            //    Toast.makeText(getApplicationContext(),dist_str,Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });


    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
        dist_str = parent.getSelectedItem().toString();
//        Toast.makeText(RenterActivity.this,dist_str,Toast.LENGTH_LONG).show();
    }


    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(RenterActivity.this,"Please select a district",Toast.LENGTH_LONG).show();
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
                            Intent intent = new Intent(RenterActivity.this,MainActivity.class);
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

