package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChooseSportActivity extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_sport, container, false);

        CardView btnSoccer=view.findViewById(R.id.chooseSoccer);
        CardView btnTennis=view.findViewById(R.id.chooseTennis);

        return view;
        
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.chooseSoccer:
                fr.replace(R.id.fragment_container, new MatchSActivity());
                fr.commit();
                break;
            case R.id.chooseBasket:
                fr.replace(R.id.fragment_container, new MatchTActivity());
                fr.commit();
                break;
            case R.id.chooseHockey:

                break;
            case R.id.choosePaddle:

                break;
            case R.id.chooseVolley:

                break;
            case R.id.chooseTennis:

                break;
        }
    }
}