package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantity.Length;
import com.example.quantity.Mass;
import com.example.quantity.Quantity;
import com.example.quantity.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * This maps Strings with static Quantity objects
     */
    private static final Map<String, Quantity> quantityMap = new HashMap<>();

    static
    {
        quantityMap.put("Length", Length.length);
        quantityMap.put("Mass", Mass.mass);
    }

    private Spinner quantityType;
    private Spinner unit1Type;
    private Spinner unit2Type;
    private EditText unit1Magnitude;
    private EditText unit2Magnitude;
    private Quantity focusedQuantity;
    private Unit focusedUnit1;
    private Unit focusedUnit2;

    /**
     * This should made true when editing text due to internal functions. This is to prevent entering into recursion
     * of calling each other
     */
    private boolean IamEditingText = false;

    private TextWatcher textWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
            if (IamEditingText) return;

            if (s.hashCode() == unit1Magnitude.getText().hashCode())
                setETMagnitude(unit1Magnitude, (Unit) unit1Type.getSelectedItem(),
                        unit2Magnitude, (Unit) unit2Type.getSelectedItem());
            else
                setETMagnitude(unit2Magnitude, (Unit) unit2Type.getSelectedItem(),
                        unit1Magnitude, (Unit) unit1Type.getSelectedItem());
        }

        @Override
        public void afterTextChanged (Editable s)
        {

        }
    };

    private OnItemSelectedListener unitTypeListener = new OnItemSelectedListener()
    {
        /**
         * This changes the magnitude of the Unit which is not clicked, i.e, if Unit1Type is clicked, magnitude at
         * unit2Magnitude is changed
         */
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
        {
            Log.d(LOG_TAG, "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position " +
                    "= [" + position + "], id = [" + id + "]");
            if (parent.getId() == unit1Type.getId())
            {
                setETMagnitude(unit1Magnitude, (Unit) unit1Type.getSelectedItem(),
                        unit2Magnitude, (Unit) unit2Type.getSelectedItem());

            }
            else
            {
                setETMagnitude(unit2Magnitude, (Unit) unit2Type.getSelectedItem(),
                        unit1Magnitude, (Unit) unit1Type.getSelectedItem());
            }
        }

        @Override
        public void onNothingSelected (AdapterView<?> parent)
        {

        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityType = findViewById(R.id.quantityTypeSpinner);
//        quantityType.setOnItemSelectedListener();
        populateQuantityTypeSpinner();
        focusedQuantity = quantityMap.get(quantityType.getSelectedItem());

        unit1Type = findViewById(R.id.unit1Spinner);
        unit2Type = findViewById(R.id.unit2Spinner);
        unit1Type.setOnItemSelectedListener(unitTypeListener);
        unit2Type.setOnItemSelectedListener(unitTypeListener);
        focusedQuantity.populateSpinner(unit1Type);
        focusedUnit1 = (Unit) unit1Type.getSelectedItem();
        focusedQuantity.populateSpinner(unit2Type);
        focusedUnit2 = (Unit) unit2Type.getSelectedItem();

        unit1Magnitude = findViewById(R.id.unit1Magnitude);
        unit2Magnitude = findViewById(R.id.unit2Magnitude);

        unit1Magnitude.addTextChangedListener(textWatcher);
        unit2Magnitude.addTextChangedListener(textWatcher);
    }


    /**
     * Extracts magnitude of the unit from getMagnitudeET, calculates the Magnitude of the other unit and sets text
     * to it
     *
     * @param getMagnitudeET The EditText from which magnitude is extracted, it is used as reference to
     *                       set magnitude for other unit
     * @param setMagnitudeET The EditText which is changed according to the magnitude in getMagnitudeET
     */
    private void setETMagnitude (EditText getMagnitudeET, Unit getMagnitudeETUnit, EditText setMagnitudeET,
                                 Unit setMagnitudeETUnit)
    {
        Log.d(LOG_TAG,
                "setETMagnitude() called with: getMagnitudeET = [" + getMagnitudeET + "], setMagnitudeET = [" + setMagnitudeET + "]");
        double magnitude = 0;
        try
        {
            magnitude = Double.parseDouble(getMagnitudeET.getText().toString());
        }
        catch (NumberFormatException ignored)
        {
        }

        focusedQuantity.setMagnitude(magnitude, focusedUnit1);
        double otherUnitMagnitude = focusedQuantity.getMagnitude(focusedUnit2);
        IamEditingText = true;
        unit2Magnitude.setText(String.format(Locale.ENGLISH, "%.4f", otherUnitMagnitude));
        IamEditingText = false;
    }

    private void populateQuantityTypeSpinner ()
    {
        Log.d(LOG_TAG, "populateQuantityTypeSpinner() called");

        ArrayList<String> quantityList = new ArrayList<>(quantityMap.keySet());
        ArrayAdapter<String> quantityTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, quantityList);
        quantityType.setAdapter(quantityTypeArrayAdapter);
        Log.d(LOG_TAG, "onCreate: quantityTypeSpinner populated");
    }


}
