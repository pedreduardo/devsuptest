package dutest.omdb.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Pedreduardo on 25/05/2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImageTask(ImageView bmImage) {
        this.imageView = bmImage;
    }

    /**
     * Retorna a imagem a partir de uma URL
     * @param urls
     * @return
     */
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {;
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
