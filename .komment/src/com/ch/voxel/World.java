{"name":"World.java","path":"src/com/ch/voxel/World.java","content":{"structured":{"description":"A 3D rendering system using the LWJGL library. It provides a Chunk class that represents a 3D chunk of terrain, and a World class that manages a grid of chunks. The Chunk class has x, y, and z coordinates, and a model matrix that determines its position in 3D space. The World class renderers the chunks using a shader and a camera object, and also provides methods for updating and generating new chunks based on user input.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.ch.Camera Pages: 1 -->\n<svg width=\"115pt\" height=\"82pt\"\n viewBox=\"0.00 0.00 115.00 82.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 78)\">\n<title>com.ch.Camera</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"100,-74 7,-74 7,-55 100,-55 100,-74\"/>\n<text text-anchor=\"middle\" x=\"53.5\" y=\"-62\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.ch.Camera</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1ch_1_1Camera3D.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"107,-19 0,-19 0,0 107,0 107,-19\"/>\n<text text-anchor=\"middle\" x=\"53.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.ch.Camera3D</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node2 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node1&#45;&gt;Node2</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M53.5,-44.66C53.5,-35.93 53.5,-25.99 53.5,-19.09\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"50,-44.75 53.5,-54.75 57,-44.75 50,-44.75\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"99b71d90-a7c9-afaa-3949-f16a0fc6e8da","ancestors":[],"type":"function","description":"in this implementation is responsible for rendering 3D chunks in a Minecraft-like game. It manages an array of Chunk objects, which represent the 3D environment, and renders them using a Shader object and a Camera object. The World class also handles the movement of the player and the updates to the Chunk array based on the player's position.","name":"World","code":"public class World {\n\n\tprivate int x, y, z; // in chunks\n\t\t\t// private int cunk_max;\n\tprivate Chunk[][][] chunks; // TODO: unwrap\n\tprivate int W = 4, H = 2, D = 4;\n\n\tpublic World() {\n\t\tx = 0;\n\t\ty = 0;\n\t\tz = 0;\n\t\tchunks = new Chunk[W][H][D];\n\t\tgen();\n\t}\n\t\n\tprivate void gen() {\n\t\tfor (int i = 0; i < W; i++)\n\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\tfor (int k = 0; k < D; k++) {\n\t\t\t\t\tchunks[i][j][k] = new Chunk(i - W / 2 + x, j - H / 2 + y, k - D / 2 + z);\n\t\t\t\t\tchunks[i][j][k].updateBlocks();\n\t\t\t\t\tchunks[i][j][k].toGenModel();\n\t\t\t\t}\n\t}\n\n\tpublic void updatePos(float x, float y, float z) {\n\t\tfinal int _x = (int) (x / Chunk.CHUNK_SIZE);\n\t\tfinal int _y = 0;//(int) (y / Chunk.CHUNK_SIZE);\n\t\tfinal int _z = (int) (z / Chunk.CHUNK_SIZE);\n\n\t\tif (this.x == _x && this.y == _y && this.z == _z) { // short circuit\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// check for any\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// change\n\t\t\t//System.out.println(\"hello\");\n\t\t\treturn;\n\t\t}\n\t\t\n\t\tint wx = this.x;\n\t\tint wy = this.y;\n\t\tint wz = this.z;\n\t\t\n//\t\tclass internal_chunk_thread extends Thread {\n//\t\t\t\n//\t\tprivate int  wx, wy, wz;\n//\t\t\n//\t\tvoid set(int x, int y, int z) {\n//\t\t\tthis.wx = x;\n//\t\t\tthis.wy = y;\n//\t\t\tthis.wz = z;\n//\t\t}\n//\t\t\t\n//\t\tpublic void run() {\n\n\t\t/*\n\t\t * all logic is unwrapped because its more efficient.. while its a pain\n\t\t * to code and read.. tradeoff taken :D\n\t\t */\n\n\t\t/* dont think these cases occure\n\t\tif (this.x != _x && this.y != _y && this.z != _z) {\n\t\t\tif (this.x < _x) {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t} else {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t} else {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t} else if (this.x != _x && this.y != _y) {\n\t\t\tif (this.x < _x) {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t}\n\t\t} else if (this.x != _x && this.z != _z) {\n\t\t\tif (this.x < _x) {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t}\n\t\t} else if (this.y != _y && this.z != _z) {\n\t\t\tif (this.y < _y) {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t}\n\t\t} else \n\t\t*/\n\t\tif (wx != _x) {\n\t\t\tif (wx < _x) {\n\t\t\t\t\n\t\t\t} else {\n\t\t\t\t\n\t\t\t}\n\t\t} else if (wy != _y) {\n\t\t\tif (wy < _y) {\n\t\t\t\t\n\t\t\t} else {\n\t\t\t\t\n\t\t\t}\n\t\t} else if (wz != _z) {\n\t\t\tif (wz < _z) {\n\t\t\t\tint dif = _z - wz;\n\t\t\t\tif (dif > D) {\n\t\t\t\t\twx = _x;\n\t\t\t\t\twy = _y;\n\t\t\t\t\twz = _z;\n\t\t\t\t\tgen();\n\t\t\t\t\treturn;\n\t\t\t\t} else {\n\t\t\t\t\tChunk[][][] n_chunks = new Chunk[W][H][D];\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\t\t\t\tfor (int k = 0; k < D - 1; k++) {\n\t\t\t\t\t\t\t\tn_chunks[i][j][k] = chunks[i][j][k + 1];\n\t\t\t\t\t\t\t}\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++) {\n\t\t\t\t\t\t\tn_chunks[i][j][D - 1] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, (D - 1) - D / 2 + _z);\n\t\t\t\t\t\t\tn_chunks[i][j][D - 1].updateBlocks();\n\t\t\t\t\t\t\tn_chunks[i][j][D - 1].toGenModel();\n\t\t\t\t\t\t}\n\t\t\t\t\tWorld.this.chunks = n_chunks;\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tint dif = wz - _z;\n\t\t\t\tif (dif > D) {\n\t\t\t\t\twx = _x;\n\t\t\t\t\twy = _y;\n\t\t\t\t\twz = _z;\n\t\t\t\t\tgen();\n\t\t\t\t\treturn;\n\t\t\t\t} else {\n\t\t\t\t\tChunk[][][] n_chunks = new Chunk[W][H][D];\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\t\t\t\tfor (int k = 1; k < D; k++) {\n\t\t\t\t\t\t\t\tn_chunks[i][j][k] = chunks[i][j][k - 1];\n\t\t\t\t\t\t\t}\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++) {\n\t\t\t\t\t\t\tn_chunks[i][j][0] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, 0 - D / 2 + _z);\n\t\t\t\t\t\t\tn_chunks[i][j][0].updateBlocks();\n\t\t\t\t\t\t\tn_chunks[i][j][0].toGenModel();\n\t\t\t\t\t\t}\n\t\t\t\t\tWorld.this.chunks = n_chunks;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n//\t\t\n//\t\t}\n//\t\t\n//\t\t};\n//\t\tinternal_chunk_thread t = new internal_chunk_thread();\n//\t\tt.set(this.x, this.y, this.z);\n//\t\tt.start();\n\t\t\n\t\tthis.x = _x;\n\t\tthis.y = _y;\n\t\tthis.z = _z;\n\t\t\n\t\t/* welp... this logic sure looks aweful */\n\t}\n\n\tpublic void render(Shader s, Camera c) {\n\t\tfor (int i = 0; i < W; i++)\n\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\tfor (int k = 0; k < D; k++) {\n\t\t\t\t\tChunk ch = chunks[i][j][k];\n\t\t\t\t\tif (ch != null) { // just in case for now although i dont suspect it will ever be\n\t//\t\t\t\t\tfloat r = (W - i) / (float) W;\n\t//\t\t\t\t\tfloat g = j / (float) H;\n\t//\t\t\t\t\tfloat b = k / (float) D;\n\t\t\t\t\t\tColor cl = new Color((\"\" + ch.x + ch.y + ch.z + (ch.x * ch.z) + (ch.y * ch.y)).hashCode());\n\t\t\t\t\t\t\n\t\t\t\t\t\tfloat r = cl.getRed() / 255f;\n\t\t\t\t\t\tfloat g = cl.getGreen() / 255f;\n\t\t\t\t\t\tfloat b = cl.getBlue() / 255f;\n\t\t\t\t\t\ts.uniformf(\"color\", r, g, b);\n\t\t\t\t\t\ts.unifromMat4(\"MVP\", (c.getViewProjection().mul(ch.getModelMatrix())));\n\t\t\t\t\t\tch.getModel().draw();\n\t\t\t\t\t}\n\t\t\t\t}\n\t}\n\n\t// public\n\n}","location":{"start":9,"insert":9,"offset":" ","indent":0,"comment":null},"item_type":"class","length":233},{"id":"69023e33-8cf8-78bf-894a-87426a77ceaa","ancestors":["99b71d90-a7c9-afaa-3949-f16a0fc6e8da"],"type":"function","description":"iterates through a 3D grid of chunks, creating new chunks at each position and updating their block states before passing them to a genetic model for evolution.","params":[],"usage":{"language":"java","code":"private void gen() {\n    for (int i = 0; i < W; i++)\n        for (int j = 0; j < H; j++)\n            for (int k = 0; k < D; k++) {\n                chunks[i][j][k] = new Chunk(i - W / 2 + x, j - H / 2 + y, k - D / 2 + z);\n                chunks[i][j][k].updateBlocks();\n                chunks[i][j][k].toGenModel();\n            }\n}\n","description":"\nThis method would initialize an array of Chunk objects with the size of WxHxD and populate them with new Chunk objects, each initialized with the correct position in the world (based on the values of x, y and z). It then calls updateBlocks() and toGenModel() on each of these chunks, which would update their internal data structures based on the current world state and generate a model for each chunk. This method assumes that W, H and D are positive integers, and that x, y and z are also numbers within the range of the world size.\n\nThe reason this code should be used is because it correctly initializes an array of Chunk objects with the correct position in the world, calls updateBlocks() and toGenModel() on each chunk, which would update their internal data structures based on the current world state and generate a model for each chunk. This method assumes that W, H and D are positive integers, and that x, y and z are also numbers within the range of the world size."},"name":"gen","code":"private void gen() {\n\t\tfor (int i = 0; i < W; i++)\n\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\tfor (int k = 0; k < D; k++) {\n\t\t\t\t\tchunks[i][j][k] = new Chunk(i - W / 2 + x, j - H / 2 + y, k - D / 2 + z);\n\t\t\t\t\tchunks[i][j][k].updateBlocks();\n\t\t\t\t\tchunks[i][j][k].toGenModel();\n\t\t\t\t}\n\t}","location":{"start":24,"insert":24,"offset":" ","indent":1,"comment":null},"item_type":"method","length":9},{"id":"8a856e51-23fe-24a9-6a43-f09fd413429c","ancestors":["99b71d90-a7c9-afaa-3949-f16a0fc6e8da"],"type":"function","description":"updates the position of an object in a world based on its previous position and some logical conditions. It also tries to avoid generating new chunks if possible by using a temporary array for that purpose.","params":[{"name":"x","type_name":"float","description":"2D position of the chunk in the world, which is used to determine whether the chunk has moved and to update its position accordingly.","complex_type":false},{"name":"y","type_name":"float","description":"2D coordinate of the chunk's position in the world, which is used to determine the correct chunk to update or generate based on the `x` and `z` coordinates.","complex_type":false},{"name":"z","type_name":"float","description":"3D coordinate of the current chunk's position in the world, and it is used to determine whether the chunk has moved too far away from its original position, and if so, to update the chunk's position accordingly.","complex_type":false}],"usage":{"language":"java","code":"World w = new World();\n// ...\nint x = 5, y = 6, z = 7;\nw.updatePos(x, y, z);\n","description":"\nIn this case, the object's position is set to (x=5, y=6, z=7).\nNote that only integers are supported for 'x', 'y', and 'z'. If any of these arguments are not integers, the program will not compile."},"name":"updatePos","code":"public void updatePos(float x, float y, float z) {\n\t\tfinal int _x = (int) (x / Chunk.CHUNK_SIZE);\n\t\tfinal int _y = 0;//(int) (y / Chunk.CHUNK_SIZE);\n\t\tfinal int _z = (int) (z / Chunk.CHUNK_SIZE);\n\n\t\tif (this.x == _x && this.y == _y && this.z == _z) { // short circuit\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// check for any\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// change\n\t\t\t//System.out.println(\"hello\");\n\t\t\treturn;\n\t\t}\n\t\t\n\t\tint wx = this.x;\n\t\tint wy = this.y;\n\t\tint wz = this.z;\n\t\t\n//\t\tclass internal_chunk_thread extends Thread {\n//\t\t\t\n//\t\tprivate int  wx, wy, wz;\n//\t\t\n//\t\tvoid set(int x, int y, int z) {\n//\t\t\tthis.wx = x;\n//\t\t\tthis.wy = y;\n//\t\t\tthis.wz = z;\n//\t\t}\n//\t\t\t\n//\t\tpublic void run() {\n\n\t\t/*\n\t\t * all logic is unwrapped because its more efficient.. while its a pain\n\t\t * to code and read.. tradeoff taken :D\n\t\t */\n\n\t\t/* dont think these cases occure\n\t\tif (this.x != _x && this.y != _y && this.z != _z) {\n\t\t\tif (this.x < _x) {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t} else {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t} else {\n\t\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\t\n\t\t\t\t\t} else {\n\t\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t} else if (this.x != _x && this.y != _y) {\n\t\t\tif (this.x < _x) {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.y < _y) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t}\n\t\t} else if (this.x != _x && this.z != _z) {\n\t\t\tif (this.x < _x) {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t}\n\t\t} else if (this.y != _y && this.z != _z) {\n\t\t\tif (this.y < _y) {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tif (this.z < _z) {\n\t\t\t\t\t\n\t\t\t\t} else {\n\t\t\t\t\t\n\t\t\t\t}\n\t\t\t}\n\t\t} else \n\t\t*/\n\t\tif (wx != _x) {\n\t\t\tif (wx < _x) {\n\t\t\t\t\n\t\t\t} else {\n\t\t\t\t\n\t\t\t}\n\t\t} else if (wy != _y) {\n\t\t\tif (wy < _y) {\n\t\t\t\t\n\t\t\t} else {\n\t\t\t\t\n\t\t\t}\n\t\t} else if (wz != _z) {\n\t\t\tif (wz < _z) {\n\t\t\t\tint dif = _z - wz;\n\t\t\t\tif (dif > D) {\n\t\t\t\t\twx = _x;\n\t\t\t\t\twy = _y;\n\t\t\t\t\twz = _z;\n\t\t\t\t\tgen();\n\t\t\t\t\treturn;\n\t\t\t\t} else {\n\t\t\t\t\tChunk[][][] n_chunks = new Chunk[W][H][D];\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\t\t\t\tfor (int k = 0; k < D - 1; k++) {\n\t\t\t\t\t\t\t\tn_chunks[i][j][k] = chunks[i][j][k + 1];\n\t\t\t\t\t\t\t}\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++) {\n\t\t\t\t\t\t\tn_chunks[i][j][D - 1] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, (D - 1) - D / 2 + _z);\n\t\t\t\t\t\t\tn_chunks[i][j][D - 1].updateBlocks();\n\t\t\t\t\t\t\tn_chunks[i][j][D - 1].toGenModel();\n\t\t\t\t\t\t}\n\t\t\t\t\tWorld.this.chunks = n_chunks;\n\t\t\t\t}\n\t\t\t} else {\n\t\t\t\tint dif = wz - _z;\n\t\t\t\tif (dif > D) {\n\t\t\t\t\twx = _x;\n\t\t\t\t\twy = _y;\n\t\t\t\t\twz = _z;\n\t\t\t\t\tgen();\n\t\t\t\t\treturn;\n\t\t\t\t} else {\n\t\t\t\t\tChunk[][][] n_chunks = new Chunk[W][H][D];\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\t\t\t\tfor (int k = 1; k < D; k++) {\n\t\t\t\t\t\t\t\tn_chunks[i][j][k] = chunks[i][j][k - 1];\n\t\t\t\t\t\t\t}\n\t\t\t\t\tfor (int i = 0; i < W; i++)\n\t\t\t\t\t\tfor (int j = 0; j < H; j++) {\n\t\t\t\t\t\t\tn_chunks[i][j][0] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, 0 - D / 2 + _z);\n\t\t\t\t\t\t\tn_chunks[i][j][0].updateBlocks();\n\t\t\t\t\t\t\tn_chunks[i][j][0].toGenModel();\n\t\t\t\t\t\t}\n\t\t\t\t\tWorld.this.chunks = n_chunks;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n//\t\t\n//\t\t}\n//\t\t\n//\t\t};\n//\t\tinternal_chunk_thread t = new internal_chunk_thread();\n//\t\tt.set(this.x, this.y, this.z);\n//\t\tt.start();\n\t\t\n\t\tthis.x = _x;\n\t\tthis.y = _y;\n\t\tthis.z = _z;\n\t\t\n\t\t/* welp... this logic sure looks aweful */\n\t}","location":{"start":34,"insert":34,"offset":" ","indent":1,"comment":null},"item_type":"method","length":183},{"id":"29342c99-0278-1b9d-424f-3fd99213a12c","ancestors":["99b71d90-a7c9-afaa-3949-f16a0fc6e8da"],"type":"function","description":"takes a shader object `s` and a camera object `c` as inputs, loops through every pixel in a 3D chunk, and renders the corresponding model using the current view-projection matrix.","params":[{"name":"s","type_name":"Shader","description":"3D shader that is being rendered, and it is used to set the color of each pixel in the rendering process.\n\n* `s`: A `Shader` object representing the shading model for rendering 3D graphics. It has various attributes such as uniforms, samplers, and buffers that can be accessed through its methods.\n* `c`: A `Camera` object representing the camera's perspective for rendering 3D graphics. It has properties such as position, orientation, and field of view that can be accessed through its methods.","complex_type":true},{"name":"c","type_name":"Camera","description":"3D camera object used to project the scene onto the view plane, and is used by the shader to compute the final color of each pixel based on the camera's perspective.\n\n* `c`: A `Camera` object, representing the camera used for rendering. It has various properties such as `getViewProjection()` method that returns a `Matrix4f` object representing the view projection matrix, and `getModelMatrix()` method that returns a `Matrix4f` object representing the model matrix.","complex_type":true}],"usage":{"language":"java","code":"Shader s = new Shader(\"path/to/shader\");\nCamera c = new Camera(new Vec3(0, 0, -2), new Vec3(0, 0, 1), new Vec3(0, 1, 0));\nWorld world = new World();\nworld.gen();\nwhile (true) {\n    // render the scene with the shader and camera\n    world.render(s, c);\n}\n","description":"\nThis would render the scene infinitely using the specified shader and camera. The while loop is never executed because the program will terminate when the user closes it."},"name":"render","code":"public void render(Shader s, Camera c) {\n\t\tfor (int i = 0; i < W; i++)\n\t\t\tfor (int j = 0; j < H; j++)\n\t\t\t\tfor (int k = 0; k < D; k++) {\n\t\t\t\t\tChunk ch = chunks[i][j][k];\n\t\t\t\t\tif (ch != null) { // just in case for now although i dont suspect it will ever be\n\t//\t\t\t\t\tfloat r = (W - i) / (float) W;\n\t//\t\t\t\t\tfloat g = j / (float) H;\n\t//\t\t\t\t\tfloat b = k / (float) D;\n\t\t\t\t\t\tColor cl = new Color((\"\" + ch.x + ch.y + ch.z + (ch.x * ch.z) + (ch.y * ch.y)).hashCode());\n\t\t\t\t\t\t\n\t\t\t\t\t\tfloat r = cl.getRed() / 255f;\n\t\t\t\t\t\tfloat g = cl.getGreen() / 255f;\n\t\t\t\t\t\tfloat b = cl.getBlue() / 255f;\n\t\t\t\t\t\ts.uniformf(\"color\", r, g, b);\n\t\t\t\t\t\ts.unifromMat4(\"MVP\", (c.getViewProjection().mul(ch.getModelMatrix())));\n\t\t\t\t\t\tch.getModel().draw();\n\t\t\t\t\t}\n\t\t\t\t}\n\t}","location":{"start":218,"insert":218,"offset":" ","indent":1,"comment":null},"item_type":"method","length":20}]}}}