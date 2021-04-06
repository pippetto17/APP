package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class MyappIntro extends AppIntro2 {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Pagina 1", "pagina uno dove si vede il login", R.drawable.login, ContextCompat.getColor(getApplicationContext(),R.color.green_sporty)));
        addSlide(AppIntroFragment.newInstance("Pagina 2", "pagina due dove si vede la registrazione", R.drawable.signup, ContextCompat.getColor(getApplicationContext(),R.color.green_500)));
        addSlide(AppIntroFragment.newInstance("Pagina 3", "pagina tre dove si vede un match", R.drawable.choose, ContextCompat.getColor(getApplicationContext(),R.color.purple_700)));

        // Fade Transition
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences != null){
            boolean checkShared = sharedPreferences.getBoolean("checkStated", false);

            if (checkShared == true) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        editor.putBoolean("checkStated", false).commit();
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        editor.putBoolean("checkStated", true).commit();
        finish();
    }
}