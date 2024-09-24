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
 * Is designed to load and manage textures in an OpenGL context. It provides functionality
 * for binding and accessing textures, with support for multiple sampler slots. The
 * class utilizes the LWJGL library for direct access to OpenGL functions.
 */
public class Texture {
	
	private String fileName;
	private int id;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = Texture.loadTexture(fileName);
	}

	/**
	 * Is called when an object becomes eligible for garbage collection. It does not have
	 * any functional code, effectively making it empty and doing nothing. This method
	 * was intended to be used for resource cleanup but has been largely replaced by
	 * try-with-resources statements and other best practices.
	 */
	@Override
	protected void finalize() {
	}

	/**
	 * Initializes a binding with default arguments or values by delegating to another
	 * overload that takes an integer parameter, which is set to 0 in this case. This
	 * allows for customization through overloading while providing a basic default
	 * behavior. It calls the `bind(0)` method.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Assigns a 2D texture to an active texture unit based on a provided sampler slot
	 * number, ensuring the sampler slot is within valid range and binds the corresponding
	 * texture with ID `id`. The active texture unit is determined by the sampler slot.
	 *
	 * @param samplerSlot 0-based index of the texture unit to be bound to.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Returns an integer value representing the ID. The ID is retrieved from the `id`
	 * field and exposed through the method, allowing it to be accessed externally. This
	 * enables the ID to be queried without modifying its underlying storage location.
	 *
	 * @returns an integer representing a unique identifier value stored in the variable
	 * `id`.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Loads an image file, converts its pixel data to a buffer, and creates a texture
	 * from it using OpenGL. It specifies the texture's wrapping, filtering, and storage
	 * format, then returns the texture ID. The function handles exceptions by printing
	 * the error message and exiting.
	 *
	 * @param fileName 2D image file to be loaded and processed by the function, which
	 * is used to create a texture.
	 *
	 * @returns a unique texture ID, typically an integer.
	 *
	 * The method returns an integer identifier for a texture created by OpenGL. This
	 * identifier can be used to bind the texture and perform further operations on it.
	 * The value is generated using glGenTextures().
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
