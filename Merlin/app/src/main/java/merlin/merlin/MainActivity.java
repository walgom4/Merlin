package merlin.merlin;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import merlin.merlin.adapter.ListAdapter;
import merlin.merlin.database.Merlin_DB;
import merlin.merlin.objects.element;
import merlin.merlin.objects.object;
import merlin.merlin.objects.persistence_object;

public class MainActivity extends ListActivity {

    private TextView mTv;
    private object obj;
    private List<element> listElements;
    private ProgressDialog progDailog;

    private persistence_object persistenceObject;
    private Merlin_DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Merlin_DB.getDatabase(getApplicationContext());
        progDailog = new ProgressDialog(MainActivity.this);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        listElements= new ArrayList<element>();
        this.obj=(object) getApplicationContext();
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            Toast toast= Toast.makeText(this, "conected, online version of the data",Toast.LENGTH_LONG);
            toast.show();
            database.daoObject().removeAllObjects();
            new Load().execute("");
        }else{
            Toast toast= Toast.makeText(this, "not Internet access, local version of the data",Toast.LENGTH_LONG);
            toast.show();
            List<persistence_object> persistence_objects=database.daoObject().getAllObjects();
            for (int i=0; i<persistence_objects.size();i++){
                element myElement= new element();
                try {
                    myElement.setJsonObject(new JSONObject(persistence_objects.get(i).json));
                    if(persistence_objects.get(i).img!=null)
                        myElement.setImg(BitmapFactory.decodeByteArray(persistence_objects.get(i).img, 0, persistence_objects.get(i).img.length));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listElements.add(myElement);
            }
            this.obj.setElements(listElements);
            setListAdapter(new ListAdapter(MainActivity.this, listElements));
        }
    }

    private void convertirJSON(JSONObject jsonObject){
        try{
            JSONObject obj= jsonObject.getJSONObject("data");

            JSONArray children= obj.getJSONArray("children");

            for (int i=0; i<children.length();i++){
                element myElement= new element();
                JSONObject unique= children.getJSONObject(i);
                myElement.setJsonObject(unique);
                JSONObject data= new JSONObject(unique.getString("data"));
                byte[] databitmap=null;
                try {
                    InputStream in = new java.net.URL(data.getString("icon_img")).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    myElement.setImg(bitmap);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    databitmap = outputStream.toByteArray();
                }catch (MalformedURLException error){
                    Log.e("error", error.toString());
                }catch (IOException ex){
                    Log.e("error", ex.toString());
                }
                persistenceObject = new persistence_object(unique.toString(), databitmap);
                database.daoObject().addObject(persistenceObject);
                listElements.add(myElement);

            }
            this.obj.setElements(listElements);
        }catch (JSONException error){
            Log.e("error", error.toString());
        }
    }
    class Load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        @Override
        protected String doInBackground(String... aurl) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url ="https://www.reddit.com/reddits.json";

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            convertirJSON(response);
                            setListAdapter(new ListAdapter(MainActivity.this, listElements));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(stringRequest);
            return null;
        }
        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            final boolean active = true;
            final int maxTime = 3000; // timeout 10s
            final int ctrlTime = 1000; // control time, 100ms
            Thread splashThread = new Thread(){
                @Override
                public void run() {
                    try {
                        int waited = 0;
                        while (active && (waited < maxTime)){
                            sleep(ctrlTime);
                            if (active){
                                waited += ctrlTime;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        progDailog.dismiss();
                    }
                }
            };
            splashThread.start();

        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        element selectedValue = (element) getListAdapter().getItem(position);
        Toast.makeText(MainActivity.this, "pos:"+position, Toast.LENGTH_SHORT).show();
        this.obj.setElement(selectedValue);
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }
}

