package com.e.afinal.Model.Games;

import com.e.afinal.ListAdapters.RowModel;

import java.util.ArrayList;


//Aquí utilicé el Síngleton para que la lista no se esté actualizando en el método cada vez que la llame
public class VideoGamesList{

    private static ArrayList<RowModel> rows;

    private VideoGamesList(){}

    public static ArrayList<RowModel> getInstance(){
        if( rows == null){
            rows = new ArrayList<>();
        }
        return rows;
    }

    // Se usa este método para añadir un juego
    public static boolean addGame(RowModel r){
        boolean gameExist = false;
        for(RowModel row: rows){
            if(row.getGame().getId() == r.getGame().getId()){
                gameExist = true;
            }
        }

        // Si el juego existe, no se añadirá; así no habrá repetición de datos
        if(gameExist){
            return false;
        }else{
            rows.add(r);
            return true;
        }

    }

}
