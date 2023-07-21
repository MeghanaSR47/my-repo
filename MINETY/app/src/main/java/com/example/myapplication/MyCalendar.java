package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCalendar extends DialogFragment
{
    int day=Calendar.DAY_OF_MONTH;
    int month=Calendar.MONTH;
    int year=Calendar.YEAR;
    Calendar calendar=Calendar.getInstance();
    public interface OnCalendarOkClickListener
    {
        void onClick(int year,int month,int day);
    }
    public OnCalendarOkClickListener onCalendarOkClickListener;
    public void setOnCalendarOkClickListener(OnCalendarOkClickListener onCalendarOkClickListener)
    {
        this.onCalendarOkClickListener=onCalendarOkClickListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        return new DatePickerDialog(getActivity(),((view, year, month, dayOfMonth)->{
            onCalendarOkClickListener.onClick(year,month,dayOfMonth);
        }),calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    //to modify date to the current date
    void setDate(int year,int month,int day)
    {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
    }

    String getDate()
    {
        return DateFormat.format("dd.MM.yyyy",calendar).toString();
    }
}
