package com.phoenixjcam.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import com.phoenixjcam.gui.JumperGUI;
import com.phoenixjcam.net.envelope.PlayerEnvelope;

/**
 * this. convention
 * 
 * @author Bart88
 *
 */
public class ClientGUI implements Runnable
{
	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private DefaultCaret caret;
	private String clientNick;
	private ClientSet clientSet;
	private JumperGUI jumperGUI;
	
	private boolean isAlone = true;
	
	private PlayerEnvelope playerEnvelope;

	public ClientGUI(ClientSet clientSet)
	{
		this.frame = new JFrame();
		this.clientSet = clientSet;
		// System.out.println(clientSet.readServerMsg());

		// block until read first msg from server
		this.clientNick = JOptionPane.showInputDialog(this.frame, this.clientSet.readServerMsg());
		clientSet.writeServerMsg(this.clientNick);

		this.frame.setTitle("Jumper3 - Nick name - " + this.clientNick);
		// try
		// {
		// clientSet.getObjectOutputStream().writeObject(this.clientNick);
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// }

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addTextArea(this.frame);
		this.getTextArea().append(clientSet.readServerMsg() + "\n");

		this.frame.setResizable(false);
		this.frame.setLocation(200, 200);
		this.frame.setSize(JumperGUI.WIDTH + 300, JumperGUI.HEIGHT);

		this.jumperGUI = new JumperGUI(clientSet, this);
		this.frame.add(jumperGUI, BorderLayout.CENTER);

		this.frame.setVisible(true);

		new Thread(this).start();
	}
	
	
	//synchronized dead lock :) between render thread and reader thread
	public PlayerEnvelope getPlayerEnvelope()
	{
		return playerEnvelope;
	}



	public synchronized void setPlayerEnvelope(PlayerEnvelope playerEnvelope)
	{
		this.playerEnvelope = playerEnvelope;
	}



	/**
	 * Get all msg's from server about new users and render them.
	 */
	@Override
	public void run()
	{
		synchronized (this)
		{
			while (true)
			{
				
//				String aa = this.clientSet.readServerMsg();
//				
//				System.out.println(aa);
				//renderOtherPlayer(new Point2D.Double(560, 340));
				
				PlayerEnvelope playerEnvelope = this.clientSet.readPlayerEnvelope();
				//this.getTextArea().append(msg + "\n");
				
				this.setPlayerEnvelope(playerEnvelope);
				
				this.getTextArea().append("Receive - " + playerEnvelope.getName() + "  " + playerEnvelope.getPosition().x + "  " + playerEnvelope.getPosition().y + "\n");
				
				System.out.println(playerEnvelope.getName());
				System.out.println(playerEnvelope.getPosition());
				
//				if(msg.startsWith("1"))
//				{
//					// position x
//					System.out.println("1");
//				}
//				
//				if(msg.startsWith("2"))
//				{
//					// position y
//					System.out.println("2");
//				}
//				
//				if(msg.startsWith("3"))
//				{
//					// position else
//					System.out.println("3");
//				}
				
				// char[] postX = new char[10];
				// msg.getChars(20, 23, postX, 0);

//				if (msg.contains("A new player"))
//				{
//					System.out.println("A new player");
//					isAlone = false;
//					// inform new player about me
//					this.clientSet.writeServerMsg("Already in game " + this.clientNick);
//					
//					
//					// render
//					//renderOtherPlayer();
//				}
//				
//				if (msg.contains("Already in game"))
//				{
//					System.out.println("Already in game");
//					isAlone = false;
//					// render
//					//renderOtherPlayer();
//				}
//				
//				if(isAlone == false)
//				{
//					
//				}
			}
		}
	}
	
	

	private void addTextArea(JFrame frame)
	{
		this.textArea = new JTextArea();
		this.textArea.setEditable(true);
		this.scrollPane = new JScrollPane(this.textArea);

		this.updateScrollPaneCaret();

		this.scrollPane.setPreferredSize(new Dimension(300, JumperGUI.HEIGHT));
		this.frame.add(this.scrollPane, BorderLayout.LINE_END);
	}

	public void updateScrollPaneCaret()
	{
		this.caret = (DefaultCaret) this.textArea.getCaret();
		this.caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// textArea.setC
	}

	public JTextArea getTextArea()
	{
		return this.textArea;
	}

	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}

	public String getClientNick()
	{
		return clientNick;
	}

}
