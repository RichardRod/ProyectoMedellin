package cucei.mx.udg.proyectomedellin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

/*
 * @autor: Los dos barbaros
 */

public class MainActivity extends AppCompatActivity {

    private String url = "http://mandt.com.mx/mecanix/getDataClienteJson.php";
    private String urlBarbara = "http://floval.mx/Barbaro/index.php?control=twitter&accion=obtenerNoticias";

    private ListView listView;
    private String url1 = "http://jsonplaceholder.typicode.com/posts";
    private String CONSUMER_KEY = "MYSIMkR490FwRFi3F43omgCQn";
    private String CONSUMER_SECRET = "0Kq0Pw92jGxsgrZVO9aNSy4r0LwSKudufdeLggLxHLvX1gMaiY";
    private String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    private String TwitterStreamURL = "https://api.twitter.com/1.1/search/tweets.json?";
    List<String> list = new ArrayList<String>();
    private ArrayAdapter adapter;

    List<News> noticias = new ArrayList<News>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        final StringRequest request = new StringRequest(urlBarbara, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("JSON", response.toString());
                adapter = obtenerNoticias(response);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", error.getMessage());
            }
        });
        //AppController.getInstance().addToRequestQueue(request);
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    public ArrayAdapter<String> obtenerNoticias(String response){
        Object objectJson = JSONValue.parse(response);
        JSONArray jsonArray = (JSONArray)objectJson;

        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject rowJson = (JSONObject)jsonArray.get(i);
            noticias.add(new News("",rowJson.get("text").toString()));
        }

        ArrayAdapter adapter = new NewsArrayAdapter(getApplicationContext(), noticias);
        return adapter;
    }
}