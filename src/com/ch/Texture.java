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
 * provides functionality for loading and managing textures in a 3D graphics context.
 * It allows for binding the texture to a specific slot in the graphics pipeline and
 * provides methods for loading textures from files. The loadTexture method takes a
 * file path as an argument and returns the ID of the loaded texture.
 */
public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


	/**
	 * 0 is called, performing an action related to the binding of something.
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * sets the active texture slot to a specific index (0-31) and binds a texture ID to
	 * that slot using the `glBindTexture()` method.
	 * 
	 * @param samplerSlot 0-based index of a texture slot in the GPU, with values ranging
	 * from 0 to 31, which is used to select and bind a specific texture to the current
	 * rendering operation.
	 */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * returns the `id` variable's value.
	 * 
	 * @returns an integer value representing the ID of the object.
	 */
	public int getID() {
		return id;
	}

	/**
	 * loads a texture from an image file and returns the ID of the generated texture.
	 * It reads the image using ImageIO, converts it to a buffer, and then uses GL11 to
	 * create a texture and apply filtering and mipmapping.
	 * 
	 * @param fileName 2D image file to be loaded and converted into a texture.
	 * 
	 * @returns an integer representing the ID of the generated texture.
	 * 
	 * 	- `id`: This is an integer value representing the ID of the generated texture.
	 * 	- `image`: This is a `BufferedImage` object containing the loaded texture data.
	 * 	- `hasAlpha`: This is a boolean value indicating whether the texture has alpha
	 * channel or not.
	 * 	- `pixels`: This is an array of integers containing the pixel values of the texture.
	 * 	- `width`: This is an integer value representing the width of the texture.
	 * 	- `height`: This is an integer value representing the height of the texture.
	 * 	- `buffer`: This is a `ByteBuffer` object containing the loaded texture data.
	 * 
	 * The function returns the ID of the generated texture, which can be used to reference
	 * the texture in subsequent OpenGL operations. The `image` variable contains the
	 * loaded texture data, and the `hasAlpha` variable indicates whether the texture has
	 * an alpha channel or not. The `pixels` array contains the pixel values of the
	 * texture, and the `width` and `height` variables represent the dimensions of the
	 * texture. Finally, the `buffer` variable contains the loaded texture data in a
	 * `ByteBuffer` format.
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
