package com.ch.game;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * Is responsible for loading and managing textures from image files using OpenGL
 * libraries. It provides a way to bind and use these textures in rendering operations.
 * The class encapsulates the process of generating and configuring texture IDs,
 * buffer creation, and texture mapping.
 */
public class Texture {
	
	private String fileName;
	private int id;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = Texture.loadTexture(fileName);
	}

	/**
	 * Overrides the default method from the `Object` class and provides a way to release
	 * system resources when an object is being garbage-collected, but its implementation
	 * is optional and not guaranteed to be called by the JVM.
	 */
	@Override
	protected void finalize() {
	}

	/**
	 * Initializes a binding operation by calling another `bind` function with an argument
	 * of 0. This single-function implementation implies that it is delegating its
	 * responsibility to another method, likely for further processing or initialization
	 * purposes. The outcome depends on the behavior of the called `bind` function.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Activates a texture unit with a specified slot and binds a texture to it. It asserts
	 * that the provided slot is within a valid range (0-31) before performing the binding
	 * operation. The active texture unit is updated accordingly.
	 *
	 * @param samplerSlot 32-bit enumeration value that specifies the texture unit to
	 * bind the texture to.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Retrieves and returns the value of the variable `id`. It does not modify or
	 * manipulate the `id` in any way, simply exposing its current state to the caller.
	 * The returned value is an integer representation of the `id`.
	 *
	 * @returns an integer value representing a unique identifier or code.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Loads a texture from a file, converts it to a ByteBuffer, and returns an integer
	 * identifier for the newly created OpenGL texture. The function handles exceptions
	 * and prints stack traces if any errors occur during execution.
	 *
	 * @param fileName filename of the image file that is to be loaded and converted into
	 * a texture for use in an OpenGL application.
	 *
	 * @returns an integer ID of a generated texture.
	 *
	 * The output is an integer representing the unique identifier (id) assigned to the
	 * generated texture. It is used for referencing and binding the texture in subsequent
	 * OpenGL operations.
	 */
	private static int loadTexture(String fileName) {
		try {
			BufferedImage image = ImageIO.read(new File(fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();

			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];

					buffer.put((byte) ((pixel >> 16) & 0xFF));
					buffer.put((byte) ((pixel >> 8) & 0xFF));
					buffer.put((byte) ((pixel) & 0xFF));
					if (hasAlpha)
						buffer.put((byte) ((pixel >> 24) & 0xFF));
					else
						buffer.put((byte) (0xFF));
				}
			}

			buffer.flip();

			int id = GL11.glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, id);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

//			GL30.glGenerateMipmap(GL_TEXTURE_2D);
//			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			
//			if (GL11.glE)
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return 0;
	}

	
}
