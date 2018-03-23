package com.insigtech.physics;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.PrintWriter;

/**
 * Author: Tyler Cook
 * Date: 1/2/14
 * Time: 6:02 PM
 */
public class Particle {
	private double mass;
	private double radius;
	private double volume;
	private Ellipse2D.Double bounds;
	private Particle next;
	private Ellipse2D.Double drawBounds;
	private Vector velocity;
	private boolean stationary = false;
	private boolean selected = false;


	/*
	public Particle(final double x, final double y, final double mass, final double radius, final double volume)
	{
		this.mass = mass;
		this.radius = radius;
		this.volume = volume;
		this.bounds = new Ellipse2D.Double(x - radius,y - radius,radius * 2,radius * 2);
		velocity = new Vector(0,0);
		drawBounds = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
	}
	*/
	public Particle(final double x, final double y, final double mass)
	{
		this.mass = mass;
		this.volume = Science.get_volume(mass);
		this.radius = Science.get_radius(volume);
		this.bounds = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
		this.velocity = new Vector(0,0);
		drawBounds = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
		System.out.println(getCenter());
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(final Vector velocity) {

		this.velocity = velocity;
	}

	public void setStationary(final boolean stationary) {
		this.stationary = stationary;
	}

	public Particle draw(Graphics2D g, Rectangle view, double scale)
	{

		//g.fill(bounds);
		drawBounds.x = bounds.x - view.x;
		drawBounds.y = bounds.y - view.y;
		//System.out.println(drawBounds.x);
		//System.out.println(drawBounds.y);
		//System.out.println();
		double drawRadius = radius * scale;
		drawRadius = (drawRadius < .75)? .75 : drawRadius;
		drawBounds.x *= scale;
		drawBounds.y *= scale;
		drawBounds.x -= drawRadius - radius;
		drawBounds.y -= drawRadius - radius;
		drawBounds.width = drawRadius + drawRadius;
		drawBounds.height = drawRadius+ drawRadius;
		if(selected)
		{
			g.setColor(Color.YELLOW);
			g.fill(drawBounds);
			g.setColor(Color.WHITE);
			if(next != null)
				next.draw(g,view,scale);
			return this;
		}
		else
		{
			g.fill(drawBounds);
			if(next != null)
				return next.draw(g,view,scale);
			return null;
		}
	}

	public boolean checkCollision(Particle p)
	{
		PointDouble center1 = getCenter();
		PointDouble center2 = p.getCenter();
		return (getRadius() + p.getRadius() > Science.distance(center1.getX(), center1.getY(), center2.getX(), center2.getY()));
	}

	public void add(Particle p)
	{
		if(next == null)
			next = p;
		else
			next.add(p);
	}

	public boolean remove(final Particle p) {
		if(next == null)
			return false;
		if(next.equals(p))
		{
			next = next.getNext();
			return true;
		}
		return next.remove(p);
	}


	public double getMass() {
		return mass;
	}

	public void setMass(final double mass) {
		this.mass = mass;
	}

	public Particle getNext() {
		return next;
	}

	public void setNext(final Particle next) {
		this.next = next;
	}

	public Ellipse2D.Double getBounds() {
		return bounds;
	}

	public PointDouble getCenter()
	{
		return new PointDouble(bounds.x + radius, bounds.y + radius);
	}

	public PointDouble getDrawCenter()
	{
		return new PointDouble(drawBounds.x + (.5 * drawBounds.getWidth()), drawBounds.y + (.5 * drawBounds.getHeight()));
	}

	public double getRadius()
	{
		return radius;
	}

	@Override
	public boolean equals(final Object obj) {
		if(! (obj instanceof Particle))
			return false;
		Particle p = (Particle) obj;
		return (p.getMass() == mass && p.getBounds().equals(bounds) && radius == p.getRadius() && volume == p.getVolume());
	}

	public void save(final PrintWriter pw) {
		StringBuilder sb = new StringBuilder();
		sb.append(Double.toString(bounds.x) + ",");
		sb.append(Double.toString(bounds.y) + ",");
		sb.append(Double.toString(mass) + ",");
		sb.append(Double.toString(getRadius()));
		pw.println(sb.toString());
		if(next != null)
			next.save(pw);
	}

	public boolean hasNext() {
		return next != null;
	}

	public void accelerate(final Vector forceSum) {
		velocity = velocity.add(Science.acceleration(forceSum,mass));

	}

	public void update() {
		//System.out.println("meow");
		if(stationary)
		{
			velocity.update(0,0);
		}
		else
		{
			Vector loc = velocity.add(new Vector(getCenter().getX(),getCenter().getY()));
			bounds.x = loc.getX() - getRadius();
			bounds.y = loc.getY() - getRadius();
		}
		if(next != null)
			next.update();
	}

	public Particle collide(final Particle p2) {
		double totalMass = mass + p2.getMass();
		double cmx = ((mass * getCenter().getX()) + (p2.getMass() * p2.getCenter().getX())) / totalMass;
		double cmy = ((mass * getCenter().getY()) + (p2.getMass() * p2.getCenter().getY())) / totalMass;
		Particle p = new Particle(cmx, cmy, mass + p2.getMass());
		if (this.isStationary() || p2.isStationary())
			p.setStationary(true);
		else
			p.setVelocity(Science.get_velocity(this, p2));

		if (selected || p2.isSelected())
			p.setSelected(true);
		//Science.debug_collision(this, p2,p);


		return p;
	}

	public double getVolume() {
		return volume;
	}

	public int count() {
		if(next == null)
			return 1;
		else return 1 + next.count();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mass: " + mass);
		sb.append("\nradius: " + radius);
		sb.append("\nvolume: " + volume);
		sb.append("\ncenter: " + getCenter());
		sb.append("\nvelocity vector: " + velocity);
		sb.append("\nvelocity: " + velocity.getMagnitude());
		sb.append("\nmomentum vector:" + Science.get_momentum(this).toString());
		sb.append("\nenergy: " + Science.get_momentum(this).getMagnitude());
		return sb.toString();
	}

	public void print() {
		System.out.println(this);
		System.out.println();
		if(next != null)
			next.print();

	}

	public double getTotalMass() {
		if(next == null)
			return mass;
		return mass + next.getTotalMass();
	}

	public Particle select(Point mousePoint) {
		if(drawBounds.contains(mousePoint.getX(),mousePoint.getY()))
		{
			selected = true;
			if(next != null)
				next.deselect();
			return this;
		}
		else
		{
			selected = false;
			if(next != null)
				return next.select(mousePoint);
			return null;
		}

	}

	public void deselect()
	{
		selected = false;
		if(next != null)
			next.deselect();
	}

	public void setSelected(final boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isStationary() {
		return stationary;
	}

	public String[] getPaintString()
	{
		/*
		StringBuilder sb = new StringBuilder();
		sb.append("mass: " + mass);
		sb.append("\nradius: " + radius);
		sb.append("\nvolume: " + volume);
		sb.append("\ncenter: " + getCenter());
		sb.append("\nvelocity vector: " + velocity);
		sb.append("\nvelocity: " + velocity.getMagnitude());
		sb.append("\nmomentum vector:" + Science.get_momentum(this).toString());
		sb.append("\nenergy: " + Science.get_momentum(this).getMagnitude());
		*/
		String[] paintStr = {"mass: " + Double.toString(mass),
							"radius: " + Double.toString(radius),
							"volume: " + Double.toString(volume),
							"center: " + getCenter().toString(),
							"velocity vector: " + velocity.toString(),
							"velocity: " + Double.toString(velocity.getMagnitude()),
							"momentum vector: " + Science.get_momentum(this).toString(),
							"momentum: " + Double.toString(Science.get_momentum(this).getMagnitude())};
		return paintStr;

	}
}
