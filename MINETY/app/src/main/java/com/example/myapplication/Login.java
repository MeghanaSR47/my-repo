package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText name=(EditText) findViewById(R.id.un) ;
        EditText password=(EditText) findViewById(R.id.pwd) ;
        Button button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val1=name.getEditableText().toString();
                String val2 = password.getEditableText().toString();
                if ((validateUsername(val1) && validatePassword(val2))) {
                    isUser();
                }
            }

            public Boolean validateUsername(String val) {
                if (val.length()==0) {
                    name.setError("Field cannot be empty");
                    return false;
                } else {
                    name.setError(null);
                    return true;
                }
            }
            public Boolean validatePassword(String val) {
                if (val.isEmpty()) {
                    password.setError("Field cannot be empty");
                    return false;
                } else {
                    password.setError(null);
                    return true;
                }
            }

            public void isUser() {
                String enterUsername = name.getEditableText().toString().trim();
                String enterPassword = password.getEditableText().toString().trim();
                Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(enterUsername);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            name.setError(null);
                            String passwordFromDb = snapshot.child(enterUsername).child("password").getValue(String.class);
                            if (passwordFromDb.equals(enterPassword)) {
                                password.setError(null);
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), InfoPage.class);
                                String namefromdb=snapshot.child(enterUsername).child("Name").getValue(String.class);
                                intent.putExtra("Name", namefromdb);
                                String desfdb=snapshot.child(enterUsername).child("designation").getValue(String.class);
                                intent.putExtra("designation", desfdb);
                                String subfdb=snapshot.child(enterUsername).child("subject").getValue(String.class);
                                intent.putExtra("subject", subfdb);
                                String subcfdb=snapshot.child(enterUsername).child("subject code").getValue(String.class);
                                intent.putExtra("subject code", subcfdb);
                                intent.putExtra("username", enterUsername);
                               startActivity(intent);
                            } else {
                                password.setError("Wrong password");
                                password.requestFocus();
                            }
                        } else {
                            name.setError("No such user exists");
                            name.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
