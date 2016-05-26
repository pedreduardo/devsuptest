package dutest.omdb.util.http;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedreduardo on 24/05/2016.
 */
public class HttpUtil{

    private static Gson gson = new Gson();

    /**
     *
     * @param json
     * @param T
     * @return Retorna um objeto populado com os elementos json.
     * @throws JSONException
     *
     */
    public static Object fromJson(String json, Class T) throws JSONException {

        Object jsonToken = json.isEmpty() ? new JSONObject() : new JSONTokener(json).nextValue();

        if (jsonToken instanceof JSONObject) {
            return gson.fromJson(json, T);
        } else if (jsonToken instanceof JSONArray) {
            JSONArray jsonArray = new JSONArray(json);
            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                objects.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), T));
            }
            return objects;
        }
        return null;
    }
    
    /**
     * Leitura das respostas do servidor
     * @return String
     * @throws IOException
     */
    public static String readResponse(HttpURLConnection httpConn) throws IOException {
        InputStream inputStream;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));

        String response = reader.readLine();
        reader.close();

        return response;
    }
}