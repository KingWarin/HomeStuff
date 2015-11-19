package de.kingwarin.homestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class ShowHeizungActivity extends ActionBarActivity {
    private TextView result_view;
    private String heater_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        heater_data = intent.getStringExtra(OpenHeizungActivity.heater_value);
        setContentView(R.layout.activity_show_heizung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result_view = (TextView) findViewById(R.id.service_results);
        result_view.setText(heater_data);
    }
}
