package com.phoenixjcam.player;

/**
 * fields that specify player physics
 * 
 * @author Bart Bien
 * 
 */
public class Physics
{
	/** player movement speed - x axis */
	public int moveSpeed;

	/** player jump vector has to minus - y axis */
	public int jumpVector;

	/** player is turned to left - y axis */
	public int fallingSpeed;

	public Physics()
	{
		moveSpeed = 5;
		jumpVector = -14;
		fallingSpeed = 5;
	}
}