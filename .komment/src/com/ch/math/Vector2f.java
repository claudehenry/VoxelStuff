{"name":"Vector2f.java","path":"src/com/ch/math/Vector2f.java","content":{"structured":{"description":"A `Vector2f` class that represents 2D vectors in mathematical form. It provides various methods for manipulating and transforming these vectors, including length, maximum, dot product, normalization, cross product, and rotation. Additionally, it includes methods for adding, subtracting, multiplying, and dividing vectors, as well as providing an `abs` method for calculating the absolute value of a vector. The class also provides a `toString` method for converting the vector to a string representation, and an `aset` method for setting the vector's components directly.","items":[{"id":"8c5f2a7d-6fa1-f8a9-1640-454a34f725ac","ancestors":[],"type":"function","description":"TODO","name":"Vector2f","code":"public class Vector2f {\n\t\n\tprivate float x;\n\tprivate float y;\n\n\tpublic Vector2f(float x, float y) {\n\t\tthis.x = x;\n\t\tthis.y = y;\n\t}\n\n\tpublic float length() {\n\t\treturn (float) Math.sqrt(x * x + y * y);\n\t}\n\n\tpublic float max() {\n\t\treturn Math.max(x, y);\n\t}\n\n\tpublic float dot(Vector2f r) {\n\t\treturn x * r.getX() + y * r.getY();\n\t}\n\n\tpublic Vector2f normalized() {\n\t\tfloat length = length();\n\n\t\treturn new Vector2f(x / length, y / length);\n\t}\n\n\tpublic float cross(Vector2f r) {\n\t\treturn x * r.getY() - y * r.getX();\n\t}\n\n\tpublic Vector2f lerp(Vector2f dest, float lerpFactor) {\n\t\treturn dest.sub(this).mul(lerpFactor).add(this);\n\t}\n\n\tpublic Vector2f rotate(float angle) {\n\t\tdouble rad = Math.toRadians(angle);\n\t\tdouble cos = Math.cos(rad);\n\t\tdouble sin = Math.sin(rad);\n\n\t\treturn new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));\n\t}\n\n\tpublic Vector2f add(Vector2f r) {\n\t\treturn new Vector2f(x + r.getX(), y + r.getY());\n\t}\n\n\tpublic Vector2f add(float r) {\n\t\treturn new Vector2f(x + r, y + r);\n\t}\n\n    public Vector2f add(float x, float y) {\n        return new Vector2f(this.x + x, this.y + y);\n    }\n\n\tpublic Vector2f sub(Vector2f r) {\n\t\treturn new Vector2f(x - r.getX(), y - r.getY());\n\t}\n\n\tpublic Vector2f sub(float r) {\n\t\treturn new Vector2f(x - r, y - r);\n\t}\n\n\tpublic Vector2f mul(Vector2f r) {\n\t\treturn new Vector2f(x * r.getX(), y * r.getY());\n\t}\n\n\tpublic Vector2f mul(float r) {\n\t\treturn new Vector2f(x * r, y * r);\n\t}\n\n\tpublic Vector2f div(Vector2f r) {\n\t\treturn new Vector2f(x / r.getX(), y / r.getY());\n\t}\n\n\tpublic Vector2f div(float r) {\n\t\treturn new Vector2f(x / r, y / r);\n\t}\n\n\tpublic Vector2f abs() {\n\t\treturn new Vector2f(Math.abs(x), Math.abs(y));\n\t}\n\n\tpublic String toString() {\n\t\treturn \"(\" + x + \" \" + y + \")\";\n\t}\n\n\tpublic Vector2f set(float x, float y) {\n\t\tthis.x = x;\n\t\tthis.y = y;\n\t\treturn this;\n\t}\n\n\tpublic Vector2f set(Vector2f r) {\n\t\tset(r.getX(), r.getY());\n\t\treturn this;\n\t}\n\n    public Vector3f as3DVector() {\n        return new Vector3f(x, y, 0);\n    }\n\n\tpublic float getX() {\n\t\treturn x;\n\t}\n\n\tpublic void setX(float x) {\n\t\tthis.x = x;\n\t}\n\n\tpublic float getY() {\n\t\treturn y;\n\t}\n\n\tpublic void setY(float y) {\n\t\tthis.y = y;\n\t}\n\n\tpublic boolean equals(Vector2f r) {\n\t\treturn x == r.getX() && y == r.getY();\n\t}\n\t\n}","location":{"start":3,"insert":3,"offset":" ","indent":0},"item_type":"class","length":124},{"id":"c93134bc-fb00-ae94-054a-c18d82bd654b","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"calculates the Euclidean distance of a point in two-dimensional space, given its coordinates `x` and `y`. It uses the formula for the length of a vector to return the square root of the sum of the squares of the coordinates.","params":[],"returns":{"type_name":"float","description":"the square root of the sum of the squares of the `x` and `y` coordinates.","complex_type":false},"usage":{"language":"java","code":"Vector2f myVector = new Vector2f(3, 4);\nSystem.out.println(myVector.length()); // Outputs 5\n","description":""},"name":"length","code":"public float length() {\n\t\treturn (float) Math.sqrt(x * x + y * y);\n\t}","location":{"start":13,"insert":13,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"fcdfc8c1-04e0-f8ab-ff4d-3682f4a4eaec","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"returns the maximum value of two floating-point arguments `x` and `y`.","params":[],"returns":{"type_name":"float","description":"the larger of the two input values, `x` and `y`, represented as a floating-point number.","complex_type":false},"usage":{"language":"java","code":"float myVector = new Vector2f(3, 4).max();\n","description":"\nThis will return a float equal to the largest value between 3 and 4. In this case, it will return the number 4."},"name":"max","code":"public float max() {\n\t\treturn Math.max(x, y);\n\t}","location":{"start":17,"insert":17,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"80ac7105-c2ac-2dba-4248-f41a9897ad36","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a `Vector2f` object `r` as input and computes the dot product of its `x` and `y` components with the corresponding components of the function's input parameters, returning the result as a float value.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector to be multiplied with the component values of the `Vector2f` object returned by the method.\n\n* `r` is a `Vector2f` class instance representing a two-dimensional vector in homogeneous coordinates.\n* `x` and `y` are the x and y coordinates of the vector, respectively.","complex_type":true}],"returns":{"type_name":"float","description":"a floating-point number representing the dot product of the input vector and the component values of the function.","complex_type":false},"usage":{"language":"java","code":"Vector2f vector1 = new Vector2f(3, 4);\nvector1.dot(new Vector2f(5, 6)); //Returns 37\n","description":""},"name":"dot","code":"public float dot(Vector2f r) {\n\t\treturn x * r.getX() + y * r.getY();\n\t}","location":{"start":21,"insert":21,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"ce164af2-5b94-42a1-e644-7186b7d62f35","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"normalizes a vector by dividing its components by their magnitudes, returning a new vector with the same direction but with magnitude equal to the original vector's length.","params":[],"returns":{"type_name":"Vector2f","description":"a normalized version of the input vector, with a length of 1.\n\n* The returned vector has the same x-coordinates as the original input vector, scaled by the length of the original vector.\n* The returned vector has the same y-coordinates as the original input vector, scaled by the length of the original vector.\n* The scale factor is always non-zero, meaning that the output vector will never be zero or have a magnitude of zero.\n\nOverall, the `normalized` function takes an input vector and returns a scaled version of it, where the scaling factor is the length of the input vector.","complex_type":true},"usage":{"language":"java","code":"Vector2f v = new Vector2f(3,4);\nv.normalize();\nSystem.out.println(v.x + \" \" + v.y);\n","description":""},"name":"normalized","code":"public Vector2f normalized() {\n\t\tfloat length = length();\n\n\t\treturn new Vector2f(x / length, y / length);\n\t}","location":{"start":25,"insert":25,"offset":" ","indent":1},"item_type":"method","length":5},{"id":"fb6b995a-a7f1-8599-d041-504fe7e0a319","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"computes the vector product of two vectors, returning a scalar value representing the magnitude of the cross product.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector to perform the cross product with the current vector.\n\n* `r` is a `Vector2f` object representing a 2D point or vector in the mathematical space.\n* `x` and `y` are the coordinates of the point or vector, which are float values.\n\nThe function calculates the cross product of two vectors by multiplying the x-coordinate of the first vector by the y-coordinate of the second vector and vice versa, and then returning the result as a floating-point value.","complex_type":true}],"returns":{"type_name":"float","description":"a floating-point value representing the dot product of two vectors.","complex_type":false},"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(3, 4);\nVector2f v2 = new Vector2f(6, 5);\nfloat crossProduct = v1.cross(v2); // equals -3\n","description":""},"name":"cross","code":"public float cross(Vector2f r) {\n\t\treturn x * r.getY() - y * r.getX();\n\t}","location":{"start":31,"insert":31,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"72943539-ba8d-07a7-404d-73f0bfb5b030","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a `Vector2f` object `dest` and a `float` parameter `lerpFactor`, and returns a new `Vector2f` object that is a linear interpolation of the current object and `dest`.","params":[{"name":"dest","type_name":"Vector2f","description":"2D destination point to which the vector is being interpolated towards.\n\nThe `dest` parameter is of type `Vector2f`, which represents a 2D vector in mathematical notation. It contains the target values for the lerping operation.","complex_type":true},{"name":"lerpFactor","type_name":"float","description":"linear interpolation factor between the current vector value and the destination vector value.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a vector that interpolates between the input `dest` and the current position of the object.\n\n* The output is a `Vector2f` object that represents the interpolated value between the input `dest` and the current position of the entity.\n* The interpolation is performed using the `sub`, `mul`, and `add` methods of the `Vector2f` class, which allow for smooth and precise calculation of the interpolated value.\n* The `lerpFactor` parameter represents the ratio of the distance between the input `dest` and the current position of the entity to the total distance between the input `dest` and the entity's final destination.","complex_type":true},"usage":{"language":"java","code":"Vector2f position = new Vector2f(10, 10);\nVector2f destination = new Vector2f(15, 20);\n\n// Calculate the linear interpolation between the current and destination positions\nVector2f lerpedPosition = position.lerp(destination, 0.5f);\n\nSystem.out.println(\"Lerped Position: \" + lerpedPosition);\n","description":""},"name":"lerp","code":"public Vector2f lerp(Vector2f dest, float lerpFactor) {\n\t\treturn dest.sub(this).mul(lerpFactor).add(this);\n\t}","location":{"start":35,"insert":35,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"ec31e801-89f9-d482-1b42-ff13463c762c","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"rotates a `Vector2f` object by the specified angle in radians, returning a new `Vector2f` object with the rotated coordinates.","params":[{"name":"angle","type_name":"float","description":"2D angle of rotation in radians, which is used to calculate the resulting vector's coordinates.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` object with the x and y components rotated by the specified angle.\n\nThe output is a `Vector2f` object, which represents a 2D point in homogeneous coordinates. The x-coordinate represents the horizontal position of the point, while the y-coordinate represents its vertical position.\n\nThe values of the output are calculated by multiplying the original point's x and y coordinates by the cosine and sine of the angle of rotation, respectively. This results in a new point that has been rotated around the origin by the specified angle.","complex_type":true},"usage":{"language":"java","code":"Vector2f vector = new Vector2f(1, 0);\nvector.rotate(90);\nSystem.out.println(vector); // prints (0 1)\n","description":""},"name":"rotate","code":"public Vector2f rotate(float angle) {\n\t\tdouble rad = Math.toRadians(angle);\n\t\tdouble cos = Math.cos(rad);\n\t\tdouble sin = Math.sin(rad);\n\n\t\treturn new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));\n\t}","location":{"start":39,"insert":39,"offset":" ","indent":1},"item_type":"method","length":7},{"id":"d7f621ff-cb0f-ee89-bd4a-61cb2aab77e3","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a `Vector2f` argument `r` and returns a new `Vector2f` instance with the sum of the current vector's components and those of the given `r` vector.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector to be added to the current vector.\n\nThe `Vector2f` object `r` represents a two-dimensional vector with an x-component and a y-component, both of which are doubles. The x-component is represented by the variable `x`, and the y-component is represented by the variable `y`.","complex_type":true}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` object representing the sum of the input vectors.\n\nThe returned vector has an x-coordinate that is equal to the sum of the x-coordinates of the two input vectors and a y-coordinate that is equal to the sum of the y-coordinates of the two input vectors.","complex_type":true},"usage":{"language":"java","code":"public static void main(String[] args) {\n\tVector2f vector1 = new Vector2f(3, 4);\n\tVector2f vector2 = new Vector2f(5, 6);\n\tVector2f result = vector1.add(vector2); //returns (8, 10)\n}\n","description":""},"name":"add","code":"public Vector2f add(Vector2f r) {\n\t\treturn new Vector2f(x + r.getX(), y + r.getY());\n\t}","location":{"start":47,"insert":47,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"b7e34fe0-3384-6d88-fc4c-7594bb3b2914","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"adds a floating-point value to the vector's x and y components, returning a new vector object with the updated values.","params":[{"name":"r","type_name":"float","description":"2D vector to be added to the current vector.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` instance with the sum of the original vector's components and the given scalar value.\n\nThe Vector2f object returned by the `add` function has two components, x and y, which represent the added value in each dimension, respectively. Specifically, the x component represents the addition of r to the existing x-coordinate, while the y component represents the addition of r to the existing y-coordinate. The resulting vector maintains the same orientation as the original input vector.","complex_type":true},"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(2, 3);\nVector2f v2 = new Vector2f(-5, -7);\nfloat r = 4;\nVector2f result = v1.add(r);\nSystem.out.println(result); // Output: (6, 7)\n","description":"\nIn this example, the `v1` vector is initialized with the values `(2, 3)`, and the `v2` vector is initialized with the values `(-5, -7)`. The `add` method is called on the `v1` vector with a float value of `4`, which results in a new vector that is added to the original vector. This new vector is then printed out using the `System.out.println` method."},"name":"add","code":"public Vector2f add(float r) {\n\t\treturn new Vector2f(x + r, y + r);\n\t}","location":{"start":51,"insert":51,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"285e3bb9-006f-4bb3-ce45-f2e2edda5b35","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes two floating-point arguments `x` and `y`, and returns a new `Vector2f` instance with the sum of the current instance's `x` and `y` components and the input `x` and `y` components.","params":[{"name":"x","type_name":"float","description":"2D coordinate that is added to the current position of the vector.","complex_type":false},{"name":"y","type_name":"float","description":"2nd component of the resulting vector.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` instance representing the sum of the input `x` and `y` values.\n\nThe return type is `Vector2f`, which means it represents a 2D vector with floating-point components.\nThe method takes two floating-point arguments `x` and `y` and adds them to the corresponding components of this object's vector, resulting in a new vector object that represents the sum of the original vector and the input values.","complex_type":true},"usage":{"language":"java","code":"float x = 3;\nfloat y = 5;\nVector2f vector2f = new Vector2f(x, y);\nvector2f.add(10, 10);\n\n//Outputs \"x: 13 y: 15\"\nSystem.out.println(\"x: \"+ vector2f.getX() + \" y: \" + vector2f.getY());\n","description":""},"name":"add","code":"public Vector2f add(float x, float y) {\n        return new Vector2f(this.x + x, this.y + y);\n    }","location":{"start":55,"insert":55,"offset":" ","indent":4},"item_type":"method","length":3},{"id":"00c85517-4a99-01b2-ae4a-b199c9c125e1","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a `Vector2f` argument `r` and returns a new `Vector2f` object representing the difference between the current vector and `r`.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector to be subtracted from the current vector.\n\n* `x`: The x-coordinate of the vector, which is also the input to the function.\n* `y`: The y-coordinate of the vector, which is also a part of the input to the function.","complex_type":true}],"returns":{"type_name":"Vector2f","description":"a new vector representing the difference between the input vector and the reference vector.\n\nThe returned value represents the difference between the original vector's components (x and y) and the input vector's components (r.x and r.y).\n\nThe resulting vector has the same magnitude as the original vector, but its direction is shifted by the amount of the input vector's components.\n\nThe returned value can be used to calculate various geometric calculations such as distances, angles, and areas.","complex_type":true},"usage":{"language":"java","code":"Vector2f myVector = new Vector2f(10, 20);\nVector2f otherVector = new Vector2f(-15, 43);\nmyVector.sub(otherVector).toString(); // Returns (-25, -6)\n","description":""},"name":"sub","code":"public Vector2f sub(Vector2f r) {\n\t\treturn new Vector2f(x - r.getX(), y - r.getY());\n\t}","location":{"start":59,"insert":59,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"d25b53eb-5a1b-f19b-124b-7cf6b2f03aa4","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a single float argument `r` and returns a new `Vector2f` object with components `x - r` and `y - r`.","params":[{"name":"r","type_name":"float","description":"2D vector to subtract from the current vector.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` instance representing the difference between the original vector and the given scalar value.\n\nThe output is a `Vector2f` object representing a point in 2D space, where `x` and `y` represent the x- and y-coordinates, respectively, of the point. The value of `r` is subtracted from both coordinates to obtain the resulting point.","complex_type":true},"usage":{"language":"java","code":"Vector2f v = new Vector2f(3, 4);\nfloat r = 2;\nv.sub(r); // return a new vector with x - r = 1 and y - r = 2\n","description":"\nIn this example we create a new instance of the Vector2f class with x and y values equal to 3 and 4, respectively, and then use the sub method to subtract a value of 2 from both the x and y components. The result is a new vector with x - r = 1 and y - r = 2."},"name":"sub","code":"public Vector2f sub(float r) {\n\t\treturn new Vector2f(x - r, y - r);\n\t}","location":{"start":63,"insert":63,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"ad60e43f-7ba4-a3a0-fb4f-aec8fdb7de63","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"multiplies a `Vector2f` object by another `Vector2f` object, returning a new `Vector2f` with the product of the two components.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector that multiplies with the current vector, resulting in a new 2D vector output.\n\n* `x`: The real part of the vector representing the x-coordinate.\n* `y`: The imaginary part of the vector representing the y-coordinate.","complex_type":true}],"returns":{"type_name":"Vector2f","description":"a new vector with the product of the input vector's x and y components multiplied by the corresponding components of the passed vector.\n\nThe `Vector2f` object returned by the function has the same x-coordinate as the input `r`, and the y-coordinate is obtained by multiplying the x-coordinate of the input by the scalar value passed as an argument to the function.\n\nThe resulting vector object has a length equal to the product of the magnitudes of the two input vectors, and its direction is the same as that of the input vector r, but with its magnitude increased by the product of the scalar value and the magnitude of the input vector.","complex_type":true},"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(3, 4);\nVector2f v2 = new Vector2f(5, 6);\nVector2f result = v1.mul(v2);\nSystem.out.println(\"The resulting vector is \" + result);\n","description":"\nThis will output: The resulting vector is (15, 24)"},"name":"mul","code":"public Vector2f mul(Vector2f r) {\n\t\treturn new Vector2f(x * r.getX(), y * r.getY());\n\t}","location":{"start":67,"insert":67,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"ca83feb3-6c74-55a6-2048-114dc23629e0","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"multiplies the components of a `Vector2f` object by a given scalar value, returning a new `Vector2f` object with the scaled components.","params":[{"name":"r","type_name":"float","description":"scalar value to be multiplied with the vector components of the `Vector2f` instance returned by the function.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` instance with x and y components scaled by the input `r`.\n\nThe output is a `Vector2f` object, representing a 2D point with x and y components.\nThe x component of the output is equal to the product of the x component of the input vector and the scalar value r.\nThe y component of the output is equal to the product of the y component of the input vector and the scalar value r.","complex_type":true},"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(3, 4);\nfloat r = 2;\nVector2f result = v1.mul(r); // result will be (6, 8)\n","description":""},"name":"mul","code":"public Vector2f mul(float r) {\n\t\treturn new Vector2f(x * r, y * r);\n\t}","location":{"start":71,"insert":71,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"4da5ef82-eb22-4897-6d4a-a4a0c68ebcf7","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a vector `r` as input and returns a new vector with the same x-component scaled by the reciprocal of the x-component of `r`, and the y-component scaled by the reciprocal of the y-component of `r`.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector to be divided by the current vector.\n\n* `x`: The x-coordinate of `r`.\n* `y`: The y-coordinate of `r`.","complex_type":true}],"returns":{"type_name":"Vector2f","description":"a new vector with the same x-coordinates as the original vector, scaled by the reciprocal of the input vector's x-coordinate.\n\nThe output is a `Vector2f` object with x-coord and y-coord components calculated as follows: `x / r.getX()` and `y / r.getY()`. Therefore, the output has the same units (e.g. pixels) as the input.","complex_type":true},"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(4, 6);\nVector2f v2 = new Vector2f(3, 7);\n\nVector2f result = v1.div(v2);\nSystem.out.println(\"Result of dividing \" + v1 + \" by \" + v2 + \" is: \" + result);\n","description":""},"name":"div","code":"public Vector2f div(Vector2f r) {\n\t\treturn new Vector2f(x / r.getX(), y / r.getY());\n\t}","location":{"start":75,"insert":75,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"18446df5-06b1-c280-a340-6ac52de13495","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"takes a single floating-point argument `r` and returns a new `Vector2f` instance with scaled x and y components proportional to `r`.","params":[{"name":"r","type_name":"float","description":"scalar value that is used to perform the division on the `x` and `y` components of the `Vector2f` object.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` instance with the x-coordinate and y-coordinate scaled by the input parameter `r`.\n\n* The output is a `Vector2f` object representing a scaled version of the original vector.\n* The scaling factor is the input parameter `r`.\n* The x and y components of the output are calculated as the original x and y components divided by the input r.","complex_type":true},"usage":{"language":"java","code":"Vector2f myVector = new Vector2f(5, 3);\nfloat divisor = 2;\nVector2f result = myVector.div(divisor);\nSystem.out.println(\"Result: \" + result);\n","description":"\nThis example initializes a Vector2f object with the values (5, 3) and then divides each of its components by the divisor value of 2. The method returns a new Vector2f object that contains the resulting values. Finally, it prints the result to the console."},"name":"div","code":"public Vector2f div(float r) {\n\t\treturn new Vector2f(x / r, y / r);\n\t}","location":{"start":79,"insert":79,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"8c83ce5b-18f7-5cb4-7a44-8affe84c6a09","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"calculates and returns a new `Vector2f` instance with the absolute values of its `x` and `y` components.","params":[],"returns":{"type_name":"Vector2f","description":"a new `Vector2f` instance with the absolute value of its input components.\n\n* The output is a Vector2f object representing the absolute value of the input Vector2f.\n* The x and y components of the output are equal to the absolute values of the corresponding input components.\n* The output has the same dimensions as the input, i.e., it has the same number of elements.","complex_type":true},"usage":{"language":"java","code":"public Vector2f v1 = new Vector2f(-5, 7);\npublic Vector2f v2 = new Vector2f(0, -3);\nSystem.out.println(v1.abs()); // Output (5, 7)\nSystem.out.println(v2.abs()); // Output (0, 3)\n","description":""},"name":"abs","code":"public Vector2f abs() {\n\t\treturn new Vector2f(Math.abs(x), Math.abs(y));\n\t}","location":{"start":83,"insert":83,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"e8b5b6ab-e7d6-8097-814b-24174c00c784","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"returns a string representing the location of an object in a two-dimensional space, comprising its x and y coordinates enclosed in parentheses.","params":[],"returns":{"type_name":"string","description":"a string representing the point (x, y) using parentheses and the values of x and y separated by a space.\n\n* The parentheses in the return statement indicate that the output is a tuple of two values, x and y.\n* The individual values within the parentheses are also expressed as literals, indicating that they are not variables or expressions that can be evaluated at runtime.\n* The concatenation operator used to combine the two values into a single string (\" \" separates them) suggests that these values may represent coordinates or positions in some context.","complex_type":true},"usage":{"language":"java","code":"Vector2f vector = new Vector2f(3, 4);\nSystem.out.println(vector.toString()); //prints (3 4)\n","description":""},"name":"toString","code":"public String toString() {\n\t\treturn \"(\" + x + \" \" + y + \")\";\n\t}","location":{"start":87,"insert":87,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"b87b1718-9649-699b-944d-d1af6cf6f5de","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"modifies the component values of a `Vector2f` object by assigning new float values to its `x` and `y` fields, and then returns the modified object reference.","params":[{"name":"x","type_name":"float","description":"2D coordinate value that updates the `x` component of the `Vector2f` object.","complex_type":false},{"name":"y","type_name":"float","description":"2nd component of the `Vector2f` object, which is being updated to match the provided value.","complex_type":false}],"returns":{"type_name":"Vector2f","description":"a reference to the same `Vector2f` instance, with the `x` and `y` components updated to the provided values.\n\nThe `Vector2f` class returns an instance of itself after setting the `x` and `y` components of the object to the input values `x` and `y`, respectively. Therefore, the output is the same instance of `Vector2f` that was passed as an argument to the function.","complex_type":true},"usage":{"language":"java","code":"Vector2f vector2 = new Vector2f();\nvector2.set(1, 2);\nSystem.out.println(vector2); // (1.0 2.0)\n","description":""},"name":"set","code":"public Vector2f set(float x, float y) {\n\t\tthis.x = x;\n\t\tthis.y = y;\n\t\treturn this;\n\t}","location":{"start":91,"insert":91,"offset":" ","indent":1},"item_type":"method","length":5},{"id":"96baf71b-05e1-259b-fd4a-f1d4a87f6c80","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"sets the corresponding components of this vector to the corresponding components of the `Vector2f` reference `r`.","params":[{"name":"r","type_name":"Vector2f","description":"2D vector to be set as the value of the `Vector2f` instance, and its `getX()` and `getY()` methods are called to set the corresponding components of the instance.\n\n* `getX()` and `getY()`: These are methods that retrieve the x-coordinate and y-coordinate values of `r`, respectively.","complex_type":true}],"returns":{"type_name":"Vector2f","description":"a reference to the original vector with the x and y coordinates updated.\n\n* The method sets the values of the `x` and `y` fields of the `Vector2f` object to those of the parameter `r`.\n* The return type of the method is the same as the original object, indicating that the method does not create a new object but rather modifies the existing one.\n* The method name `set` suggests that it is intended for setting or modifying the properties of an object, rather than creating a new one.","complex_type":true},"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(5, 3);\nv1.set(new Vector2f(-10, -8)); // Set v1 to (-10, -8)\n","description":""},"name":"set","code":"public Vector2f set(Vector2f r) {\n\t\tset(r.getX(), r.getY());\n\t\treturn this;\n\t}","location":{"start":97,"insert":97,"offset":" ","indent":1},"item_type":"method","length":4},{"id":"dcc58639-de8a-67b5-3546-01b5eb3c2aa5","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"converts a `Vector2f` instance into a `Vector3f` instance by adding a third component representing the z-coordinate, which is initially set to zero.","params":[],"returns":{"type_name":"Vector3f","description":"a new `Vector3f` instance with values `(x, y, 0)`.\n\n* The Vector3f object represents a 3D vector with x, y, and z components.\n* The x, y, and z components are set to the input values of the function.\n* The resulting Vector3f object has a length of 0, indicating that it is a unit vector.","complex_type":true},"usage":{"language":"java","code":"import com.ch.math.Vector2f;\nimport com.ch.math.Vector3f;\n\npublic class Main {\n\tpublic static void main(String[] args) {\n\t\tVector2f v = new Vector2f(1, 1);\n\t\tVector3f v3 = v.as3DVector();\n\t\tSystem.out.println(v3); // Output: (1.0, 1.0, 0.0)\n\t}\n}\n","description":"\nThis code creates a Vector2f object with coordinates (1, 1). Then the as3DVector method is called on this vector to convert it into a 3D vector with z-coordinate set to zero. Finally, the resulting 3D vector is printed using the println method from the System class."},"name":"as3DVector","code":"public Vector3f as3DVector() {\n        return new Vector3f(x, y, 0);\n    }","location":{"start":102,"insert":102,"offset":" ","indent":4},"item_type":"method","length":3},{"id":"2a24b730-709f-c0a6-0649-2feb5c40f0fd","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"returns the value of the `x` field.","params":[],"returns":{"type_name":"float","description":"a floating-point value representing the variable `x`.","complex_type":false},"usage":{"language":"java","code":"public class MyClass {\n    public static void main(String[] args) {\n        Vector2f vector = new Vector2f(1, 1);\n        System.out.println(\"X value of vector is: \" + vector.getX());\n    }\n}\n","description":""},"name":"getX","code":"public float getX() {\n\t\treturn x;\n\t}","location":{"start":106,"insert":106,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"a8bfb3da-3e49-daa6-2642-6a40c48ca226","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"sets the value of the `x` field of its container object to the argument passed as a float value.","params":[{"name":"x","type_name":"float","description":"float value that will be assigned to the `x` field of the class instance upon calling the `setX()` method.","complex_type":false}],"usage":{"language":"java","code":"Vector2f vector = new Vector2f(5, 3);\nvector.setX(10);\nSystem.out.println(vector); // Outputs (10.0, 3.0)\n","description":"\nIn this example, we create a Vector2f with x and y values of 5 and 3 respectively. We then use the setX method to change the value of x to 10, which will result in the vector having an x value of 10 and a y value of 3. Finally, we print out the updated vector using System.out.println()."},"name":"setX","code":"public void setX(float x) {\n\t\tthis.x = x;\n\t}","location":{"start":110,"insert":110,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"103a0edb-ad7f-9192-854b-c363ffd39eae","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"returns the value of `y`.","params":[],"returns":{"type_name":"float","description":"the value of the `y` field.","complex_type":false},"usage":{"language":"java","code":"Vector2f vector = new Vector2f(0, 0);\nfloat y = vector.getY();\n","description":""},"name":"getY","code":"public float getY() {\n\t\treturn y;\n\t}","location":{"start":114,"insert":114,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"ef77a7d6-fc4d-f4bd-2e4c-dbdcc9acfa85","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"sets the value of the field `y` to the argument passed as a float.","params":[{"name":"y","type_name":"float","description":"float value that will be assigned to the `y` field of the object being passed as an argument to the `setY()` method.","complex_type":false}],"usage":{"language":"java","code":"Vector2f v1 = new Vector2f(0, 0);\nv1.setY(5); // sets y value to 5\nSystem.out.println(v1); // prints \"(0 5)\"\n","description":""},"name":"setY","code":"public void setY(float y) {\n\t\tthis.y = y;\n\t}","location":{"start":118,"insert":118,"offset":" ","indent":1},"item_type":"method","length":3},{"id":"8b3e6962-e2ee-4881-5242-eb0cf77f2b08","ancestors":["8c5f2a7d-6fa1-f8a9-1640-454a34f725ac"],"type":"function","description":"compares two `Vector2f` objects based on their `x` and `y` components, returning `true` if they are identical, and `false` otherwise.","params":[{"name":"r","type_name":"Vector2f","description":"2D point to which the current vector is compared for equality.\n\n`x`: It is a double value representing the x-coordinate of the vector.","complex_type":true}],"returns":{"type_name":"Boolean","description":"a boolean value indicating whether the Vector2f object has the same x and y coordinates as the provided Vector2f object.\n\n* `x`: The value of `x` in the current object is compared with the corresponding value of `r`. If they are equal, the function returns `true`.\n* `y`: The value of `y` in the current object is compared with the corresponding value of `r`. If they are equal, the function returns `true`.","complex_type":true},"usage":{"language":"java","code":"//create Vector2f objects with the coordinates (0, 1) and (3, 4) respectively\nVector2f v1 = new Vector2f(0, 1);\nVector2f v2 = new Vector2f(3, 4);\n\n//check if two vectors are equal using method equals()\nif (v1.equals(v2)) {\n\tSystem.out.println(\"Vectors are equal\");\n} else {\n\tSystem.out.println(\"Vectors are not equal\");\n}\n","description":"\nIn this example, the first Vector2f object is created with coordinates (0, 1) and the second object is created with coordinates (3, 4). Then, the `equals()` method is called on both objects to check if they are equal. The output would be \"Vectors are not equal\" because the two vectors have different coordinates."},"name":"equals","code":"public boolean equals(Vector2f r) {\n\t\treturn x == r.getX() && y == r.getY();\n\t}","location":{"start":122,"insert":122,"offset":" ","indent":1},"item_type":"method","length":3}]}}}