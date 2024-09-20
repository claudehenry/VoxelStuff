package com.ch;

import org.lwjgl.Sys;

/**
 * Measures and updates frame rates and elapsed time in real-time using system timing
 * functionality.
 * It provides means to calculate frame rate and delta time for use in game or animation
 * loops.
 * The class is designed for use in a single-threaded environment with LWJGL dependencies.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Returns a time value in milliseconds by multiplying the current system time in
	 * seconds by 1000 and dividing it by the timer resolution. This calculates an
	 * approximate millisecond time based on system clock ticks. The result is returned
	 * as a long data type.
	 *
	 * @returns a numerical representation of current time in milliseconds.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes a variable `lastFPS` by recording the current system time using the
	 * `getTimeS()` method, effectively capturing the initial timestamp for subsequent
	 * FPS calculations. This operation serves as a starting point to measure frame rate
	 * performance over time. The timestamp is stored in a static context.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Computes the elapsed time since the previous frame. It retrieves the current
	 * timestamp, calculates the difference with the last recorded frame time, updates
	 * the last frame time to the current value, and returns the time delta as a float.
	 * The delta is cast from an integer.
	 *
	 * @returns a float representing the elapsed time between frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Tracks and updates the frames per second (FPS) metric by incrementing a counter,
	 * resetting it when one second has passed since last update, and storing the average
	 * FPS over time.
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
	 * Returns a floating-point value representing a delta, indicating a difference or
	 * change. The function is declared as static, suggesting it operates on class-level
	 * data rather than instance data. It appears to provide access to a pre-computed
	 * delta value.
	 *
	 * @returns a single floating-point value representing an unknown quantity named `delta`.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Retrieves and returns the current Frames Per Second value, which is a measure of
	 * the frame rate. It provides an accessor for the `currentFPS` variable, allowing
	 * it to be accessed from other parts of the program. The returned value represents
	 * the current frame rate in floating-point format.
	 *
	 * @returns a floating-point number representing the current frames per second value.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Returns a floating-point value representing the current time, accessing it from
     * an external variable named `time`. The function is declared as `static`, indicating
     * it can be accessed without creating an instance of the class. It does not modify
     * the `time` variable.
     *
     * @returns a fixed floating-point value stored in the `time` variable.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Calculates and updates the game's timestamp, taking into account the average frame
     * rate per second. It limits the calculated delta to a valid range (0-1) to prevent
     * errors. The updated time is then incremented by the delta value.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
