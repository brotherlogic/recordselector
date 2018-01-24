package com.github.brotherlogic.recordselector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import godiscogs.Godiscogs.Release;

public class MainDisplay extends JFrame {

    ImagePanel mainPanel;
    ScorePanel scorePanel;
    TitlePanel titlePanel;

    public MainDisplay() {
        mainPanel = new ImagePanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 480));
        this.add(mainPanel, BorderLayout.CENTER);

        scorePanel = new ScorePanel();
        this.add(scorePanel, BorderLayout.SOUTH);

        titlePanel = new TitlePanel();
        this.add(titlePanel, BorderLayout.NORTH);
    }

    public void showRelease(Release r) throws Exception {
        System.setProperty("https.protocols", "TLSv1.2");
        mainPanel.setImage(ImageIO.read(new URL(r.getImagesList().get(0).getUri())));
        titlePanel.setTitle(r.getTitle());
    }
}

class ScorePanel extends JPanel {
    public ScorePanel() {
        for (int i = 1; i < 6; i++) {
            this.add(new JButton("" + i));
        }
    }
}

class TitlePanel extends JPanel {
    JLabel label;

    public void setTitle(String title){
        label.setText(title);
    }

    public TitlePanel() {
        label = new JLabel();
        this.add(label);
    }
}
