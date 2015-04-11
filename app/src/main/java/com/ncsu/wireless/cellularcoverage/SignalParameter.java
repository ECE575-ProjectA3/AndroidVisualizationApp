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
import android.widget.Toast;


public class SignalParameter extends ActionBarActivity {

    private String var_carrier;
    private RadioGroup radioSignalGroup;
    private RadioButton radioSignalButton;
    private Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_parameter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            var_carrier = extras.getString("var_carrier");
        }

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioSignalGroup = (RadioGroup) findViewById(R.id.radioParameters);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioSignalGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSignalButton = (RadioButton) findViewById(selectedId);
                String radioSignalButtonValue = (String) radioSignalButton.getText();

                Intent intent = new Intent(SignalParameter.this, MapDisplay.class);
                intent.putExtra("var_carrier", var_carrier);
                intent.putExtra("signal_parameter", radioSignalButtonValue);
                startActivity(intent);


//                Toast.makeText(SignalParameter.this,radioSignalButton.getText(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signal_parameter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
