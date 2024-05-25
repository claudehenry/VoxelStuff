package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java class that provides various functions for measuring time and frame rate.
 * It has several methods for calculating and updating frame rate, time, and delta.
 * The class also has a method for getting the current time and delta value.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * calculates the current time in milliseconds based on the system's timer resolution,
	 * multiplying the current time by 1000 and then dividing it by the timer resolution.
	 * 
	 * @returns a millisecond value representing the current time, calculated as the
	 * product of the current UNIX time and a resolution factor.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * records the current FPS at the time of execution using `getTimeS()`.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * measures the time elapsed between two frames and returns the difference as a float
	 * value.
	 * 
	 * @returns a floating-point value representing the time difference between two frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * updates the frame rate by incrementing a counter every 1 second, resetting it to
	 * 0 when the count reaches 1000. It also stores the current frame rate as a variable.
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
	 * retrieves the value of the `delta` field, which is a floating-point number
	 * representing the change in some quantity.
	 * 
	 * @returns a floating-point value representing the difference between two values.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * returns the current frames per second (FPS) as a floating-point value.
	 * 
	 * @returns the current frame rate of the application.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returns the value of a `time` field, which is not further defined in the given
     * code snippet.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates the FPS, calculates and limits the delta time, and adds it to the total time.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
