package com.ncsu.wireless.cellularcoverage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class CarrierList extends ActionBarActivity {

    ArrayList<String> carrierNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_list);

        // Get the reference ID of CarrierListView
        ListView carrierList=(ListView)findViewById(R.id.list_Carriers);

        // populate the carrier list in an array list
        carrierNameList = new ArrayList<String>();
        getCarrierNames();

        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, carrierNameList);
        // Set The Adapter
        carrierList.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        carrierList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                String selectedCarrier;
                if (carrierNameList.get(position).equals("SPRINT")){
                    selectedCarrier = "Sprint";
                } else if (carrierNameList.get(position).equals("VERIZON")){
                    selectedCarrier = "Verison";
                } else if (carrierNameList.get(position).equals("T-MOBILE")){
                    selectedCarrier = "TMobile";
                } else if (carrierNameList.get(position).equals("AT & T")){
                    selectedCarrier = "ATT";
                } else if (carrierNameList.get(position).equals("US CELLULAR")){
                    selectedCarrier = "USCellular";
                } else {
                    selectedCarrier = "testRequest";
                }
                /// Create Intent for SignalParameter Activity and Start The Activity.
                /// Also pass the carrier selected to the next activity in a variable
                Intent intent = new Intent(CarrierList.this, SignalParameter.class);
                intent.putExtra("var_carrier", selectedCarrier);
                startActivity(intent);
            }
        });
    }

    void getCarrierNames() {
        // Function to add the carriers to the arraylist
        carrierNameList.add("TEST REQUEST");
        carrierNameList.add("SPRINT");
        carrierNameList.add("VERIZON");
        carrierNameList.add("T-MOBILE");
        carrierNameList.add("AT & T");
        carrierNameList.add("US CELLULAR");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carrier_list, menu);
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
