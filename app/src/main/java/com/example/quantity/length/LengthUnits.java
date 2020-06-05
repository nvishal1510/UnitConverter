package com.example.quantity.length;

import com.example.quantity.Unit;

public enum LengthUnits implements Unit
{
    METRE(1), KILOMETRE(1e3), CENTIMETRE(1e-2),
    MILLIMETRE(1e-3), MICROMETRE(1e-6);

    /**
     * Normalization factor times magnitude gives the normalized value i.e, terms of SI units
     */
    private double normalizationFactor;

    private LengthUnits (double normalizationFactor)
    {
        this.normalizationFactor = normalizationFactor;
    }

    public double getNormalizationFactor ()
    {
        return normalizationFactor;
    }
}

