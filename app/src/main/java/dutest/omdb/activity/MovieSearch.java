package dutest.omdb.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dutest.omdb.R;
import dutest.omdb.http.HttpGet;
import dutest.omdb.http.OnFinishHttpTask;
import dutest.omdb.model.json.Movie;
import dutest.omdb.util.Util;
import dutest.omdb.util.http.HttpUtil;
import dutest.omdb.util.movie.MovieUtil;

/**
 * Created by Pedreduardo on 26/05/2016.
 */
public class MovieSearch extends AppCompatActivity implements View.OnClickListener, OnFinishHttpTask {

    private Context context;
    private EditText movieName;
    private FloatingActionButton searchButton;
    private ProgressDialog progressDialog;
    private Button myMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movie_search);

        this.context = getApplicationContext();

        initComponents();
    }


    /**
     * Inicialização das views da activity
     */
    private void initComponents() {
        this.movieName = (EditText) findViewById(R.id.movieText);
        this.searchButton = (FloatingActionButton) findViewById(R.id.search);
        this.searchButton.setOnClickListener(this);
        this.myMovies = (Button) findViewById(R.id.myMovies);
        this.myMovies.setOnClickListener(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getResources().getString(R.string.loadingMovieData));
    }


    @Override
    public void onClick(View v) {
        if(v == this.searchButton){

            if(Util.isOnline(this.context)) {
                if (this.movieName.getText().toString().isEmpty()) {
                    Toast.makeText(this, getResources().getString(R.string.InsertMovie),
                            Toast.LENGTH_LONG).show();
                } else {
                    MovieUtil.browseMovie(this.movieName.getText().toString(), this, this.progressDialog);
                }
            }
        }
        else if(v == this.myMovies){
            Intent it = new Intent(MovieSearch.this, MyMovies.class);
            startActivity(it);
        }
    }

    @Override
    public void onFinishTask(Object result, String method) {

        Movie movie = (Movie) result;
        this.progressDialog.dismiss();
        if(movie != null){
            if(movie.getResponse().equals("False")){
                Toast.makeText(this, getResources().getString(R.string.movieNotFound),
                        Toast.LENGTH_LONG).show();
            }
            else{
                Intent it = new Intent(MovieSearch.this, MovieProfile.class);
                it.putExtra("movie", movie);


                if(MovieUtil.findMovieByTitle(movie.getTitle()) == null){
                    movie.save();
                }

                startActivity(it);
            }
        }
    }
}
