package com.example.quantity;

import android.util.Log;
import android.widget.Spinner;

public class Volume extends Quantity
{
    public static final Volume volume = new Volume();

    private static final String LOG_TAG = Volume.class.getSimpleName();

    private Volume ()
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
     * Enumerates the units for Volume
     */
    public enum Units implements Unit
    {
        CUBIC_KILOMETRE(Math.pow(Length.Units.KILOMETRE.getNormalizationFactor(), 3), "Cu. Kilometre"),
        CUBIC_DECIMETRE(Math.pow(Length.Units.DECIMETRE.getNormalizationFactor(), 3), "Cu. Decimetre"),
        CUBIC_METRE(Math.pow(Length.Units.METRE.getNormalizationFactor(), 3), "Cu. Metre"),
        CUBIC_CENTIMETRE(Math.pow(Length.Units.CENTIMETRE.getNormalizationFactor(), 3), "Cu. Centimetre"),
        CUBIC_MILLIMETRE(Math.pow(Length.Units.MILLIMETRE.getNormalizationFactor(), 3), "Cu. Millimetre"),
        CUBIC_MICROMETRE(Math.pow(Length.Units.MICROMETRE.getNormalizationFactor(), 3), "Cu. Micrometre"),
        CUBIC_NANOMETRE(Math.pow(Length.Units.NANOMETRE.getNormalizationFactor(), 3), "Nanometre (nm)"),
        CUBIC_FEET(Math.pow(Length.Units.FEET.getNormalizationFactor(), 3), "Cu. Feet"),
        CUBIC_MILE(Math.pow(Length.Units.MILE.getNormalizationFactor(), 3), "Cu. Mile");

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
