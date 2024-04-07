{"name":"Camera.java","path":"src/com/ch/Camera.java","content":{"structured":{"description":"","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.ch.Camera.CameraStruct Pages: 1 -->\n<svg width=\"198pt\" height=\"82pt\"\n viewBox=\"0.00 0.00 198.00 82.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 78)\">\n<title>com.ch.Camera.CameraStruct</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"176.5,-74 13.5,-74 13.5,-55 176.5,-55 176.5,-74\"/>\n<text text-anchor=\"middle\" x=\"95\" y=\"-62\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.ch.Camera.CameraStruct</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1ch_1_1Camera3D_1_1CameraStruct3D.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"190,-19 0,-19 0,0 190,0 190,-19\"/>\n<text text-anchor=\"middle\" x=\"95\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.ch.Camera3D.CameraStruct3D</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node2 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node1&#45;&gt;Node2</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M95,-44.66C95,-35.93 95,-25.99 95,-19.09\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"91.5,-44.75 95,-54.75 98.5,-44.75 91.5,-44.75\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"e83f2150-a47f-4c56-a5cb-64aadcf0588a","ancestors":[],"type":"function","name":"getViewProjection","location":{"offset":"\t","indent":1,"insert":18,"start":18},"returns":"Matrix4f","params":[],"code":"public Matrix4f getViewProjection() {\n\n\t\tif (viewProjectionMat4 == null || transform.hasChanged()) {\n\t\t\tcalculateViewMatrix();\n\t\t}\n\n\t\treturn viewProjectionMat4;\n\t}","skip":false,"length":8,"comment":{"description":"retrieves and returns a `Matrix4f` object representing the view projection matrix, which is used to transform 3D vectors into screen space.","params":[],"returns":{"type":"Matrix4f","description":"a matrix representing the view projection transformation."}}},{"id":"b99c2e7f-409d-4dc0-b163-a05f4af027db","ancestors":[],"type":"function","name":"calculateViewMatrix","location":{"offset":"\t","indent":1,"insert":27,"start":27},"returns":"Matrix4f","params":[],"code":"public Matrix4f calculateViewMatrix() {\n\n\t\tMatrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();\n\t\tMatrix4f cameraTranslation = getTranslationMatrix();\n\n\t\treturn (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));\n\n\t}","skip":false,"length":8,"comment":{"description":"generates a view matrix that combines a rotation and translation of a camera based on its position, rotation, and projection information.","params":[],"returns":{"type":"Matrix4f","description":"a Matrix4f object representing the view matrix, which combines the rotation and translation of the camera."}}},{"id":"932117e1-5458-471a-af2e-f68efddb9c90","ancestors":[],"type":"function","name":"getTranslationMatrix","location":{"offset":"\t","indent":1,"insert":36,"start":36},"returns":"Matrix4f","params":[],"code":"public Matrix4f getTranslationMatrix() {\n\t\tVector3f cameraPos = transform.getTransformedPos().mul(-1);\n\t\treturn new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());\n\t}","skip":false,"length":4,"comment":{"description":"generates a matrix that represents the translation of a virtual camera's position from its original position to the opposite side of the screen.","params":[],"returns":{"type":"Matrix4f","description":"a 4x4 matrix representing the translation of the camera's position by the negative of its current position."}}},{"id":"519c2e24-f099-416a-8c8d-573e1d85ee98","ancestors":[],"type":"function","name":"getTransform","location":{"offset":"\t","indent":1,"insert":41,"start":41},"returns":"Transform","params":[],"code":"public Transform getTransform() {\n\t\treturn transform;\n\t}","skip":false,"length":3,"comment":{"description":"retrieves the transform object associated with the current state.","params":[],"returns":{"type":"Transform","description":"a `Transform` object representing the transformation of the input."}}}]}}}