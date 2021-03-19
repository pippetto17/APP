package com.example.app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class RegistrationActvity extends Fragment {

    private int STORAGE_PERMISSION_CODE = 1;

    private Spinner fascia_eta;
    private CardView btnSigReg;
    private TextView nomeTextView;
    private TextView cognomeTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private TextView confermaPasswordTextView;
    private AppCompatImageView btnCaricaFoto;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration, container, false);

        btnSigReg = view.findViewById(R.id.SignInButton);
        fascia_eta = view.findViewById(R.id.fascia_eta);
        nomeTextView = view.findViewById(R.id.nomeRegistration);
        cognomeTextView = view.findViewById(R.id.cognomeRegistration);
        emailTextView = view.findViewById(R.id.emailRegistration);
        passwordTextView = view.findViewById(R.id.passwordRegistration);
        confermaPasswordTextView = view.findViewById(R.id.confermaPasswordRegistration);
        btnCaricaFoto = view.findViewById(R.id.userImage);

        setSpinnerStyle();

        btnSigReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

        btnCaricaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    requestStoragePermission();
                }
            }
        });

        return view;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permesso accesso galleria")
                    .setMessage("È richiesto il seguente permesso per caricare un immagine profilo")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void userRegister() {

        /**
        String nome = nomeTextView.getText().toString().trim();
        String cognome = cognomeTextView.getText().toString().trim();
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();
        String confermaPassword = confermaPasswordTextView.getText().toString().trim();

        //Controlla che il campo per il nome non sia vuoto
        if(nome.isEmpty()){
            nomeTextView.setError("Questo campo non può essere vuoto");
            nomeTextView.requestFocus();
            return;
        }

        //Controlla che il campo per il cognome non sia vuoto
        if(cognome.isEmpty()){
            cognomeTextView.setError("Questo campo non può essere vuoto");
            cognomeTextView.requestFocus();
            return;
        }

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

        //Controlla che il campo per la conferma della password non sia vuoto
        if(confermaPassword.isEmpty()){
            confermaPasswordTextView.setError("Questo campo non può essere vuoto");
            confermaPasswordTextView.requestFocus();
            return;
        }

        //Controlla che la password e la conferma della password siano uguali
        if(!confermaPassword.equals(password)){
            confermaPasswordTextView.setError("La password di conferma non è uguale");
            confermaPasswordTextView.requestFocus();
            return;
        }

        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new Login());
        fr.commit();
         **/


        //Creo la connessione al database

        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/luca";
        String user = "root";
        String rootPassword = "Luca2002Perseverance";
        String driver = "com.mysql.cj.jdbc.Driver";

        //Installo i Driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            Log.e("connectiondb", "ClassNotFoundException " + e.getMessage());
            return;
        }

        // Creo la connessione al database
        try{
            cn = DriverManager.getConnection(url, user, rootPassword);
        }
        catch (SQLException e){
            Log.e("connectiondb", "Errore nella connessione " + e.getMessage());
            return;
        }

        //Esegue comandi
        try {
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM luca.tabella");

            //Verifica se c'è un ulteriore campo dopo quello appena letto
            while(rs.next()){
                Log.e("connectiondb", rs.getString("Nome"));
            }
        }
        catch (SQLException e) {
            Log.e("connectiondb", "Errore nell'interrogazione' " + e.getMessage());
            return;
        }

        //Chiude connessione
        try {
            cn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void setSpinnerStyle() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.eta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fascia_eta.setAdapter(adapter);
    }

}