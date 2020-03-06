package com.e.afinal;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.e.afinal.StrategyPattern.*;

public class MyActivity extends AppCompatActivity {
    // Con esta activity, se hace posible el uso del patrón Strategy

    // Interfaz a usar para cambiar el comportamiento de las activities
    // En este caso, se cambia su interfaz gráfica
    protected DarkMode darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        loadTheme(sp.getBoolean("darkMode", false));
    }

    @Override
    protected void onPause() {
        // Se guarda la última configuración del modo oscuro

        super.onPause();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean("darkMode", getDarkMode() == DarkMode.DARK);

        editor.apply();
    }

    @Override
    protected void onResume() {
        // Se carga la última configuración guardada del modo oscuro

        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        loadTheme(sp.getBoolean("darkMode", false));
    }

    protected int getDarkMode() {
        // Retorna el estado del Dark Mode (Activado 1, Desactivado 0)
        return darkMode.getDarkMode();
    }


    protected void loadTheme(boolean b){

        // Carga el dark mode dependiendo la condición que le sea pasada
        if (b) {
            darkMode = new DarkModeActivated();
        } else {
            darkMode = new DarkModeNotActivated();
        }

        darkMode.setTheme(this);

    }
}
