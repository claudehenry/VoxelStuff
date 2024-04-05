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

public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


 /**
  * 0 binds an event handler to a specific event.
  */
	public void bind() {
		bind(0);
	}

 /**
  * sets the active texture slot to a specific index (0-31) and binds a texture to
  * that slot using the `glBindTexture()` method.
  * 
  * @param samplerSlot 0-based index of a texture slot to bind for use with the current
  * GPU context.
  */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

 /**
  * retrieves the value of a field named `id`.
  * 
  * @returns the value of the `id` field.
  */
	public int getID() {
		return id;
	}

 /**
  * loads a texture from an image file and returns the generated texture ID. It reads
  * the image, converts it to a byte array, and uploads it to the GPU as a 2D texture
  * using the GL_RGBA8 format with mipmapping enabled.
  * 
  * @param fileName filename of the image to be loaded and processed by the `loadTexture()`
  * method.
  * 
  * 	- `ImageIO.read(new File(fileName))` is used to read the contents of the file
  * specified by `fileName`.
  * 	- The resulting `BufferedImage` object contains information about the image's
  * pixels, color model, and other attributes.
  * 	- `image.getWidth()` and `image.getHeight()` provide the dimensions of the image.
  * 	- `image.getColorModel().hasAlpha()` indicates whether the image has an alpha
  * channel (i.e., transparency).
  * 	- The `ByteBuffer` object is created to store the pixel data, which is then put
  * into it using the `put()` method.
  * 	- The pixel data is stored as a 4-component RGBA value (red, green, blue, and
  * alpha) for each pixel in the image.
  * 	- The `glGenTextures()` function is used to create a new OpenGL texture ID.
  * 	- The `glBindTexture(GL_TEXTURE_2D, id)` command binds the newly created texture
  * ID to the current GL context.
  * 	- The `glTexParameteri()` functions are used to set various texture parameters,
  * including wrapping, filtering, and mipmap generation.
  * 	- The `glTexImage2D()` function is used to load the pixel data from the `BufferedImage`
  * object into the OpenGL texture.
  * 	- The `GL30.glGenerateMipmap()` function is used to generate mipmap levels for
  * the texture, if necessary.
  * 	- Finally, the `return id;` statement returns the newly created texture ID.
  * 
  * @returns an OpenGL texture ID for a loaded image.
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
