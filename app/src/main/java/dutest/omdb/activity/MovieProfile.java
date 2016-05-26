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

import org.w3c.dom.Text;

import dutest.omdb.R;
import dutest.omdb.constant.Constant;
import dutest.omdb.model.json.Movie;
import dutest.omdb.util.image.DownloadImageTask;

/**
 * Created by Pedreduardo on 25/05/2016.
 */
public class MovieProfile extends AppCompatActivity{

    private Movie movie;
    private Context context;
    private AppBarLayout appBar;
    private CollapsingToolbarLayout collapsing;
    private Toolbar toolbar;

    private ImageView bannerImage;
    private ImageView posterImage;
    private TextView year;
    private TextView rated;
    private TextView genre;
    private TextView plot;
    private TextView imdbRating;
    private TextView imdbVotes;
    private TextView metaScore;
    private TextView awards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movie_profile);
        this.context = getApplicationContext();

        initComponents();
        getExtras();
        populateProfile();
    }

    private void initComponents() {
        this.appBar = (AppBarLayout) findViewById(R.id.appBar);
        this.collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.bannerImage = (ImageView) findViewById(R.id.bannerImage);
        this.posterImage = (ImageView) findViewById(R.id.posterImage);
        this.year = (TextView) findViewById(R.id.year);
        this.rated = (TextView) findViewById(R.id.rated);
        this.genre = (TextView) findViewById(R.id.genre);
        this.plot = (TextView) findViewById(R.id.plot);
        this.imdbRating = (TextView) findViewById(R.id.imdbRating);
        this.imdbVotes = (TextView) findViewById(R.id.imdbVotes);
        this.metaScore = (TextView) findViewById(R.id.metaScore);
        this.awards = (TextView) findViewById(R.id.awards);

        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getExtras() {
            this.movie = (Movie) getIntent().getSerializableExtra("movie");
    }

    private void populateProfile() {

        this.collapsing.setTitle(this.movie.getTitle());
        new DownloadImageTask(this.bannerImage).execute(this.movie.getPoster());
        new DownloadImageTask(this.posterImage).execute(this.movie.getPoster());
        this.year.setText(this.movie.getYear());
        this.rated.setText(this.movie.getRated());
        this.genre.setText(this.movie.getGenre().replace(","," |"));
        this.plot.setText(this.movie.getPlot());
        this.imdbRating.setText(this.movie.getImdbRating() + "/10");
        this.imdbVotes.setText(this.movie.getImdbVotes() + " "  + getResources().getString(R.string.votes));
        this.metaScore.setText(this.movie.getMetascore());
        this.awards.setText(this.movie.getAwards());
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
