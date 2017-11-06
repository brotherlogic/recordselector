package com.github.brotherlogic.recordselector;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	Image img, scaledImg;

	public ImagePanel() {
		img = null;
		scaledImg = null;
	}

	public ImagePanel(Image image) {
		img = image;
	}

	public void setImage(Image i) {
		img = i;
		scaledImg = null;
		System.out.println("SET IMAGE");
		System.out.println("SIZINGTON = " + img.getHeight(null));
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("PAINTING");
		if (img != null) {
			System.out.println("Scaling Image");
			int imgHeight = img.getHeight(null);
			int imgWidth = img.getWidth(null);

			double scaleFactor = (imgHeight + 0.0) / this.getHeight();

			int scaledHeight = (int) (Math.ceil(imgHeight / scaleFactor));
			int scaledWidth = (int) (Math.ceil(imgWidth / scaleFactor));

			if (scaledImg == null) {
				scaledImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			}
			System.out.println("Drawing image");
			g.drawImage(scaledImg, (this.getWidth() - scaledWidth) / 2, 0, null);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		System.out.println("HERE");
		return new Dimension(480, 480);
	}

	@Override
	public Dimension getMaximumSize() {
		System.out.println("HERE");
		return getPreferredSize();
	}
}
