package com.insigtech.graphics;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Author: Tyler Cook
 * Date: 1/6/14
 * Time: 9:28 PM
 */
public class MyMenuBar extends JMenuBar {
	private JMenu fileMenu;
	private JMenu optionMenu;

	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem exitItem;


	public MyMenuBar()
	{
		fileMenu = new JMenu("File");
		optionMenu = new JMenu("Options");

		newItem = new JMenuItem("New");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
	}

	public void init()
	{
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);

		add(fileMenu);
		add(optionMenu);
	}

	public void setListeners(final ActionListener actionListener) {
		newItem.setActionCommand(NSA.NEW_COMMAND);
		openItem.setActionCommand(NSA.OPEN_COMMAND);
		saveItem.setActionCommand(NSA.SAVE_COMMAND);
		exitItem.setActionCommand(NSA.EXIT_COMMAND);

		newItem.addActionListener(actionListener);
		openItem.addActionListener(actionListener);
		saveItem.addActionListener(actionListener);
		exitItem.addActionListener(actionListener);
	}
}
