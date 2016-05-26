package dutest.omdb.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import dutest.omdb.R;
import dutest.omdb.util.image.DownloadImageTask;

/**
 * Created by Pedreduardo on 25/05/2016.
 */
public class MovieProfile extends AppCompatActivity{

    Context context;
    AppBarLayout appBar;
    CollapsingToolbarLayout collapsing;
    ImageView imageView;
    Toolbar toolbar;
    ImageView posterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movie_profile);
        this.context = getApplicationContext();

        initComponents();
    }

    private void initComponents() {
        this.appBar = (AppBarLayout) findViewById(R.id.appBar);
        this.collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        this.imageView = (ImageView) findViewById(R.id.movieImage);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.posterImage = (ImageView) findViewById(R.id.posterImage);

        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.collapsing.setTitle("Harry Potter and the Deathly Hallows");
        this.collapsing.setCollapsedTitleTextColor(ContextCompat.getColor(this.context, R.color.white));
        this.collapsing.setExpandedTitleColor(ContextCompat.getColor(this.context, R.color.white));

        new DownloadImageTask(this.imageView).execute("http://ia.media-imdb.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg");
        new DownloadImageTask(this.posterImage).execute("http://ia.media-imdb.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg");


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_profile_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MovieProfile.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MovieProfile.this.getComponentName()));
        }

        EditText searchText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        searchText.setHintTextColor(ContextCompat.getColor(this.context, R.color.white));
        searchText.setTextColor(ContextCompat.getColor(this.context, R.color.white));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {return true; }

            public boolean onQueryTextSubmit(String query) {
                String amazonSearch = "http://www.amazon.com/gp/mas/dl/android?s=";
                query = query.replace(' ', '+');
                amazonSearch += query + "+dvd+bluray";

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(amazonSearch));
                startActivity(browserIntent);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);


        return super.onCreateOptionsMenu(menu);
    }


}
