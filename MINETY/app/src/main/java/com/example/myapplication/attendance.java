package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class attendance extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems=new ArrayList<>();
    Toolbar toolbar;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        fab=findViewById(R.id.fab_main);
        fab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0788DB")));
        fab.setOnClickListener(v->showDialog());
        dbHelper=new DbHelper(this);
        loadData();

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        classAdapter=new ClassAdapter(this,classItems);
        recyclerView.setAdapter(classAdapter);

        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));

        setToolbar();

    }

    private void loadData()
    {
        Cursor cursor=dbHelper.getClassTable();
        classItems.clear();
        while (cursor.moveToNext())
        {
            @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex(DbHelper.C_ID));
            @SuppressLint("Range") String className=cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_NAME_KEY));
            @SuppressLint("Range") String subjectName=cursor.getString(cursor.getColumnIndex(DbHelper.SUBJECT_NAME_KEY));
            classItems.add(new ClassItem(className,subjectName,id));
        }

    }

    private void setToolbar() {

        toolbar=findViewById(R.id.toolbar);
        TextView title=toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);
        ImageButton save=toolbar.findViewById(R.id.save);

        title.setText("MINETY");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);

    }

    private void gotoItemActivity(int position)
    {
        Intent intent1=getIntent();
        String subject = intent1.getStringExtra("subject");

        Intent intent=new Intent(this,StudentActivity.class);
        intent.putExtra("className",classItems.get(position).getClassName());
        intent.putExtra("subjectName",classItems.get(position).getSubjectName());
        intent.putExtra("position",position);
        intent.putExtra("cid",classItems.get(position).getCid());

        if(subject.equals(classItems.get(position).getSubjectName()))
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"INVALID CLASS",Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog()
    {
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className,subjectName)->addClass(className,subjectName));
    }

    private void addClass(String className,String subjectName)
    {
        long cid=dbHelper.addClass(className,subjectName);
        ClassItem classItem=new ClassItem(className,subjectName,cid);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position)
    {
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className,subjectName)->updateClass(position,className,subjectName));
    }

    private void updateClass(int position, String className, String subjectName)
    {
        dbHelper.updateClass(classItems.get(position).getCid(),className,subjectName);
        classItems.get(position).setClassName(className);
        classItems.get(position).setSubjectName(subjectName);
        classAdapter.notifyItemChanged(position);
    }

    private void deleteClass(int position)
    {
        dbHelper.deleteClass(classItems.get(position).getCid());
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}