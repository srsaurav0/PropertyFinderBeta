package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,phone,password,conPassword;
    RadioGroup radioGroup;
    Button signUp;

    TextView textView;

    String namestr,emailstr,phonestr,passstr,conpassstr,genstr = "";

    FirebaseAuth mAuth;
    DatabaseReference myRef =  FirebaseDatabase.getInstance().getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        name = (EditText)findViewById(R.id.editTextNameId);
        email = (EditText)findViewById(R.id.editTextEmailAddressId);
        phone = (EditText)findViewById(R.id.editTextPhoneId);
        password = (EditText)findViewById(R.id.editTextPasswordId1);
        conPassword = (EditText)findViewById(R.id.editTextTextPasswordId2);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupId);
        signUp = (Button)findViewById(R.id.SignUpButtonId);
        textView = (TextView)findViewById(R.id.textView3);

        mAuth = FirebaseAuth.getInstance();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButtonMaleId)
                    genstr = "Male";
                else
                    genstr = "Female";
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namestr = name.getText().toString();
                emailstr = email.getText().toString();
                phonestr = phone.getText().toString();
                passstr = password.getText().toString();
                conpassstr = conPassword.getText().toString();

                if(namestr.isEmpty()) {
                    name.setError("Required!");
                    return;
                }
                if(emailstr.isEmpty()) {
                    email.setError("Required!");
                    return;
                }
                if(phonestr.isEmpty()) {
                    phone.setError("Required!");
                    return;
                }
                if(genstr.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please select a gender",Toast.LENGTH_LONG).show();
                    return;
                }
                if(passstr.isEmpty()) {
                    password.setError("Required!");
                    return;
                }
                if(conpassstr.isEmpty()) {
                    conPassword.setError("Required!");
                    return;
                }
                if(!passstr.equals(conpassstr)){
                    conPassword.setError("Password didn't match!");
                }

                mAuth.createUserWithEmailAndPassword(emailstr,passstr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Profile profile = new Profile(namestr,emailstr,phonestr,genstr);
                        String uid = mAuth.getCurrentUser().getUid();
                        myRef.child(uid).setValue(profile);
                        mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterActivity.this,"A verification email has been sent to your email.Please confirm it.",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this,"User created but verification email couldn't be sent.",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = "Error"+e.getMessage();
                        Toast.makeText(RegisterActivity.this,"Failed!Please try again.",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Intent intent = new Intent(RegisterActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }
}