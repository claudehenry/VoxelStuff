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
 * TODO
 */
public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


 /**
  * 0 at the root level calls the method `bind` with the argument `0`, which is applied
  * to the corresponding method parameter.
  */
	public void bind() {
		bind(0);
	}

 /**
  * sets the active texture slot to a specific index (0-31) and binds the specified
  * texture ID to that slot.
  * 
  * @param samplerSlot 0-based index of a texture slot in the GPU, with values ranging
  * from 0 to 31, and is used to specify which texture to bind to the current texture
  * slot.
  */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

 /**
  * returns the current ID of an object.
  * 
  * @returns the value of the `id` field.
  */
	public int getID() {
		return id;
	}

 /**
  * loads a 2D texture image from a file and creates a texture object in OpenGL, setting
  * up mipmapping and texture filtering parameters.
  * 
  * @param fileName filename of the image to be loaded and read into a texture.
  * 
  * 	- `ImageIO.read(new File(fileName))` - This method reads an image from the specified
  * file and returns a `BufferedImage`.
  * 	- `int[] pixels` - This is an array of integers that represents the pixel data
  * of the image.
  * 	- `BufferedImage.getRGB()` - This method returns the color values of the image
  * at a given position (x, y).
  * 	- `image.getWidth()` and `image.getHeight()` - These methods return the width and
  * height of the image, respectively.
  * 	- `image.getColorModel().hasAlpha()` - This method checks if the image has an
  * alpha channel (transparency information).
  * 	- `ByteBuffer buffer` - This is a buffer that will be used to store the pixel
  * data of the image.
  * 	- `glBindTexture(GL_TEXTURE_2D, id)` - This method binds the texture ID to the
  * current GL context.
  * 	- `glTexParameteri(GL_TEXTURE_2D, ...)` - These methods set various parameters
  * for the texture, such as wrapping and filtering.
  * 	- `glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(),
  * 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)` - This method sets the pixel data of the
  * texture to the specified buffer.
  * 	- `GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)` - This method generates mipmaps for
  * the texture.
  * 	- `GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
  * GL11.GL_LINEAR_MIPMAP_LINEAR)` - This method sets the minimum filtering mode to
  * linear for mipmapped textures.
  * 
  * @returns an OpenGL texture ID representing a loaded texture.
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
