package com.example.quantity.length;


import com.example.quantity.Quantity;
import com.example.quantity.Unit;

public class Length extends Quantity
{
    public Length (double normalizedMagnitude)
    {
        super(normalizedMagnitude);
    }

    public Length (double magnitude, Unit unit) throws InstantiationError
    {
        super(magnitude, unit);
        if (!(unit instanceof LengthUnits))
            throw new InstantiationError("Length object cannot be instantiated for non length units");
    }

    @Override
    public double getMagnitude (Unit unit) throws NoSuchMethodError
    {
        if (!(unit instanceof LengthUnits))
            throw new NoSuchMethodError("One quantity cannot be converted to unit of other quantity");
        return super.getMagnitude(unit);
    }
}
