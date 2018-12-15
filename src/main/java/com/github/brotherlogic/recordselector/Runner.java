package com.github.brotherlogic.recordselector;

import com.github.brotherlogic.javaserver.JavaServer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import godiscogs.Godiscogs.Image;
import godiscogs.Godiscogs.Release;
import recordcollection.Recordcollection.ReleaseMetadata.Category;
import io.grpc.BindableService;
import recordgetter.Recordgetter.GetRecordResponse;

public class Runner extends JavaServer {

    MainDisplay mainDisplay;
    Release oldRelease = null;

    public static void main(String[] args) throws Exception {
        Option optionHost = OptionBuilder.withLongOpt("server").hasArg().withDescription("Hostname of server")
                .create("s");
        Options options = new Options();
        options.addOption(optionHost);

        CommandLineParser parser = new GnuParser();
        CommandLine line = parser.parse(options, args);

        String rServer = "192.168.86.42";
        if (line.hasOption("server"))
            rServer = line.getOptionValue("s");

        Runner r = new Runner();
        r.Serve(rServer);
    }

    @Override
    public String getServerName() {
        return "RecordSelector";
    }

    @Override
    public List<BindableService> getServices() {
        return new LinkedList<BindableService>();
    }

    private void displayScreen() {
        if (mainDisplay == null) {
            mainDisplay = new MainDisplay(new Getter(getHost("recordgetter"), getPort("recordgetter")));
        }

        mainDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainDisplay.pack();
        mainDisplay.setSize(new Dimension(800, 480));
        mainDisplay.setLocationRelativeTo(null);
        mainDisplay.revalidate();
        mainDisplay.setVisible(true);
    }

    private void refreshDisplay() {
        while (true) {
            try {
                String maybeImage = "";
                if (oldRelease != null) {
                    for (Image img : oldRelease.getImagesList()) {
                        if (img.getUri().length() > 0) {
                            maybeImage = img.getUri();
                        }
                    }
                }
                GetRecordResponse r = new Getter(getHost("recordgetter"), getPort("recordgetter")).getRecord(
                        oldRelease != null && (oldRelease.getImagesCount() == 0 || maybeImage.length() == 0));
                if (mainDisplay != null) {
                    if (r.getRecord().getMetadata().getCategory() == Category.STAGED_TO_SELL)
			mainDisplay.showRelease(r.getRecord().getRelease(), Color.RED, r.getDisk());
		    else if (r.getRecord().getMetadata().getCategory() == Category.PRE_HIGH_SCHOOL)
			mainDisplay.showRelease(r.getRecord().getRelease(), Color.BLUE, r.getDisk());
		    else if (r.getRecord().getMetadata().getCategory() == Category.PRE_FRESHMAN)
			mainDisplay.showRelease(r.getRecord().getRelease(), Color.YELLOW, r.getDisk());
                    else
                        mainDisplay.showRelease(r.getRecord().getRelease(), Color.GREEN, r.getDisk());
                    oldRelease = r.getRecord().getRelease();
                }
            } catch (Exception e) {
                // Ignore errors here
                e.printStackTrace();
            }

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void localServe() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                displayScreen();
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshDisplay();
            }
        });
        t.start();
    }
}
