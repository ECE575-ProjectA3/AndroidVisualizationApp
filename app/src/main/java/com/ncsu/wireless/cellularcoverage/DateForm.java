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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            var_carrier = extras.getString("var_carrier");
            var_parameter = extras.getString("signal_parameter");
        }

        findViewById(R.id.btnDisplay).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditText fromDateText = (EditText) findViewById(R.id.editText_fromDate);
                EditText toDateText = (EditText) findViewById(R.id.editText_toDate);

                fromDate = fromDateText.getText().toString();
                toDate = toDateText.getText().toString();

                // validate Date
                if (!(isValidDate(fromDate))) {
                    fromDateText.setError("Invalid Date");
                } else if (!(isValidDate(toDate)))  {
                    toDateText.setError("Invalid Date");
                } else {
                    parseInput();
                    Intent new_intent = new Intent(DateForm.this,MapDisplay.class);
                    System.out.println("The URL: "+url);
                    new_intent.putExtra("url", url);
                    new_intent.putExtra("var_carrier", var_carrier);
                    new_intent.putExtra("var_parameter", var_parameter);
                    startActivity(new_intent);
                }
            }
        });
    }

    public void parseInput() {
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
