package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java class that provides functions to measure the elapsed time and frame rate.
 * The class has several methods for calculating and updating the frame rate, as well
 * as accessing the current frame rate and elapsed time. These methods include
 * `getDelta()`, `getFPS()`, and `getTime()`.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

 /**
  * calculates seconds since the epoch by multiplying milliseconds by a resolution
  * factor and then dividing by 1000.
  * 
  * @returns a long value representing a time interval in milliseconds based on the
  * system's timer resolution.
  */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

 /**
  * initializes a variable `lastFPS` with the current time's value using the `getTimeS()`
  * method, which captures the FPS (frames per second) of the application at startup.
  */
	public static void init() {
		lastFPS = getTimeS();
	}

 /**
  * calculates the time difference between two points, represented by `lastFrame` and
  * `time`, respectively. The returned value is a floating-point number representing
  * the delta time in seconds.
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
  * updates the frames per second (FPS) count every 1 second, resetting the counter
  * when 10 seconds have passed since the last update.
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
  * retrieves the value of the `delta` field, which is a private static variable storing
  * an unknown value.
  * 
  * @returns a floating-point value representing the delta.
  */
	public static float getDelta() {
		return delta;
	}

 /**
  * retrieves the current frame rate per second (FPS) and returns it as a float value.
  * 
  * @returns the current frame rate of the application.
  */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returns the value of a `time` field, which is likely used to store the current
     * time or date.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates the frame rate, calculates and sets a delta time value, and increments a
     * time variable.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
