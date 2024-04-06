package com.ch;

import org.lwjgl.Sys;

/**
 * TODO
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

 /**
  * multiplies the current time in milliseconds by a factor of 1000 and then divides
  * it by the timer resolution to provide a consistent measurement of time.
  * 
  * @returns a converted and scaled version of the current system time, represented
  * as a long value in milliseconds.
  */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

 /**
  * initializes a variable `lastFPS` with the current time using the `getTimeS()`
  * method, which is used to track the frame rate of an application.
  */
	public static void init() {
		lastFPS = getTimeS();
	}

 /**
  * calculates the time difference between two points, represented by `time` and
  * `lastFrame`, respectively, using the `getTimeS()` method. It then returns the
  * result as a float value.
  * 
  * @returns a floating-point number representing the time difference between two frames.
  */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

 /**
  * updates the frame rate by incrementing `currentFPS` every 1000 milliseconds,
  * resetting `fps` to zero when the time since the last update exceeds 1 second, and
  * storing the new value of `fps`.
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
  * returns the value of the `delta` field, which is a `float` variable storing an
  * unspecified value.
  * 
  * @returns a floating-point value representing the delta.
  */
	public static float getDelta() {
		return delta;
	}

 /**
  * returns the current frame rate as a floating-point number.
  * 
  * @returns the current frame rate in floats.
  */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returs a floating-point representation of the current time.
     * 
     * @returns a floating-point representation of time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates the frame rate, calculates and stores the time elapsed since the last
     * update, and scales the time value to the range [0, 1].
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
