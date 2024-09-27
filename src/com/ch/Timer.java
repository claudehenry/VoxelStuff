package com.ch;

import org.lwjgl.Sys;

/**
 * Tracks and calculates the elapsed time, frame rate, and time delta between frames.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Converts system time into milliseconds by multiplying the time in seconds by 1000
	 * and dividing by the timer resolution. This results in a time value in milliseconds,
	 * which is a common unit for measuring time in many applications. The timer resolution
	 * is used to ensure accurate conversion.
	 *
	 * @returns a time value in milliseconds, scaled from the system's timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Records the current time using the `getTimeS` method and stores it in the `lastFPS`
	 * variable.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Calculates the time difference between the current frame and the previous frame.
	 * It returns this time difference as a floating-point value. The calculation is based
	 * on the system time retrieved by the `getTimeS` method.
	 *
	 * @returns a floating-point value representing the time elapsed between the current
	 * and previous frames in milliseconds.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Tracks and updates the frames per second (FPS) of the application. It increments
	 * the FPS counter and, every 1000 milliseconds (1 second), resets the FPS counter
	 * and updates the current FPS value.
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
	 * Returns the value of a variable named `delta` as a floating-point number. The
	 * function is static, indicating it belongs to the class rather than an instance of
	 * the class. It does not modify the `delta` variable.
	 *
	 * @returns the value of the variable `delta`, a float.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Returns the current frames per second (FPS) value, which is presumably a static variable.
	 *
	 * @returns the current frames per second value, represented as a floating-point number.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Returns the current value of the static variable `time`, a float representing a
     * time measurement.
     *
     * @returns a static float value representing the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Calculates the time elapsed since the last frame, limits it to a valid range, and
     * adds it to the total time, while also ensuring the frame rate is updated.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
