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
import android.widget.Toast;

import java.util.ArrayList;


public class CarrierList extends ActionBarActivity {

    ArrayList<String> carrierNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_list);

        // Get the reference of ListViewAnimals
        ListView carrierList=(ListView)findViewById(R.id.list_Carriers);


        carrierNameList = new ArrayList<String>();
        getCarrierNames();
        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, carrierNameList);
        // Set The Adapter
        carrierList.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        carrierList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Intent intent = new Intent(CarrierList.this, SignalParameter.class);
                String selectedCarrier = carrierNameList.get(position);
                intent.putExtra("var_carrier", selectedCarrier);
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), "Carrier Selected : " + selectedCarrier, Toast.LENGTH_LONG).show();
            }
        });
    }

    void getCarrierNames()
    {
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
