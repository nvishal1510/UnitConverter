package com.example.quantity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public abstract class Quantity
{
    private static final String LOG_TAG = Quantity.class.getSimpleName();

    /**
     * normalizedMagnitude represent magnitude of a quantity in SI units
     */
    private double normalizedMagnitude;


    protected Quantity (double normalizedMagnitude)
    {
        this.normalizedMagnitude = normalizedMagnitude;
    }

    /**
     * normalizedMagnitude represent magnitude of a quantity in SI units
     *
     * @return the normalizedMagnitude
     */
    public double getNormalizedMagnitude ()
    {
        Log.d(LOG_TAG, "getNormalizedMagnitude() called");
        return normalizedMagnitude;
    }

    public void setNormalizedMagnitude (double normalizedMagnitude)
    {
        Log.d(LOG_TAG,
                "setNormalizedMagnitude() called with: normalizedMagnitude = [" + normalizedMagnitude + "]");
        this.normalizedMagnitude = normalizedMagnitude;
    }

    /**
     * Returns the magnitude of the quantity in given unit
     */
    public double getMagnitude (Unit unit)
    {
        Log.d(LOG_TAG, "getMagnitude() called with: unit = [" + unit + "]");
        return getNormalizedMagnitude() / unit.getNormalizationFactor();
    }

    /**
     * Set magnitude of the Quantity using Unit unit
     */
    public void setMagnitude (double magnitude, Unit unit)
    {
        Log.d(LOG_TAG,
                "setMagnitude() called with: magnitude = [" + magnitude + "], unit = [" + unit + "]");
        this.normalizedMagnitude = magnitude * unit.getNormalizationFactor();
    }

    public abstract void populateSpinner (Spinner spinner);

    protected void populateSpinner (Spinner spinner, Unit[] values)
    {
        ArrayAdapter<Unit> unitsArrayAdapter = new ArrayAdapter<>(spinner.getContext(),
                android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(unitsArrayAdapter);
    }

}
