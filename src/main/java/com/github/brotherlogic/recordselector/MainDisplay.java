package com.github.brotherlogic.recordselector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import godiscogs.Godiscogs.Release;

public class MainDisplay extends JFrame {

    ImagePanel mainPanel;
    ScorePanel scorePanel;
    TitlePanel titlePanel;
    ListensPanel listensPanel;
    DoScorePanel doScorePanel;

    public MainDisplay() {
        mainPanel = new ImagePanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 480));
        this.add(mainPanel, BorderLayout.CENTER);

        scorePanel = new ScorePanel();
        this.add(scorePanel, BorderLayout.SOUTH);

        titlePanel = new TitlePanel();
        this.add(titlePanel, BorderLayout.NORTH);

        listensPanel = new ListensPanel();
        this.add(listensPanel, BorderLayout.WEST);

        doScorePanel = new DoScorePanel();
        this.add(doScorePanel, BorderLayout.EAST);
    }

    public void showRelease(Release r, int listens) throws Exception {
        System.setProperty("https.protocols", "TLSv1.2");
        mainPanel.setImage(ImageIO.read(new URL(r.getImagesList().get(0).getUri())));
        titlePanel.setTitle(r.getTitle());
        listensPanel.setListens(listens);
    }
}

class ScorePanel extends JPanel {
    JToggleButton[] arr = new JToggleButton[5];

    public ScorePanel() {
        for (int i = 0; i < 5; i++) {
            arr[i] = new JToggleButton("" + (i + 1));
            this.add(arr[i]);
            final int index = i;
            arr[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reset(index);
                }
            });
        }
    }

    private void reset(int n) {
        for (int i = 0; i < arr.length; i++) {
            if (i != n) {
                arr[i].setSelected(false);
            }
        }
    }
}

class TitlePanel extends JPanel {
    JLabel label;

    public TitlePanel() {
        label = new JLabel();
        this.add(label);
    }

    public void setTitle(String title) {
        label.setText(title);
    }
}

class DoScorePanel extends JPanel {
    JButton score;

    public DoScorePanel() {
        score = new JButton("Score");
        score.setEnabled(false);
        this.add(score);
    }
}

class ListensPanel extends JPanel {
    JLabel listens;

    public ListensPanel() {
        listens = new JLabel("?");
        this.add(listens);
    }

    public void setListens(int num) {
        listens.setText("" + num);

    }
}
