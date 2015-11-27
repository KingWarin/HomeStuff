package de.kingwarin.homestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowHeizungActivity extends ActionBarActivity {
    private String heater_data;

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
        setContentView(R.layout.activity_show_heizung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
