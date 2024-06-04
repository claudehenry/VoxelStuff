package com.ch;

import org.lwjgl.Sys;

/**
 * Provides a mechanism for measuring and updating frames per second (FPS). It has
 * several static methods: `init()`, `calculateDelta()`, `updateFPS()`, `getDelta()`,
 * `getFPS()`, and `getTime()`. These methods calculate and update various values
 * related to FPS, such as the current FPS, time elapsed, and delta. The class also
 * has several instance fields: `fps`, `lastFPS`, `lastFrame`, `time`, and `delta`.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Calculates the current time in milliseconds using the system's timer resolution
	 * and converts it to a value between 0 and 1000.
	 * 
	 * @returns a long value representing the current time in milliseconds, calculated
	 * from the system's timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes a variable `lastFPS` with the current time stamp using `getTimeS()` method.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Calculates the time difference between two points, represented by `lastFrame` and
	 * `time`, using the `getTimeS()` method. The resulting delta is returned as a float
	 * value.
	 * 
	 * @returns a float value representing the time difference between two frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Updates the framerate counter every 1 second, storing the current framerate and
	 * resetting it to 0 when a threshold is met.
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
	 * Retrieves the value of the `delta` field, which stores a floating-point number
	 * representing the difference between two values.
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
     * Retrieves the current time value stored as a `float` named `time`.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Updates frame rate, calculates and stores delta time, and increments time variable.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
