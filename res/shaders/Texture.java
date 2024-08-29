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
 * Loads and manages textures from images. It provides methods for binding the texture
 * to a specific sampler slot and retrieving its ID. The class also contains a static
 * method loadTexture that generates and configures a texture based on an image file.
 */
public class Texture {
	
	private String fileName;
	private int id;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = Texture.loadTexture(fileName);
	}

	/**
	 * Releases system resources and performs cleanup operations when an object is about
	 * to be garbage-collected. It provides a mechanism for implementing the disposal of
	 * system resources held by objects when they are no longer referenced. The provided
	 * implementation does not perform any actual cleanup.
	 */
	@Override
	protected void finalize() {
	}

	/**
	 * Calls another function with argument 0, effectively binding or associating something
	 * to a starting point or index. The function appears to be part of an initialization
	 * process for an object or a data structure. The actual behavior depends on the
	 * implementation of the called function.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Specifies a texture to be used and sets it as the active texture for rendering.
	 * It takes an integer `samplerSlot` parameter that determines which texture unit to
	 * use. The function binds the texture with ID `id` to the specified sampler slot.
	 *
	 * @param samplerSlot 1-based index of the texture unit to be used for binding the
	 * specified texture.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Returns an integer value representing a unique identifier. It simply retrieves and
	 * returns the value stored in the variable `id`. The returned value is not modified
	 * or processed in any way, but rather passed back to the caller.
	 *
	 * @returns an integer value representing a unique identifier.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Loads a texture image from a file and generates a corresponding OpenGL texture ID.
	 * It converts the image data to a buffer, then binds and configures the OpenGL texture
	 * with parameters for wrapping, filtering, and uploading the pixel data.
	 *
	 * @param fileName name of the file to be loaded and converted into a texture, which
	 * is then used for OpenGL rendering.
	 *
	 * @returns a unique identifier for the loaded texture.
	 *
	 * The return value is an integer denoting a unique texture identifier (id) generated
	 * by OpenGL. This id can be used to identify and manipulate the loaded texture in
	 * subsequent graphics operations.
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
