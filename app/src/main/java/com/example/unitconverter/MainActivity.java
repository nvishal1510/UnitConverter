package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantity.Area;
import com.example.quantity.Length;
import com.example.quantity.Mass;
import com.example.quantity.Quantity;
import com.example.quantity.Unit;
import com.example.quantity.Volume;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Formatter used for formatting numbers in ET magnitude
     */
    private final NumberFormat numberFormat = new DecimalFormat("##,##,##,##,###.###########");
    private Spinner quantityType;
    private Spinner unit1Type;
    private Spinner unit2Type;
    private EditText unit1Magnitude;
    private EditText unit2Magnitude;
    private Quantity focusedQuantity;

    /**
     * This should made true when editing text due to internal functions. This is to prevent
     * entering into recursion of calling each other
     */
    private boolean IamEditingText = false;

    /**
     * This object to attached magnitude editTexts to respond to change in text
     */
    private final TextWatcher textWatcher = new TextWatcher()
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

    /**
     * This object is attached to unitType spinners as OnItemSelectedListener
     * When an item is selected in any unitType spinner, this changes the Unit 2 Magnitude
     * according to units after the change
     */
    private final OnItemSelectedListener unitTypeListener = new OnItemSelectedListener()
    {
        /**
         * This changes the magnitude of the unit 2 regardless of whether unit 1 type is changed
         * or unit 2 type is changed
         */
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
        {
            Log.d(LOG_TAG,
                    "onItemSelected() called with: parent = [" + parent + "], view = [" + view +
                            "], position " + "= [" + position + "], id = [" + id + "]");
            setETMagnitude(unit1Magnitude, (Unit) unit1Type.getSelectedItem(),
                    unit2Magnitude, (Unit) unit2Type.getSelectedItem());
        }

        @Override
        public void onNothingSelected (AdapterView<?> parent)
        {
        }
    };

    /**
     * This object is attached to quantityType spinner as OnItemSelectedListener
     */
    private final OnItemSelectedListener quantityTypeListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
        {
            updateFocusedQuantity();
            focusedQuantity.populateSpinner(unit1Type);
            focusedQuantity.populateSpinner(unit2Type);
            resetEditTexts();
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

        initializeQuantitySpinner();
        initializeUnitSpinners();
        initializeMagnitudeET();
    }

    /**
     * Updates focusedQuantity variable using quantityMap
     */
    private void updateFocusedQuantity ()
    {
        Quantities selectedObject = (Quantities) quantityType.getSelectedItem();
        focusedQuantity = selectedObject.getQuantityObject();
    }

    /**
     * Extracts magnitude of the unit from getMagnitudeET, calculates the Magnitude of the other
     * unit and sets text to it
     *
     * @param getMagnitudeET     The EditText from which magnitude is extracted, it is used as
     *                           reference to set magnitude for other unit
     * @param getMagnitudeETUnit The unit of getMagnitudeET
     * @param setMagnitudeET     The EditText which is changed according to the magnitude in
     *                           getMagnitudeET
     * @param setMagnitudeETUnit The unit of setMagnitudeET
     */
    private void setETMagnitude (EditText getMagnitudeET, Unit getMagnitudeETUnit,
                                 EditText setMagnitudeET, Unit setMagnitudeETUnit)
    {
        Log.d(LOG_TAG, "setETMagnitude() called with: getMagnitudeET = [" + getMagnitudeET + "], "
                + "setMagnitudeET = [" + setMagnitudeET + "]");
        double magnitude = 0;
        try
        {
            magnitude = Double.parseDouble(getMagnitudeET.getText().toString());
        }
        catch (NumberFormatException ignored)
        {
        }

        focusedQuantity.setMagnitude(magnitude, getMagnitudeETUnit);
        double otherUnitMagnitude = focusedQuantity.getMagnitude(setMagnitudeETUnit);
        IamEditingText = true;
        setMagnitudeET.setText(numberFormat.format(otherUnitMagnitude));
        IamEditingText = false;
    }

    private void initializeMagnitudeET ()
    {
        unit1Magnitude = findViewById(R.id.unit1Magnitude);
        unit2Magnitude = findViewById(R.id.unit2Magnitude);
        resetEditTexts();
        unit1Magnitude.addTextChangedListener(textWatcher);
        unit2Magnitude.addTextChangedListener(textWatcher);
    }

    private void initializeUnitSpinners ()
    {
        unit1Type = findViewById(R.id.unit1Spinner);
        unit2Type = findViewById(R.id.unit2Spinner);
        unit1Type.setOnItemSelectedListener(unitTypeListener);
        unit2Type.setOnItemSelectedListener(unitTypeListener);
        focusedQuantity.populateSpinner(unit1Type);
        focusedQuantity.populateSpinner(unit2Type);
    }

    private void initializeQuantitySpinner ()
    {
        quantityType = findViewById(R.id.quantityTypeSpinner);
        quantityType.setOnItemSelectedListener(quantityTypeListener);
        populateQuantityTypeSpinner();
        updateFocusedQuantity();
    }

    /**
     * Sets the values of the two editText to 0
     */
    private void resetEditTexts ()
    {
        IamEditingText = true;
        unit1Magnitude.setText(numberFormat.format(0));
        unit2Magnitude.setText(numberFormat.format(0));
        IamEditingText = false;
    }

    private void populateQuantityTypeSpinner ()
    {
        Log.d(LOG_TAG, "populateQuantityTypeSpinner() called");

        ArrayAdapter<Quantities> quantityTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Quantities.values());
        quantityType.setAdapter(quantityTypeArrayAdapter);
        Log.d(LOG_TAG, "onCreate: quantityTypeSpinner populated");
    }

    /**
     * This maps Strings with static Quantity objects
     */
    private enum Quantities
    {
        LENGTH(Length.length, "Length"),
        AREA(Area.area, "Area"),
        MASS(Mass.mass, "Mass"),
        VOLUME(Volume.volume, "Volume");

        private final Quantity quantityObject;
        private final String friendlyName;

        Quantities (Quantity quantityObject, String friendlyName)
        {
            this.quantityObject = quantityObject;
            this.friendlyName = friendlyName;
        }

        public Quantity getQuantityObject ()
        {
            return quantityObject;
        }

        @Override
        public String toString ()
        {
            return friendlyName;
        }
    }


}
