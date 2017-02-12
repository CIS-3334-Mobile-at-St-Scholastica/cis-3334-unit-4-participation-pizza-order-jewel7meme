package css.cis3334.pizzaorder;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements updateViewInterface {

    RadioButton rbSmall;
    RadioButton rbMedium;
    RadioButton rbLarge;
    CheckBox chkbxCheese;
    CheckBox chkbxDelivery;
    TextView txtTotal;
    TextView txtStatus;
    PizzaOrderInterface pizzaOrderSystem;

    Spinner spinner;
    String[] toppings;
    String sTopping;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbSmall = (RadioButton) findViewById(R.id.radioButtonSmall);
        rbMedium = (RadioButton) findViewById(R.id.radioButtonMedium);
        rbLarge = (RadioButton) findViewById(R.id.radioButtonLarge);

        chkbxCheese = (CheckBox) findViewById(R.id.checkBoxCheese);
        chkbxDelivery = (CheckBox) findViewById(R.id.checkBoxDeluvery);

        txtTotal = (TextView) findViewById(R.id.textViewTotal);
        txtStatus = (TextView) findViewById(R.id.textViewStatus);

        spinner = (Spinner) findViewById(R.id.spinner);
        toppings = getResources().getStringArray(R.array.toppings);

        pizzaOrderSystem = new PizzaOrder(this);


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toppings) {

            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                    tv.setTextColor(Color.GRAY);
                else
                    tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                //On selecting a spinner item
                sTopping = adapter.getItemAtPosition(position).toString();
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    // Showing selected spinner item
                    Toast.makeText(getApplicationContext(), "Selected Topping: " + sTopping, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



            public void updateView(String orderStatus) {
                txtStatus.setText("Order Status: " + orderStatus);
            }

            public void onClickOrder(View view) {
                String size = "";
                if (rbSmall.isChecked()) {
                    size = "small";
                }
                if (rbMedium.isChecked()) {
                    size = "medium";
                }
                if (rbLarge.isChecked()) {
                    size = "large";
                }
                String orderDescription = pizzaOrderSystem.OrderPizza(sTopping, size, false);
                //display a pop up message for a long period of time
                Toast.makeText(getApplicationContext(), "You have ordered a " + orderDescription, Toast.LENGTH_LONG).show();
                txtTotal.setText("Total Due: " + pizzaOrderSystem.getTotalBill().toString());
                pizzaOrderSystem.setDelivery(chkbxDelivery.isChecked());
            }
        }
