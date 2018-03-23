package com.insigtech.graphics;

import com.insigtech.physics.PointDouble;

/**
 * Author: Tyler Cook
 * Date: 1/6/14
 * Time: 9:52 PM
 */
public class GraphicsRunnable implements Runnable {

	private RunMode runMode;
	private MainFrame mainFrame;
	private static final int DELAY = 17;
	private boolean upKey = false;
	private boolean rightKey = false;
	private boolean downKey = false;
	private boolean leftKey = false;
	private boolean track = true;

	public GraphicsRunnable(RunMode runMode, MainFrame mainFrame)
	{
		this.runMode = runMode;
		this.mainFrame = mainFrame;

	}

	@Override
	public void run() {
		long st = System.currentTimeMillis();
		long dt = 0;
		while (true)
		{
			st = System.currentTimeMillis();
			if(track && mainFrame.getPaintComponent().getSelected() != null)
			{
				System.out.println("mooo");
				PointDouble center = mainFrame.getPaintComponent().getSelected().getCenter();
				mainFrame.getPaintComponent().getUniverse().setViewCenter(center.getX(),center.getY());
			}
			else
			{
				if(upKey)
					mainFrame.getPaintComponent().getUniverse().moveViewUp();
				if(rightKey)
					mainFrame.getPaintComponent().getUniverse().moveViewRight();
				if(downKey)
					mainFrame.getPaintComponent().getUniverse().moveViewDown();
				if(leftKey)
					mainFrame.getPaintComponent().getUniverse().moveViewLeft();
			}
			mainFrame.getPaintComponent().setCenterOfMass(mainFrame.getPaintComponent().getUniverse().getCenterOfMass());
			mainFrame.getPaintComponent().repaint();
			//System.out.println();
			//universe.printParticles();
			dt = System.currentTimeMillis() - st + 1;
			try {
				Thread.sleep((DELAY - dt > 0)?DELAY - dt: 0);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			mainFrame.getPaintComponent().setGraphicFrameTime(System.currentTimeMillis() - st);

		}
	}

	public RunMode getRunMode() {
		return runMode;
	}

	public void setRunMode(final RunMode runMode) {
		this.runMode = runMode;
	}

	public void setUpKey(final boolean upKey) {
		this.upKey = upKey;
	}

	public void setRightKey(final boolean rightKey) {
		this.rightKey = rightKey;
	}

	public void setDownKey(final boolean downKey) {
		this.downKey = downKey;
	}

	public void setLeftKey(final boolean leftKey) {
		this.leftKey = leftKey;
	}

	public boolean isTrack() {
		return track;
	}

	public void setTrack(final boolean track) {
		this.track = track;
	}
}
