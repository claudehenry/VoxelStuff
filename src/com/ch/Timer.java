package com.ch;

import org.lwjgl.Sys;

/**
 * Is designed to measure and update frames per second (FPS) with high accuracy. It
 * has several methods for calculating and updating FPS, as well as retrieving the
 * current value of FPS. The class also provides a method for retrieving the elapsed
 * time in milliseconds.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Converts the system's timestamp, measured in milliseconds, to a value in seconds
	 * by dividing it by the timer resolution.
	 * 
	 * @returns a long value representing the current time in milliseconds, calculated
	 * by dividing the system's current time in nanoseconds by the timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes a variable `lastFPS` with the current time, presumably for the purpose
	 * of measuring and tracking frames per second (FPS).
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Calculates the time difference between two points, represented as long values, and
	 * returns the result as a float value.
	 * 
	 * @returns a floating-point value representing the time elapsed since the last frame.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Updates frame rate by incrementing the current frame count and resetting the timer
	 * when a threshold time is reached.
	 */
	private static void updateFPS() {
		if (getTimeS() - lastFPS > 1000) {
			currentFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	/**
	 * Retrieves the value of a field called `delta`.
	 * 
	 * @returns a floating-point value representing the difference between two values.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Returns the current frame rate as a floating-point number.
	 * 
	 * @returns the current frame rate of the application.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Retrieves and returns the value of a static variable named `time`.
     * 
     * @returns a floating-point representation of time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Updates FPS, calculates delta time, and increments time.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
