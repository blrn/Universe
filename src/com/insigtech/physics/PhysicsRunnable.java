package com.insigtech.physics;

import com.insigtech.graphics.MainFrame;
import com.insigtech.graphics.RunMode;

/**
 * Author: Tyler Cook
 * Date: 1/6/14
 * Time: 10:26 PM
 */
public class PhysicsRunnable implements Runnable {

	private final MainFrame mainFrame;
	private Universe universe;
	private RunMode runMode;

	private static final int RUN_DELAY = 3;
	private static final int PAUSE_DELAY = 17;

	public PhysicsRunnable(final Universe universe, final MainFrame mainFrame)
	{
		this.universe = universe;
		this.mainFrame = mainFrame;
		this.runMode = RunMode.PAUSE;
	}

	public RunMode getRunMode() {
		return runMode;
	}

	public void setRunMode(final RunMode runMode) {
		this.runMode = runMode;
	}

	@Override
	public void run() {
		long st = System.currentTimeMillis();
		long dt = 0;
		while (true)
		{
			st = System.currentTimeMillis();
			if(runMode == RunMode.RUN)
			{
				universe.update();
				dt = System.currentTimeMillis() - st;
				try {
					Thread.sleep((RUN_DELAY - dt > 0)?RUN_DELAY - dt: 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else
			{
				try {
					Thread.sleep(PAUSE_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mainFrame.getPaintComponent().setPhysicsFrameTime(System.currentTimeMillis() - st);
		}
	}
}
