package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoPage extends AppCompatActivity {
    //String name;
    TextView name;
    TextView des;
    TextView sub;
    TextView subc;
    Button attend;
    String subj="DSC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        name= findViewById(R.id.nameid);
        des= findViewById(R.id.designation);
        sub= findViewById(R.id.subject);
        subc= findViewById(R.id.subjectcode);
        attend=findViewById(R.id.button);
        showuserdata();
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), attendance.class);
                Intent intent2=getIntent();
                String username=intent2.getStringExtra("username");
                String subject=intent2.getStringExtra("subject");
                intent.putExtra("username", username);
                intent.putExtra("subject",subject);
                startActivity(intent);
            }
        });
    }
    private void showuserdata()
    {
        Intent intent=getIntent();
        String Name=intent.getStringExtra("Name");
        name.setText(Name);
        String desi=intent.getStringExtra("designation");
        des.setText(desi);
        String subj=intent.getStringExtra("subject");
        sub.setText(subj);
        String subjc=intent.getStringExtra("subject code");
        subc.setText(subjc);
    }
}
