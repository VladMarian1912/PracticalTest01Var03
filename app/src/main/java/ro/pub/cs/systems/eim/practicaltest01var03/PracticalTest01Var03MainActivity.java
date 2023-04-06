package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private Button plus;
    private Button minus;
    private Button navigateToSecondaryActivityButton;
    private EditText firstTerm;
    private EditText secondTerm;
    private EditText ecuatie;

    private int serviceStatus = Constants.SERVICE_STARTED;

    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("main", intent.getStringExtra("message"));
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int nr1 = 0, nr2 = 0, suma;
            String t1;
            String t2;

            switch(view.getId()) {
                case R.id.plus:
                    t1 = firstTerm.getText().toString();
                    try {
                        nr1 = Integer.parseInt(t1);
                    } catch (NumberFormatException e) {
                        Toast.makeText(PracticalTest01Var03MainActivity.this, "The string is not a valid integer", Toast.LENGTH_SHORT).show();
                    }

                    t2 = secondTerm.getText().toString();
                    try {
                        nr2 = Integer.parseInt(t2);
                    } catch (NumberFormatException e) {
                        Toast.makeText(PracticalTest01Var03MainActivity.this, "The string is not a valid integer", Toast.LENGTH_SHORT).show();
                    }
                    suma = nr1 + nr2;
                    ecuatie.setText(t1 + "+" + t2 + "=" + suma);

                    Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent1.putExtra(Constants.FIRST_NUMBER, nr1);
                    intent1.putExtra(Constants.SECOND_NUMBER, nr2);
                    getApplicationContext().startService(intent1);

                    break;
                case R.id.minus:
                    t1 = firstTerm.getText().toString();
                    try {
                        nr1 = Integer.parseInt(t1);
                    } catch (NumberFormatException e) {
                        Toast.makeText(PracticalTest01Var03MainActivity.this, "The string is not a valid integer", Toast.LENGTH_SHORT).show();
                    }

                    t2 = secondTerm.getText().toString();
                    try {
                        nr2 = Integer.parseInt(t2);
                    } catch (NumberFormatException e) {
                        Toast.makeText(PracticalTest01Var03MainActivity.this, "The string is not a valid integer", Toast.LENGTH_SHORT).show();
                    }
                    suma = nr1 - nr2;
                    ecuatie.setText(t1 + "+" + t2 + "=" + suma);

                    Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent2.putExtra(Constants.FIRST_NUMBER, nr1);
                    intent2.putExtra(Constants.SECOND_NUMBER, nr2);
                    getApplicationContext().startService(intent2);

                    break;
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                    intent.putExtra(Constants.ECUATIE, ecuatie.getText().toString());
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        plus = (Button)findViewById(R.id.plus);
        plus.setOnClickListener(buttonClickListener);
        minus = (Button)findViewById(R.id.minus);
        minus.setOnClickListener(buttonClickListener);
        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        firstTerm = (EditText)findViewById(R.id.first_number);
        secondTerm = (EditText)findViewById(R.id.second_number);
        ecuatie = (EditText)findViewById(R.id.ecuatie);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.FIRST_TERM)) {
                firstTerm.setText(savedInstanceState.getString(Constants.FIRST_TERM));
            } else {
                firstTerm.setText("");
            }
            if (savedInstanceState.containsKey(Constants.SECOND_TERM)) {
                secondTerm.setText(savedInstanceState.getString(Constants.SECOND_TERM));
            } else {
                secondTerm.setText("");
            }
            if (savedInstanceState.containsKey(Constants.ECUATIE)) {
                ecuatie.setText(savedInstanceState.getString(Constants.ECUATIE));
            } else {
                ecuatie.setText("");
            }
        }

        intentFilter.addAction("ro.pub.cs.systems.eim.suma");
        intentFilter.addAction("ro.pub.cs.systems.eim.dif");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.FIRST_TERM, firstTerm.getText().toString());
        savedInstanceState.putString(Constants.SECOND_TERM, secondTerm.getText().toString());
        savedInstanceState.putString(Constants.ECUATIE, ecuatie.getText().toString());
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.FIRST_TERM)) {
            firstTerm.setText(savedInstanceState.getString(Constants.FIRST_TERM));
        } else {
            firstTerm.setText("");
        }
        if (savedInstanceState.containsKey(Constants.SECOND_TERM)) {
            secondTerm.setText(savedInstanceState.getString(Constants.SECOND_TERM));
        } else {
            secondTerm.setText("");
        }
        if (savedInstanceState.containsKey(Constants.ECUATIE)) {
            ecuatie.setText(savedInstanceState.getString(Constants.ECUATIE));
        } else {
            ecuatie.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            String code = resultCode == 0 ? "Incorrect" : resultCode == -1 ? "Correct" : "Erorr";
            Toast.makeText(this, "The activity returned with result " + code, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
        getApplicationContext().stopService(intent2);
        Toast.makeText(getApplicationContext(), "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        Log.d("main", "onResume() method was invoked");
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        Log.d("main", "onPause() method was invoked");
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}