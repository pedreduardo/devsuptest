package dutest.omdb.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dutest.omdb.R;
import dutest.omdb.http.OnFinishHttpTask;

/**
 * Created by Pedreduardo on 26/05/2016.
 */
public class MovieSearch extends AppCompatActivity implements View.OnClickListener, OnFinishHttpTask {

    Context context;
    EditText movieName;
    FloatingActionButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movie_search);
        this.context = getApplicationContext();

        initComponents();
    }

    private void initComponents() {
        this.movieName = (EditText) findViewById(R.id.movieText);
        this.searchButton = (FloatingActionButton) findViewById(R.id.search);
        this.searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == this.searchButton){
            if(this.movieName.getText().toString().isEmpty()){
                Toast.makeText(this, getResources().getString(R.string.InsertMovie),
                        Toast.LENGTH_LONG).show();
            }
            else{
                //Intent it = new Intent(MovieSearch.this, MovieProfile.class);
                //it.putExtra("movieName", this.movieName.getText().toString());
                //startActivity(it);
            }
        }
    }

    @Override
    public void onFinishTask(Object result, String method) {
    }
}
