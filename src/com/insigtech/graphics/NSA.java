package com.insigtech.graphics;

import com.insigtech.physics.PhysicsRunnable;

import java.awt.event.*;

/**
 * Author: Tyler Cook
 * Date: 1/6/14
 * Time: 9:37 PM
 */
public class NSA implements ActionListener, KeyListener, MouseListener, MouseWheelListener {

	public static final String NEW_COMMAND = "new";
	public static final String OPEN_COMMAND = "open";
	public static final String SAVE_COMMAND = "save";
	public static final String EXIT_COMMAND = "exit";

	private MainFrame mainFrame;

	private GraphicsRunnable graphicsRunnable;
	private PhysicsRunnable physicsRunnable;

	private Thread graphicsThread;
	private Thread physicsThread;

	public NSA(final MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;

		this.graphicsRunnable = new GraphicsRunnable(RunMode.PAUSE,mainFrame);
		this.graphicsThread = new Thread(graphicsRunnable);

		this.physicsRunnable = new PhysicsRunnable(mainFrame.getPaintComponent().getUniverse(), mainFrame);
		this.physicsThread = new Thread(physicsRunnable);

	}

	public void start() {
		graphicsThread.start();
		physicsThread.start();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		System.out.println(e.getActionCommand());
	}

	@Override
	public void keyTyped(final KeyEvent e) {

	}

	@Override
	public void keyPressed(final KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			switch (graphicsRunnable.getRunMode())
			{
				case PAUSE:
					graphicsRunnable.setRunMode(RunMode.RUN);
					physicsRunnable.setRunMode(RunMode.RUN);
					break;
				case RUN:
					graphicsRunnable.setRunMode(RunMode.PAUSE);
					physicsRunnable.setRunMode(RunMode.PAUSE);
					break;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
			graphicsRunnable.setUpKey(true);
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			graphicsRunnable.setDownKey(true);
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			graphicsRunnable.setLeftKey(true);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			graphicsRunnable.setRightKey(true);
		if(e.getKeyCode() == KeyEvent.VK_D)
			mainFrame.getPaintComponent().getUniverse().setDebug(!mainFrame.getPaintComponent().getUniverse().isDebug());
		if(e.getKeyCode() == KeyEvent.VK_T)
			graphicsRunnable.setTrack(!graphicsRunnable.isTrack());

	}

	@Override
	public void keyReleased(final KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			graphicsRunnable.setUpKey(false);
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			graphicsRunnable.setDownKey(false);
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			graphicsRunnable.setLeftKey(false);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			graphicsRunnable.setRightKey(false);

	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		mainFrame.getPaintComponent().setMousePoint(e.getPoint());
		mainFrame.getPaintComponent().getUniverse().selectParticle(mainFrame.getPaintComponent().getMousePoint());
	}

	@Override
	public void mousePressed(final MouseEvent e) {

	}

	@Override
	public void mouseReleased(final MouseEvent e) {

	}

	@Override
	public void mouseEntered(final MouseEvent e) {

	}

	@Override
	public void mouseExited(final MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		if(e.getWheelRotation() > 0)
		{
			//zoom out
			mainFrame.getPaintComponent().getUniverse().zoomOut();
		}
		else
		{
			//zoom in
			mainFrame.getPaintComponent().getUniverse().zoomIn();
		}

	}
}
