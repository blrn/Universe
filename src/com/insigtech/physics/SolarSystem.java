package com.insigtech.physics;

import java.awt.*;

/**
 * Author: Tyler Cook
 * Date: 1/7/14
 * Time: 1:31 AM
 */
public class SolarSystem {

	public static ParticleList getSolarSystem(Rectangle view)
	{
		//moon .0123 * earth
		// moon .0024 AU - .0027 AU
		//sun 333000 * earth
		ParticleList pList = new ParticleList();
		Particle sun = new Particle(view.getCenterX(),view.getCenterY(),660000);
		Particle earth = new Particle(sun.getCenter().getX() + 500, sun.getCenter().getY(),2);
		Particle moon = new Particle(earth.getCenter().getX() + 1.35, earth.getCenter().getY(), .0246);
		earth.setSelected(true);
		earth.setVelocity(new Vector(0, .938));
		moon.setVelocity(new Vector(0,.94));
		pList.add(sun);
		pList.add(earth);
		//pList.add(moon);
		return pList;
	}
}
