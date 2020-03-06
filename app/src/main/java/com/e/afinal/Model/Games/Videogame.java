package com.e.afinal.Model.Games;

import java.util.List;

public class Videogame {

    // Modelo para la info básica de un juego devuelto por la API

    private int id;
    private String slug;
    private String name;
    private String released;
    private boolean tba;
    private String background_image;
    private float rating;
    private List<PlatformModel> platforms;
    private List<Genres> genres;
    private Clip clip;
    private List<Screenshot> short_screenshots;

    public List<Screenshot> getScreenshots() {
        return short_screenshots;
    }

    public int getId() {
        return id;
    }

    public List<PlatformModel> getPlatforms() {
        return platforms;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getReleased() {
        return released;
    }

    public boolean isTba() {
        return tba;
    }

    public String getBackground_image() {
        return background_image;
    }

    public float getRating() {
        return rating;
    }

    public Clip getClip() {
        return clip;
    }

}
