package de.kingwarin.homestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class OpenHeizungActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_heizung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addHeizung(View view) {
        Intent intent = new Intent(this, AddHeizungActivity.class);
        startActivity(intent);
    }

    public void showHeizung(View view) {
        Intent intent = new Intent(this, ShowHeizungActivity.class);
        startActivity(intent);
    }
}
