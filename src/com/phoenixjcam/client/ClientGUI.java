package com.phoenixjcam.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
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

	public ClientGUI(ClientSet clientSet)
	{
		this.frame = new JFrame("Jumper");
		this.clientSet = clientSet;
		// System.out.println(clientSet.readServerMsg());

		// block until read first msg from server
		this.clientNick = JOptionPane.showInputDialog(this.frame, this.clientSet.readServerMsg());
		clientSet.writeServerMsg(this.clientNick);

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

		this.frame.add(new JumperGUI(clientSet, this), BorderLayout.CENTER);

		this.frame.setVisible(true);

		new Thread(this).start();
	}

	/**
	 * Get all msg's from server about new users and render them.
	 */
	@Override
	public void run()
	{
		while (true)
		{
			String msg = this.clientSet.readServerMsg();
			this.getTextArea().append(msg + "\n");
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
