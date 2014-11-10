package com.phoenixjcam.player;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * 
 * @author Bart Bien
 *
 */
public class Player
{
	/** player position x, y */
	private Point position;

	/** player next position x, y */
	private Point nextPosition;

	/** player size as x - width and y - height */
	private Point size;

	private Physics physics;

	private Direcrion direcrion;

	/**
	 * Point size - size of player, x = width, y = height <br>
	 * Point2D.Double position - first position of player at the map
	 * 
	 * @param Point
	 *            size
	 * @param Point2D
	 *            .Double position
	 */
	public Player(Point size, Point position)
	{
		this.size = size;
		this.position = position;
		physics = new Physics();
		direcrion = new Direcrion();

		nextPosition = new Point(0, 0);

	}

	public Point getPosition()
	{
		return position;
	}

	/**
	 * first position of player at the map
	 * 
	 * @param position
	 */
	public void setPosition(Point position)
	{
		this.position = position;
	}

	public Point getNextPosition()
	{
		return nextPosition;
	}

	public void setNextPosition(Point nextPosition)
	{
		this.nextPosition = nextPosition;
	}

	public boolean isTurnedLeft()
	{
		return direcrion.turnedLeft;
	}

	public void setTurnedLeft(boolean turnedLeft)
	{
		direcrion.turnedLeft = turnedLeft;
	}

	public boolean isTurnedRight()
	{
		return direcrion.turnedRight;
	}

	public void setTurnedRight(boolean turnedRight)
	{
		direcrion.turnedRight = turnedRight;
	}

	public boolean isJumping()
	{
		return direcrion.jumping;
	}

	public void setJumping(boolean jumping)
	{
		direcrion.jumping = jumping;
	}

	/**
	 * When falling is done, set jumping.
	 */
	public void setJumping()
	{
		if (!direcrion.falling)
		{
			direcrion.jumping = true;
		}
	}

	public boolean isFalling()
	{
		return direcrion.falling;
	}

	public void setFalling(boolean falling)
	{
		direcrion.falling = falling;
	}

	public int getWidth()
	{
		return size.x;
	}

	public void setWidth(int width)
	{
		this.size.x = width;
	}

	public int getHeight()
	{
		return size.y;
	}

	public void setHeight(int height)
	{
		this.size.y = height;
	}

	public int getMoveSpeed()
	{
		return physics.moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed)
	{
		physics.moveSpeed = moveSpeed;
	}

	public int getFallingSpeed()
	{
		return physics.fallingSpeed;
	}

	public void setFallingSpeed(int fallingSpeed)
	{
		physics.fallingSpeed = fallingSpeed;
	}

	public int getJumpVector()
	{
		return physics.jumpVector;
	}

	public void setJumpVector(int jumpStart)
	{
		physics.jumpVector = jumpStart;
	}

	public void increaseSpeed()
	{
		if (physics.moveSpeed > 10)
			return;

		physics.moveSpeed += 1;
	}

	public void decreaseSpeed()
	{
		if (physics.moveSpeed < 2)
			return;
		physics.moveSpeed -= 1;
	}
}
