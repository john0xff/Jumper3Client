package com.phoenixjcam.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.phoenixjcam.client.ClientGUI;
import com.phoenixjcam.client.ClientSet;
import com.phoenixjcam.map.GameMap;
import com.phoenixjcam.movement.Movement;
import com.phoenixjcam.player.Player;

/**
 * contend: game loop, running at new thread (66 FPS) and main method
 * 
 * @author Bart Bien
 * 
 */
public class GamePanel extends JPanel implements Runnable, KeyListener
{
	private static final long serialVersionUID = 1L;

	// size of frame
	public static final int WIDTH = 750;
	public static final int HEIGHT = 440;

	/** game loop run at new thread */
	private Thread thread;
	private boolean running;

	/** place to render game */
	private BufferedImage image;

	/** graphic to draw at image */
	private Graphics2D g2D;

	private GameMap map;
	private Movement movement;
	private Player player;

	private DrawTime drawTime;

	private ClientSet clientSet;
	private ClientGUI clientGUI;

	private static int counterServer = 0;

	private boolean autoLeft = true;
	private boolean autoRight = false;

	public GamePanel(ClientSet clientSet, ClientGUI clientGUI)
	{
		this.clientGUI = clientGUI;
		this.clientSet = clientSet;
		drawTime = new DrawTime();
		setFocusable(true);
		addKeyListener(this);

		new Thread(this).start();
	}

	@Override
	public void run()
	{
		synchronized (this)
		{
			init();

			while (running)
			{
				drawTime.start = System.currentTimeMillis();

				update();
				render();
				renderOtherPlayer();
				draw();

				if (autoLeft)
				{
					autoLeftMovement();
				}

				if (!autoLeft)
				{
					autoRightMovement();
				}
				drawTime.delay = (System.currentTimeMillis() - drawTime.start);
				drawTime.wait = drawTime.target - drawTime.delay;

				// after focusing on different component like - textArea in this case it require come back to game
				this.requestFocus();

				// this.clientGUI.updateScrollPaneCaret(); doesn't work - caret is lost after activating text area
				// component

				if (drawTime.wait > 0)
				{
					try
					{
						Thread.sleep(drawTime.wait);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void autoLeftMovement()
	{
		int x = movement.getCurrentPosition().x;

		if (x >= 61)
			player.setTurnedLeft(true);

		if (x == 61)
		{
			autoLeft = false;
			player.setTurnedLeft(false);
		}

	}

	private void autoRightMovement()
	{
		int x = movement.getCurrentPosition().x;

		if (x <= 689)
			player.setTurnedRight(true);

		if (x == 689)
		{
			autoLeft = true;
			player.setTurnedRight(false);
		}
	}

	/** initialize game components */
	private void init()
	{
		running = true;

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2D = (Graphics2D) image.getGraphics();

		// jumperMap.txt file 15 width x 8 height (2 first line of file says size of map)
		// square size 50 x 50
		// frame size 15 * 50 = 750 px width at 8 * 50 = 400 height - frame made as static size
		map = new GameMap(50);

		player = new Player(new Point(22, 22), new Point(150, 150));

		movement = new Movement(map, player, clientSet, this.clientGUI);
	}

	private void update()
	{
		movement.update();
	}

	private void render()
	{
		map.draw(g2D);
		movement.draw(g2D);
	}

	/** All rendered stuff in buffered image, draw at current component. */
	private void draw()
	{
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public synchronized void renderOtherPlayer()
	{
		// player color
		g2D.setColor(Color.BLUE);

		int width = 22;
		int height = 22;

		// left corner of player (square)
		// int x = (int) (position.x - width / 2);
		// int y = (int) (position.y - height / 2);

		if (this.clientGUI.getPlayerEnvelope() != null)
		{
			int x = (int) (this.clientGUI.getPlayerEnvelope().getPosition().x - width / 2);
			int y = (int) (this.clientGUI.getPlayerEnvelope().getPosition().y - height / 2);
			g2D.fillRect(x, y, width, height);
		}

	}

	/**
	 * One instance of this g2D will be in use for many threads.
	 * 
	 * @return
	 */
	public synchronized Graphics2D getG2D()
	{
		return g2D;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		Point position = player.getPosition();

		if (key == KeyEvent.VK_LEFT)
		{
			player.setTurnedLeft(true);
		}
		else if (key == KeyEvent.VK_RIGHT)
		{
			player.setTurnedRight(true);
		}

		if (key == KeyEvent.VK_SPACE)
		{
			player.setJumping();
		}
		// new position
		if (key == KeyEvent.VK_N)
		{
			player.setPosition(new Point(600, 70));
		}
		// increase 1.0 to player speed
		if (key == KeyEvent.VK_P)
		{
			player.increaseSpeed();
		}
		// decrease 1.0 from player speed
		else if (key == KeyEvent.VK_M)
		{
			player.decreaseSpeed();
		}
	}

	private void sendPlayerPosition(Point2D.Double position)
	{
		Point2D.Double pos = new Point2D.Double(position.x, position.y);

		try
		{
			// clientSet.getObjectOutputStream().writeObject(counterServer);
			// clientSet.getObjectOutputStream().writeObject(position);
			clientSet.getObjectOutputStream().writeObject(pos);
			counterServer++;
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
		{
			player.setTurnedLeft(false);
		}
		else if (key == KeyEvent.VK_RIGHT)
		{
			player.setTurnedRight(false);
		}
	}

	/** All fields should be initialized by using milliseconds */
	private class DrawTime
	{
		/**
		 * specify time that thread will be wait (milliseconds). <br>
		 * As default target = 15 ms (effect will be 1000 / 15 = 66 FPS)
		 */
		public final long target = 15;
		/** start time */
		public long start;
		/** specify time that graphic objects need to done */
		public long delay;
		/** calculated time that current thread will be wait to next iteration */
		public long wait;

		public DrawTime()
		{
		}
	}

}
