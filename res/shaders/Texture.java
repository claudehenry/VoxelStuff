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
 * Loads and manages image textures for OpenGL rendering. It creates a texture from
 * an image file using the ImageIO library and stores it in an OpenGL texture object.
 * The class provides methods to bind and retrieve the texture ID.
 */
public class Texture {
	
	private String fileName;
	private int id;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = Texture.loadTexture(fileName);
	}

	/**
	 * Performs automatic cleanup when an object is about to be garbage collected.
	 * It allows an object to free resources before being destroyed.
	 * It may not be called at all if the object is not garbage collected.
	 */
	@Override
	protected void finalize() {
	}

	/**
	 * Binds an object with default index value of 0, implying it is a call to another
	 * overloaded `bind` method. The actual binding operation depends on the implementation
	 * of the other method. Binding results in the establishment of a connection or
	 * association between two objects.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Sets up a texture for use by OpenGL. It selects an active texture unit based on a
	 * given sampler slot and then binds a specified 2D texture ID to that unit. The
	 * assertion ensures the samplerSlot is within valid range.
	 *
	 * @param samplerSlot 32-bit texture slot where the bound texture will be accessed
	 * for rendering purposes.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Returns an integer value representing a unique identifier stored in the variable
	 * `id`. It is a getter method, providing direct access to the `id` field for other
	 * parts of the program. The returned value can be used or manipulated as needed.
	 *
	 * @returns an integer value representing a unique identifier.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Loads a texture from a file, converts it into a ByteBuffer format suitable for
	 * OpenGL, and generates a new OpenGL texture ID. It sets various texture parameters
	 * to optimize rendering performance. On failure, it prints the exception stack trace
	 * and exits the program.
	 *
	 * @param fileName 2D image file to be loaded into OpenGL as a texture, with its
	 * contents used for generating an OpenGL texture object.
	 *
	 * @returns a generated OpenGL texture ID.
	 *
	 * The return type is an integer representing a unique OpenGL texture identifier. The
	 * value 0 indicates failure to load the texture due to an exception. Otherwise, the
	 * integer represents the generated texture ID that can be used for rendering purposes.
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
