package com.ch;

import org.lwjgl.Sys;

/**
 * Is designed to manage game performance metrics and update timer values. It maintains
 * a frame rate counter and calculates delta time between frames. The class provides
 * methods for updating and retrieving these metrics.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Returns a value representing system time in milliseconds based on the current timer
	 * resolution. It does so by multiplying the current system time by 1000 and dividing
	 * by the current timer resolution. The result is a precise representation of system
	 * time in milliseconds.
	 *
	 * @returns a system time in milliseconds.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes a variable representing the last time the system's clock was recorded.
	 * It records the current time using the `getTimeS()` method and stores it in the
	 * `lastFPS` variable. This is used to track frame rate over time.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Computes the elapsed time between two consecutive frames by subtracting the previous
	 * frame's time from the current frame's time and returns the result as a float. It
	 * also updates the lastFrame variable to the current frame's time for future
	 * calculations. The result is in milliseconds.
	 *
	 * @returns a float representing time elapsed between two consecutive frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Resets and updates the frame rate by incrementing a counter every millisecond,
	 * capturing the current frame rate when one second has passed since the last update,
	 * and storing it as the average frame rate over the interval. It then increments the
	 * total frame count for that period.
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
	 * Returns a floating-point value representing the `delta` variable. The `delta`
	 * variable is not defined within this snippet, implying it is an external or private
	 * variable. This accessor method provides read-only access to its value.
	 *
	 * @returns the value of a private static variable `delta`.
	 * It represents a fixed or calculated quantity. The value is likely a constant.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Returns the current frames per second value. The value is likely stored as a class
	 * variable or constant, allowing for access and retrieval by other parts of the
	 * program. This facilitates monitoring or display of frame rate performance within
	 * an application.
	 *
	 * @returns a floating-point representation of the current frames per second (FPS).
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Returns a floating-point value representing a time measurement. The time is stored
     * as a class variable, implying that it may be updated elsewhere in the code. The
     * function provides read-only access to the current time measurement.
     *
     * @returns a constant floating-point value stored in the variable `time`.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Calculates a time increment based on frame rate, limits it to the range 0-1, and
     * adds it to the total game time. It performs calculations from an FPS update method
     * and a delta calculation method. The resulting time increment is used for gameplay
     * progression.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
