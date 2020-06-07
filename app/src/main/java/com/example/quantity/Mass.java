package com.example.quantity;

import android.widget.Spinner;

public class Mass extends Quantity
{
    public Mass (double normalizedMagnitude)
    {
        super(normalizedMagnitude);
    }

    public Mass ()
    {
        super(0);
    }

    public Mass (double magnitude, Unit unit) throws InstantiationError
    {
        super(magnitude, unit);
        if (!(unit instanceof Units))
            throw new InstantiationError("Mass object cannot be instantiated for non mass units");
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
        KILOGRAM(1, "Kilogram (kg)"),
        GRAM(1e-3, "Gram (g)"),
        MILLIGRAM(1e-6, "Milligram(mg)");

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
