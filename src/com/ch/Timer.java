package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java utility class that provides real-time measurement of frames per second
 * (FPS) and elapsed time. It has several public static methods for calculating and
 * updating FPS, as well as getting the current FPS, time, and delta value. The class
 * uses Sys.getTime() and Sys.getTimerResolution() to measure time and calculate
 * delta, respectively.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * calculates the current Unix time in milliseconds by multiplying the system time
	 * in seconds by 1000 and dividing it by the timer resolution in seconds.
	 * 
	 * @returns a long value representing milliseconds since the Unix epoch.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * measures and stores the current FPS in a variable called `lastFPS`.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * calculates the time difference between two frames, represented by `time` and
	 * `lastFrame`, respectively, and returns it as a float value.
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
	 * updates the frames per second (FPS) counter every second, incrementing the FPS
	 * count by 1 and resetting it to 0 after 1 second has passed since the last update.
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
	 * retrieves the value of the `delta` variable, which is presumably used to compute
	 * some other value or perform an action.
	 * 
	 * @returns a floating-point value representing the difference between two values.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * retrieves the current frame rate as a floating-point number representing the frames
	 * per second (FPS) of an application.
	 * 
	 * @returns the current frame rate of the application in floating-point format.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returns the value of a `time` field, which is presumably used to represent a time
     * of day or other time-related metric.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates FPS, calculates and sets delta time, and increases the time variable by
     * the delta value within a range of 0 to 1.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
