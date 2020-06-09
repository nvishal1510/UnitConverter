package com.example.quantity;

import android.widget.Spinner;

public class Mass extends Quantity
{
    public static final Mass mass=new Mass();

    private Mass ()
    {
        super(0);
    }

    @Override
    public double getMagnitude (Unit unit) throws NoSuchMethodError
    {
        if (!(unit instanceof Units))
            throw new NoSuchMethodError("Mass quantity cannot be converted to unit of other " +
                    "quantity");
        return super.getMagnitude(unit);
    }

    @Override
    public void setMagnitude (double magnitude, Unit unit) throws NoSuchMethodError
    {
        super.setMagnitude(magnitude, unit);
    }

    @Override
    public void populateSpinner (Spinner spinner)
    {
        super.populateSpinner(spinner, Units.values());
    }


    public enum Units implements Unit
    {
        GRAM(1e-3, "Gram (g)"),
        KILOGRAM(1, "Kilogram (kg)"),
        MILLIGRAM(1e-6, "Milligram(mg)"),
        MICROGRAM(1e-9, "Microgram (\u00B5g)"),
        TONNE(1e3,"Metric Tonne (t)"),
        POUND(0.45359237, "Pound (lb)");

        /**
         * Normalization factor times magnitude gives the normalized value i.e, terms of SI units
         */
        private final double normalizationFactor;
        private final String friendlyName;

        Units (double normalizationFactor, String friendlyName)
        {
            this.normalizationFactor = normalizationFactor;
            this.friendlyName = friendlyName;
        }

        public double getNormalizationFactor ()
        {
            return normalizationFactor;
        }

        @Override
        public String toString ()
        {
            return friendlyName;
        }
    }
}
