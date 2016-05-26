package dutest.omdb.model.json;

import java.io.Serializable;

/**
 * Created by Pedreduardo on 26/05/2016.
 */
public class Movie implements Serializable {

    String Response;
    String Title;
    String Plot;
    String Poster;
    String imdbRating;
    String imdbVotes;
    String Metascore;
    String Awards;
    String Year;
    String Rated;
    String Genre;


    public String getResponse() {
        return Response;
    }
    public void setResponse(String response) {
        this.Response = response;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getPlot() {
        return Plot;
    }
    public void setPlot(String plot) {
        Plot = plot;
    }
    public String getPoster() {
        return Poster;
    }
    public void setPoster(String poster) {
        Poster = poster;
    }
    public String getImdbRating() {
        return imdbRating;
    }
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
    public String getImdbVotes() {
        return imdbVotes;
    }
    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }
    public String getMetascore() {
        return Metascore;
    }
    public void setMetascore(String metascore) {
        Metascore = metascore;
    }
    public String getAwards() {
        return Awards;
    }
    public void setAwards(String awards) {
        Awards = awards;
    }
    public String getYear() {
        return Year;
    }
    public void setYear(String year) {
        Year = year;
    }
    public String getRated() {
        return Rated;
    }
    public void setRated(String rated) {
        Rated = rated;
    }
    public String getGenre() {
        return Genre;
    }
    public void setGenre(String genre) {
        Genre = genre;
    }

}
