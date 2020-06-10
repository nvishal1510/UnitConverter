package com.example.quantity;

import android.util.Log;
import android.widget.Spinner;

public class Length extends Quantity
{
    public static final Length length = new Length();

    private static final String LOG_TAG = Length.class.getSimpleName();

    private Length ()
    {
        super(0);
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
        Log.d(LOG_TAG, "populateSpinner() called with: spinner = [" + spinner + "]");
        super.populateSpinner(spinner, Units.values());
    }

    /**
     * Enumerates the units for Length
     */
    public enum Units implements Unit
    {
        KILOMETRE(1e3, "Kilometre (km)"),
        DECIMETRE(10, "Decimetre (dm)"),
        METRE(1, "Metre (m)"),
        CENTIMETRE(1e-2, "Centimetre (cm)"),
        MILLIMETRE(1e-3, "Millimetre (mm)"),
        MICROMETRE(1e-6, "Micrometre (\u00B5m)"),
        NANOMETRE(1e-9, "Nanometre (nm)"),
        FEET(0.3048, "Feet (ft)"),
        MILE(1609.344,"Mile (mi)"),
        NAUTICAL_MILE(1852, "Nautical Mile (nmi)"),
        YARD(0.9144, "Yard (yd)");


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
