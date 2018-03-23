package com.insigtech.physics;

/**
 * Author: Tyler Cook
 * Date: 1/2/14
 * Time: 10:30 PM
 */
public class Vector{
	private double x;
	private double y;

	public Vector(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector v)
	{
		return new Vector(this.x + v.getX(), this.y + v.getY());
	}

	public double getMagnitude()
	{
		return Math.sqrt((x*x) + (y*y));
	}

	public void update(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void zero() {
		this.x = 0;
		this.y = 0;
	}

	@Override
	public String toString() {
		return "<" + x + "," + y + ">";
	}
}
