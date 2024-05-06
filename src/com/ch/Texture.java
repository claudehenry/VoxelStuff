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
 * in this code loads and manages textures for use in a graphics pipeline. It takes
 * a filename as its constructor parameter, loads the texture from the file using
 * `ImageIO.read()`, and then binds the texture to the GPU using `glBindTexture()`.
 * The class also provides methods for binding the texture to specific sampler slots
 * (`bind()` and `bind(int)`) and returning the ID of the loaded texture (`getID()`).
 */
public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


	/**
	 * 0 is called, performing an operation related to binding.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * sets the active texture slot to a specific index (0-31) and binds a texture using
	 * the specified slot and ID.
	 * 
	 * @param samplerSlot 0-based index of a texture slot that the function binds to the
	 * current GL context, with valid values ranging from 0 to 31.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * retrieves the value of a class instance field `id`.
	 * 
	 * @returns an integer representing the ID of the object.
	 */
	public int getID() {
		return id;
	}

	/**
	 * loads an image file into a texture object, using ImageIO to read the image and
	 * GL11 to create and bind the texture. It then sets texture wrapping and filtering
	 * parameters and calls `glTexImage2D` to upload the image data to the GPU. Finally,
	 * it generates mipmap levels and sets the texture's minimum filter to linear-mipmap
	 * linear.
	 * 
	 * @param fileName name of the texture file to be loaded and read by the function.
	 * 
	 * @returns an integer identifier of a generated texture.
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
