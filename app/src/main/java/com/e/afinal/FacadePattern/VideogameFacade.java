package com.e.afinal.FacadePattern;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.afinal.Interface.RAWGService;
import com.e.afinal.ListAdapters.GameAct.ColListAdapter;
import com.e.afinal.Model.GameExtraInfo.Developer;
import com.e.afinal.Model.GameExtraInfo.Publisher;
import com.e.afinal.Model.Games.Genres;
import com.e.afinal.Model.Games.PlatformModel;
import com.e.afinal.Model.Games.Screenshot;
import com.e.afinal.Model.Games.Videogame;
import com.e.afinal.Model.Games.VideogameExtraInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideogameFacade {

    private Videogame game;

    private Context context;

    private VideoView vv1;

    private TextView tvName, tvRating, tvRelease, tvPlatforms, tvGenres,
            tvESRB, tvDescription, tvDevelopers, tvPublishers, tvWebsite;

    private RecyclerView rv;


    private Retrofit retrofit;


    public VideogameFacade(Videogame game, Context context, VideoView vv1, TextView tvName, TextView tvRating, TextView tvRelease, TextView tvPlatforms, TextView tvGenres, TextView tvESRB, TextView tvDescription, TextView tvDevelopers, TextView tvPublishers, TextView tvWebsite, RecyclerView rv, Retrofit retrofit) {
        this.game = game;
        this.context = context;
        this.vv1 = vv1;
        this.tvName = tvName;
        this.tvRating = tvRating;
        this.tvRelease = tvRelease;
        this.tvPlatforms = tvPlatforms;
        this.tvGenres = tvGenres;
        this.tvESRB = tvESRB;
        this.tvDescription = tvDescription;
        this.tvDevelopers = tvDevelopers;
        this.tvPublishers = tvPublishers;
        this.tvWebsite = tvWebsite;
        this.rv = rv;
        this.retrofit = retrofit;
    }

    public void setUpGameData() {

        // Método para "Setear" los distintos componentes del GamesActivity


        /* Al esto ser un facade, se podría mandar directamente los métodos siguientes,
            pero en mi caso no es necesario ya que cargaré datos del mismo juego que
            paso por parámetro, así que para más organización, sólo mando un método

         */
        loadGameHeader(game);

        loadPlatforms(game);

        loadGenres(game);

        loadClip(game);

        loadScreenshots(game);

        getExtraInfo(game.getId() + "");

    }


    // Carga el nombre, calificación y fecha de lanzamiento del juego
    private void loadGameHeader(Videogame game){
        tvName.setText(game.getName());
        tvRating.setText(game.getRating() + "/5.0");

        int location = context.getResources().getDisplayMetrics().widthPixels - ((context.getResources().getDisplayMetrics().widthPixels / 10) * 6 );

        tvRating.setPadding(location, 0, 0, 0);

        tvRelease.setText("Release Date: " + game.getReleased() + "\n");
    }

    // Carga las plataformas para las que fue lanzado el juego
    private void loadPlatforms(Videogame game){

        String platforms = "";
        for (PlatformModel platform : game.getPlatforms()) {
            platforms += platform.getPlatform().getName() + ", ";
        }

        String platformTag = setTagFormat("Platforms", platforms);
        tvPlatforms.append(platformTag + "\n");

    }

    // Carga los géneros a los que pertenece el juego
    private void loadGenres(Videogame game){

        String genres = "";
        for (Genres genre : game.getGenres()) {
            genres += genre.getName() + ", ";
        }
        String genreTag = setTagFormat("Genres", genres);
        tvGenres.append(genreTag + "\n");
    }

    // Carga un vídeo del juego, junto con controles de reproducción
    private void loadClip(Videogame game){
        MediaController mc = new MediaController(context);
        Uri uri = Uri.parse(game.getClip().getClip());
        vv1.setVideoURI(uri);
        mc.setAnchorView(vv1);
        vv1.setMediaController(mc);
        vv1.start();
    }

    // Carga distintos screenshots del juego en un recycler view horizontal
    private void loadScreenshots(Videogame game){
        ArrayList<String> cols = new ArrayList<>();

        for (Screenshot scs : game.getScreenshots()) {
            cols.add(scs.getImage());
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(rvLayoutManager);

        // specify an adapter
        ColListAdapter rvAdapter = new ColListAdapter(cols, context);
        rv.setAdapter(rvAdapter);

    }


    // Obtiene información adicional del juego desde un endpoint diferente de la API
    private void getExtraInfo(String id) {

        RAWGService service = retrofit.create(RAWGService.class);

        Call<VideogameExtraInfo> call = service.getVideogameExtraInfo(id);

        call.enqueue(new Callback<VideogameExtraInfo>() {
            @Override
            public void onResponse(Call<VideogameExtraInfo> call, Response<VideogameExtraInfo> response) {

                VideogameExtraInfo vgInfo = response.body();

                String site = "<a href=\"" + vgInfo.getWebsite() + "\"> " + vgInfo.getWebsite() + "</a>";
                tvWebsite.setText(Html.fromHtml(site));

                tvDescription.setText("\n" + vgInfo.getDescription_raw() + "\n");

                loadDevs(vgInfo);

                loadPubs(vgInfo);

                tvESRB.setText("ESRB Rating: " + vgInfo.getEsrb_rating().getName() + "\n");

            }

            @Override
            public void onFailure(Call<VideogameExtraInfo> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }


    // Carga los desarrolladores del juego con formato
    private void loadDevs(VideogameExtraInfo vgInfo){

        String devs = "";
        for (Developer dev : vgInfo.getDevelopers()) {
            devs += dev.getName() + ", ";
        }
        String devData = setTagFormat("Developers", devs);
        tvDevelopers.setText(devData + "\n");

    }

    // Carga los publicadores del juego con formato
    private void loadPubs(VideogameExtraInfo vgInfo){

        String pubs = "";
        for (Publisher pub : vgInfo.getPublishers()) {
            pubs += pub.getName() + ", ";
        }

        String pubData = setTagFormat("Publishers", pubs);
        tvPublishers.setText(pubData+ "\n");

    }

    // Da el formato a los strings, éstos string terminan en " ~ ".
    private String setTagFormat(String tagTitle, String tagData){
        StringBuffer str = new StringBuffer(tagData);
        str.delete(str.length() - 2, str.length());

        String finalTag = tagTitle + ": "+ str;

        return finalTag;
    }

}
