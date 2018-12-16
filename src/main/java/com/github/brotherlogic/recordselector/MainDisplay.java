package com.github.brotherlogic.recordselector;

import java.awt.BorderLayout;
import java.awt.Color;
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
import godiscogs.Godiscogs.Label;

public class MainDisplay extends JFrame {

    ImagePanel mainPanel;
    ScorePanel scorePanel;
    TitlePanel titlePanel;
    ListensPanel listensPanel;
    DoScorePanel doScorePanel;

    public MainDisplay(Getter g) {
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

        doScorePanel = new DoScorePanel(scorePanel, g);
        this.add(doScorePanel, BorderLayout.EAST);
    }

    public void showRelease(Release r, Color color, int disk) throws Exception {
        System.setProperty("https.protocols", "TLSv1.2");
        if (r.getImagesList().size() > 0) {
            mainPanel.setImage(ImageIO.read(new URL(r.getImagesList().get(0).getUri())));
        } else {
            mainPanel.setImage(null);
        }
	String labelString = "";
	for (Label l : r.getLabelsList()) {
	    labelString += l.getName() + ", ";
	}
        titlePanel.setTitle(r.getTitle() + "("+labelString+")[" + disk + "/" + r.getFormatQuantity() + "]");
        listensPanel.setListens(color);
    }
}

class ScorePanel extends JPanel {
    JToggleButton[] arr = new JToggleButton[5];
    int selected = -1;

    public ScorePanel() {
        for (int i = 0; i < 5; i++) {
            arr[i] = new JToggleButton("" + (i + 1));
            this.add(arr[i]);
            final int index = i;
            arr[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reset(index);
                    selected = index;
                }
            });
        }
    }

    public int getScore() {
        return selected;
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
    ScorePanel scPanel;
    Getter g;

    public DoScorePanel(ScorePanel sc, Getter getter) {
        score = new JButton("Score");
        this.add(score);
        scPanel = sc;
        g = getter;

        score.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int scoreVal = scPanel.getScore();
                if (scoreVal > 0) {
                    try {
                        g.setScore(scoreVal);
                        score.setEnabled(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}

class ListensPanel extends JPanel {
    public ListensPanel() {
     }

    public void setListens(Color color) {
        this.setBackground(color);
    }
}
