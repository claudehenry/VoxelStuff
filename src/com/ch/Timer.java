package com.ch;

import org.lwjgl.Sys;

/**
 * Tracks and updates frame rate and timing data. It calculates the difference between
 * consecutive frames (delta) and accumulates this value to calculate the average
 * frame rate. The class also provides methods for retrieving the current frame rate
 * and time.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Returns a value representing system time in milliseconds, calculated by multiplying
	 * the result from `Sys.getTime()` with 1000 and then dividing it by `Sys.getTimerResolution()`.
	 * This converts the system time to milliseconds with millisecond resolution.
	 *
	 * @returns a Unix timestamp in milliseconds.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes a variable `lastFPS` with the current time obtained from the `getTimeS`
	 * method, likely for tracking and measuring frame rates or performance metrics. This
	 * sets the baseline for subsequent measurements. The time is captured at the start
	 * of an application or game.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Calculates the difference between the current and previous time points, represented
	 * by `lastFrame`. It updates `lastFrame` with the current time point after calculation.
	 * The result is returned as a floating-point number representing the time delta.
	 *
	 * @returns a float value representing the time difference between frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Updates and calculates the current frames per second (FPS) rate by incrementing a
	 * counter every time the function is called, and then resets it to zero and updates
	 * the last FPS timestamp when 1000 milliseconds have passed since the previous update.
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
	 * Returns a floating-point value representing the delta. The method does not perform
	 * any calculations or modifications, instead it simply retrieves and returns an
	 * existing value named `delta`.
	 *
	 * @returns a floating-point value of `delta`.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Returns a floating-point value representing the current Frames Per Second (FPS)
	 * metric. It retrieves and outputs the stored `currentFPS` value without performing
	 * any calculations or modifications. The result is a direct read-only access to the
	 * `currentFPS` variable.
	 *
	 * @returns a float value representing the current frame per second count.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Retrieves and returns a floating-point value named `time`. This value is not defined
     * within the provided code snippet, implying it may be an instance variable or passed
     * as an argument from another method. The purpose of this function appears to be
     * accessing and returning the current time.
     *
     * @returns a float value representing the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Calculates and applies a time increment to maintain a consistent frame rate,
     * ensuring that game logic runs at a stable pace. It calls sub-functions to calculate
     * the frame per second (FPS) and the delta time between frames, then adjusts the
     * time accordingly.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
