package dutest.omdb.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dutest.omdb.R;
import dutest.omdb.constant.Constant;
import dutest.omdb.http.HttpGet;
import dutest.omdb.http.OnFinishHttpTask;
import dutest.omdb.model.json.Movie;
import dutest.omdb.util.http.HttpUtil;
import dutest.omdb.util.image.DownloadImageTask;

/**
 * Created by Pedreduardo on 25/05/2016.
 */
public class MovieProfile extends AppCompatActivity implements View.OnClickListener, OnFinishHttpTask{

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
    private Button amazonButton;

    private ProgressDialog progressDialog;

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
        this.amazonButton = (Button) findViewById(R.id.amazonButton);

        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getResources().getString(R.string.loadingMovieData));

    }

    private void getExtras() {
            this.movie = (Movie) getIntent().getSerializableExtra("movie");
    }

    private void populateProfile() {

        this.collapsing.setTitle(this.movie.getTitle());
        this.collapsing.setTitleEnabled(true);
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

        this.amazonButton.setOnClickListener(this);
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
                browseMovie(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View v) {
        if(v == this.amazonButton){
            String amazonSearch = Constant.AMAZON_SEARCH;
            String query = this.movie.getTitle().replace(' ', '+');
            amazonSearch += query + Constant.AM_DEFAULT_PARAMS;

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(amazonSearch));
            startActivity(browserIntent);
        }
    }

    public void browseMovie(String movieName) {
        String finalURL = HttpUtil.standardizeUrlTerm(movieName);
        new HttpGet(this, this.progressDialog, finalURL, Movie.class).execute();
    }

    @Override
    public void onFinishTask(Object result, String method) {

        this.movie = (Movie) result;
        this.progressDialog.dismiss();
        if(movie != null){
            if(movie.getResponse().equals("False")){
                Toast.makeText(this, getResources().getString(R.string.movieNotFound),
                        Toast.LENGTH_LONG).show();
            }
            else{
                Intent it = new Intent(MovieProfile.this, MovieProfile.class);
                it.putExtra("movie", this.movie);
                finish();
                startActivity(it);
            }
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
