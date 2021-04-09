package com.example.app;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (controlloConnessione(MainActivity.this)) {

            //Connessione disponibile

            //Legge le shared preferences dal file di testo
            sharedPreferences  = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("emailLogin", "");
            String password = sharedPreferences.getString("passwordLogin", "");

            if(!email.isEmpty() && !password.isEmpty())
            {
                //Viene effettuato il login automatico se dovessero essere presenti le credenziali
                userLogin(email, password);
            }
            else {

                //Apre la schermata di login
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }

        }
        else {
            // Connessione assente
            setContentView(R.layout.offline);
            return;
        }

    }

    public void userLogin(String email, String password){

        if (!email.isEmpty() && !password.isEmpty()) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "password";

                    String[] data = new String[2];
                    data[0] = email;
                    data[1] = password;
                    PutData putData = new PutData("http://93.43.208.27/carletti/sportydb/login.php", "POST", field, data);

                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Login riuscito")) {
                                Toast.makeText(MainActivity.this, "Bentornato" , Toast.LENGTH_LONG).show();

                                Intent intent = new Intent (MainActivity.this, Search.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Sono richiesti tutti i campi", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Controlla la connessione internet del dispositivo
     * @return restituisce FALSE se il device non è collegato ad una frete internet
     */
    public static boolean controlloConnessione(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet

                // Connesso al provider
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // Connesso al wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
}