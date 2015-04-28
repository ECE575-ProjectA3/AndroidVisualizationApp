package com.ncsu.wireless.cellularcoverage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateForm extends ActionBarActivity {

    private String fromDate;
    private String toDate;
    private String var_carrier;
    private String var_parameter;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_form);

        // Get the variables passed from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            var_carrier = extras.getString("var_carrier");
            var_parameter = extras.getString("signal_parameter");
        }

        findViewById(R.id.btnDisplay).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get the reference ID of toDate and fromDate text fields
                EditText fromDateText = (EditText) findViewById(R.id.editText_fromDate);
                EditText toDateText = (EditText) findViewById(R.id.editText_toDate);

                fromDate = fromDateText.getText().toString();
                toDate = toDateText.getText().toString();

                // validate Date using the isValidDate function. If valid proceed to the next activity
                if (!(isValidDate(fromDate))) {
                    fromDateText.setError("Invalid Date");
                } else if (!(isValidDate(toDate)))  {
                    toDateText.setError("Invalid Date");
                } else {
                    // Call parseInput function to form the http URL
                    parseInput();
                    /// Create Intent for MapDisplay Activity and Start The Activity.
                    /// Also pass the carrier selected to the next activity in a variable
                    Intent new_intent = new Intent(DateForm.this,MapDisplay.class);
                    new_intent.putExtra("url", url);
                    new_intent.putExtra("var_carrier", var_carrier);
                    new_intent.putExtra("var_parameter", var_parameter);
                    startActivity(new_intent);
                }
            }
        });
    }

    public void parseInput() {
        // This function forms the http url based on carrier, parameter and date values
        url = "http://ece575a3.ddns.net:8080/";
        url += "request?carrier="+var_carrier + "&type="+var_parameter;
        //only include non-empty parameters
        if(!(fromDate.equals(""))) {
            url += "&minDate="+fromDate;
        }
        if(!(toDate.equals(""))) {
            url += "&maxDate="+toDate;
        }
    }

    public static boolean isValidDate(String text) {
        // This function validates the date passed as string. Referenced from stackoverflow.com
        if (text.equals(""))
            return true;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date_form, menu);
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
