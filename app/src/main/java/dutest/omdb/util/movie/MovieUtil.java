package dutest.omdb.util.movie;

import android.app.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import dutest.omdb.http.HttpGet;
import dutest.omdb.http.OnFinishHttpTask;
import dutest.omdb.model.json.Movie;
import dutest.omdb.util.http.HttpUtil;

/**
 * Created by Pedreduardo on 28/05/2016.
 */
public class MovieUtil {

    /**
     * Busca de um título de um filme
     * @param movieName Nome do filme
     * @param requisitor classe que implementa OnFinishHttpTask para resposta da resuisição
     * @param pd ProgressDialog para exibição na tela
     */
    public static void browseMovie(String movieName, OnFinishHttpTask requisitor, ProgressDialog pd){
        String finalURL = HttpUtil.standardizeUrlTerm(movieName);
        new HttpGet(requisitor, pd, finalURL, Movie.class).execute();
    }

    public static List<Movie> findMovieByTitle(String movieTitle){
        List<Movie> movies = new ArrayList<Movie>();
        movies = Movie.find(Movie.class, "Title = ?", movieTitle);
        if(movies == null || movies.isEmpty()){
            return null;
        }
        return movies;
    }
}
