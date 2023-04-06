package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {
    private Button correct, incorrect;
    private TextView ecuatie;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        ecuatie = (TextView)findViewById(R.id.ecuatie);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("ecuatie")) {
            ecuatie.setText(intent.getStringExtra("ecuatie"));
        }

        correct = (Button)findViewById(R.id.ok_button);
        correct.setOnClickListener(buttonClickListener);
        incorrect = (Button)findViewById(R.id.cancel_button);
        incorrect.setOnClickListener(buttonClickListener);
    }
}
