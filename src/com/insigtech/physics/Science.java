package com.insigtech.physics;

import com.insigtech.graphics.RunMode;

/**
 * Author: Tyler Cook
 * Date: 1/2/14
 * Time: 6:03 PM
 */
public class Science {

	//mass range: 3-9
	//density range : 14 - 15

	private static final double G = -6.67E-3;
	private static final double ACCELERATION_CONSTANT = 1;
	private static final double DENSITY = 1.4;
	public static RunMode run_mode = RunMode.PAUSE;

	private Science()
	{}

	private static double force(final double m1, final double m2, final double r)
	{
		double num = m1 * m2 * G;
		double den = r * r;
		return num/den;
	}
	public static Vector gravity_force(final Particle p1, final Particle p2)
	{
		PointDouble center1 = p1.getCenter();
		PointDouble center2 = p2.getCenter();
		double radius = distance(center1.getX(),center1.getY(),center2.getX(),center2.getY());
		double f =  force(p1.getMass(),p2.getMass(),radius);
		double x = f * (center1.getX() - center2.getX()) / radius;
		double y = f * (center1.getY() - center2.getY()) / radius;
		return new Vector(x,y);
	}

	public static double distance(final double x1, final double y1, final double x2, final double y2)
	{
		double x = x1 - x2;
		x = x * x;
		double y = y1 - y2;
		y = y * y;
		return Math.sqrt(x + y);
	}

	public static Vector acceleration(final Vector force, final double mass)
	{
		return new Vector((force.getX() / mass) * ACCELERATION_CONSTANT, (force.getY() / mass) * ACCELERATION_CONSTANT);
	}

	public static double get_radius(final double volume)
	{
		double num = 3 * volume;
		double den = 4 * Math.PI;
		return Math.cbrt(num / den);
	}
	public static double get_volume(final double mass)
	{
		return mass / DENSITY;
	}

	/*
	public static Vector getVelocity(final Particle p1, final Particle p2) {
		double totalMass = p1.getMass() + p2.getMass();
		double energyX = get_energy(p1.getMass(),p1.getVelocity().getX());
		energyX += get_energy(p2.getMass(),p2.getVelocity().getX());
		double energyY = get_energy(p1.getMass(),p1.getVelocity().getY());
		energyY += get_energy(p2.getMass(),p2.getVelocity().getY());
		// v = sqrt((2e)/m)
		double vX = 0;
		double vY = 0;
		if(energyX > 0d)
			vX = Math.sqrt((2 * energyX) / totalMass);
		else
			vX = -1 * Math.sqrt((2 * Math.abs(energyX)) / totalMass);

		if(energyY > 0d)
			vY = Math.sqrt((2 * energyY) / totalMass);
		else
			vY = -1 * Math.sqrt((2 * Math.abs(energyY)) / totalMass);
		Vector velocity = new Vector(vX,vY);
		//System.out.println(velocity);
		//System.out.println();
		return velocity;
	}
	*/

	public static Vector get_velocity(final Particle p1, final Particle p2)
	{
		double totalMass = p1.getMass() + p2.getMass();
		Vector totalMomentum = get_momentum(p1).add(get_momentum(p2));
		return new Vector(totalMomentum.getX()/totalMass, totalMomentum.getY()/totalMass);

	}

	public static Vector get_momentum(final Particle p1) {
		return new Vector(p1.getMass()*p1.getVelocity().getX(),p1.getMass()*p1.getVelocity().getY());
	}

	public static void debug_collision(final Particle p1, final Particle p2, final Particle newParticle) {
		System.out.println("*********COLLISION*********");
		System.out.println("Particle 1:");
		System.out.println(p1);

		System.out.println();

		System.out.println("Particle 2:");
		System.out.println(p2);

		System.out.println();
		System.out.println("New Particle:");
		System.out.println(newParticle);
		System.out.println("********END_COLLISION******");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	public static double get_energy(final double mass, final double velocity)
	{
		double energy = .5 * mass * velocity * velocity;
		return (velocity > 0d)? energy : -energy;
	}

	public static Vector get_energy(Particle p)
	{
		double energyX = get_energy(p.getMass(),p.getVelocity().getX());
		double energyY = get_energy(p.getMass(),p.getVelocity().getY());
		return new Vector(energyX,energyY);
	}
	*/
}
