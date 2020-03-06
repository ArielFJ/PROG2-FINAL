package com.e.afinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.e.afinal.ListAdapters.ListAdapter;
import com.e.afinal.ListAdapters.RowModel;
import com.e.afinal.Interface.RAWGService;
import com.e.afinal.Model.Games.GamesResponse;
import com.e.afinal.Model.Games.VideoGamesList;
import com.e.afinal.Model.Games.Videogame;
import com.e.afinal.StrategyPattern.DarkMode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Actividad principal que presenta una lista de todos los juegos
public class MainActivity extends MyActivity {

    private static final String TAG = "RAWG";

    private static ArrayList<RowModel> rows;
    private ListAdapter adapter;
    private GamesResponse gamesResponse;

    private TextView tvToolbar;
    private ListView lv;
    private Retrofit retrofit;

    private androidx.appcompat.widget.Toolbar tb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // Método que inicia todos los view y elementos de la lista
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Método para llenar los campos del menú
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Opción de cambiar al modo oscuro
            case R.id.toggleDark:
                loadTheme(getDarkMode() == DarkMode.LIGHT);

                // Ya que hay que recargar la aplicación, se reinicia ésta con in intent
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                return true;

            // Opción de cargar la actividad "About"
            case R.id.itemInfo:
                changeToInfo(null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void init(){
        lv = findViewById(R.id.listView);

        // Iniciar la conexión con retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rawg.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tvToolbar = findViewById(R.id.tvToolbar);
        tvToolbar.setText("MagnaG... - Loading");

        tb = findViewById(R.id.toolbar);

        setSupportActionBar(tb);

        // Obtener la lista de videojuegos
        // Recibe la página de juegos que se cargará como parámetro, se pasa null ya que es la primera página
        getVideoGames(null);

    }

    private void getVideoGames(String pagina) {

        RAWGService service = retrofit.create(RAWGService.class);

        Call<GamesResponse> call;

        // Si página es null, carga el endpoint por defecto, "games"
        if (pagina == null) {
            call = service.getVideogames("games");
        } else {
            // Si no es null, carga una página específica, Ej: games?page=2
            call = service.getVideogames(pagina);
        }

        // Obtiene la lista de videojuegos, la cual es un Síngleton
        rows = VideoGamesList.getInstance();

        call.enqueue(new Callback<GamesResponse>() {
            @Override
            public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Code: " + response.code());
                    return;
                }

                // Retorna el objeto GamesResponse, que contiene una lista de Videojuegos
                gamesResponse = response.body();

                ArrayList<Videogame> videogames = gamesResponse.getResults();

                // Añade el modelo a presentar en formato lista de los juegos
                addGamesToActivity(videogames);

                // Termina con la preparación de la estructura de la lista
                GamesListSetUp();

                tvToolbar.setText("MagnaGames");

                // Coloca la lista en la selección pertinente
                int selection = rows.size() / 20 <= 1 ? rows.size() - 20 : rows.size() - 25;

                lv.setSelection(selection);
            }

            @Override
            public void onFailure(Call<GamesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }


    private void addGamesToActivity(ArrayList<Videogame> videogames){
        for (int i = 0; i < videogames.size(); i++) {
            // Recorre la lista de Videojuegos

            Videogame vg = videogames.get(i);

            // Crea un modelo de fila con el videojuego
            RowModel r = new RowModel(vg, vg.getBackground_image(), vg.getName(), vg.getRating());

            //Lo añade a la lista general de Videojuegos
            VideoGamesList.addGame(r);
        }
    }


    private void GamesListSetUp(){

        // Verifica si el modo oscuro está activado
        boolean darkModeActivated = getDarkMode() == DarkMode.DARK ? true : false;

        // Clase ListAdapter de la lista
        adapter = new ListAdapter(getApplicationContext(), rows, darkModeActivated);

        // Coloca un footer a la lista
        if (lv.getFooterViewsCount() < 1) {
            View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer, null, false);

            footerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Da al footer el evento de cargar la siguiente página
                    // Aquí se usa la recursión, pues se llama a este mismo método
                    getVideoGames(getNextPageRoute());
                }
            });

            lv.addFooterView(footerView);
        }
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Se le da el evento a cada elemento de la lista de ir a la actividad Games
                Intent intent = new Intent(getApplicationContext(), GamesActivity.class);

                intent.putExtra("id", rows.get(position).getGame().getId());

                startActivity(intent);
            }
        });
    }

    // Obtiene la siguiente página a cargar de los juegos
    private String getNextPageRoute() {
        return "games?page=" + gamesResponse.getPage();
    }


    // Carga la actividad Información
    public void changeToInfo(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    // Retorna las columnas existentes en la lista de Videojuegos
    public static ArrayList<RowModel> getRows() {
        return rows;
    }


}
