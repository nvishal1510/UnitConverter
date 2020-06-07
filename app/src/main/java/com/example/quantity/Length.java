package com.example.quantity;

import android.widget.Spinner;

public class Length extends Quantity
{
    public Length (double normalizedMagnitude)
    {
        super(normalizedMagnitude);
    }

    public Length ()
    {
        super(0);
    }

    public Length (double magnitude, Unit unit) throws InstantiationError
    {
        super(magnitude, unit);
        if (!(unit instanceof Units))
            throw new InstantiationError("Length object cannot be instantiated for non length " +
                    "units");
    }

    @Override
    public double getMagnitude (Unit unit) throws NoSuchMethodError
    {
        if (!(unit instanceof Units))
            throw new NoSuchMethodError("One quantity cannot be converted to unit of other " +
                    "quantity");
        return super.getMagnitude(unit);
    }

    @Override
    public void setMagnitude (double magnitude, Unit unit) throws NoSuchMethodError
    {
        if (!(unit instanceof Units))
            throw new NoSuchMethodError("One quantity cannot be converted to unit of other " +
                    "quantity");
        super.setMagnitude(magnitude, unit);
    }


    @Override
    public void populateSpinner (Spinner spinner)
    {
        super.populateSpinner(spinner, Units.values());
    }

    /**
     * Enumerates the units for Length
     */
    public enum Units implements Unit
    {
        METRE(1, "Metre (m)"),
        KILOMETRE(1e3, "Kilometre (km)"),
        CENTIMETRE(1e-2, "Centimetre (cm)"),
        MILLIMETRE(1e-3, "Millimetre (mm)"),
        MICROMETRE(1e-6, "Micrometre (um)");

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
