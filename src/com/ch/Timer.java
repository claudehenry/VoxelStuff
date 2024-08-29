package com.ch;

import org.lwjgl.Sys;

/**
 * Measures and updates the frame rate (FPS), calculates the time delta between frames,
 * and keeps track of the total time elapsed since initialization. The class also
 * provides methods to retrieve these values.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * Calculates the current system time in seconds by multiplying the result of
	 * `Sys.getTime()` with 1000 and then dividing it by the timer resolution returned
	 * by `Sys.getTimerResolution()`. The resulting value is a long integer representing
	 * the elapsed time in seconds.
	 *
	 * @returns the current system time in seconds.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Initializes the last frame per second (FPS) by recording the current time using
	 * `getTimeS`. This sets a baseline for subsequent FPS calculations, indicating the
	 * starting point for measuring performance.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * Calculates the time difference between the current frame and the previous frame.
	 * It returns this difference as a float value, which is the elapsed time since the
	 * last frame. The result is used to control the game's speed or timing.
	 *
	 * @returns a floating-point value representing the time difference between frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * Updates the frames per second (FPS) counter by incrementing a `fps` variable and
	 * updating `currentFPS` when a second has elapsed since the last update, calculated
	 * using the `getTimeS()` method. The `lastFPS` timestamp is also updated accordingly.
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
	 * Returns a floating-point value named `delta`. The returned value is not modified
	 * or calculated within the function, suggesting that it may be a getter method
	 * retrieving an existing variable's state. The purpose of this function appears to
	 * be providing access to the external scope's `delta` variable.
	 *
	 * @returns a floating-point value representing the current value of the variable `delta`.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * Returns a floating-point value representing the current frame per second (FPS)
	 * rate. It does not perform any computations or modifications to the FPS data, instead
	 * simply retrieves and returns the existing `currentFPS` value. The returned value
	 * is read-only and static.
	 *
	 * @returns a floating-point value representing the current frames per second.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * Returns a float value representing the current time. The time is stored in the
     * `time` variable, which is not explicitly defined within the provided code snippet.
     *
     * @returns a `float` value representing the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * Updates the game's FPS and calculates a time delta by dividing the calculated delta
     * by 1000, ensuring it stays within the range [0, 1]. The accumulated time is
     * incremented by the updated delta. This process controls the game's timing.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
