package com.example.quantity;

public abstract class Quantity
{

    /**
     * normalizedMagnitude represent magnitude of a quantity in SI units
     */
    private double normalizedMagnitude;


    public Quantity (double normalizedMagnitude)
    {
        this.normalizedMagnitude = normalizedMagnitude;
    }

    public Quantity (double magnitude, Unit unit)
    {
        this(magnitude * unit.getNormalizationFactor());
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

    /**
     * Returns the magnitude of the quantity in given unit
     */
    public double getMagnitude (Unit unit)
    {
        return getNormalizedMagnitude() / unit.getNormalizationFactor();
    }
}
