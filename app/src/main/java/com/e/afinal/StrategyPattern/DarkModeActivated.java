package com.e.afinal.StrategyPattern;

import android.content.Context;

import com.e.afinal.R;

public class DarkModeActivated implements DarkMode {

    // Clase que hereda de Dark Mode que define la activación del modo oscuro

    @Override
    public void setTheme(Context context) {
        context.setTheme(R.style.DarkAppTheme);
    }

    @Override
    public int getDarkMode() {
        return DarkMode.DARK;
    }
}
