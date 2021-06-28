package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button signIn;
    TextView signUp,resetPass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        email =(EditText)findViewById(R.id.editTextEmailId);
        password=(EditText)findViewById(R.id.editTextPasswordId);
        signIn=(Button)findViewById(R.id.loginButtonId);
        signUp=(TextView)findViewById(R.id.signupTextId);
        resetPass = (TextView)findViewById(R.id.resetPassId);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr =email.getText().toString();
                String passStr = password.getText().toString();

                if(emailStr.isEmpty()){
                    email.setError("Required!");
                    return;
                }
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches())
                {
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                    return;
                }
                if(passStr.isEmpty()){
                    password.setError("Required!");
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailStr,passStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(!mAuth.getCurrentUser().isEmailVerified()){
                            Toast.makeText(MainActivity.this,"Please verify your account or request verification link again",Toast.LENGTH_LONG).show();
                            openDialog();
                        }
                        else{
                            //User found & ready for next page
                            Toast.makeText(MainActivity.this,"Login successful!",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,PanelActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    //    Toast.makeText(MainActivity.this,"Login successful!",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Login failed!Please check your password and try again...",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset password ?");
                passwordResetDialog.setMessage("Enter your email to receive the password reset link : ");

                LinearLayout container = new LinearLayout(MainActivity.this);
                container.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(50, 20, 50, 20);

                resetMail.setLayoutParams(lp);
                resetMail.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
                resetMail.setLines(1);
                resetMail.setMaxLines(1);
                resetMail.setHint("Enter your email here");
                container.addView(resetMail, lp);

                passwordResetDialog.setView(container);


              //  passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String resMail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(resMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this,"Password reset link sent to your email!",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                Toast.makeText(MainActivity.this,"Error! Reset link could not be sent " + e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
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
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void openDialog()
    {
        VerificationDialog verificationDialog = new VerificationDialog();
        verificationDialog.show(getSupportFragmentManager(),"Verification");
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
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
}