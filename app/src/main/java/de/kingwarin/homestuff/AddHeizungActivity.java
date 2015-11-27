package de.kingwarin.homestuff;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class AddHeizungActivity extends ActionBarActivity {
    public final static String KUCHE = "stuff";
    public final static String SZI = "stuff";
    public final static String BAD = "stuff";
    public final static String WOZI = "stuff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_heizung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void pushEntries(View view) {
        Intent intent = new Intent(this, PushEntriesActivity.class);
        EditText kuche = (EditText) findViewById(R.id.kuche);
        String kuche_count = kuche.getText().toString();
        EditText szi = (EditText) findViewById(R.id.szi);
        String szi_count = szi.getText().toString();
        EditText bad = (EditText) findViewById(R.id.bad);
        String bad_count = bad.getText().toString();
        EditText wozi = (EditText) findViewById(R.id.wozi);
        String wozi_count = wozi.getText().toString();
        CheckBox on = (CheckBox) findViewById(R.id.on);
        boolean on_val = on.isChecked();

        JSONObject json_data = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_data.put("bath", bad_count);
            json_data.put("kitchen", kuche_count);
            json_data.put("bed", szi_count);
            json_data.put("living", wozi_count);
            if (on_val) {
                json_data.put("on", new Integer(1));
            }
            else {
                json_data.put("on", new Integer(0));
            }
            json.put("type", "new_heat_date");
            json.put("heat_data", json_data);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        new PerformPost().execute(json_data.toString());
    }

    private void post_data(String... json_data) {
        HttpURLConnection conn = null;
        Intent intent;
        try {
            String data = "type=" + URLEncoder.encode("new_heat_date", "UTF-8");
            data += "&heat_data=" + URLEncoder.encode(json_data[0], "UTF-8");
            URL url = new URL("http://www.kingwarin.de/homestuff/service.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setFixedLengthStreamingMode(data.getBytes().length);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();
            String response = "";
            Scanner inStream = new Scanner(conn.getInputStream());
            while(inStream.hasNextLine()) {
                response += (inStream.nextLine());
            }
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            intent = new Intent(this, HomeScreen.class);
            conn.disconnect();
            startActivity(intent);
        }
    }

    class PerformPost extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... json_data) {
            try {
                post_data(json_data);
                return "Yes";
            }
            catch (Exception e) {
                this.exception = e;
                return "No";
            }
        }
    }

}
