package com.ncsu.wireless.cellularcoverage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SignalParameter extends ActionBarActivity {

    private String var_carrier;
    private RadioGroup radioSignalGroup;
    private RadioButton radioSignalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_parameter);

        // Get the variables passed from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            var_carrier = extras.getString("var_carrier");
        }

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        // Get the reference ID of Radio group and the button
        radioSignalGroup = (RadioGroup) findViewById(R.id.radioParameters);
        Button btnDisplay = (Button) findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioSignalGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSignalButton = (RadioButton) findViewById(selectedId);
                String radioSignalButtonValuesStr = (String) radioSignalButton.getText();
                String radioSignalButtonValue = null;
                if (radioSignalButtonValuesStr.equals("Signal Strength")) {
                    radioSignalButtonValue = "signalStrength";
                } else if (radioSignalButtonValuesStr.equals("Download Speed")) {
                    radioSignalButtonValue = "downloadSpeed";
                } else if (radioSignalButtonValuesStr.equals("Upload Speed")) {
                    radioSignalButtonValue = "uploadSpeed";
                }
                /// Create Intent for DateForm Activity and Start The Activity.
                /// Also pass the carrier selected to the next activity in a variable
                Intent intent = new Intent(SignalParameter.this, DateForm.class);
                System.out.println(radioSignalButtonValue);
                intent.putExtra("var_carrier", var_carrier);
                intent.putExtra("signal_parameter", radioSignalButtonValue);
                startActivity(intent);
            }
        }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signal_parameter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically
        // handle clicks, as long as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
