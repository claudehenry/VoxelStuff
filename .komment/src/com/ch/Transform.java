{"name":"Transform.java","path":"src/com/ch/Transform.java","content":{"structured":{"description":"a `Transform` class, which represents a transformation in 3D space. The class has various methods and fields for manipulating transformations, including:\n\n* `update()`: updates the position, rotation, and scale of the transform based on previous values\n* `rotate(axis, angle)`: rotates the transform around the specified axis by the specified angle\n* `lookAt(point, up)`: sets the look-at point and up vector for the transform\n* `getLookAtRotation(point, up)`: returns the rotation required to look at a point from a given up vector\n* `hasChanged()`: checks if the transformation has changed since the last update\n* `getTransformation()`: returns the full transformation matrix, including translation, rotation, and scale\n* `setParent(transform)`: sets the parent transform for this transform\n* `getTransformedPos()`: returns the position of the transform after applying the parent transformation\n* `getTransformedRot()`: returns the rotation of the transform after applying the parent transformation\n* `getPos()`: returns the current position of the transform\n* `setPos(pos)`: sets the new position of the transform\n* `addToPos(addVec)`: adds a vector to the current position of the transform\n* `getRot()`: returns the current rotation of the transform\n* `setRot(rotation)`: sets the new rotation of the transform\n* `getScale()`: returns the current scale of the transform\n* `setScale(scale)`: sets the new scale of the transform.","items":[{"id":"6ecb9610-80ae-a593-8549-e1e73dc11c62","ancestors":[],"type":"function","description":"is a class that represents a transformation in 3D space. It has several fields and methods for manipulating its transform, including position, rotation, and scale. The class also has a parent reference, which allows it to inherit transformations from other Transform objects. Additionally, the class provides methods for updating its transform based on changes to its parent's transform, and for calculating the transformation matrix for its position, rotation, and scale.","name":"Transform","code":"public class Transform {\n\n\tprivate Transform parent;\n\tprivate Matrix4f parentMatrix;\n\n\tprivate Vector3f pos;\n\tprivate Quaternion rot;\n\tprivate Vector3f scale;\n\n\tprivate Vector3f oldPos;\n\tprivate Quaternion oldRot;\n\tprivate Vector3f oldScale;\n\n\tpublic Transform() {\n\t\tpos = new Vector3f(0, 0, 0);\n\t\trot = new Quaternion(1, 0, 0, 0);\n\t\tscale = new Vector3f(1, 1, 1);\n\t\t\n\t\toldPos = new Vector3f(0, 0, 0);\n\t\toldRot = new Quaternion(1, 0, 0, 0);\n\t\toldScale = new Vector3f(1, 1, 1);\n\n\t\tparentMatrix = new Matrix4f().initIdentity();\n\t}\n\n\tpublic void update() {\n\t\tif (oldPos != null) {\n\t\t\tif (!oldPos.equals(pos))\n\t\t\t\toldPos.set(pos);\n\t\t\tif (!oldRot.equals(rot))\n\t\t\t\toldRot.set(rot);\n\t\t\tif (!oldScale.equals(scale))\n\t\t\t\toldScale.set(scale);\n\t\t} else {\n\t\t\toldPos = new Vector3f().set(pos);\n\t\t\toldRot = new Quaternion().set(rot);\n\t\t\toldScale = new Vector3f().set(scale);\n\t\t}\n\t}\n\n\tpublic void rotate(Vector3f axis, float angle) {\n\t\trot = new Quaternion(axis, angle).mul(rot).normalized();\n\t}\n\n\tpublic void lookAt(Vector3f point, Vector3f up) {\n\t\trot = getLookAtRotation(point, up);\n\t}\n\n\tpublic Quaternion getLookAtRotation(Vector3f point, Vector3f up) {\n\t\treturn new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));\n\t}\n\n\tpublic boolean hasChanged() {\n\t\tif (parent != null && parent.hasChanged())\n\t\t\treturn true;\n\n\t\tif (!pos.equals(oldPos))\n\t\t\treturn true;\n\n\t\tif (!rot.equals(oldRot))\n\t\t\treturn true;\n\n\t\tif (!scale.equals(oldScale))\n\t\t\treturn true;\n\n\t\treturn false;\n\t}\n\n\tpublic Matrix4f getTransformation() {\n\t\tMatrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());\n\t\tMatrix4f rotationMatrix = rot.toRotationMatrix();\n\t\tMatrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());\n\n\t\treturn getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));\n\t}\n\n\tprivate Matrix4f getParentMatrix() {\n\t\tif (parent != null && parent.hasChanged())\n\t\t\tparentMatrix = parent.getTransformation();\n\n\t\treturn parentMatrix;\n\t}\n\n\tpublic void setParent(Transform parent) {\n\t\tthis.parent = parent;\n\t}\n\n\tpublic Vector3f getTransformedPos() {\n\t\treturn getParentMatrix().transform(pos);\n\t}\n\n\tpublic Quaternion getTransformedRot() {\n\t\tQuaternion parentRotation = new Quaternion(1, 0, 0, 0);\n\n\t\tif (parent != null)\n\t\t\tparentRotation = parent.getTransformedRot();\n\n\t\treturn parentRotation.mul(rot);\n\t}\n\n\tpublic Vector3f getPos() {\n\t\treturn pos;\n\t}\n\n\tpublic void setPos(Vector3f pos) {\n\t\tthis.pos = pos;\n\t}\n\n\tpublic void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }\n\n\tpublic Quaternion getRot() {\n\t\treturn rot;\n\t}\n\n\tpublic void setRot(Quaternion rotation) {\n\t\tthis.rot = rotation;\n\t}\n\n\tpublic Vector3f getScale() {\n\t\treturn scale;\n\t}\n\n\tpublic void setScale(Vector3f scale) {\n\t\tthis.scale = scale;\n\t}\n\t\n\t@Override\n\tpublic String toString() { return \"\";\n\t}\n\n}","location":{"start":7,"insert":7,"offset":" ","indent":0,"comment":null},"item_type":"class","length":131},{"id":"1c173ed5-2bcd-3fbe-d749-24a9dc51a6fb","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"updates an object's position, rotation, and scale based on its previous values, storing the new values for future use.","params":[],"usage":{"language":"java","code":"public static void main(String[] args) {\n    Transform t = new Transform();\n    // Update the position and rotation\n    t.update();\n}\n","description":"\nThis is a very simple use case of the update() method, which updates the transform's position and rotation based on the current values in the `pos` and `rot` fields. The `update()` method sets the `oldPos`, `oldRot`, and `oldScale` fields to the current values in the `pos`, `rot`, and `scale` fields, respectively."},"name":"update","code":"public void update() {\n\t\tif (oldPos != null) {\n\t\t\tif (!oldPos.equals(pos))\n\t\t\t\toldPos.set(pos);\n\t\t\tif (!oldRot.equals(rot))\n\t\t\t\toldRot.set(rot);\n\t\t\tif (!oldScale.equals(scale))\n\t\t\t\toldScale.set(scale);\n\t\t} else {\n\t\t\toldPos = new Vector3f().set(pos);\n\t\t\toldRot = new Quaternion().set(rot);\n\t\t\toldScale = new Vector3f().set(scale);\n\t\t}\n\t}","location":{"start":32,"insert":32,"offset":" ","indent":1,"comment":null},"item_type":"method","length":14},{"id":"a3d4ec08-cd66-b8a7-c84b-b5fa5567c91c","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"takes a reference to a quaternion `rot` and multiplies it by a new quaternion created by rotating a `Vector3f` axis by an angle, normalizing the result.","params":[{"name":"axis","type_name":"Vector3f","description":"3D rotational axis around which the rotation will be performed.\n\n* `axis`: A `Vector3f` object representing the rotation axis.\n\t+ It has three components: x, y, and z, which are the coordinates of the rotation axis in the 3D space.","complex_type":true},{"name":"angle","type_name":"float","description":"3D rotation angle of the axis about which the quaternion multiplication is performed.","complex_type":false}],"usage":{"language":"java","code":"Transform myTransform = new Transform();\nVector3f axis = new Vector3f(1, 0, 0);\nfloat angle = 90;\nmyTransform.rotate(axis, angle);\n","description":""},"name":"rotate","code":"public void rotate(Vector3f axis, float angle) {\n\t\trot = new Quaternion(axis, angle).mul(rot).normalized();\n\t}","location":{"start":47,"insert":47,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"30cc1024-6dd2-3581-0345-c1100523012b","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"computes and stores the rotation needed to face a specified point while maintaining a specific up direction.","params":[{"name":"point","type_name":"Vector3f","description":"3D position that the camera should look at.\n\n1. The `Vector3f` class represents a 3D vector with three components: x, y, and z.\n2. The `getLookAtRotation()` method computes the rotation required to face a point in 3D space relative to the current position of the object.","complex_type":true},{"name":"up","type_name":"Vector3f","description":"3D direction towards which the camera should look, relative to its current orientation.\n\n* `up`: A vector representing the direction from the object's position to the look-at point.\n* It has three components (x, y, and z) that determine the direction of the vector in 3D space.","complex_type":true}],"usage":{"language":"java","code":"public static void main(String[] args) {\n  Transform transform = new Transform();\n  Vector3f point = new Vector3f(1, 0, 0);\n  Vector3f up = new Vector3f(0, 1, 0);\n  transform.lookAt(point, up);\n}\n","description":"\nIn this example, the method lookAt is called on a Transform object with two vectors as arguments: point and up. The method returns a Quaternion that represents the rotation of the transform to face the given point while keeping the specified up direction."},"name":"lookAt","code":"public void lookAt(Vector3f point, Vector3f up) {\n\t\trot = getLookAtRotation(point, up);\n\t}","location":{"start":51,"insert":51,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"257dc750-eee7-4aa2-dc4a-75ad571c1db5","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"computes the rotation needed to face a given point while maintaining a constant orientation with respect to a provided up vector.","params":[{"name":"point","type_name":"Vector3f","description":"3D position from which to compute the look-at rotation.\n\n* `point` is a `Vector3f` object representing a 3D point in space.\n* The `x`, `y`, and `z` components of `point` represent the coordinates of the point in the XYZ coordinate system.\n* `up` is also a `Vector3f` object representing a direction in 3D space, which is used to compute the rotation matrix.","complex_type":true},{"name":"up","type_name":"Vector3f","description":"3D direction towards which the rotation is applied.\n\n* The `Vector3f` class is used to represent the vector `up`.\n* The `normalized()` method is called on the `up` vector to make its magnitude equal to 1. This is done to ensure that the resulting quaternion has a length of 1, which makes it easier to interpret and use in various applications.","complex_type":true}],"returns":{"type_name":"Quaternion","description":"a Quaternion representing the rotation from the camera's current position to look at a point in the scene.\n\n* The Quaternion object represents a rotation that aligns a virtual camera with a given point and up vector.\n* The rotation is represented as a 4x4 matrix in the form `M = R * T`, where `R` is the rotation matrix and `T` is the translation vector.\n* The rotation matrix `M` is obtained by initializing the product of the position vector difference `p - pos` and the normalized direction vector `up`.","complex_type":true},"usage":{"language":"java","code":"Transform transform = new Transform();\nVector3f point = new Vector3f(1, 2, 3);\nVector3f up = new Vector3f(0, 1, 0);\nQuaternion lookAtRotation = transform.getLookAtRotation(point, up);\n","description":""},"name":"getLookAtRotation","code":"public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {\n\t\treturn new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));\n\t}","location":{"start":55,"insert":55,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"c46a6f60-7e0b-a88f-404f-8b45ed0d3dd6","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"determines if an object has been modified by checking its parent, position, rotation, and scale. If any of these properties have changed from their previous values, the function returns `true`. Otherwise, it returns `false`.","params":[],"returns":{"type_name":"boolean","description":"a boolean value indicating whether the object has changed.","complex_type":false},"usage":{"language":"java","code":"public void update() {\n\tif (oldPos != null) {\n\t\tif (!oldPos.equals(pos)) {\n\t\t\toldPos.set(pos);\n\t\t}\n\t\tif (!oldRot.equals(rot)) {\n\t\t\toldRot.set(rot);\n\t\t}\n\t\tif (!oldScale.equals(scale)) {\n\t\t\toldScale.set(scale);\n\t\t}\n\t} else {\n\t\toldPos = new Vector3f().set(pos);\n\t\toldRot = new Quaternion().set(rot);\n\t\toldScale = new Vector3f().set(scale);\n\t}\n}\n","description":"\nIn this example, the update method is being called to update the transform's state. This includes updating the position, rotation and scale variables to their current values. If oldPos, oldRot and oldScale are not null, then they will be updated to reflect the current values of pos, rot and scale. The method returns true if any of these values have changed since the last update call, false otherwise."},"name":"hasChanged","code":"public boolean hasChanged() {\n\t\tif (parent != null && parent.hasChanged())\n\t\t\treturn true;\n\n\t\tif (!pos.equals(oldPos))\n\t\t\treturn true;\n\n\t\tif (!rot.equals(oldRot))\n\t\t\treturn true;\n\n\t\tif (!scale.equals(oldScale))\n\t\t\treturn true;\n\n\t\treturn false;\n\t}","location":{"start":59,"insert":59,"offset":" ","indent":1,"comment":null},"item_type":"method","length":15},{"id":"ea078575-8703-2fb6-814a-3a44e0db0692","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"computes and returns a transformation matrix that combines a translation, rotation, and scaling of a given object based on its parent matrix and local position, rotation, and scale values.","params":[],"returns":{"type_name":"Matrix4f","description":"a transformation matrix that combines a translation, rotation, and scaling operation.\n\nThe `getParentMatrix()` method is used to obtain the parent matrix of the current transformation matrix. This parent matrix represents the transformation that was applied to the object before it reached its current position and rotation.\n\nThe `mul()` method is used to multiply two matrices together, resulting in a new matrix that represents the composition of the two transformations. In this case, the input matrices are `translationMatrix`, `rotationMatrix`, and `scaleMatrix`. The resultant matrix represents the combined transformation of translation, rotation, and scaling.\n\nThe properties of the returned output can be explained as follows:\n\n* The matrix is a 4x4 homogeneous transformation matrix, representing the combination of translation, rotation, and scaling.\n* The elements of the matrix represent the amounts of transformation applied to each axis (x, y, z, w). For example, the element at position (1, 2) represents the amount of translation along the x-axis, plus the amount of rotation around the y-axis, plus the amount of scaling along the z-axis.\n* The matrix can be used to transform points, vectors, and other geometric objects in a 3D space.\n* The matrix is immutable, meaning it cannot be modified after it is created.","complex_type":true},"usage":{"language":"java","code":"Transform transform = new Transform();\ntransform.setPos(new Vector3f(1, 2, 3));\ntransform.setRot(new Quaternion(0, 1, 0, 0));\ntransform.setScale(new Vector3f(1, 2, 3));\nMatrix4f transformation = transform.getTransformation();\n","description":""},"name":"getTransformation","code":"public Matrix4f getTransformation() {\n\t\tMatrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());\n\t\tMatrix4f rotationMatrix = rot.toRotationMatrix();\n\t\tMatrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());\n\n\t\treturn getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));\n\t}","location":{"start":75,"insert":75,"offset":" ","indent":1,"comment":null},"item_type":"method","length":7},{"id":"bd612136-845e-aba7-9c4f-f808c695f96e","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"retrieves and returns the transformation matrix of its parent component, if it exists and has been modified.","params":[],"returns":{"type_name":"Matrix4f","description":"a Matrix4f representation of the parent transformation matrix.\n\n* The function returns a `Matrix4f` object representing the parent transformation matrix of the current transform node.\n* If the `parent` field is not null and its `hasChanged()` method returns true, then the parent matrix is computed by calling the `getTransformation()` method on the `parent` object.\n* The returned matrix represents the combination of the current transform node's transformation and its parent's transformation.","complex_type":true},"usage":{"language":"java","code":"Transform t = new Transform();\nTransform parent = new Transform();\nt.setParent(parent);\n\n// set the parent matrix\nparent.setTransformation(new Matrix4f().initRotation(Vector3f(0, 1, 0), Vector3f(0, 0, 1)));\n\n// get the parent matrix\nMatrix4f parentMatrix = t.getParentMatrix();\n","description":""},"name":"getParentMatrix","code":"private Matrix4f getParentMatrix() {\n\t\tif (parent != null && parent.hasChanged())\n\t\t\tparentMatrix = parent.getTransformation();\n\n\t\treturn parentMatrix;\n\t}","location":{"start":83,"insert":83,"offset":" ","indent":1,"comment":null},"item_type":"method","length":6},{"id":"d989024c-6190-3585-eb44-9d729c65b111","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"sets the `Transform` object reference for the current instance.","params":[{"name":"parent","type_name":"Transform","description":"Transform object that will serve as the new parent of the current object.\n\nThe `Transform` class contains the `parent` attribute, which is set to a new value passed as a parameter in this function call. The `parent` attribute holds a reference to an object of type `Transform`, representing the parent transform for this one.","complex_type":true}],"usage":{"language":"java","code":"Transform transform = new Transform();\nTransform parent = new Transform();\ntransform.setParent(parent);\n","description":""},"name":"setParent","code":"public void setParent(Transform parent) {\n\t\tthis.parent = parent;\n\t}","location":{"start":90,"insert":90,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"3758c925-8501-8bba-9c4c-4863b8e91e9a","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"takes a `Vector3f` object `pos` and applies a transformation matrix provided by `getParentMatrix()` to obtain a new `Vector3f` object representing the transformed position.","params":[],"returns":{"type_name":"Vector3f","description":"a transformed position vector in homogeneous coordinates.\n\nThe Vector3f object represent the transformed position after applying the parent matrix transformation. The components of the vector are affected by the scaling, translation, and rotation properties of the parent matrix. Therefore, the transformed position may have different values compared to the original position due to these transformations.","complex_type":true},"usage":{"language":"java","code":"Vector3f transformedPos = transform.getTransformedPos();\n","description":""},"name":"getTransformedPos","code":"public Vector3f getTransformedPos() {\n\t\treturn getParentMatrix().transform(pos);\n\t}","location":{"start":94,"insert":94,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"236443b2-f9b6-aaaa-6648-f90bff4d83de","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"transforms a given quaternion `rot` using the parent rotation `parentRotation`, and returns the transformed quaternion.","params":[],"returns":{"type_name":"Quaternion","description":"a transformed quaternion representation of the given rotation.\n\n* The `Quaternion` object `parentRotation` is created with the scalar value 1 in the `w` component and the vector values (0, 0, 0) in the `x`, `y`, and `z` components.\n* If the `parent` parameter is not null, the `parentRotation` object is transformed by multiplying it with the `rot` parameter, resulting in a new `Quaternion` object with the combined rotation values.\n\nThe output of the function is a `Quaternion` object representing the transformed rotation matrix.","complex_type":true},"usage":{"language":"java","code":"Transform transform = new Transform();\ntransform.setRot(new Quaternion(1, 0, 0, 0)); // set the initial rotation of the transform to (1, 0, 0, 0)\n\nQuaternion parentRotation = new Quaternion(2, 3, 4, 5); // set the initial rotation of the parent transform to (2, 3, 4, 5)\nTransform parent = new Transform();\nparent.setParent(transform);\nparent.setRot(parentRotation);\n\nQuaternion transformedRotation = transform.getTransformedRot();\nSystem.out.println(transformedRotation); // Output: (3, 0, 4, 5)\n","description":""},"name":"getTransformedRot","code":"public Quaternion getTransformedRot() {\n\t\tQuaternion parentRotation = new Quaternion(1, 0, 0, 0);\n\n\t\tif (parent != null)\n\t\t\tparentRotation = parent.getTransformedRot();\n\n\t\treturn parentRotation.mul(rot);\n\t}","location":{"start":98,"insert":98,"offset":" ","indent":1,"comment":null},"item_type":"method","length":8},{"id":"21073f91-b6cd-e887-ea48-13e49784a147","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"returns the position of an object as a `Vector3f` instance.","params":[],"returns":{"type_name":"Vector3f","description":"a `Vector3f` object representing the position of the game entity.\n\n* `pos`: This is a `Vector3f` object that represents the position of the entity in 3D space. It has three components: x, y, and z, which correspond to the position of the entity along the x, y, and z axes, respectively.\n* Type: The type of the output is `Vector3f`, which is a class in the Java programming language that represents a 3D vector.","complex_type":true},"usage":{"language":"java","code":"Transform transform = new Transform();\nVector3f position = transform.getPos();\nSystem.out.println(position);\n","description":""},"name":"getPos","code":"public Vector3f getPos() {\n\t\treturn pos;\n\t}","location":{"start":107,"insert":107,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"f4e5e639-1394-8ea9-0f41-34c7a06f1a27","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"sets the position of an object to a specified Vector3f value.","params":[{"name":"pos","type_name":"Vector3f","description":"3D position of an object or entity that the method is called on, and it assigns that position to the `pos` field of the calling object or entity.\n\n* `this.pos = pos;` sets the position of the object to which the function belongs.\n* `pos` is a `Vector3f` class representing a 3D vector with x, y, and z components.","complex_type":true}],"usage":{"language":"java","code":"Transform t = new Transform();\nVector3f pos = new Vector3f(1, 2, 3);\nt.setPos(pos);\n","description":"\nThis code creates a new instance of the Transform class and sets its position to the vector (1, 2, 3) using the setPos method."},"name":"setPos","code":"public void setPos(Vector3f pos) {\n\t\tthis.pos = pos;\n\t}","location":{"start":111,"insert":111,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"943d5ce8-4e33-c6a3-2243-c1b5a6058ff9","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"adds a vector to the position of an object, updating its position accordingly.","params":[{"name":"addVec","type_name":"Vector3f","description":"3D vector to be added to the current position of the object, which is then updated by calling the `setPos()` method with the result.\n\n* `Vector3f` is a class representing a 3D vector with floating-point values.\n* `getPos()` and `setPos()` are methods that return and set the position of an object, respectively.","complex_type":true}],"usage":{"language":"java","code":"Transform myTransform = new Transform(); \nVector3f vector3f = new Vector3f(1, 2, 3); \nmyTransform.addToPos(vector3f); \nSystem.out.println(myTransform.getPos()); \n// Output: (1, 2, 3)\n","description":""},"name":"addToPos","code":"public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }","location":{"start":115,"insert":115,"offset":" ","indent":1,"comment":null},"item_type":"method","length":1},{"id":"b80e85c3-1fb6-1597-2c4c-b8803927b549","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"returns a `Quaternion` object representing the rotation component of the game object's state.","params":[],"returns":{"type_name":"Quaternion","description":"a Quaternion object representing the rotation of the game object.\n\nThe `getRot` function returns a `Quaternion` object representing the rotation of an entity in 3D space. The `Quaternion` class in Java represents a quaternion number as a combination of real and imaginary parts, which can be used to represent rotations in 3D space.\nThe returned `Quaternion` object has four components: x, y, z, and w, where x, y, and z represent the real parts, and w represents the imaginary part. These components can be accessed and manipulated separately or together to perform various operations related to rotations in 3D space.","complex_type":true},"usage":{"language":"java","code":"Transform t = new Transform(); // constructs a new transform object with default values \nQuaternion q = t.getRot();     // retrieves the quaternion representing the transform's current rotation\n","description":""},"name":"getRot","code":"public Quaternion getRot() {\n\t\treturn rot;\n\t}","location":{"start":117,"insert":117,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"84d266e4-8a51-1487-f241-8ea15c45bb3b","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"sets the rotation of an object, represented by the `Quaternion` class, to a new value provided as input.","params":[{"name":"rotation","type_name":"Quaternion","description":"3D rotational transformation to be applied to the object instance being manipulated by the `setRot` method.\n\n* The Quaternion data type is represented by the variable `rot`.\n* The rotation values of the Quaternion are stored in this variable.\n* A Quaternion represents a 3D rotation as a combination of axis and angle, with the axis being a 3D vector and the angle being an angle measured from the x-axis.","complex_type":true}],"usage":{"language":"java","code":"Transform transform = new Transform();\nQuaternion rot = new Quaternion(1, 0, 0, 0);\ntransform.setRot(rot);\n","description":""},"name":"setRot","code":"public void setRot(Quaternion rotation) {\n\t\tthis.rot = rotation;\n\t}","location":{"start":121,"insert":121,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"e0e6ccbc-deb2-d399-2d42-bddec4f2d18e","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"returns the scale value of a vector, which is a fundamental component in computer graphics and game development.","params":[],"returns":{"type_name":"Vector3f","description":"a `Vector3f` object representing the current scale of the entity.\n\n* The `Vector3f` object represents a 3D vector with three components: x, y, and z.\n* The `scale` field of the object contains the magnitude or length of the vector.\n* The vector's direction is not explicitly defined, but can be inferred based on the magnitude.","complex_type":true},"usage":{"language":"java","code":"Transform myTransform = new Transform(); \nmyTransform.setScale(new Vector3f(2, 2, 2)); // Set the scale of the transform to (2, 2, 2)\nVector3f scale = myTransform.getScale(); // Get the current scale of the transform\n","description":""},"name":"getScale","code":"public Vector3f getScale() {\n\t\treturn scale;\n\t}","location":{"start":125,"insert":125,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"2331a85b-dc79-4688-9646-2651b8fb16a4","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"sets the scale of an object, which affects its size and proportions in 3D space.","params":[{"name":"scale","type_name":"Vector3f","description":"3D scaling factor for the game object, which is applied to its position, rotation, and size.\n\n* `scale` is a `Vector3f` object, representing a 3D vector in Java.\n* It contains the x, y, and z components of the scale factor for this GameObject.\n* The values of these components can range from -1 to 1, where a value of 0 represents no scaling.","complex_type":true}],"usage":{"language":"java","code":"Transform transform = new Transform(); // Create an instance of the Transform class\nVector3f scale = new Vector3f(1, 2, 3); // Create a vector with values for x, y, and z axis\ntransform.setScale(scale); // Set the scale to the newly created vector\n","description":""},"name":"setScale","code":"public void setScale(Vector3f scale) {\n\t\tthis.scale = scale;\n\t}","location":{"start":129,"insert":129,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"02b11f52-6e5c-5e84-5f4a-59e4898f9c9a","ancestors":["6ecb9610-80ae-a593-8549-e1e73dc11c62"],"type":"function","description":"returned an empty string, indicating that the class has no inherent value or identity beyond its name and possible references.","params":[],"returns":{"type_name":"empty","description":"an empty string.\n\n* The return type is `String`, indicating that the function returns a string value.\n* The function itself does not perform any action or modification to the object it is called on, as indicated by the absence of any explicit parameters in the function signature.\n* The function always returns an empty string (\"\") by default, meaning that no information about the object is provided when the `toString` method is called.","complex_type":true},"usage":{"language":"java","code":"@Override \npublic String toString() { return this.getPos().toString() + \" \" + this.getRot().toString(); }\n","description":""},"name":"toString","code":"@Override\n\tpublic String toString() { return \"\";\n\t}","location":{"start":133,"insert":133,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3}]}}}