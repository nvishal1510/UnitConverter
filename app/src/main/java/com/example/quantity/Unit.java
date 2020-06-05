package com.example.quantity;

public interface Unit
{
    /**
     * Normalization factor times magnitude gives the normalized value i.e, terms of SI units
     *
     * @return the normalizationFactor
     */
    public double getNormalizationFactor ();
}
