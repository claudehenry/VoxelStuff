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
 * TODO
 */
public class Texture {
	
	private String fileName;
	private int id;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = Texture.loadTexture(fileName);
	}

 /**
  * does nothing, as it is already null by design.
  */
	@Override
	protected void finalize() {
	}

 /**
  * 0 invokes the method `bind` on the current object, passing the argument `0`.
  */
	public void bind() {
		bind(0);
	}

 /**
  * sets the active texture slot in the GPU's texture unit to a specific texture ID.
  * 
  * @param samplerSlot 0-based index of a texture slot to bind to the current active
  * texture context.
  */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

 /**
  * returns the `id` field's value.
  * 
  * @returns the value of the `id` field.
  */
	public int getID() {
		return id;
	}

 /**
  * loads a texture from a file and returns its ID for use in OpenGL rendering. It
  * reads the image data, converts it to an OpenGL texture format, and stores it in a
  * buffer. The function then creates a new texture and sets its parameters before
  * copying the image data into it. Finally, it returns the texture ID.
  * 
  * @param fileName file path of the image to load as a texture.
  * 
  * 	- `fileName`: A string representing the file path of the image to be loaded.
  * 	- `ImageIO.read(new File(fileName))`: This method reads the image from the specified
  * file location and returns a `BufferedImage` object.
  * 	- `int[] pixels`: An array of integers representing the pixel values of the image.
  * 	- `image.getWidth()` and `image.getHeight()`: These methods return the width and
  * height of the `BufferedImage` object, respectively.
  * 	- `image.getColorModel().hasAlpha()`: This method checks whether the image has
  * an alpha channel (i.e., transparency).
  * 	- `ByteBuffer buffer`: A byte buffer used to store the pixel values of the image.
  * 	- `GL11.glGenTextures()`: This method creates a new texture ID for the loaded image.
  * 	- `glBindTexture(GL_TEXTURE_2D, id)`: This method binds the newly created texture
  * ID to the current GL context.
  * 	- `glTexParameteri(GL_TEXTURE_2D, ...)`: These methods set various texture
  * parameters such as wrapping, filtering, and mipmap generation.
  * 	- `glTexImage2D(GL_TEXTURE_2D, ...)`: This method loads the pixel values of the
  * image into the specified texture ID using the specified texture parameters.
  * 
  * @returns an integer ID representing a newly created texture.
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
