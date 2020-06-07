package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantity.Length;
import com.example.quantity.Mass;
import com.example.quantity.Quantity;
import com.example.quantity.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{
    private static final Map<String, Quantity> quantityMap = new HashMap<>();

    static
    {
        quantityMap.put("Length", new Length());
        quantityMap.put("Mass", new Mass());
    }

    private Spinner quantityType;
    private Spinner unit1Type;
    private Spinner unit2Type;
    private EditText unit1Magnitude;
    private EditText unit2Magnitude;

    private Quantity focusedQuantity;
    private Unit focusedUnit1;
    private Unit focusedUnit2;


    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateQuantityTypeSpinner();

        unit1Type = findViewById(R.id.unit1Spinner);
        unit2Type = findViewById(R.id.unit2Spinner);

        unit1Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
            {
                focusedUnit1 = (Unit) parent.getItemAtPosition(position);
                double magnitude = Double.parseDouble(unit1Magnitude.getText().toString());
                focusedQuantity.setMagnitude(magnitude, focusedUnit1);
                double otherUnitMagnitude = focusedQuantity.getMagnitude((Unit) focusedUnit2);
                unit2Magnitude.setText(Double.toString(otherUnitMagnitude));
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent)
            {

            }
        });

        unit2Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
            {
                focusedUnit2 = (Unit) parent.getItemAtPosition(position);
                double magnitude = Double.parseDouble(unit2Magnitude.getText().toString());
                focusedQuantity.setMagnitude(magnitude, focusedUnit2);
                double otherUnitMagnitude = focusedQuantity.getMagnitude((Unit) focusedUnit1);
                unit1Magnitude.setText(Double.toString(otherUnitMagnitude));
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent)
            {

            }
        });


        focusedQuantity = quantityMap.get(quantityType.getSelectedItem());
        try
        {
            focusedQuantity.populateSpinner(unit1Type);
            focusedUnit1 = (Unit) unit1Type.getSelectedItem();
            focusedQuantity.populateSpinner(unit2Type);
            focusedUnit2 = (Unit) unit2Type.getSelectedItem();
        }
        catch (NullPointerException ignored)
        {
        }


        unit1Magnitude = findViewById(R.id.unit1Magnitude);
        unit2Magnitude = findViewById(R.id.unit2Magnitude);

        unit1Magnitude.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count)
            {
                double magnitude = Double.parseDouble(unit1Magnitude.getText().toString());
                focusedQuantity.setMagnitude(magnitude, focusedUnit1);
                double otherUnitMagnitude = focusedQuantity.getMagnitude((Unit) focusedUnit2);
                unit2Magnitude.setText(Double.toString(otherUnitMagnitude));
            }

            @Override
            public void afterTextChanged (Editable s)
            {

            }
        });

        unit2Magnitude.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count)
            {
                double magnitude = Double.parseDouble(unit2Magnitude.getText().toString());
                focusedQuantity.setMagnitude(magnitude, focusedUnit2);
                double otherUnitMagnitude = focusedQuantity.getMagnitude((Unit) focusedUnit1);
                unit1Magnitude.setText(Double.toString(otherUnitMagnitude));
            }

            @Override
            public void afterTextChanged (Editable s)
            {

            }
        });


    }


    private void populateQuantityTypeSpinner ()
    {
        quantityType = findViewById(R.id.quantityTypeSpinner);
        ArrayList<String> quantityList = new ArrayList<>(quantityMap.keySet());
        ArrayAdapter<String> quantityTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, quantityList);
        quantityType.setAdapter(quantityTypeArrayAdapter);
    }


}
