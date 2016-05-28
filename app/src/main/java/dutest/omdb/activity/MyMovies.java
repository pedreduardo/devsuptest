package dutest.omdb.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import dutest.omdb.R;
import dutest.omdb.adapter.MyMoviesAdapter;
import dutest.omdb.model.json.Movie;

/**
 * Created by Pedreduardo on 27/05/2016.
 */
public class MyMovies extends AppCompatActivity {

    ListView lv;
    Context context;
    private Toolbar toolbar;
    private ArrayList<Movie> myMovies;  //Lista dos filmes pesquisados pelo usuário
    private TextView noSeacrh;  //Texto de exibição quando não existir busca anterior.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movie_search_list);

        this.myMovies = new ArrayList<>();
        context=this;
        initComponents();
        initList();

    }

    private void initComponents() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.MyMovies));

        this.lv = (ListView) findViewById(R.id.list);
        this.noSeacrh =  (TextView) findViewById(R.id.wrong);
    }

    /**
     * Carrega a lista de filmes e exibe na ListView
     */
    private void initList() {

        Iterator<Movie> moviesList = Movie.findAll(Movie.class);
        this.myMovies = Lists.newArrayList(moviesList);
        if(myMovies == null || myMovies.isEmpty()) {
            this.noSeacrh.setText(getResources().getString(R.string.noSearch));
        }
        else{
            this.lv.setAdapter(new MyMoviesAdapter(this, this.myMovies));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}