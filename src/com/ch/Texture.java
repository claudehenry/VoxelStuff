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
 * False
 */
public class Texture {

	private int id;
	private String fileName;

	public Texture(String fileName) {
		this.fileName = fileName;
		this.id = loadTexture(fileName);
	}


 /**
  * 0 invokes the bind method with no arguments, resulting in a void return type.
  */
	public void bind() {
		bind(0);
	}

 /**
  * sets the active texture slot to a specified value within a range of 0 to 31 and
  * binds a texture to the respective slot using the `glBindTexture()` function.
  * 
  * @param samplerSlot 0-based index of a texture slot in the current texture unit,
  * with values ranging from 0 to 31, and is used to select and bind a specific texture
  * in the current texture unit.
  */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, id);
	}

 /**
  * returns the `id` variable's value.
  * 
  * @returns an integer representing the ID of the object.
  */
	public int getID() {
		return id;
	}

 /**
  * loads an image file and generates a textured buffer for use in a graphics rendering
  * pipeline. It reads the image file using `ImageIO`, converts it to a byte array,
  * and then binds it as a texture using `GL11`.
  * 
  * @param fileName name of the texture file to be loaded.
  * 
  * 	- `File fileName`: represents the file path of the texture to be loaded.
  * 	- `BufferedImage image`: holds the pixel data of the specified file as an instance
  * of the `BufferedImage` class.
  * 	- `int[] pixels`: stores the color values of each pixel in the `image`, with each
  * value represented by an integer value between 0 and 255 (inclusive).
  * 	- `Boolean hasAlpha`: indicates whether the texture has alpha channel or not,
  * which is represented by a boolean value.
  * 	- `GL11 glGenTextures()`: generates a new texture ID using the `glGenTextures`
  * function from the OpenGL API.
  * 	- `int id`: stores the newly generated texture ID returned by `glGenTextures()`.
  * 	- `glBindTexture(GL_TEXTURE_2D, id)`: binds the specified texture ID to the current
  * context using the `glBindTexture` function.
  * 	- `glTexParameteri(GL_TEXTURE_2D, ...)`: sets various texture parameters such as
  * wrapping, filtering, and mipmap generation using the `glTexParameteri` function.
  * 	- `glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, ...)`: uploads the pixel data of the
  * specified file to the newly bound texture ID using the `glTexImage2D` function.
  * The `0` argument specifies that the texture is an RGBA8 texture.
  * 	- `GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)`: generates mipmap levels for the
  * specified texture ID using the `GL30.glGenerateMipmap` function.
  * 	- `GL11.glTexParameteri(GL11.GL_TEXTURE_2D, ...)`: sets additional texture
  * parameters such as minimum filtering and lod bias using the `glTexParameteri` function.
  * 
  * @returns an OpenGL texture ID representing a loaded image.
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
