package com.example.app;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import java.util.Calendar;

public class MatchSActivity extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    TextView dateText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_match_soccer, container, false);

        dateText = view.findViewById(R.id.date_text);

        CardView btnSign=view.findViewById(R.id.create_ad);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_ad:
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new AdvertsActivity());
                fr.commit();
                break;
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


