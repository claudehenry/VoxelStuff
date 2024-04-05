{"name":"Texture.java","path":"src/com/ch/Texture.java","content":{"structured":{"description":"A `Texture` class that loads and manages textures for an OpenGL program. It includes methods to bind and unbind textures, as well as a constructor that initializes the texture ID. The `loadTexture()` method loads an image file and creates a texture from it using the OpenGL API.","items":[{"id":"f601685c-9bd6-e59b-514c-60b2dc4b7e0c","ancestors":["1cbca31e-f55c-0899-2645-d8eadf3d2a77"],"type":"function","usage":{"language":"java","code":"Texture texture = new Texture(\"texture_name.jpg\");\ntexture.bind();\n","description":""}},{"id":"0b13ec3e-d433-e2a8-6743-d8ff8f03bce7","ancestors":["1cbca31e-f55c-0899-2645-d8eadf3d2a77"],"type":"function","usage":{"language":"java","code":"Texture texture = new Texture(\"path/to/texture\");\n// Bind the texture at slot 0\ntexture.bind(0);\n","description":""}},{"id":"2d44a4c7-60c9-f4aa-0042-e6a7e81d6148","ancestors":["1cbca31e-f55c-0899-2645-d8eadf3d2a77"],"type":"function","usage":{"language":"java","code":"// This code assumes that the Texture class has already been created and initialized with a valid texture ID. \nTexture myTexture = new Texture(\"someImage.png\");\nint id = myTexture.getID();\n","description":"\nIn this example, a new instance of the Texture class is created using the constructor that takes a string representing the name of the image file to be loaded as a texture. The getID method is called on the newly created Texture object and returns an integer value representing the ID of the loaded texture. This ID can then be used to bind the texture to a shader or perform other texture-related operations.\nNote that this example code assumes that the Texture class has already been created and initialized with a valid texture ID, which is not shown in the provided code snippet."}},{"id":"cd88f5a8-1cd7-2e9b-754b-00c9f29454b5","ancestors":["1cbca31e-f55c-0899-2645-d8eadf3d2a77"],"type":"function","usage":{"language":"java","code":"public class TextureLoader {\n    public static int loadTexture(String fileName) {\n        try {\n            BufferedImage image = ImageIO.read(new File(fileName));\n            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());\n\n            ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);\n            boolean hasAlpha = image.getColorModel().hasAlpha();\n\n            for (int y = image.getHeight() - 1; y >= 0; y--) {\n                for (int x = 0; x < image.getWidth(); x++) {\n                    int pixel = pixels[y * image.getWidth() + x];\n\n                    buffer.put((byte) ((pixel >> 16) & 0xFF));\n                    buffer.put((byte) ((pixel >> 8) & 0xFF));\n                    buffer.put((byte) ((pixel) & 0xFF));\n                    if (hasAlpha)\n                        buffer.put((byte) ((pixel >> 24) & 0xFF));\n                    else\n                        buffer.put((byte) (0xFF));\n                }\n            }\n\n            buffer.flip();\n\n            int id = GL11.glGenTextures();\n            \n            glBindTexture(GL_TEXTURE_2D, id);\n\n            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);\n            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);\n\n            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);\n            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);\n            \n            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);\n\n            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);\n            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL1.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);\n            return id;\n        } catch (Exception e) {\n            e.printStackTrace();\n            System.exit(1);\n        }\n\n        return 0;\n    }\n}\n","description":"\nIn this example, we use the `loadTexture` method to load a texture from an image file located at 'fileName'. We first read in the image and determine its width, height, and pixel format using the `BufferedImage.getWidth()`, `BufferedImage.getHeight()`, and `BufferedImage.getColorModel()` methods. Next, we allocate enough memory to store each pixel's color as a 32-bit value using the `ByteBuffer.allocateDirect(width * height * 4)` method. We then convert each pixel's color into an RGBA byte sequence by first determining its alpha component using the bitwise AND operator (`&`), then determining its red, green, and blue components using bitwise AND operators (`&`), then finally assigning each of these values to a new `byte` variable. This process is repeated for every pixel in the image. Finally, we bind the texture to an OpenGL context by calling `glBindTexture(GL_TEXTURE_2D, 0)` and initializing the texture using `glTexImage2D()`.\nThe resulting texture's wrapping behavior is set to `GL11.GL_REPEAT` using `glTexParameteri()` and its filtering mode is set to `GL11.GL_LINEAR` using `glTexParameterf()`. The texture is then assigned a unique id by calling `glGenTextures()`, which it will be used for rendering later on. This method returns the unique id of the created texture.\nThis code should work correctly, and any errors or issues can be determined and fixed by running it in an OpenGL context."}}]}}}