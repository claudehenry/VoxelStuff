package com.ch;

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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

/**
 * Loads a given image file and generates an OpenGL texture from it. It provides
 * methods to bind the texture to a specific sampler slot for rendering. The class
 * also handles error handling during the loading process.
 */
public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


	/**
	 * Invokes another overloaded version named `bind(int)`, passing an integer argument
	 * with a value of 0. This implies that the `bind(int)` method is responsible for
	 * performing some binding operation, and the `bind()` function simply delegates this
	 * task to it with a default parameter.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Binds a texture to a specific sampler slot using OpenGL commands. It activates the
	 * specified texture and binds it to the target texture ID. The function also ensures
	 * that the sampler slot is within the valid range of 0 to 31.
	 *
	 * @param samplerSlot 1-based index of the sampler slot to bind the texture to, and
	 * it is used to determine the active texture unit for binding the texture.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Retrieves the value of an integer variable named `id`. The returned value is the
	 * current state of the `id` variable, which is not modified within the function
	 * itself. This method simply returns the existing value without performing any
	 * calculations or operations.
	 *
	 * @returns an integer value representing the identifier stored in the `id` variable.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Loads a specified image file into a texture buffer and returns its ID. It reads
	 * the image, converts it to a ByteBuffer, binds the texture, sets parameters for
	 * filtering and wrapping, uploads the data to OpenGL, and generates mipmaps.
	 *
	 * @param fileName name of the file to be loaded as a texture, which is then used to
	 * read the image and generate a corresponding OpenGL texture object.
	 *
	 * @returns an integer identifier of a loaded texture.
	 *
	 * The output is an integer value representing the ID of the generated texture in
	 * OpenGL. The ID can be used to bind and manipulate the texture later in the program.
	 */
	private static int loadTexture(String fileName) {
		try {
			BufferedImage image = ImageIO.read(new File(fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

			ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();

			for (int y = image.getHeight() - 1; y >= 0; y--) {
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

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -.6f);
			
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return 0;
	}

}
