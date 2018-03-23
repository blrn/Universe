package com.insigtech.graphics;

import javax.swing.*;

/**
 * Author: Tyler Cook
 * Date: 12/30/13
 * Time: 8:09 PM
 */
public class Main {
	private final static int WIDTH = 1600;
	private final static int HEIGHT = 1000;
	public static void main(String[] args)
	{
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		MainFrame mainFrame = new MainFrame(WIDTH,HEIGHT);
		NSA nsa = new NSA(mainFrame);
		mainFrame.setListeners(nsa,nsa,nsa, nsa);
		mainFrame.setSize(1600,900);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setup(WIDTH,HEIGHT,RunMode.STOP);
		nsa.start();
	}
}
