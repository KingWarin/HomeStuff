package de.kingwarin.homestuff;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OpenHeizungActivity extends ActionBarActivity {
    private String heater_data;
    private Intent intent;
    public final static String heater_value = "None";

    private TextView createTextView(String desc, String value) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setText(desc + ": " + value);
        return tv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        heater_data = intent.getStringExtra(OpenHeizungActivity.heater_value);
        setContentView(R.layout.activity_open_heizung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Create GrivView instead of linearLayout
        LinearLayout show_view = (LinearLayout) findViewById(R.id.show_view);

        try {
            JSONArray json = new JSONArray(heater_data);
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonObject = json.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String date = jsonObject.optString("date").toString();
                String bath = jsonObject.optString("bath").toString();
                String kitchen = jsonObject.optString("kitchen").toString();
                String bed = jsonObject.optString("bed").toString();
                String living = jsonObject.optString("living").toString();
                String on_s = "Nein";
                int on = Integer.parseInt(jsonObject.optString("is_on").toString());
                if (on == 1) {
                    on_s = "Ja";
                }
                LinearLayout new_line = new LinearLayout(this);
                new_line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                new_line.setOrientation(LinearLayout.HORIZONTAL);
                TextView date_tv = createTextView("Datum", date);
                TextView bath_tv = createTextView("Bad", bath);
                TextView kitchen_tv = createTextView("Küche", bath);
                TextView bed_tv = createTextView("Schlafen", bath);
                TextView living_tv = createTextView("WoZi", bath);
                TextView on_tv = createTextView("Heizung an", on_s);
                new_line.addView(date_tv);
                new_line.addView(bath_tv);
                new_line.addView(kitchen_tv);
                new_line.addView(bed_tv);
                new_line.addView(living_tv);
                new_line.addView(on_tv);
                show_view.addView(new_line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHeizung(View view) {
        intent = new Intent(this, AddHeizungActivity.class);
        startActivity(intent);
    }

    public void showHeizung(View view) {
        intent = new Intent(this, ShowHeizungActivity.class);
        fetchHeaterData();
    }

    public void fetchHeaterData() {
        String stringUrl = "http://www.kingwarin.de/homestuff/service.php?type=heater";
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            heater_data = "No network connection available.";
            intent.putExtra(heater_value, heater_data);
            startActivity(intent);
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            heater_data = result;
            intent.putExtra(heater_value, heater_data);
            startActivity(intent);
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = convertStreamToString(is);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        public String convertStreamToString(InputStream stream) {
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }
}
