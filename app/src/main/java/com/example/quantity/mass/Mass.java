package com.example.quantity.mass;

import com.example.quantity.Quantity;
import com.example.quantity.Unit;

public class Mass extends Quantity
{
    public Mass (double normalizedMagnitude)
    {
        super(normalizedMagnitude);
    }

    public Mass (double magnitude, Unit unit) throws InstantiationError
    {
        super(magnitude, unit);
        if (!(unit instanceof MassUnits))
            throw new InstantiationError("Mass object cannot be instantiated for non mass units");
    }

    @Override
    public double getMagnitude (Unit unit) throws NoSuchMethodError
    {
        if (!(unit instanceof MassUnits))
            throw new NoSuchMethodError("Mass quantity cannot be converted to unit of other quantity");
        return super.getMagnitude(unit);
    }
}
