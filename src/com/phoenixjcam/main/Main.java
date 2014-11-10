package com.phoenixjcam.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.phoenixjcam.client.ClientGUI;
import com.phoenixjcam.client.ClientSet;
import com.phoenixjcam.gui.JumperGUI;

public class Main
{

	public static void main(String[] args)
	{
		int port = 9002;
		String host = "127.0.0.1";

		// blocked until server response
		ClientSet clientSet = new ClientSet(host, port);

		if (clientSet.getClientSocket() != null)
		{
			ClientGUI clientGUI = new ClientGUI(clientSet);

		}
		else
		{
			System.out.println("Couldn't connect to server");
		}

		// createGUI(clientSet);

		// new Thread(new ClientSide(host, port, clientGUI)).start();
	}

}
