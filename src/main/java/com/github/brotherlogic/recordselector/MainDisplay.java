package com.github.brotherlogic.recordselector;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainDisplay extends JFrame{

	JPanel mainPanel;
	
	public MainDisplay() {
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(800, 480));
		this.add(mainPanel);
	}
}
