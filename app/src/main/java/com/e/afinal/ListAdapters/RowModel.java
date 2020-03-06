package com.e.afinal.ListAdapters;

import com.e.afinal.Model.Games.Videogame;

public class RowModel {

    // Modelo de filas que recibirá la lista principal

    private Videogame game;
    private String image;
    private String name;
    private float rating;

    public RowModel(Videogame game, String image, String name, float rating) {
        this.game = game;
        this.image = image;
        this.name = name;
        this.rating = rating;
    }

    public Videogame getGame() {
        return game;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }
}
