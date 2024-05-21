package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java class that provides functions for measuring and updating frames per
 * second (FPS) and time. The class has several methods for calculating and updating
 * FPS, as well as retrieving the current FPS, time, and delta value. These methods
 * include `init()`, `calculateDelta()`, `updateFPS()`, `getDelta()`, `getFPS()`, and
 * `getTime()`.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * multiplies the current time in milliseconds by a factor of 1000 and then divides
	 * it by the timer resolution to obtain a value representing the time in seconds.
	 * 
	 * @returns a long value representing the current time in milliseconds, adjusted for
	 * timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * measures the last frame rate of the program using the `getTimeS()` method and
	 * stores it in the `lastFPS` variable.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * calculates the time difference between two points, represented by `time` and
	 * `lastFrame`, using the `getTimeS()` method. The result is returned as a float value.
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
	 * updates the frame rate counter by incrementing it every second, storing the previous
	 * value for later use, and checking for updates every 1000 milliseconds.
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
	 * retrieves the value of the `delta` field, which is a floating-point number.
	 * 
	 * @returns a `float` value representing the delta between two values.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * calculates and returns the current frame rate of an application in floating-point
	 * numbers.
	 * 
	 * @returns the current frame rate of the system in floating-point format.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returns the value of a `time` field, which is likely used to store a current time
     * stamp or other timing information.
     * 
     * @returns a floating-point representation of the current time in seconds.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates FPS, calculates and limits delta time, and increments time.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
