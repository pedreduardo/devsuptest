package dutest.omdb.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(ContextCompat.getColor(this.context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        this.collapsing.setTitle("Harry Potter and the Deathly Hallows");
        this.collapsing.setCollapsedTitleTextColor(ContextCompat.getColor(this.context, R.color.white));
        this.collapsing.setExpandedTitleColor(ContextCompat.getColor(this.context, R.color.white));

        new DownloadImageTask(this.imageView).execute("http://ia.media-imdb.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg");
        new DownloadImageTask(this.posterImage).execute("http://ia.media-imdb.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg");


    }


}
