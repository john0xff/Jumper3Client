package com.phoenixjcam.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.phoenixjcam.client.ClientSet;
import com.phoenixjcam.gui.JumperGUI;

public class Main
{
	public static void main(String[] args)
	{
		int port = 9002;
		String host = "127.0.0.1";

		ClientSet clientSet = new ClientSet(host, port);

		createGUI(clientSet);


		// new Thread(new ClientSide(host, port, clientGUI)).start();
	}

	/**
	 * Game frame
	 * 
	 * @param clientSide2
	 */
	public static void createGUI(ClientSet clientSet)
	{

		JFrame frame = new JFrame("Jumper");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addTextArea(frame);
		frame.add(new JumperGUI(clientSet), BorderLayout.CENTER);
		// frame.setContentPane();

		frame.setResizable(false);
		frame.setLocation(200, 200);
		frame.setSize(JumperGUI.WIDTH + 300, JumperGUI.HEIGHT);

		frame.setVisible(true);
	}

	private static void addTextArea(JFrame frame)
	{

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300, JumperGUI.HEIGHT));
		frame.add(scrollPane, BorderLayout.LINE_END);

	}
}
