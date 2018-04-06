package me.colingreybosh.midterm;

public class Circle
{
    public double x;
    public double y;
    public int cx;
    public int cy;
    public int r;
    public boolean isInUnitCircle;

    public Circle(double x, double y, int r, int drawScale)
    {
        this.x = x;
        this.y = y;
        cx = (int) (x * drawScale);
        cy = drawScale - (int) (y * drawScale);
        this.r = r;
        isInUnitCircle = (dist(this.x, this.y) < 1);
    }

    private static double dist(double x, double y)
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
