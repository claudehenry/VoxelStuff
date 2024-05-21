package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java class that provides functions for measuring time and frame rate. It has
 * several methods for calculating and updating frame rate, as well as getting the
 * current time and delta time. These methods include `getTime()`, `getFPS()`,
 * `update()`, `calculateDelta()`, and `init()`. The class also has instance variables
 * for tracking frame rate and time.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * converts milliseconds since the epoch to seconds since the epoch by dividing the
	 * input value by the timer resolution.
	 * 
	 * @returns a long value representing the current time in milliseconds, calculated
	 * by multiplying the current system time by 1000 and dividing it by the timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * initializes lastFPS, a variable storing the current frame rate, by calling the
	 * `getTimeS()` method and assigning the result to lastFPS.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * calculates the time difference between two points and returns it as a floating-point
	 * value. It takes advantage of the `getTimeS()` method to retrieve the current time
	 * in milliseconds and updates the `lastFrame` variable accordingly.
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
	 * updates the frame rate (FPS) by incrementing it when a second has passed since the
	 * last update and resetting it to zero when a thousand seconds have passed since the
	 * last update.
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
	 * returns the value of a variable named `delta`.
	 * 
	 * @returns a floating-point value representing the delta.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * retrieves and returns the current frame rate of a system in floating-point format.
	 * 
	 * @returns the current frame rate of the application in floating-point format.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * retrieves the current time value as a float.
     * 
     * @returns a floating-point representation of the current time in milliseconds.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates FPS, calculates and stores delta time in milliseconds, and updates the
     * overall time variable.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
