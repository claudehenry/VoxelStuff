package com.ch;

import org.lwjgl.Sys;

public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

 /**
  * This function returns the current time measured by the operating system's timer
  * resolution divided by 1000 and multiplied by Sys.getTime()
  * 
  * 
  * @returns { long } The output of the function `getTimeS()` is a long value representing
  * the number of milliseconds since the Unix epoch (January 1st 1970 00:00:00 UTC),
  * based on the system's time and timer resolution.
  */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

 /**
  * The `init()` function sets the last frame render time to the current timestamp
  * using `getTimeS()`.
  */
	public static void init() {
		lastFPS = getTimeS();
	}

 /**
  * This function calculates the difference (delta) between the current time and the
  * previous frame's time.
  * 
  * 
  * @returns { float } The function calculates the difference between the current time
  * and the previous frame's time. The return value is a floating-point number that
  * represents the time passed since the last frame (in milliseconds).
  */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

 /**
  * This function updates the current frames per second (FPS) value and maintains a
  * running average of the FPS over the past 1 second. It does this by tracking the
  * time elapsed since the last frame and incrementing the FPS counter if the elapsed
  * time exceeds 1000 milliseconds.
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
  * This function returns the value of the static field `delta` of the current class.
  * 
  * 
  * @returns { float } The output returned by this function is `float.NaN` (Not a
  * Number) because the field `delta` is not initialized or defined.
  */
	public static float getDelta() {
		return delta;
	}

 /**
  * The function `getFPS()` returns the current frames per second (FPS) value of the
  * application.
  * 
  * 
  * @returns { float } The output returned by the `getFPS()` function is `undefined`.
  */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * The function `getTime()` returns the value of the field `time`, which is a static
     * variable set to `undefined`. Since the field `time` is undefined and the function
     * returns it as is without any manipulation or conversion., this function essentially
     * returns `undefined`.
     * 
     * 
     * @returns { float } The function `getTime()` returns the value of the field `time`,
     * which is `undefined`. Therefore the output returned by this function is `NaN` (Not
     * a Number) since `undefined` cannot be converted to a number.
     */
    public static float getTime() {
        return time;
    }

    /**
     * This function updates the game's timestep (time) based on the delta time (change
     * since last update) and the frame rate (FPS). It normalizes the delta time to be
     * between 0 and 1 and sets the time variable to the updated value.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }
}


