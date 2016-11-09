package top.rama_agastya.butterknife2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SingleItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        TextView txtWil = (TextView) findViewById(R.id.viewWil);

        Intent i = getIntent();

        String wil = i.getStringExtra("wil");

        txtWil.setText(wil);
    }
}
