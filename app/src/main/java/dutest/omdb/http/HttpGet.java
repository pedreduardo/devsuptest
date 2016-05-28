package dutest.omdb.http;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import dutest.omdb.constant.Constant;
import dutest.omdb.util.http.HttpUtil;

/**
 * Created by Pedreduardo on 24/05/2016.
 */
public class HttpGet extends AsyncTask<Void, Void, String> {

    private ProgressDialog progressDialog;  //Diálogo de progresso para exibição durante requisição
    private String url;                     //URL destino
    private OnFinishHttpTask onFinishTask;  //responsável por capturar o retorno da requisição.
    private String response = "";
    private Object responseObject;          //objeto de resposta da requisição
    private Class T;                        //Classe de casting da resposta
    private ConnectionManager conn;

    public HttpGet(OnFinishHttpTask onFinishTask, ProgressDialog progressDialog, String url, Class T) {
        super();
        this.onFinishTask = onFinishTask;
        this.progressDialog = progressDialog;
        this.url = url;
        this.T = T;
    }

    @Override protected void onPreExecute(){
        if (this.progressDialog != null) {
            this.progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(Void... voids){
        try {

            //Abre uma conexão
            this.conn = new ConnectionManager(this.url, Constant.GET);
            //Realiza a conexão
            this.conn.connect();
            //Obtém a resposta
            this.response = HttpUtil.readResponse(this.conn.getHttpConn());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response){

        try {
            this.responseObject = HttpUtil.fromJson(this.response, this.T);
            this.conn.disconnect();
            this.onFinishTask.onFinishTask(this.responseObject, Constant.GET);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}