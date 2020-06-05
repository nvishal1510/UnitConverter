package com.example.quantity.mass;

import com.example.quantity.Unit;

public enum MassUnits implements Unit
{
    KILOGRAM(1), GRAM(1e-3), MILLIGRAM(1e-6);

    /**
     * Normalization factor times magnitude gives the normalized value i.e, terms of SI units
     */
    private double normalizationFactor;

    private MassUnits (double normalizationFactor)
    {
        this.normalizationFactor = normalizationFactor;
    }

    public double getNormalizationFactor ()
    {
        return normalizationFactor;
    }
}
