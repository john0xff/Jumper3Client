package com.phoenixjcam.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientSet
{
	private final static String NEW_LINE = "\n";
	private final static String END_OF_CONV = "END";

	private Socket clientSocket;

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	private boolean isOpen = false;

	public ClientSet(String host, int port)
	{
		try
		{
			clientSocket = new Socket(host, port);
			isOpen = true;

			objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			
			objectOutputStream.writeObject(Thread.currentThread().getId());
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

		// ActionListener runs in separated thread so on each user typing it'll do the trick
//		clientGUI.getUserText().addActionListener(new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				String clientMsg = e.getActionCommand();
//				clientGUI.getTextArea().append(clientMsg + NEW_LINE);
//
//				if (clientMsg.equals(END_OF_CONV))
//				{
//					isOpen = false;
//					clientGUI.getTextArea().append("Now you can close the application.");
//					clientGUI.getUserText().setEnabled(false);
//					clientGUI.getTextArea().setEnabled(false);
//				}
//
//				try
//				{
//					objectOutputStream.writeObject(clientMsg);
//				}
//				catch (IOException e1)
//				{
//					e1.printStackTrace();
//				}
//			}
//		});

//		try
//		{
//			while (true)
//			{
//				// setup msg from other clients and append them to main txt area
//
//				String broadcastMsg;
//
//				try
//				{
//					if ((broadcastMsg = objectInputStream.readObject().toString()) != null)
//					{
//						clientGUI.getTextArea().append(broadcastMsg + NEW_LINE);
//
//						if (broadcastMsg.equals("BYE"))
//							break;
//					}
//				}
//				catch (SocketException e)
//				{
//					System.err.println("SocketException - socket first level ex");
//					break;
//				}
//				catch (EOFException e) 
//				{
//					System.err.println("EOFException");
//					break;
//				}
//				catch (ClassNotFoundException | IOException e1)
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			}
//		}
//		finally
//		{
//			try
//			{
//				objectInputStream.close();
//				objectOutputStream.close();
//				clientSocket.close();
//				
//				clientSocket = null;
//			}
//			catch (IOException e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//		}
//
//	}

	public ObjectOutputStream getObjectOutputStream()
	{
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream)
	{
		this.objectOutputStream = objectOutputStream;
	}

	public ObjectInputStream getObjectInputStream()
	{
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream)
	{
		this.objectInputStream = objectInputStream;
	}
	
	

}
