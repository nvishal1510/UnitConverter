package com.example.quantity;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

public abstract class Quantity
{

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
        return normalizedMagnitude;
    }

    public void setNormalizedMagnitude (double normalizedMagnitude)
    {
        this.normalizedMagnitude = normalizedMagnitude;
    }

    /**
     * Returns the magnitude of the quantity in given unit
     */
    public double getMagnitude (Unit unit)
    {
        return getNormalizedMagnitude() / unit.getNormalizationFactor();
    }

    /**
     * Set magnitude of the Quantity using Unit unit
     */
    public void setMagnitude (double magnitude, Unit unit)
    {
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
