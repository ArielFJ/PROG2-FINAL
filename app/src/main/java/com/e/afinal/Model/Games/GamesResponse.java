package com.e.afinal.Model.Games;

import java.util.ArrayList;

public class GamesResponse {

    // Modelo devuelto por la API en el que aparecen todos los juegos

    // Se almacenan en esta lista
    private ArrayList<Videogame> results;
    private String next;

    public String getPage(){
        String[] params = next.split("=");
        return params[params.length - 1];
    }

    public ArrayList<Videogame> getResults() {
        return results;
    }
}
