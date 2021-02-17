package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class RegistrationActvity extends Fragment implements View.OnClickListener {

    Spinner fascia_eta;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration, container, false);

        CardView btnSigReg=view.findViewById(R.id.SignInButton);

        fascia_eta=view.findViewById(R.id.fascia_eta);

        setSpinnerStyle();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fascia_eta:
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ChooseSportActivity());
                fr.commit();
                break;
        }
    }

    private void setSpinnerStyle() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.eta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fascia_eta.setAdapter(adapter);
    }

}