package com.e.afinal.StrategyPattern;

import android.content.Context;

public interface DarkMode {

    // Interfaz de la que heredan 2 clases que define el dark mode
    // Este es uno de los pasos para implementar el patrón Strategy

    int LIGHT = 0;
    int DARK = 1;

    void setTheme(Context context);

    int getDarkMode();

}
