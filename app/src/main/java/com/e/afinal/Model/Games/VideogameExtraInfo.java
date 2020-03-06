package com.e.afinal.Model.Games;

import com.e.afinal.Model.GameExtraInfo.Developer;
import com.e.afinal.Model.GameExtraInfo.ESRB;
import com.e.afinal.Model.GameExtraInfo.Publisher;

import java.util.List;

public class VideogameExtraInfo {

    // Modelo para info adicional de los juegos devuelto por la API

    private int id;
    private String website;
    private List<Developer> developers;
    private List<Publisher> publishers;
    private ESRB esrb_rating;
    private String description_raw;

    public int getId() {
        return id;
    }

    public String getWebsite() {
        if(website == null)
            return "none";
        return website;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public ESRB getEsrb_rating() {
        if(esrb_rating == null)
            return new ESRB();
        return esrb_rating;
    }

    public String getDescription_raw() {
        return description_raw;
    }
}
