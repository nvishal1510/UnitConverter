package com.example.quantity;

import android.widget.Spinner;

public class Area extends Quantity
{
    public static final Area area = new Area();

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
        super.populateSpinner(spinner, Units.values());
    }

    /**
     * Enumerates the units for Area
     */
    public enum Units implements Unit
    {
        SQKILOMETRE(Math.pow(Length.Units.KILOMETRE.getNormalizationFactor(), 2), "Sq. Kilometre"),
        SQDECIMETRE(Math.pow(Length.Units.DECIMETRE.getNormalizationFactor(), 2), "Sq. Decimetre"),
        SQMETRE(Math.pow(Length.Units.METRE.getNormalizationFactor(), 2), "Sq. Metre"),
        SQCENTIMETRE(Math.pow(Length.Units.CENTIMETRE.getNormalizationFactor(), 2), "Sq. Centimetre"),
        SQMILLIMETRE(Math.pow(Length.Units.MILLIMETRE.getNormalizationFactor(), 2), "Sq. Millimetre"),
        SQMICROMETRE(Math.pow(Length.Units.MICROMETRE.getNormalizationFactor(), 2), "Sq. Micrometre"),
        SQNANOMETRE(Math.pow(Length.Units.NANOMETRE.getNormalizationFactor(), 2), "Nanometre (nm)"),
        SQFEET(Math.pow(Length.Units.FEET.getNormalizationFactor(), 2), "Sq. Feet"),
        SQMILE(Math.pow(Length.Units.MILE.getNormalizationFactor(), 2), "Sq. Mile"),
        SQNAUTICALMILE(Math.pow(Length.Units.NAUTICALMILE.getNormalizationFactor(), 2), "Sq. Nautical Mile"),
        SQYARD(Math.pow(Length.Units.YARD.getNormalizationFactor(), 2), "Sq. Yard");

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
