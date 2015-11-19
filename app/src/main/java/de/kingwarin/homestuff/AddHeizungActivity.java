package de.kingwarin.homestuff;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddHeizungActivity extends ActionBarActivity {
    public final static String KUCHE = "stuff";
    public final static String SZI = "stuff";
    public final static String BAD = "stuff";
    public final static String WOZI = "stuff";

    private EditText kuche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_heizung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void pushEntries(View view) {
        Intent intent = new Intent(this, PushEntriesActivity.class);
        kuche = (EditText) findViewById(R.id.kuche);
        String kuche_count = kuche.getText().toString();
        EditText szi = (EditText) findViewById(R.id.szi);
        String szi_count = szi.getText().toString();
        EditText bad = (EditText) findViewById(R.id.bad);
        String bad_count = bad.getText().toString();
        EditText wozi = (EditText) findViewById(R.id.wozi);
        String wozi_count = wozi.getText().toString();

        JSONObject json = new JSONObject();
        try {
            json.put("bath", bad_count);
            json.put("kitchen", kuche_count);
            json.put("bed", szi_count);
            json.put("living", wozi_count);
            json.put("on", new Integer(0));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        post_data(json);
    }

    private void post_data(JSONObject json_data) {
        try {
            Intent intent;
            URL url = new URL("my fancy target url");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.addRequestProperty("type", "new_heat_date");
            conn.addRequestProperty("heat_data", json_data.toString());
            conn.connect();
            int response = conn.getResponseCode();
            if (response == 200) {
                intent = new Intent(this, OpenHeizungActivity.class);
            }
            else {
                intent = new Intent(this, HomeScreen.class);
            }
            startActivity(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
