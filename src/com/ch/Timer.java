package com.ch;

import org.lwjgl.Sys;

/**
 * Is designed to track and measure elapsed time, frames per second, and other
 * performance metrics in real-time.
 * It provides functionality for initializing, updating, and retrieving these metrics
 * through various methods.
 * The class appears to be optimized for use with the LWJGL library's timing capabilities.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Returns the system time in milliseconds, calculated by multiplying the current
	 * time obtained from `Sys.getTime()` with 1000 and dividing it by the timer resolution
	 * retrieved from `Sys.getTimerResolution()`. This conversion is done to provide a
	 * uniform time unit. The result is returned as a long value.
	 *
	 * @returns a time value in milliseconds.
	 * It represents the system's current time, scaled to millisecond resolution.
	 * This value may include fractional seconds due to timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes the `lastFPS` variable with the current time, measured in some unit
	 * based on the `getTimeS()` method. This is presumably for tracking and calculating
	 * frames per second (FPS) statistics. The purpose is to establish a baseline time value.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Measures the elapsed time between consecutive frames, returning the difference as
	 * a floating-point number. The elapsed time is calculated by subtracting the previous
	 * frame's timestamp from the current frame's timestamp. The result is updated and
	 * returned as a delta value in milliseconds.
	 *
	 * @returns a float representing time elapsed between frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Updates the current frames per second (FPS) value by incrementing a counter and
	 * resetting it periodically to store the average FPS over time. It resets the count
	 * every 1000 milliseconds, calculating the average FPS since the last reset. The
	 * average FPS is stored in the `fps` variable.
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
	 * Returns a float value representing a predefined variable named `delta`. The return
	 * value is directly accessed from the `delta` variable without any calculation or
	 * modification. It appears to be a simple accessor method for retrieving the current
	 * value of `delta`.
	 *
	 * @returns a float value representing the delta variable's current state.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Returns the current frame per second value. It provides read-only access to a
	 * variable representing the current frames per second. The result is returned as a
	 * floating-point number.
	 *
	 * @returns a float representing the current frames per second value.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Retrieves a static variable's value. It returns the current value of `time`, which
     * is presumably a pre-initialized or stored value representing some sort of timestamp
     * or elapsed time. The returned value is a floating-point number.
     *
     * @returns a static floating-point value representing elapsed time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Updates game-related variables by first calling `updateFPS`, calculates and adjusts
     * a time step (`delta`) based on elapsed time, then adds it to a running total
     * (`time`). The `delta` is clamped between 0 and 1.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
