package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java class that provides functions for tracking and calculating the frame
 * rate of an application. It has several static methods for calculating the current
 * frame rate, the delta time between frames, and the total time elapsed since the
 * initialization of the class. The class also has a private field for storing the
 * current frame rate and a method for updating the frame rate counter.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

 /**
  * calculates a timestamp in milliseconds based on the current system time and timer
  * resolution, returning the result in a long value.
  * 
  * @returns a long value representing the number of milliseconds since the epoch,
  * calculated by multiplying the current system time in seconds by 1000 and dividing
  * by the timer resolution.
  */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

 /**
  * initializes a variable `lastFPS` and sets its value to the current time, measured
  * in milliseconds.
  */
	public static void init() {
		lastFPS = getTimeS();
	}

 /**
  * calculates the time difference between two points, represented by `lastFrame` and
  * `time`, and returns the result as a floating-point number.
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
  * updates the frames per second (FPS) counter every 1000 milliseconds by incrementing
  * the current FPS, resetting the counter to zero when a threshold is met, and storing
  * the last FPS for future usage.
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
  * returns the value of `delta`.
  * 
  * @returns a floating-point value representing the difference between two values.
  */
	public static float getDelta() {
		return delta;
	}

 /**
  * returns the current frame rate as a floating-point value.
  * 
  * @returns the current frames per second (FPS) of the system.
  */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * retrieves the value of the `time` field, which is a floating-point number representing
     * current time.
     * 
     * @returns a floating-point representation of the current system time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates the FPS, calculates and stores the time delta, and sets the time to the
     * time delta.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
