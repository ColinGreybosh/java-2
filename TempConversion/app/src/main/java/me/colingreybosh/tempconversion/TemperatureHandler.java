package me.colingreybosh.tempconversion;

import java.text.DecimalFormat;

class TemperatureHandler {

    TemperatureHandler(double value, int unit)
    {
        tempFormat = new DecimalFormat("0.00");

        this.value = new double[3];
        this.value[unit] = value;
        this.value = convertFrom(unit);
    }

    DecimalFormat tempFormat;
    private double value[];

    TemperatureHandler setTemp(int unit, double value)
    {
        this.value[unit] = value;
        return this;
    }

    double[] getTemps()
    {
        return value;
    }

    String getTempString(int unit)
    {
        return (isBelowAbsZero(unit)?
                "Below Absolute Zero" : tempFormat.format(getTemps()[unit]));
    }

    double[] convertFrom(int unit)
    {
        if (unit == Unit.F)
        { // Current unit is Fahrenheit, convert...
            value[Unit.C] = 5.0/9.0 * (getTemps()[unit]-32);          // to Celsius
            value[Unit.K] = 5.0/9.0 * (getTemps()[unit]-32) + 273.15; // to Kelvin
        }
        if (unit == Unit.C)
        { // Current unit is Celsius, convert...
            value[Unit.F] = 9.0/5.0 * getTemps()[unit] + 32;          // to Fahrenheit
            value[Unit.K] = getTemps()[unit] + 273.15;                // to Kelvin
        }
        if (unit == Unit.K)
        { // Current unit is Kelvin, convert...
            value[Unit.F] = 9.0/5.0 * (getTemps()[unit]-273.15) + 32; // to Fahrenheit
            value[Unit.C] = getTemps()[unit] - 273.15;                // to Celsius
        }

        return value;
    }

    private boolean isBelowAbsZero(int unit) {
        if (unit == Unit.K) {
            return (value[unit] < 0);
        }
        if (unit == Unit.C) {
            return (value[unit] < -273.15);
        }
        return unit == Unit.F && (value[unit] < -459.67);
    }
}