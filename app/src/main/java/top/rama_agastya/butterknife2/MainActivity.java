package top.rama_agastya.butterknife2;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.rama_agastya.butterknife2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editName)
    EditText edtText;

    @BindView(R.id.textviewName)
    TextView tvName;

    @BindColor(R.color.colorPrimary)
    int color;

    String yourName;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Simple Project");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setYourName(yourName);

        ButterKnife.bind(this);

        tvName.setTextColor(color);
    }

    @OnClick(R.id.buttonName)
    public void doProcess() {
        tvName.setText("Your name is " + edtText.getText());
    }

    @OnClick(R.id.btnRV)
    public void goRecyleView() {
        startActivity(new Intent(MainActivity.this, RecyleView.class));
    }

    @OnClick(R.id.btnJson)
    public void goJson() {
        startActivity(new Intent(MainActivity.this, JsonParsing.class));
    }

}
