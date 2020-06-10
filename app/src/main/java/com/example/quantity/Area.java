package com.example.quantity;

import android.util.Log;
import android.widget.Spinner;

public class Area extends Quantity
{
    public static final Area area = new Area();

    private static final String LOG_TAG = Area.class.getSimpleName();

    private Area ()
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
     * Enumerates the units for Area
     */
    public enum Units implements Unit
    {
        SQ_KILOMETRE(Math.pow(Length.Units.KILOMETRE.getNormalizationFactor(), 2), "Sq. Kilometre"),
        SQ_DECIMETRE(Math.pow(Length.Units.DECIMETRE.getNormalizationFactor(), 2), "Sq. Decimetre"),
        SQ_METRE(Math.pow(Length.Units.METRE.getNormalizationFactor(), 2), "Sq. Metre"),
        SQ_CENTIMETRE(Math.pow(Length.Units.CENTIMETRE.getNormalizationFactor(), 2), "Sq. Centimetre"),
        SQ_MILLIMETRE(Math.pow(Length.Units.MILLIMETRE.getNormalizationFactor(), 2), "Sq. Millimetre"),
        SQ_FEET(Math.pow(Length.Units.FEET.getNormalizationFactor(), 2), "Sq. Feet"),
        SQ_MILE(Math.pow(Length.Units.MILE.getNormalizationFactor(), 2), "Sq. Mile"),
        SQ_YARD(Math.pow(Length.Units.YARD.getNormalizationFactor(), 2), "Sq. Yard");

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
