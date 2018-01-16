package com.github.brotherlogic.recordselector;

import java.awt.Dimension;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import godiscogs.Godiscogs.Release;

public class MainDisplay extends JFrame {

    ImagePanel mainPanel;

    public MainDisplay() {
        mainPanel = new ImagePanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(800, 480));
        this.add(mainPanel);
    }

    public void showRelease(Release r) throws Exception {
        System.setProperty("https.protocols", "TLSv1.2");
        mainPanel.setImage(ImageIO.read(new URL(r.getImagesList().get(0).getUri())));
    }
}
