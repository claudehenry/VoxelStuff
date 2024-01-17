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
  * The function `getTimeS()` returns the number of milliseconds that have elapsed
  * since the Java Virtual Machine (JVM) started running the program.
  * 
  * @returns The function `getTimeS()` returns a long value representing the number
  * of milliseconds since the start of the Java virtual machine (JVM) or the current
  * thread's starting time (if no JVM has been started), scaled by the timer resolution.
  * 
  * In other words: it gives the current time (in milliseconds) adjusted to be accurate
  * to the resolution of the system timer (which may vary).
  */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

 /**
  * The provided function `init()` measures the current system time (`getTimeS`) and
  * stores it as the initial value of a persistent variable `lastFPS`.
  */
	public static void init() {
		lastFPS = getTimeS();
	}

 /**
  * This function calculates the time delta (the amount of time that has passed since
  * the previous frame) by taking the current time and subtracting the previous frame's
  * time. It then returns the calculated delta as a float value.
  * 
  * @returns The output returned by this function is a float value representing the
  * time elapsed since the previous frame. The calculation takes into account the
  * current system time (getTimeS()) and the last known system time at the beginning
  * of the current frame (lastFrame), subtracting the latter from the former to obtain
  * the elapsed time. The result is then returned as a floating-point value.
  */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

 /**
  * This function updates the current FPS (frames per second) and records the last
  * time the frame was rendered. It calculates the frames rendered since the last
  * update and adds them to the total FPS.
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
  * This function returns the value of the field "delta" which is a float.
  * 
  * @returns The output returned by the function `getDelta()` is `float` value of
  * `delta`. In other words`, the function returns the current value of the field `delta`.
  */
	public static float getDelta() {
		return delta;
	}

 /**
  * This function returns the current Frames Per Second (FPS) of the application.
  * 
  * @returns The output returned by this function is `undefined`.
  */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * This function returns the value of the field "time".
     * 
     * @returns The output returned by this function is `undefined`.
     */
    public static float getTime() {
        return time;
    }

    /**
     * This function updates the elapsed time and frames per second (FPS) of an application
     * or game. It calculates the delta time since the last update based on the current
     * time and a scaling factor of 1000. It then limits the delta value to ensure it
     * remains within valid ranges (-1 to 1), and updates the elapsed time variable with
     * the result.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}


