{"name":"Block.java","path":"src/com/ch/voxel/Block.java","content":{"structured":{"description":"A class called `Block` that represents a 3D voxel in a grid. The class has several instance variables that represent the position of the voxel in the grid and various flags that indicate whether certain properties are true or false. The class also has a constructor that initializes the instance variables and sets the flags based on user input.","items":[{"id":"36827726-47cf-8690-e54d-ea4183bb1a11","ancestors":[],"type":"function","description":"represents a 3D coordinate with various flags for its properties.\nFields:\n\t- z (int): represents the vertical position of a block in a 3D space. \n\t- rt (boolean): of the Block class represents whether the block is currently being mined. \n\n","name":"Block","code":"public class Block {\n\t\n\tpublic int x, y, z;\n\tpublic boolean ft, bk, tp, bt, lt, rt;\n\t\n\tpublic Block(int x, int y, int z) {\n\t\tthis.x = x;\n\t\tthis.y = y;\n\t\tthis.z = z;\n\t\t\n\t\tthis.ft = false;\n\t\tthis.bk = false;\n\t\tthis.tp = false;\n\t\tthis.bt = false;\n\t\tthis.lt = false;\n\t\tthis.rt = false;\n\t}\n\n}","location":{"start":3,"insert":3,"offset":" ","indent":0,"comment":null},"item_type":"class","length":19}]}}}