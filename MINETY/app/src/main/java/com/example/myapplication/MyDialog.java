package com.example.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG="addClass";
    public static final String CLASS_UPDATE_DIALOG="updateClass";
    public static final String STUDENT_ADD_DIALOG="addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";

    private OnClickListener listener;
    private int id;
    private String name;

    public MyDialog(int id, String name)
    {

        this.id = id;
        this.name = name;
    }

    public MyDialog() {

    }

    public interface OnClickListener{
        void onClick(String text1,String text2);
    }

    public void setListener(OnClickListener listener) {

        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog=null;
        if(getTag().equals(CLASS_ADD_DIALOG))dialog=getAddClassDialog();
        if(getTag().equals(STUDENT_ADD_DIALOG))dialog=getAddStudentDialog();
        if(getTag().equals(CLASS_UPDATE_DIALOG))dialog=getUpdateClassDialog();
        if(getTag().equals(STUDENT_UPDATE_DIALOG))dialog=getUpdateStudentDialog();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.titleDialog);
        title.setText("Update Student");

        EditText usn_edt=view.findViewById(R.id.edt01);
        usn_edt.setHint("Roll No");
        EditText name_edt=view.findViewById(R.id.edt02);
        name_edt.setHint("Name");

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);
        add.setText("Update");
        usn_edt.setText(id+"");
        usn_edt.setEnabled(false);
        name_edt.setText(name);

        cancel.setOnClickListener(v->dismiss());
        add.setOnClickListener(v-> {
            String usn = usn_edt.getText().toString();
            String name = name_edt.getText().toString();
            listener.onClick(usn,name);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getUpdateClassDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.titleDialog);
        title.setText("Update Class");

        EditText class_edt=view.findViewById(R.id.edt01);
        class_edt.setHint("Class Name");
        EditText subject_edt=view.findViewById(R.id.edt02);
        subject_edt.setHint("Subject Name");

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);

        add.setText("UPDATE");

        cancel.setOnClickListener(v->dismiss());
        add.setOnClickListener(v-> {
            String className = class_edt.getText().toString();
            String subName = subject_edt.getText().toString();
            listener.onClick(className,subName);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddStudentDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.titleDialog);
        title.setText("Add New Student");

        EditText usn_edt=view.findViewById(R.id.edt01);
        usn_edt.setHint("Roll No");
        EditText name_edt=view.findViewById(R.id.edt02);
        name_edt.setHint("Name");

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v->dismiss());
        add.setOnClickListener(v-> {
            String usn = usn_edt.getText().toString();
            String name = name_edt.getText().toString();
            usn_edt.setText(String.valueOf(Integer.parseInt(usn)+1));
            name_edt.setText("");
            listener.onClick(usn,name);
        });

        return builder.create();

    }

    private Dialog getAddClassDialog() {


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.titleDialog);
        title.setText("Add New Class");

        EditText class_edt=view.findViewById(R.id.edt01);
        class_edt.setHint("Class Name");
        EditText subject_edt=view.findViewById(R.id.edt02);
        subject_edt.setHint("Subject Name");

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v->dismiss());
        add.setOnClickListener(v-> {
            String className = class_edt.getText().toString();
            String subName = subject_edt.getText().toString();
            listener.onClick(className,subName);
            dismiss();
        });

        return builder.create();

    }
}
