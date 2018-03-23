package com.insigtech.physics;

import java.awt.*;

/**
 * Author: Tyler Cook
 * Date: 1/2/14
 * Time: 8:54 PM
 */
public class PointDouble {
	private double x;
	private double y;

	public PointDouble(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public void update(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 * @param p PointDouble to be subtracted from this Point
	 * @return Returns new Point that is difference of This Point - p
	 */
	public PointDouble diff(PointDouble p)
	{
		return new PointDouble(x - p.getX(), y - p.getY());
	}

	/**
	 *
	 * @param p PointDouble to be subtracted from this Point
	 * @return Returns new Point that is difference of This Point - p
	 */
	public PointDouble diff(Point p)
	{
		return new PointDouble(x-p.getX(), y-p.getY());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
