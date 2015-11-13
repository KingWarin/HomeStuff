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
        intent.putExtra(KUCHE, kuche_count);
        EditText szi = (EditText) findViewById(R.id.szi);
        String szi_count = szi.getText().toString();
        intent.putExtra(SZI, szi_count);
        EditText bad = (EditText) findViewById(R.id.bad);
        String bad_count = bad.getText().toString();
        intent.putExtra(BAD, bad_count);
        EditText wozi = (EditText) findViewById(R.id.wozi);
        String wozi_count = wozi.getText().toString();
        intent.putExtra(WOZI, wozi_count);
//        startActivity(intent);

        String stringUrl = "http://www.kingwarin.de/homestuff/service.php?type=heater";

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            kuche.setText("No network connection available.");
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
            kuche.setText(result);
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                // Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }
}