package de.kingwarin.homestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

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
        startActivity(intent);
    }
}
