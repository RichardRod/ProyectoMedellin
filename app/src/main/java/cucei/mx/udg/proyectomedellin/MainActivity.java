package cucei.mx.udg.proyectomedellin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * @autor: Los dos barbaros
 */

public class MainActivity extends AppCompatActivity {

    private String url = "http://mandt.com.mx/mecanix/getDataClienteJson.php";
    private String urlBarbara = "http://floval.mx/RedUdg/index.php?control=twitter&accion=obtenerNoticias";

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("URL", news.getUrlImage());
                bundle.putString("TEXT", news.getContenido());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /*final StringRequest request = new StringRequest(urlBarbara, new Response.Listener<String>() {
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
        Volley.newRequestQueue(getApplicationContext()).add(request);*/

        obtenerNoticiasAsyn(urlBarbara);
    }

    public ArrayAdapter<String> obtenerNoticias(String response){
        Object objectJson = JSONValue.parse(response);
        JSONArray jsonArray = (JSONArray)objectJson;
        String image_url = "";

        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject rowJson = (JSONObject)jsonArray.get(i);
            JSONObject object = (JSONObject)rowJson.get("entities");
            if(object.containsKey("media")){
                JSONArray temp_json = (JSONArray) object.get("media");
                JSONObject temp = (JSONObject) temp_json.get(0);
                image_url = temp.get("media_url").toString();
            }
            noticias.add(new News(image_url,rowJson.get("text").toString()));
        }

        ArrayAdapter adapter = new NewsArrayAdapter(getApplicationContext(), noticias);
        return adapter;
    }

    private void obtenerNoticiasAsyn(String ruta){
        class Peticion extends AsyncTask<String, Void, String>{

            String result = "";

            @Override
            protected String doInBackground(String... params) {

                String ruta = params[0];

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(ruta);

                try {
                    HttpResponse response = client.execute(post);
                    result = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s.length() == 0){
                    Toast.makeText(getApplicationContext(), "Error al cargar datos", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter = obtenerNoticias(s);
                    listView.setAdapter(adapter);
                }
            }
        }

        Peticion peticion = new Peticion();
        peticion.execute(ruta);
    }
}