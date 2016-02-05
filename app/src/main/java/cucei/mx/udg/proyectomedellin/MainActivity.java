package cucei.mx.udg.proyectomedellin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
 * @autor: Los dos barbaros
 */

public class MainActivity extends AppCompatActivity {

    private String url = "http://mandt.com.mx/mecanix/getDataClienteJson.php";
    private String urlBarbara = "http://mandt.com.mx/Barbaro/";

    private ListView listView;
    private String url1 = "http://jsonplaceholder.typicode.com/posts";
    private String CONSUMER_KEY = "MYSIMkR490FwRFi3F43omgCQn";
    private String CONSUMER_SECRET = "0Kq0Pw92jGxsgrZVO9aNSy4r0LwSKudufdeLggLxHLvX1gMaiY";
    private String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    private String TwitterStreamURL = "https://api.twitter.com/1.1/search/tweets.json?";
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        //JsonResponse jsonResponse = new JsonResponse();
        //jsonResponse.execute();

        /*JsonArrayRequest request = new JsonArrayRequest(urlBarbara, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.v("JSON GENERADO: ", response.toString());

                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        list.add(object.getString("id")+ ": " + object.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
            }
        });
        */
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBarbara, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {//
                    Object object = JSONValue.parse(response);
                    org.json.simple.JSONArray array = (org.json.simple.JSONArray) object;
                    for (int i = 0; i < array.size(); i++) {
                        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) array.get(i);
                        //list.add(jsonObject.get("numcliente").toString() + ": "
                        //+ jsonObject.get("nombre").toString() + " " + jsonObject.get("apellidos"));
                        list.add(jsonObject.get("text").toString());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                }
                catch (NullPointerException nullpointer){
                    Log.v("Erro","ESTA VACIO");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //AppController.getInstance().addToRequestQueue(request);
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /*
    public ArrayAdapter fillListViewJson(String json){

        if(json.equals("") || json == null){
            Toast.makeText(getApplicationContext(), "ANDA VACIO ESTO", Toast.LENGTH_LONG).show();
            return null;
        }
        else {
            //json = json.substring(37);
            Object object = JSONValue.parse(json);
            org.json.simple.JSONArray array = (org.json.simple.JSONArray) object;

            for (int i = 0; i < array.size(); i++) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) array.get(i);
                //list.add(jsonObject.get("numcliente").toString() + ": "
                //+ jsonObject.get("nombre").toString() + " " + jsonObject.get("apellidos"));
                list.add(jsonObject.get("text").toString());
            }
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            return adapter;
        }
    }

    /*public String getTwitterStream(String request){

        String results = null;

        try{

            String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");

            String userCredentials = urlApiKey + ":" + urlApiSecret;

            String base64Encoded = Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);

            URL url = null;
            url = new URL(TwitterTokenURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic " + Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Content-Length", + Integer.toString(request.getBytes().length()));
        }
        catch (UnsupportedEncodingException excetionEncoding){}
        catch (IllegalStateException ilegal){} catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }*/


    /*private class JsonResponse extends AsyncTask<String, Void, String>{

        ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params) {

            String response = null;
            Hermes conection = new Hermes();
            try {
                response = conection.talkToOlimpus("Barbaro", "clave=roja");
                return response;
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MainActivity.this, "Cargando...", null, true, true );
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            listView.setAdapter(fillListViewJson(s));
        }
    }*/
}
