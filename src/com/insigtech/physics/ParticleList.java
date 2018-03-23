package com.insigtech.physics;

import java.awt.*;
import java.io.PrintWriter;

/**
 * Author: Tyler Cook
 * Date: 1/2/14
 * Time: 6:00 PM
 */
public class ParticleList {

	private Particle head;

	public Particle draw(final Graphics2D g, final Rectangle view, final double scale) {
		if(head != null)
			return head.draw(g,view,scale);
		return null;
	}

	public void add(Particle p)
	{
		if(head == null)
			head = p;
		else
			head.add(p);
	}
	public boolean remove(Particle p)
	{
		if(head.equals(p))
		{
			head = head.getNext();
			return true;
		}
		else if(head.getNext().equals(p))
		{
			head.setNext(head.getNext().getNext());
			return true;
		}
		return head.remove(p);
	}


	public boolean isEmpty() {
		return head == null;
	}

	public void save(final PrintWriter pw) {
		head.save(pw);
	}

	public void doPhysics() {
		if(head == null)
			return;
		Particle p1 = head;
		Particle p2 = head;
		Vector forceSum = new Vector(0,0);
		while(p1 != null)
		{
			forceSum.zero();
			p2 = head;
			while(p2 != null)
			{
				if(!p1.equals(p2))
				{
					forceSum = forceSum.add(Science.gravity_force(p1, p2));
				}
				p2 = p2.getNext();
			}
			p1.accelerate(forceSum);
			p1 = p1.getNext();
		}
	}
	public void update()
	{
		if(head == null)
			return;
		head.update();

	}


	public void checkCollisions()
	{
		if(isEmpty())
			return;
		Particle p1 = head;
		//Particle p1_prev = head;

		while(p1 != null)
		{
			boolean collisionOccurred = false;
			Particle p2 = head;
			while(p2 != null)
			{
				if(!p1.equals(p2) && p1.checkCollision(p2))
				{
					collisionOccurred = true;
					Particle p = p1.collide(p2);
					add(p);
					remove(p1);
					remove(p2);
					break;
				}
				p2 = p2.getNext();
			}
			if(collisionOccurred)
				p1 = head;
			else
				p1 = p1.getNext();

		}
	}
	/*
	public void checkCollisions() {
		if(isEmpty())
			return;
		Particle p1 = head;
		Particle p2 = head;
		int count = 0;
		while(p1 != null)
		{
			p2 = head;
			System.out.println("Black");
			count++;
			while(p2 != null)
			{
				count++;
				//System.out.println("Thomas");
				if(!p1.equals(p2))
				{
					if(p1.checkCollision(p2))
					{
						Particle p = p1.collide(p2);
						//System.out.println("Fluffy");
						System.out.println(remove(p1));
						System.out.println(remove(p2));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						add(p);
						if(p2.getNext() == null)
							p2.setNext(p);
					}
				}
				System.out.println(p2);
				p2 = p2.getNext();
				System.out.println(count());
			}

			p1 = p1.getNext();
		}
	}
	*/
	public void print()
	{
		if(head == null)
			System.out.println("empty");
		else
			head.print();
	}

	public int count() {
		if(isEmpty())
			return 0;
		else
			return head.count();
	}

	public double getTotalMass() {
		if(isEmpty())
			return -1d;
		return head.getTotalMass();
	}

	public Particle selectParticle(final Point mousePoint) {
		if(isEmpty())
		{
			return null;
		}
		return head.select(mousePoint);

	}

	public PointDouble getCenterOfMass() {
		if(head == null)
			return new PointDouble(0,0);
		double numx = 0;
		double numy = 0;
		double den = 0;
		Particle p = head;
		while(p != null)
		{
			numx += p.getMass() * p.getDrawCenter().getX();
			numy += p.getMass() * p.getDrawCenter().getY();
			den += p.getMass();
			p = p.getNext();
		}
		return new PointDouble(numx / den, numy / den);

	}
}
