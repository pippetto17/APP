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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import java.util.Calendar;

public class MatchTActivity extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    TextView dateText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_match_tennis, container, false);

        dateText = view.findViewById(R.id.date_text);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_dialog:
                showDatePickerDailog();
                break;
        }
    }


    private void showDatePickerDailog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    };

    @Override
    public void onDateSet(DatePicker datePicker, int dayOfMonth, int month, int year) {
        String date = "Data: " + dayOfMonth + "/" + month + "/" + year;
        dateText.setText(date);
    }
}
