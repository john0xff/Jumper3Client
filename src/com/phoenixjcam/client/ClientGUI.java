package com.phoenixjcam.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.phoenixjcam.gui.JumperGUI;

/**
 * this. convention
 * @author Bart88
 *
 */
public class ClientGUI
{
	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private DefaultCaret caret;

	public ClientGUI(ClientSet clientSet)
	{
		this.frame = new JFrame("Jumper");

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addTextArea(this.frame);

		this.frame.setResizable(false);
		this.frame.setLocation(200, 200);
		this.frame.setSize(JumperGUI.WIDTH + 300, JumperGUI.HEIGHT);

		this.frame.add(new JumperGUI(clientSet, this), BorderLayout.CENTER);

		this.frame.setVisible(true);
	}

	private void addTextArea(JFrame frame)
	{
		this.textArea = new JTextArea();
		this.textArea.setEditable(true);
		this.scrollPane = new JScrollPane(textArea);
		
		this.updateScrollPaneCarret();
		
		this.scrollPane.setPreferredSize(new Dimension(300, JumperGUI.HEIGHT));
		this.frame.add(this.scrollPane, BorderLayout.LINE_END);
	}

	public void updateScrollPaneCarret()
	{
		this.caret = (DefaultCaret) this.textArea.getCaret();
		this.caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public JTextArea getTextArea()
	{
		return this.textArea;
	}

	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}

}
