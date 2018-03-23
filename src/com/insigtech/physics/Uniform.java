package com.insigtech.physics;

import java.awt.*;

/**
 * Author: Tyler Cook
 * Date: 1/7/14
 * Time: 12:58 AM
 */
public class Uniform {
	public static ParticleList getUniform(Rectangle view, int space)
	{
		ParticleList pList = new ParticleList();
		for(int x=view.x; x<view.width + view.x ; x += space)
		{
			for(int y=view.y; y<view.height + view.y; y += space)
			{
				pList.add(new Particle(x,y,100));
			}
		}
		pList.add(new Particle(view.x + (.5 * view.width),view.y - space,1000));
		return pList;
	}
}
