package de.kingwarin.homestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class PushEntriesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String bad = intent.getStringExtra(AddHeizungActivity.BAD);
        String kuche = intent.getStringExtra(AddHeizungActivity.KUCHE);
        String wozi = intent.getStringExtra(AddHeizungActivity.WOZI);
        String szi = intent.getStringExtra(AddHeizungActivity.SZI);

        TextView textView1 = new TextView(this);
        textView1.setTextSize(40);
        textView1.setText("Küche:"+kuche+" Bad:"+bad+" Wohnzimmer:"+wozi+" Schlafzimmer:"+szi);
        setContentView(textView1);
    }

}
