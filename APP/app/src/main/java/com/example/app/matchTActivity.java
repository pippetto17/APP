package com.example.app;

import android.app.DatePickerDialog;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import java.util.Calendar;

public class MatchTActivity extends AppCompatActivity
{
    TextView date_text;
    CardView show_dialog;
    Calendar calendar;
    int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_match_soccer);
        //getting calendar instance
        calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

        //accessing EditText and Button
        date_text = (TextView) findViewById(R.id.date_text);
        show_dialog = (CardView) findViewById(R.id.show_dialog);


        show_dialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MatchTActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        //sets date in EditText
                        date_text.setText(dayOfMonth+"/"+ (month+1) +"/"+year);
                    }
                }, year, month, day);
                //shows DatePickerDialog
                datePickerDialog.show();

            }
        });



    }
}
