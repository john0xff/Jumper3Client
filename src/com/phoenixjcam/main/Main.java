package com.phoenixjcam.main;

import com.phoenixjcam.client.ClientGUI;
import com.phoenixjcam.client.ClientSet;

public class Main
{

	public static void main(String[] args)
	{
		int port = 9002;
		String host = "127.0.0.1";
		// String host = "phoenixjcam.no-ip.biz";

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
	}

}
