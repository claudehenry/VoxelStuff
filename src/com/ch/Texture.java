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
 * is used to load and manage textures in a graphics program. It provides methods for
 * binding the texture to a specific sampler slot and getting the ID of the loaded
 * texture. The loadTexture method takes a file path as input and returns the ID of
 * the loaded texture.
 */
public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


	/**
	 * 0 in the code calls the `bind` method with the argument 0, which is used to bind
	 * a listener to an event.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * sets the active texture slot to a specific index (0-31) and binds a texture ID to
	 * that slot using the `glBindTexture()` method.
	 * 
	 * @param samplerSlot 0-based index of a texture slot in which to bind a texture handle.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * returns the current ID value of an object.
	 * 
	 * @returns an integer value representing the ID of the object.
	 */
	public int getID() {
		return id;
	}

	/**
	 * loads a 2D texture from a file and returns the generated texture ID. It reads the
	 * image data, creates a ByteBuffer for the pixel data, and passes it to the GL_TEXTURE_2D
	 * texture object using the `glTexImage2D` method. The function also generates mipmaps
	 * using the `glGenerateMipmap` method.
	 * 
	 * @param fileName file path of the image to be loaded as a texture.
	 * 
	 * @returns a texture ID generated using GL_TEXTURE_2D.
	 * 
	 * 	- The output is an integer, which represents the ID of the generated texture.
	 * 	- The ID is generated using the `GL11.glGenTextures()` function.
	 * 	- The texture is created as a 2D texture, with the `GL_TEXTURE_2D` target.
	 * 	- The texture has an RGBA8 internal format, which means it stores each pixel as
	 * a 32-bit value, representing the red, green, blue, and alpha channels.
	 * 	- The texture is repeatable in both the x and y directions, with the `GL_REPEAT`
	 * texture wrap mode.
	 * 	- The texture has a linear filter, with the `GL_LINEAR` minification and magnification
	 * filters.
	 * 	- A mipmap is generated using the `GL30.glGenerateMipmap()` function, which creates
	 * a pyramid of sub-textures at different levels of detail.
	 * 	- The mipmap has a linear filter, with the `GL_LINEAR_MIPMAP_LINEAR` texture wrap
	 * mode.
	 * 	- The texture also has a texture lod bias of -0.6, which controls the level of
	 * detail in the mipmap.
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
