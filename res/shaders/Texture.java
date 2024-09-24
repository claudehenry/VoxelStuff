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
 * Loads and manages graphics textures from image files using LWJGL OpenGL library.
 * It generates texture IDs, binds them to specific sampler slots, and sets up texture
 * filtering and wrapping parameters. The class facilitates texture rendering in a
 * game or graphical application.
 */
public class Texture {
	
	private String fileName;
	private int id;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = Texture.loadTexture(fileName);
	}

	/**
	 * Is called before an object's memory is reclaimed by the garbage collector when it
	 * is about to be destroyed. It provides a last chance for cleanup or resource
	 * deallocation. The method does not perform any action, as it has been left empty.
	 */
	@Override
	protected void finalize() {
	}

	/**
	 * Calls itself with an argument of 0, initiating a recursive process that sets up
	 * some sort of binding or initialization, potentially utilizing a method or function
	 * with the same name but different parameters based on the value passed. The exact
	 * purpose is unclear without context.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Binds a texture to a specific slot on the graphics processing unit (GPU). It takes
	 * an integer argument representing the sampler slot and ensures it is within a valid
	 * range. The function then activates the specified texture on the GPU using OpenGL
	 * calls.
	 *
	 * @param samplerSlot 0-based index of an active texture unit where a 2D texture is
	 * bound for use with OpenGL shaders.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Returns the value of a variable named `id`. The type of the return value is `int`,
	 * indicating an integer result. This function does not modify any state and simply
	 * provides access to the stored identifier.
	 *
	 * @returns an integer value representing a unique identifier.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Loads a texture from a file, converts it to RGBA format, and binds it to an OpenGL
	 * texture. It generates a unique ID for the texture and returns it, allowing for
	 * texture mapping in graphics applications. It catches exceptions and exits if loading
	 * fails.
	 *
	 * @param fileName 2D image file to be loaded as a texture.
	 *
	 * @returns a unique integer identifier for a loaded OpenGL texture.
	 *
	 * The output is an integer representing a texture ID. It uniquely identifies a texture
	 * object created using OpenGL. This ID can be used to manipulate and render the
	 * associated texture in future operations.
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
