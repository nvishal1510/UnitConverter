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
            {
                Log.d(LOG_TAG, "onTextChanged() called with: s = [" + s + "] on EditText = " +
                        "Unit1Magnitude");
                setETMagnitude(unit1Magnitude, (Unit) unit1Type.getSelectedItem(),
                        unit2Magnitude, (Unit) unit2Type.getSelectedItem());
            }
            else
            {
                Log.d(LOG_TAG, "onTextChanged() called with: s = [" + s + "] on EditText = " +
                        "Unit2Magnitude");
                setETMagnitude(unit2Magnitude, (Unit) unit2Type.getSelectedItem(),
                        unit1Magnitude, (Unit) unit1Type.getSelectedItem());
            }
        }

        @Override
        public void afterTextChanged (Editable s)
        {
            if (IamEditingText) return;
            Log.d(LOG_TAG, "afterTextChanged() called with: s = [" + s + "]");
            if (s.toString().equals("")) return;

            String formattedNumber = getFormattedNumber(s.toString());

            Log.d(LOG_TAG, "afterTextChanged: formattedNumber = [" + formattedNumber + "]");
            IamEditingText = true;
            EditText editedET = (s.hashCode() == unit1Magnitude.getText().hashCode()) ?
                    unit1Magnitude : unit2Magnitude;
            editedET.setText(formattedNumber);
            editedET.setSelection(formattedNumber.length());
            IamEditingText = false;
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
            Log.d(LOG_TAG, "onItemSelected() called with: parent = [" + parent + "]");
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
            Log.d(LOG_TAG, "onItemSelected() called with: parent = [" + parent + "]");
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

    /**
     * Formats the number according to numberFormat
     * @param s number to be formatted
     * @return Formatted Number
     */
    private String getFormattedNumber (String s)
    {
        String formatRemovedStr = s.replace(",", "");
        Log.d(LOG_TAG, "getFormattedNumber() called with: s = [" + s + "]");
        String formattedNumber = "";
        try
        {
            formattedNumber = numberFormat.format(Double.parseDouble(formatRemovedStr));
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        //this avoids removal of "." at the end of number
        if (s.endsWith(".")) return formattedNumber + ".";
        return formattedNumber;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        Log.d(LOG_TAG, "onCreate() called ");
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
        Log.d(LOG_TAG,
                "updateFocusedQuantity() called" + "selectedObject = [" + quantityType.getSelectedItem() + "]");
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
        String formatRemovedStr = getMagnitudeET.getText().toString().replace(",", "");
        Log.d(LOG_TAG, "setETMagnitude: formatRemovedStr = [" + formatRemovedStr + "]");
        try
        {
            magnitude = Double.parseDouble(formatRemovedStr);
        }
        catch (NumberFormatException e)
        {
            if (!formatRemovedStr.equals("")) e.printStackTrace();
        }

        focusedQuantity.setMagnitude(magnitude, getMagnitudeETUnit);
        double otherUnitMagnitude = focusedQuantity.getMagnitude(setMagnitudeETUnit);
        IamEditingText = true;
        setMagnitudeET.setText(numberFormat.format(otherUnitMagnitude));
        IamEditingText = false;
    }

    private void initializeMagnitudeET ()
    {
        Log.d(LOG_TAG, "initializeMagnitudeET() called");
        unit1Magnitude = findViewById(R.id.unit1Magnitude);
        unit2Magnitude = findViewById(R.id.unit2Magnitude);
        resetEditTexts();
        unit1Magnitude.addTextChangedListener(textWatcher);
        unit2Magnitude.addTextChangedListener(textWatcher);
    }

    private void initializeUnitSpinners ()
    {
        Log.d(LOG_TAG, "initializeUnitSpinners() called");
        unit1Type = findViewById(R.id.unit1Spinner);
        unit2Type = findViewById(R.id.unit2Spinner);
        unit1Type.setOnItemSelectedListener(unitTypeListener);
        unit2Type.setOnItemSelectedListener(unitTypeListener);
        focusedQuantity.populateSpinner(unit1Type);
        focusedQuantity.populateSpinner(unit2Type);
    }

    private void initializeQuantitySpinner ()
    {
        Log.d(LOG_TAG, "initializeQuantitySpinner() called");
        quantityType = findViewById(R.id.quantityTypeSpinner);
        quantityType.setOnItemSelectedListener(quantityTypeListener);
        populateQuantityTypeSpinner();
        updateFocusedQuantity();
    }

    private void populateQuantityTypeSpinner ()
    {
        Log.d(LOG_TAG, "populateQuantityTypeSpinner() called");

        ArrayAdapter<Quantities> quantityTypeArrayAdapter = new ArrayAdapter<>(this,
                R.layout.custom_spinner_item, Quantities.values());
        quantityType.setAdapter(quantityTypeArrayAdapter);
        Log.d(LOG_TAG, "onCreate: quantityTypeSpinner populated");
    }

    /**
     * Sets the values of the two editText to 0
     */
    private void resetEditTexts ()
    {
        Log.d(LOG_TAG, "resetEditTexts() called");
        IamEditingText = true;
        unit1Magnitude.setText(numberFormat.format(0));
        unit2Magnitude.setText(numberFormat.format(0));
        IamEditingText = false;
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
            Log.d(LOG_TAG, "Quantities.getQuantityObject() called");
            return quantityObject;
        }

        @Override
        public String toString ()
        {
            return friendlyName;
        }
    }


}
