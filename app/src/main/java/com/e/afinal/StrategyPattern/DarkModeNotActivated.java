package com.e.afinal.StrategyPattern;

import android.content.Context;

import com.e.afinal.R;

public class DarkModeNotActivated implements DarkMode {

    // Clase que hereda de Dark Mode que define la desactivación del modo oscuro
    @Override
    public void setTheme(Context context) {
        context.setTheme(R.style.LightAppTheme);
    }

    @Override
    public int getDarkMode() {
        return DarkMode.LIGHT;
    }
}
