package dutest.omdb.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dutest.omdb.R;
import dutest.omdb.model.json.Movie;

/**
 * Created by Pedreduardo on 24/05/2016.
 */
public class Util {

    /**
     * Verifica conexão do usuário. Caso não haja, exibe-se um toast
     * @param c
     * @return
     */
    public static boolean isOnlineWithToast(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
            String text = c.getResources().getString(R.string.verifyConnection);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
            return false;
        }
        return true;
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
