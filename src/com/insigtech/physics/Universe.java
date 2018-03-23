package com.insigtech.physics;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Author: Tyler Cook
 * Date: 1/2/14
 * Time: 6:00 PM
 */
public class Universe {

	private static final int MOVE_DISTANCE = 10;
	private static final double ZOOM_IN = .99;
	private static final double ZOOM_OUT = 1.01;
	private ParticleList pList;
	private Rectangle bounds;
	private Rectangle view;
	private Rectangle window;
	private double scale;
	private static final int START_HEIGHT = 1000000;
	private static double ratio;
	private boolean debug = true;
	private int frameTime = 0;
	private double totalMass = -1;


	public Universe(final int viewWidth, final int viewHeight)
	{
		ratio = (double) viewWidth / (double) viewHeight;
		//bounds = new Rectangle(0,0, (int) Math.round(START_HEIGHT * ratio), START_HEIGHT);
		//view = new Rectangle(0,0,(int) bounds.getWidth(),(int) bounds.getHeight());
		bounds = new Rectangle(0,0,10000000,10000000);
		int viewX = (int) (bounds.getWidth() * .5);
		viewX -= (int) (.5 * viewWidth);
		int viewY = (int) (bounds.getWidth() * .5);
		viewY -= (int) (.5 * viewHeight);
		view = new Rectangle(viewX,viewY,viewWidth,viewHeight);
		window = new Rectangle(viewX,viewY,viewWidth,viewHeight);
		//System.out.println(viewX);
		//System.out.println(viewY);
		//Remove after debug
		//view = new Rectangle(0,0,viewWidth,viewHeight);
		//bounds = new Rectangle(0,0,viewWidth,viewHeight);
		scale = window.getWidth() / view.getWidth();
		pList = new ParticleList();
	}

	public void generateUniverse(final int number)
	{
		//mass range: 3-9
		//density range : 14 - 15
		//radius range: 4 - 16
		for(int i=0; i<number; i++)
		{
			double mass = 11. * Math.random();
			//double radius = 20. * Math.random();
			//System.out.println(bounds.getX());
			double x = ((view.getWidth()* 1.5) * Math.random()) + view.x;
			//System.out.println(view.getWidth() * Math.random() + view.x);
			double y = ((view.getHeight() * 1.5) * Math.random()) + view.y;
			while(mass < 3 || mass > 9)
			{
				mass = 11. * Math.random();
			}
			pList.add(new Particle(x,y,mass));
		}
	}

	public void update()
	{
		pList.doPhysics();
		pList.update();
		pList.checkCollisions();
	}

	public Particle draw(Graphics2D g)
	{
		scale = window.getWidth() / view.getWidth();
		return pList.draw(g, view, scale);
	}

	public void addParticle(Particle p)
	{
		pList.add(p);
	}

	public void save()
	{
		if(pList.isEmpty())
			return;
		File saveFile = new File(Long.toString(System.currentTimeMillis()) + ".txt");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(saveFile);
			PrintWriter pw = new PrintWriter(fos);
			pList.save(pw);
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}


	public int countParticles() {
		return pList.count();
	}

	public void zoomIn()
	{
		double centerX = view.getCenterX();
		double centerY = view.getCenterY();
		double newWidth = view.getWidth() * ZOOM_IN;
		double newHeight = view.getWidth() * window.getHeight();
		newHeight = newHeight / window.getWidth();
		view.setSize((int)Math.round(newWidth),(int)Math.round(newHeight));
		double x = centerX - (.5 * view.getWidth());
		double y = centerY - (.5 * view.getHeight());
		view.setLocation((int) x, (int) y);
		scale = window.getWidth() / view.getWidth();
	}
	public void zoomOut()
	{
		double centerX = view.getCenterX();
		double centerY = view.getCenterY();
		double newWidth = view.getWidth() * ZOOM_OUT;
		double newHeight = view.getWidth() * window.getHeight();
		newHeight = newHeight / window.getWidth();
		view.setSize((int)Math.round(newWidth),(int)Math.round(newHeight));
		double x = centerX - (.5 * view.getWidth());
		double y = centerY - (.5 * view.getHeight());
		view.setLocation((int) x, (int) y);
		scale = window.getWidth() / view.getWidth();
	}

	public void moveViewUp()
	{
		view.y -= MOVE_DISTANCE;
		if(view.y < 0)
			view.y = 0;
	}

	public void moveViewDown() {
		view.y += MOVE_DISTANCE;
		if(view.y + view.getHeight() > bounds.y + bounds.getHeight())
			view.y = (int) (bounds.y + (long)bounds.getHeight() - (long) view.getHeight());
	}

	public void moveViewLeft()
	{
		view.x -= MOVE_DISTANCE;
		if(view.x < 0)
			view.x = 0;
	}

	public void moveViewRight()
	{
		view.x += MOVE_DISTANCE;
		if(view.x + view.getWidth() > bounds.x + bounds.getWidth())
			view.x = (int) (bounds.x + (long)bounds.getWidth() - (long) view.getWidth());
	}

	public void setViewCenter(double x, double y)
	{
		view.x = (int) (x - (view.getWidth() / 2.));
		view.y = (int) (y - (view.getHeight() / 2.));
	}

	public void debugUniverse() {
		double x1 = view.x + 20;
		double y1 = view.y + 20;
		double x2 = x1 + 100;
		double y2 = y1 + 100;
		double mass = 8;
		Particle p1 = new Particle(x1,y1,mass);
		Particle p2 = new Particle(x2,y2,mass / 2);
		pList.add(p1);
		pList.add(p2);
		p1.setVelocity(new Vector(.05,0));
	}

	public PointDouble getCenterOfMass()
	{
		return pList.getCenterOfMass();
	}

	public void printParticles() {
		pList.print();
	}



	public boolean isDebug() {
		return debug;
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	public int getFrameTime() {
		return frameTime;
	}

	public void setFrameTime(final int frameTime) {
		this.frameTime = frameTime;
	}

	public double getTotalMass()
	{
		if(totalMass != -1)
			return totalMass;
		totalMass = pList.getTotalMass();
		return totalMass;
	}


	public void selectParticle(final Point mousePoint) {
		pList.selectParticle(mousePoint);
	}

	public Rectangle getView() {
		return view;
	}

	public void setPlist(final ParticleList uniform) {
		pList = uniform;
	}
}
