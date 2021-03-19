package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Login extends Fragment{

    //Bottoni login e registrazione
    private TextView btnSign;
    private CardView btnLogin;

    //Campi mail e password
    private TextView emailTextView;
    private TextView passwordTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        btnSign = view.findViewById(R.id.SignIn);
        btnLogin = view.findViewById(R.id.loginButton);

        emailTextView = view.findViewById(R.id.mailLogin);
        passwordTextView = view.findViewById(R.id.passwordLogin);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new RegistrationActvity());
                fr.commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new MatchSActivity());
                fr.commit();
            }
        });

        return view;
    }

    private void login() {

        //Estrapola i dati dalle textview
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        //Controlla che il campo per la email non sia vuoto
        if(email.isEmpty()){
            emailTextView.setError("Questo campo non può essere vuoto");
            emailTextView.requestFocus();
            return;
        }

        //Controllo validazione email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTextView.setError("Questa mail non è valida");
            emailTextView.requestFocus();
            return;
        }

        //Controlla che il campo per la password non sia vuoto
        if(password.isEmpty()){
            passwordTextView.setError("Questo campo non può essere vuoto");
            passwordTextView.requestFocus();
            return;
        }



    }
}