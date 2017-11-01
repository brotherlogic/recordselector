package com.github.brotherlogic.recordselector;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.github.brotherlogic.javaserver.JavaServer;

import godiscogs.Godiscogs.Release;
import io.grpc.BindableService;

public class Runner extends JavaServer {

	MainDisplay mainDisplay = new MainDisplay();

	@Override
	public String getServerName() {
		return "RecordSelector";
	}

	@Override
	public List<BindableService> getServices() {
		return new LinkedList<BindableService>();
	}

	private void displayScreen() {
		System.out.println("DISPLAY");
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
				System.err.println("Refreshing");
				Release r = new Getter().getRecord(getHost("recordgetter"), getPort("recordgetter"));
				mainDisplay.showRelease(r);
				Thread.sleep(60 * 1000);
			} catch (Exception e) {
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

	public static void main(String[] args) throws Exception {
		Option optionHost = OptionBuilder.withLongOpt("server").hasArg().withDescription("Hostname of server")
				.create("s");
		Options options = new Options();
		options.addOption(optionHost);

		CommandLineParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		String rServer = "192.168.86.42";
		System.out.println("ARGS = " + Arrays.toString(args));
		if (line.hasOption("server"))
			rServer = line.getOptionValue("s");

		Runner r = new Runner();
		r.Serve(rServer);
	}
}
