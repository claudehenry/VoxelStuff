package com.ch;

import org.lwjgl.Sys;

/**
 * provides a way to measure and update the frame rate of an application. It has
 * several methods for calculating and updating the frame rate, including `init()`,
 * `calculateDelta()`, `updateFPS()`, and `update()`. These methods allow for the
 * measurement of time and the calculation of the delta between frames, as well as
 * the updating of the frame rate counter.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * calculates the seconds since the Unix epoch (January 1, 1970, 00:00:00 UTC) based
	 * on the current system time and timer resolution.
	 * 
	 * @returns a long value representing the current time in milliseconds, adjusted for
	 * timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * initializes a variable `lastFPS` with the current value of `getTimeS()` at the
	 * moment of execution, storing the FPS of the application for future reference.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * calculates the time difference between two points and returns it as a float value.
	 * It takes advantage of the `getTimeS()` method to retrieve the current time and
	 * compares it with the previous frame time, stored in the `lastFrame` field.
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
	 * updates the frame rate by incrementing a counter every second, storing the current
	 * frame rate and resetting it after 10 seconds have passed since the last update.
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
	 * returns the `delta` variable, which is a `float` value that contains the difference
	 * between two values.
	 * 
	 * @returns a floating-point value representing the delta.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * retrieves the current frame rate per second (FPS) and returns it as a floating-point
	 * number.
	 * 
	 * @returns the current frame rate of the application in floats.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * retrieves the value of a `time` field, returning it as a floating-point number.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates FPS, calculates and stores delta time, and increments time.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
