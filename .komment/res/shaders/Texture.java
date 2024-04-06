{"name":"Texture.java","path":"res/shaders/Texture.java","content":{"structured":{"description":"A `Texture` class that handles loading and binding textures in an OpenGL environment. The class takes a file path as its constructor argument and returns an integer texture ID upon successful loading. The loadTexture() method reads an image from disk, converts it to a byte array, and sets the texture parameters for linear filtering and repeat wrapping. Finally, the method calls glTexImage2D() to upload the texture data to the GPU.","items":[{"id":"a6994d67-153d-8b82-4c47-4b447c90552b","ancestors":[],"type":"function","description":"TODO","name":"Texture","code":"public class Texture {\n\t\n\tprivate String fileName;\n\tprivate int id;\n\n\tpublic Texture(String fileName) {\n\t\tthis.fileName = fileName;\n\t\tthis.id = Texture.loadTexture(fileName);\n\t}\n\n\t@Override\n\tprotected void finalize() {\n\t}\n\n\tpublic void bind() {\n\t\tbind(0);\n\t}\n\n\tpublic void bind(int samplerSlot) {\n\t\tassert (samplerSlot >= 0 && samplerSlot <= 31);\n\t\tglActiveTexture(GL_TEXTURE0 + samplerSlot);\n\t\tglBindTexture(GL_TEXTURE_2D, id);\n\t}\n\n\tpublic int getID() {\n\t\treturn id;\n\t}\n\n\tprivate static int loadTexture(String fileName) {\n\t\ttry {\n\t\t\tBufferedImage image = ImageIO.read(new File(fileName));\n\t\t\tint[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());\n\n\t\t\tByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);\n\t\t\tboolean hasAlpha = image.getColorModel().hasAlpha();\n\n\t\t\tfor (int y = 0; y < image.getHeight(); y++) {\n\t\t\t\tfor (int x = 0; x < image.getWidth(); x++) {\n\t\t\t\t\tint pixel = pixels[y * image.getWidth() + x];\n\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 16) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 8) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel) & 0xFF));\n\t\t\t\t\tif (hasAlpha)\n\t\t\t\t\t\tbuffer.put((byte) ((pixel >> 24) & 0xFF));\n\t\t\t\t\telse\n\t\t\t\t\t\tbuffer.put((byte) (0xFF));\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tbuffer.flip();\n\n\t\t\tint id = GL11.glGenTextures();\n\t\t\t\n\t\t\tglBindTexture(GL_TEXTURE_2D, id);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);\n\n//\t\t\tGL30.glGenerateMipmap(GL_TEXTURE_2D);\n//\t\t\tGL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);\n\t\t\t\n//\t\t\tif (GL11.glE)\n\t\t\t\n\t\t\tglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);\n\n\t\t\treturn id;\n\t\t} catch (Exception e) {\n\t\t\te.printStackTrace();\n\t\t\tSystem.exit(1);\n\t\t}\n\n\t\treturn 0;\n\t}\n\n\t\n}","location":{"start":30,"insert":30,"offset":" ","indent":0},"item_type":"class","length":80},{"id":"d43c474f-c0b1-c391-7a49-3d784df70e3f","ancestors":["a6994d67-153d-8b82-4c47-4b447c90552b"],"type":"function","description":"does nothing, as it is already null by design.","params":[],"usage":{"language":"java","code":"public class Example {\n    public static void main(String[] args) {\n        try {\n            new Texture(\"texture.png\");\n        } catch (Exception e) {\n            e.printStackTrace();\n            System.exit(1);\n        }\n    }\n}\n","description":""},"name":"finalize","code":"@Override\n\tprotected void finalize() {\n\t}","location":{"start":40,"insert":40,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"9f6a128e-c8b4-b88f-b149-43746bcc7ecd","ancestors":["a6994d67-153d-8b82-4c47-4b447c90552b"],"type":"function","description":"0 invokes the method `bind` on the current object, passing the argument `0`.","params":[],"usage":{"language":"java","code":"Texture texture = new Texture(\"textureFile\");\nint samplerSlot = 0;\ntexture.bind(samplerSlot);\n","description":""},"name":"bind","code":"public void bind() {\n\t\tbind(0);\n\t}","location":{"start":44,"insert":44,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"12c080f2-1eb8-128c-6c41-d86b1a00d0ec","ancestors":["a6994d67-153d-8b82-4c47-4b447c90552b"],"type":"function","description":"sets the active texture slot in the GPU's texture unit to a specific texture ID.","params":[{"name":"samplerSlot","type_name":"int","description":"0-based index of a texture slot to bind to the current active texture context.","complex_type":false}],"usage":{"language":"java","code":"int samplerSlot = 0; // The variable samplerSlot has been assigned a value of 0.\nTexture myTex = new Texture(\"myTextureFile\"); // Create a new instance of the class Texture with \"myTextureFile\".\nmyTex.bind(samplerSlot); // Call the bind method and pass in the value of samplerSlot.\n","description":""},"name":"bind","code":"public void bind(int samplerSlot) {\n\t\tassert (samplerSlot >= 0 && samplerSlot <= 31);\n\t\tglActiveTexture(GL_TEXTURE0 + samplerSlot);\n\t\tglBindTexture(GL_TEXTURE_2D, id);\n\t}","location":{"start":48,"insert":48,"offset":" ","indent":1},"item_type":"method","length":5},{"id":"a74f672d-a20d-3494-214a-5218bfb0df8a","ancestors":["a6994d67-153d-8b82-4c47-4b447c90552b"],"type":"function","description":"returns the `id` field's value.","params":[],"returns":{"type_name":"int","description":"the value of the `id` field.","complex_type":false},"usage":{"language":"java","code":"Texture texture = new Texture(\"image.png\");\nint id = texture.getID();\n","description":""},"name":"getID","code":"public int getID() {\n\t\treturn id;\n\t}","location":{"start":54,"insert":54,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"5aabea99-2757-1889-5e4c-9a8e95b662cc","ancestors":["a6994d67-153d-8b82-4c47-4b447c90552b"],"type":"function","description":"loads a texture from a file and returns its ID for use in OpenGL rendering. It reads the image data, converts it to an OpenGL texture format, and stores it in a buffer. The function then creates a new texture and sets its parameters before copying the image data into it. Finally, it returns the texture ID.","params":[{"name":"fileName","type_name":"String","description":"file path of the image to load as a texture.\n\n* `fileName`: A string representing the file path of the image to be loaded.\n* `ImageIO.read(new File(fileName))`: This method reads the image from the specified file location and returns a `BufferedImage` object.\n* `int[] pixels`: An array of integers representing the pixel values of the image.\n* `image.getWidth()` and `image.getHeight()`: These methods return the width and height of the `BufferedImage` object, respectively.\n* `image.getColorModel().hasAlpha()`: This method checks whether the image has an alpha channel (i.e., transparency).\n* `ByteBuffer buffer`: A byte buffer used to store the pixel values of the image.\n* `GL11.glGenTextures()`: This method creates a new texture ID for the loaded image.\n* `glBindTexture(GL_TEXTURE_2D, id)`: This method binds the newly created texture ID to the current GL context.\n* `glTexParameteri(GL_TEXTURE_2D, ...)`: These methods set various texture parameters such as wrapping, filtering, and mipmap generation.\n* `glTexImage2D(GL_TEXTURE_2D, ...)`: This method loads the pixel values of the image into the specified texture ID using the specified texture parameters.","complex_type":true}],"returns":{"type_name":"int","description":"an integer ID representing a newly created texture.","complex_type":false},"usage":{"language":"java","code":"public class MyClass {\n    public static void main(String[] args) {\n        // Load the texture \"MyTexture\" from file \"texture.png\"\n        int id = loadTexture(\"texture.png\");\n        \n        // Set the active texture unit to 0\n        glActiveTexture(GL_TEXTURE0);\n        \n        // Bind the texture ID to the 0th texture unit\n        glBindTexture(GL_TEXTURE_2D, id);\n    }\n}\n","description":""},"name":"loadTexture","code":"private static int loadTexture(String fileName) {\n\t\ttry {\n\t\t\tBufferedImage image = ImageIO.read(new File(fileName));\n\t\t\tint[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());\n\n\t\t\tByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);\n\t\t\tboolean hasAlpha = image.getColorModel().hasAlpha();\n\n\t\t\tfor (int y = 0; y < image.getHeight(); y++) {\n\t\t\t\tfor (int x = 0; x < image.getWidth(); x++) {\n\t\t\t\t\tint pixel = pixels[y * image.getWidth() + x];\n\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 16) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 8) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel) & 0xFF));\n\t\t\t\t\tif (hasAlpha)\n\t\t\t\t\t\tbuffer.put((byte) ((pixel >> 24) & 0xFF));\n\t\t\t\t\telse\n\t\t\t\t\t\tbuffer.put((byte) (0xFF));\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tbuffer.flip();\n\n\t\t\tint id = GL11.glGenTextures();\n\t\t\t\n\t\t\tglBindTexture(GL_TEXTURE_2D, id);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);\n\n//\t\t\tGL30.glGenerateMipmap(GL_TEXTURE_2D);\n//\t\t\tGL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);\n\t\t\t\n//\t\t\tif (GL11.glE)\n\t\t\t\n\t\t\tglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);\n\n\t\t\treturn id;\n\t\t} catch (Exception e) {\n\t\t\te.printStackTrace();\n\t\t\tSystem.exit(1);\n\t\t}\n\n\t\treturn 0;\n\t}","location":{"start":58,"insert":58,"offset":" ","indent":1},"item_type":"method","length":49}]}}}