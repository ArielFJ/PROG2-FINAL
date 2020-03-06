package com.e.afinal;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.e.afinal.FacadePattern.VideogameFacade;
import com.e.afinal.ListAdapters.RowModel;
import com.e.afinal.Model.Games.*;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Activity para cargar la información de un juego específico
public class GamesActivity extends MyActivity {

    private int GameID;

    private VideoView vv1;
    private TextView tvName, tvRating, tvRelease, tvPlatforms, tvGenres,
            tvESRB, tvDescription, tvDevelopers, tvPublishers, tvWebsite;

    private FrameLayout frmLayout;

    private RecyclerView rv;

    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        init();

    }

    private void init(){
        // Método para inicializar los componentes

        GameID = this.getIntent().getExtras().getInt("id");

        vv1 = findViewById(R.id.gameAct_Clip);
        tvName = findViewById(R.id.gameAct_name);
        tvRating = findViewById(R.id.gameAct_rating);
        tvRelease = findViewById(R.id.gameAct_release);
        tvPlatforms = findViewById(R.id.gameAct_platforms);
        tvGenres = findViewById(R.id.gameAct_genres);
        tvESRB = findViewById(R.id.gameAct_ESRB);
        tvDescription = findViewById(R.id.gameAct_Description);
        tvDevelopers = findViewById(R.id.gameAct_Developers);
        tvPublishers = findViewById(R.id.gameAct_Publishers);
        tvWebsite = findViewById(R.id.gameAct_website);

        rv = findViewById(R.id.gameAct_recycler);

        frmLayout = findViewById(R.id.gameAct_frameLayout);

        tvWebsite.setClickable(true);
        tvWebsite.setMovementMethod(LinkMovementMethod.getInstance());

        // Define dinámicamente el tamaño del vídeo dependiendo del ancho de la pantalla del dispositivo
        int videoHeight = (getResources().getDisplayMetrics().heightPixels / 3) + (getResources().getDisplayMetrics().heightPixels / 20);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                videoHeight
        );

        frmLayout.setLayoutParams(lp);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rawg.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Utiliza un Facade para simplificar la forma en que visualiza los métodos el cliente
        VideogameFacade facade = new VideogameFacade(
                selectGame(),
                this,
                vv1,
                tvName,
                tvRating,
                tvRelease,
                tvPlatforms,
                tvGenres,
                tvESRB,
                tvDescription,
                tvDevelopers,
                tvPublishers,
                tvWebsite,
                rv,
                retrofit);

        try {

            // En vez de usar muchos métodos, se utiliza un sólo del facade
            facade.setUpGameData();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Retorna el videojuego seleccionado de la lista de juegos
    private Videogame selectGame() {
        ArrayList<RowModel> rows = MainActivity.getRows();
        for (RowModel row : rows) {
            if (row.getGame().getId() == GameID) {
                return row.getGame();
            }
        }
        return null;
    }

}
