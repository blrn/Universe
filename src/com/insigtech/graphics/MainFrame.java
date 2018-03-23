package com.insigtech.graphics;

import com.insigtech.physics.SolarSystem;
import com.insigtech.physics.Uniform;
import com.insigtech.physics.Universe;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

/**
 * Author: Tyler Cook
 * Date: 1/4/14
 * Time: 8:39 PM
 */
public class MainFrame extends JFrame {
	private static PaintComponent paintComponent ;
	private MyMenuBar myMenuBar;

	public MainFrame(final int width, final int height)
	{
		Universe universe = new Universe(width,height);
		paintComponent = new PaintComponent(universe);
		myMenuBar = new MyMenuBar();
		myMenuBar.init();
		setJMenuBar(myMenuBar);
	}

	public PaintComponent getPaintComponent() {
		return paintComponent;
	}

	public void setup(final int width,final int height, final RunMode startRunMode)
	{
//		paintComponent.getUniverse().generateUniverse(2000);
//		paintComponent.getUniverse().setPlist(SolarSystem.getSolarSystem(paintComponent.getUniverse().getView()));
		paintComponent.getUniverse().setPlist(Uniform.getUniform(paintComponent.getUniverse().getView(),47));
		//paintComponent.getUniverse().save();
		//paintComponent.getUniverse().debugUniverse();
		//Particle p = new Particle(800,450,100, Science.get_radius(Science.get_volume(100)),Science.get_volume(100));
		//p.setStationary(true);
		//universe.addParticle(p);
		add(paintComponent);

		//setBackground(Color.BLACK);
		setSize(width,height);
		revalidate();
	}
	public void setListeners(final KeyListener keyListener, final MouseListener mouseListener, final MouseWheelListener mouseWheelListener,final ActionListener actionListener)
	{
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		addMouseWheelListener(mouseWheelListener);
		myMenuBar.setListeners(actionListener);
	}
}
