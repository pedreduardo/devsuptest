package dutest.omdb.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dutest.omdb.R;
import dutest.omdb.activity.MovieProfile;
import dutest.omdb.activity.MyMovies;
import dutest.omdb.http.HttpGet;
import dutest.omdb.http.OnFinishHttpTask;
import dutest.omdb.model.json.Movie;
import dutest.omdb.util.http.HttpUtil;
import dutest.omdb.util.image.DownloadImageTask;

/**
 * Created by Pedreduardo on 27/05/2016.
 */
public class MyMoviesAdapter extends BaseAdapter implements OnFinishHttpTask{

    private ArrayList<Movie> movies;
    private Context context;
    private static LayoutInflater inflater = null;
    private ProgressDialog progressDialog;

    public MyMoviesAdapter(MyMovies myMovies, ArrayList<Movie> movies) {
        this.context = myMovies;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movies = movies;
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage(this.context.getResources().getString(R.string.loadingMovieData));
    }

    @Override
    public int getCount() {
        return this.movies.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView movieTitle;
        TextView moviePlot;
        ImageView moviePoster;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.lv_movie_search, null);

        holder.movieTitle = (TextView) rowView.findViewById(R.id.movieTitle);
        holder.moviePlot = (TextView) rowView.findViewById(R.id.moviePlot);
        holder.moviePoster = (ImageView) rowView.findViewById(R.id.moviePoster);

        holder.movieTitle.setText(this.movies.get(position).getTitle());
        holder.moviePlot.setText(this.movies.get(position).getPlot());
        new DownloadImageTask(holder.moviePoster).execute(this.movies.get(position).getPoster());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseMovie(holder.movieTitle.getText().toString());

            }
        });
        return rowView;
    }

    public void browseMovie(String movieName) {
        String finalURL = HttpUtil.standardizeUrlTerm(movieName);
        new HttpGet(this, this.progressDialog, finalURL, Movie.class).execute();
    }

    @Override
    public void onFinishTask(Object result, String method) {

        Movie chosenMovie = (Movie) result;
        this.progressDialog.dismiss();
        if(chosenMovie != null){
                Intent intent= new Intent(MyMoviesAdapter.this.context, MovieProfile.class);
                intent.putExtra("movie", chosenMovie);
                MyMoviesAdapter.this.context.startActivity(intent);
        }
    }

}
