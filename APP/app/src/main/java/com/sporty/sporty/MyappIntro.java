package com.sporty.sporty;

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

        Slide1 fragment1 = new Slide1();

        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String showIntro = sharedPreferences.getString("firstAccess", "");

        if (showIntro.isEmpty()) {
            addSlide(fragment1);
            addSlide(AppIntroFragment.newInstance("Pagina 2", "pagina due dove si vede la registrazione", R.drawable.signup, ContextCompat.getColor(getApplicationContext(), R.color.green_500)));
            addSlide(AppIntroFragment.newInstance("Pagina 3", "pagina tre dove si vede un match", R.drawable.def_wallpaper, ContextCompat.getColor(getApplicationContext(), R.color.purple_700)));

            // Fade Transition
            setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        editor.putString("firstAccess", "false").commit();
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        editor.putString("firstAccess", "false").commit();
        finish();
    }
}