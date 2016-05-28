package dutest.omdb.adapter;

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
import dutest.omdb.model.json.Movie;
import dutest.omdb.util.image.DownloadImageTask;
import dutest.omdb.util.movie.MovieUtil;

/**
 * Created by Pedreduardo on 27/05/2016.
 */
public class MyMoviesAdapter extends BaseAdapter{

    private ArrayList<Movie> movies;
    private Context context;
    private static LayoutInflater inflater = null;

    public MyMoviesAdapter(MyMovies myMovies, ArrayList<Movie> movies) {
        this.context = myMovies;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movies = movies;
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
        TextView movieTitle;    //Nome do filme
        TextView moviePlot;     //Sinopse
        ImageView moviePoster;  //Imagem do filme
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

        //Exibe o filme pesquisado na activity do perfil
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Movie> movies = MovieUtil.findMovieByTitle(holder.movieTitle.getText().toString());
                Movie movieSelected = movies.get(0);

                Intent intent = new Intent(MyMoviesAdapter.this.context, MovieProfile.class);
                intent.putExtra("movie", movieSelected);
                MyMoviesAdapter.this.context.startActivity(intent);

            }
        });
        return rowView;
    }



}
