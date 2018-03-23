package com.insigtech.graphics;

import com.insigtech.physics.Particle;
import com.insigtech.physics.PointDouble;
import com.insigtech.physics.Universe;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Tyler Cook
 * Date: 12/30/13
 * Time: 9:04 PM
 */
public class PaintComponent extends JComponent{

	private Universe universe;
	private Point mousePoint;
	private Particle selected;
	private long graphicFrameTime = 0;
	private long physicsFrameTime = 0;
	private PointDouble centerOfMass;

	public PaintComponent(final Universe universe)
	{
		this.universe = universe;
		setBackground(Color.BLACK);
		mousePoint = new Point(0,0);

	}

	public Universe getUniverse() {
		return universe;
	}

	public void update() {
		universe.update();
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.WHITE);
		//g2d.drawOval(50,50,100,100);
		selected = universe.draw(g2d);
		//center of mass lines length: 20
		g2d.setColor(Color.RED);
		g2d.drawLine((int)Math.round(centerOfMass.getX()) - 10,(int)Math.round(centerOfMass.getY()),(int)Math.round(centerOfMass.getX() + 10),(int)Math.round(centerOfMass.getY())); //horizontal
		g2d.drawLine((int)Math.round(centerOfMass.getX()), (int)Math.round(centerOfMass.getY()) - 10, (int)Math.round(centerOfMass.getX()), (int)Math.round(centerOfMass.getY()) + 10); //vertical
		if(universe.isDebug())
		{
			g2d.setColor(Color.YELLOW);
			g2d.drawString("Total Mass: " + universe.getTotalMass(),0,10);
			g2d.drawString("Particles: " + universe.countParticles(), 0, 25);
			g2d.drawString("Graphic Frame Time: " + graphicFrameTime,0,40);
			g2d.drawString("Physics Frame Time"  + physicsFrameTime,0,55);
			if(selected != null)
			{
				int y = 70;
				String[] paintStr = selected.getPaintString();
				for(int i=0; i<paintStr.length;i++)
				{
					g2d.drawString(paintStr[i],0,y);
					y+= 15;
				}
			}

		}

	}

	public Point getMousePoint() {
		return mousePoint;
	}

	public void setMousePoint(final Point mousePoint) {
		this.mousePoint.setLocation(mousePoint.x, mousePoint.y - 23);

	}

	public Particle getSelected() {
		return selected;
	}

	public void setGraphicFrameTime(final long graphicFrameTime) {
		this.graphicFrameTime = graphicFrameTime;
	}

	public void setPhysicsFrameTime(final long physicsFrameTime) {
		this.physicsFrameTime = physicsFrameTime;
	}

	public void setCenterOfMass(final PointDouble centerOfMass) {
		this.centerOfMass = centerOfMass;
	}
}
