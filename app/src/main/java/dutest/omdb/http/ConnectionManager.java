package dutest.omdb.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pedreduardo on 24/05/2016.
 */
public class ConnectionManager{

    //Atributos
    //----------------------------------------------------------------------------------------------
    private HttpURLConnection httpConn;
    private String method;
    private String urlString;
    private URL url;
    //----------------------------------------------------------------------------------------------

    public ConnectionManager(String urlString, String method){

        try {
            this.setMethod(method);
            this.setUrlString(urlString);
            this.setUrl(this.urlString);
            this.setHttpConn((HttpURLConnection) this.url.openConnection());    //Abre conexão
            this.httpConn.setUseCaches(false);                                  //Sem controle de cache
            this.httpConn.setReadTimeout(10000);                                //(ms)
            this.httpConn.setConnectTimeout(15000);                             //(ms)
            this.httpConn.setRequestMethod(this.method);
            this.httpConn.setDoInput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Realiza a conexão com o endpoint
     */
    public void connect(){
        try {
            this.httpConn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fecha uma conexão com o endpoint
     */
    public void disconnect() {
        if (this.httpConn != null) {
            this.httpConn.disconnect();
        }
    }


    //GETs e SETs
    // -------------------------------------------------------------------------------
    public HttpURLConnection getHttpConn() {
        return httpConn;
    }
    public void setHttpConn(HttpURLConnection httpConn) {
        this.httpConn = httpConn;
    }
    public URL getUrl() {
        return url;
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getUrlString() {
        return urlString;
    }
    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    // -------------------------------------------------------------------------------

}