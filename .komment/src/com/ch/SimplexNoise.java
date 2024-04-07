{"name":"SimplexNoise.java","path":"src/com/ch/SimplexNoise.java","content":{"structured":{"description":"","items":[{"id":"6ac77109-ae7d-4714-8179-41741b7e784c","ancestors":[],"type":"function","name":"fastfloor","location":{"offset":"\t","indent":2,"insert":43,"start":43},"returns":"int","params":[{"name":"x","type":"double"}],"code":"private static int fastfloor(double x) {\n\t return x>0 ? (int)x : (int)x-1;\n\t }","skip":false,"length":3,"comment":{"description":"calculates the nearest integer to a given double value, rounding down if the input is positive and up if it is negative.","params":[{"name":"x","type":"double","description":"floating-point value to be rounded."}],"returns":{"type":"int","description":"an integer value representing the nearest integer to the input double value."}}},{"id":"bfd4cf85-c225-4c74-b1bd-21f3d777c095","ancestors":[],"type":"function","name":"dot","location":{"offset":"\t","indent":2,"insert":46,"start":46},"returns":"double","params":[{"name":"g","type":"int"},{"name":"x","type":"double"},{"name":"y","type":"double"}],"code":"private static double dot(int g[], double x, double y) {\n\t return g[0]*x + g[1]*y; }","skip":false,"length":2,"comment":{"description":"computes the dot product of two vectors, represented as integer indices `g[0]` and `g[1]`. The result is a double value representing the dot product of the two vectors.","params":[{"name":"g","type":"int","description":"2D coordinates of a point to which the dot product is being computed."},{"name":"x","type":"double","description":"xtick value in the dot product calculation."},{"name":"y","type":"double","description":"2nd coordinate of the point for which the dot product is being calculated."}],"returns":{"type":"double","description":"a double value representing the dot product of the input vectors."}}},{"id":"bcb45ffb-25e4-4972-bc54-321fadfcb640","ancestors":[],"type":"function","name":"dot","location":{"offset":"\t","indent":2,"insert":48,"start":48},"returns":"double","params":[{"name":"g","type":"int"},{"name":"x","type":"double"},{"name":"y","type":"double"},{"name":"z","type":"double"}],"code":"private static double dot(int g[], double x, double y, double z) {\n\t return g[0]*x + g[1]*y + g[2]*z; }","skip":false,"length":2,"comment":{"description":"computes the dot product of a given vector with another vector or a scalar.","params":[{"name":"g","type":"int","description":"3D coordinates of a point in space, which are multiplied by the x, y, and z components of the input values to compute the final output value."},{"name":"x","type":"double","description":"0-based index of the first array element to be multiplied by the corresponding value in the `g` array."},{"name":"y","type":"double","description":"2nd dimension of the grid, which is multiplied by the corresponding element of the `g` array to compute the output value."},{"name":"z","type":"double","description":"3rd dimension of the array `g`, which is multiplied by the corresponding component of the input values `x`, `y`, and `z` to compute the output value."}],"returns":{"type":"double","description":"a value representing the dot product of the input vectors."}}},{"id":"e6fda967-3d77-43ef-96e3-74c1eccd0a13","ancestors":[],"type":"function","name":"dot","location":{"offset":"\t","indent":2,"insert":50,"start":50},"returns":"double","params":[{"name":"g","type":"int"},{"name":"x","type":"double"},{"name":"y","type":"double"},{"name":"z","type":"double"},{"name":"w","type":"double"}],"code":"private static double dot(int g[], double x, double y, double z, double w) {\n\t return g[0]*x + g[1]*y + g[2]*z + g[3]*w; }","skip":false,"length":2,"comment":{"description":"computes the dot product of a given set of values and a vector of numerical values.","params":[{"name":"g","type":"int","description":"4-dimensional vector that contains the dot product of the 4 components with the corresponding coordinates `x`, `y`, `z`, and `w`."},{"name":"x","type":"double","description":"0th element of the input array `g`."},{"name":"y","type":"double","description":"2nd component of the input vector and is multiplied by the corresponding element of the input array `g`."},{"name":"z","type":"double","description":"3rd coordinate of the input vector, which is multiplied by the value of `g[2]` and added to the result of the previous computations."},{"name":"w","type":"double","description":"4th component of the input vector and is multiplied by the value of `g[3]` before being added to the sum of the other components."}],"returns":{"type":"double","description":"a value representing the dot product of the given vectors."}}},{"id":"a4327c6c-4192-4c62-9529-ec9aebbf4377","ancestors":[],"type":"function","name":"noise","location":{"offset":"\t","indent":2,"insert":53,"start":53},"returns":"double","params":[{"name":"xin","type":"double"},{"name":"yin","type":"double"}],"code":"public static double noise(double xin, double yin) {\n\t double n0, n1, n2; // Noise contributions from the three corners\n\t // Skew the input space to determine which simplex cell we're in\n\t final double F2 = 0.5*(Math.sqrt(3.0)-1.0);\n\t double s = (xin+yin)*F2; // Hairy factor for 2D\n\t int i = fastfloor(xin+s);\n\t int j = fastfloor(yin+s);\n\t final double G2 = (3.0-Math.sqrt(3.0))/6.0;\n\t double t = (i+j)*G2;\n\t double X0 = i-t; // Unskew the cell origin back to (x,y) space\n\t double Y0 = j-t;\n\t double x0 = xin-X0; // The x,y distances from the cell origin\n\t double y0 = yin-Y0;\n\t // For the 2D case, the simplex shape is an equilateral triangle.\n\t // Determine which simplex we are in.\n\t int i1, j1; // Offsets for second (middle) corner of simplex in (i,j) coords\n\t if(x0>y0) {i1=1; j1=0;} // lower triangle, XY order: (0,0)->(1,0)->(1,1)\n\t else {i1=0; j1=1;} // upper triangle, YX order: (0,0)->(0,1)->(1,1)\n\t // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and\n\t // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where\n\t // c = (3-sqrt(3))/6\n\t double x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) unskewed coords\n\t double y1 = y0 - j1 + G2;\n\t double x2 = x0 - 1.0 + 2.0 * G2; // Offsets for last corner in (x,y) unskewed coords\n\t double y2 = y0 - 1.0 + 2.0 * G2;\n\t // Work out the hashed gradient indices of the three simplex corners\n\t int ii = i & 255;\n\t int jj = j & 255;\n\t int gi0 = perm[ii+perm[jj]] % 12;\n\t int gi1 = perm[ii+i1+perm[jj+j1]] % 12;\n\t int gi2 = perm[ii+1+perm[jj+1]] % 12;\n\t // Calculate the contribution from the three corners\n\t double t0 = 0.5 - x0*x0-y0*y0;\n\t if(t0<0) n0 = 0.0;\n\t else {\n\t t0 *= t0;\n\t n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient\n\t }\n\t double t1 = 0.5 - x1*x1-y1*y1;\n\t if(t1<0) n1 = 0.0;\n\t else {\n\t t1 *= t1;\n\t n1 = t1 * t1 * dot(grad3[gi1], x1, y1);\n\t }\n\t double t2 = 0.5 - x2*x2-y2*y2;\n\t if(t2<0) n2 = 0.0;\n\t else {\n\t t2 *= t2;\n\t n2 = t2 * t2 * dot(grad3[gi2], x2, y2);\n\t }\n\t // Add contributions from each corner to get the final noise value.\n\t // The result is scaled to return values in the interval [-1,1].\n\t return 70.0 * (n0 + n1 + n2);\n\t }","skip":false,"length":54,"comment":{"description":"generates high-quality noise values for a given set of input coordinates using the Perlin noise algorithm. It takes two doubles as inputs, skews them to determine which simplex cell they belong to, and then calculates the contribution from each of the three corners of the simplex to produce the final noise value.","params":[{"name":"xin","type":"double","description":"2D coordinate of the point in the input space that is used to determine which simplex cell the point belongs to."},{"name":"yin","type":"double","description":"2D noise value for the y-coordinate of the current point, which is used in calculating the contribution from each simplex corner."}],"returns":{"type":"double","description":"a scaled noise value between [-1, 1]."}}},{"id":"0b494a2b-0adc-409c-8669-70b9033d0724","ancestors":[],"type":"function","name":"noise","location":{"offset":"\t","indent":2,"insert":108,"start":108},"returns":"double","params":[{"name":"xin","type":"double"},{"name":"yin","type":"double"},{"name":"zin","type":"double"}],"code":"public static double noise(double xin, double yin, double zin) {\n\t double n0, n1, n2, n3; // Noise contributions from the four corners\n\t // Skew the input space to determine which simplex cell we're in\n\t final double F3 = 1.0/3.0;\n\t double s = (xin+yin+zin)*F3; // Very nice and simple skew factor for 3D\n\t int i = fastfloor(xin+s);\n\t int j = fastfloor(yin+s);\n\t int k = fastfloor(zin+s);\n\t final double G3 = 1.0/6.0; // Very nice and simple unskew factor, too\n\t double t = (i+j+k)*G3;\n\t double X0 = i-t; // Unskew the cell origin back to (x,y,z) space\n\t double Y0 = j-t;\n\t double Z0 = k-t;\n\t double x0 = xin-X0; // The x,y,z distances from the cell origin\n\t double y0 = yin-Y0;\n\t double z0 = zin-Z0;\n\t // For the 3D case, the simplex shape is a slightly irregular tetrahedron.\n\t // Determine which simplex we are in.\n\t int i1, j1, k1; // Offsets for second corner of simplex in (i,j,k) coords\n\t int i2, j2, k2; // Offsets for third corner of simplex in (i,j,k) coords\n\t if(x0>=y0) {\n\t if(y0>=z0)\n\t { i1=1; j1=0; k1=0; i2=1; j2=1; k2=0; } // X Y Z order\n\t else if(x0>=z0) { i1=1; j1=0; k1=0; i2=1; j2=0; k2=1; } // X Z Y order\n\t else { i1=0; j1=0; k1=1; i2=1; j2=0; k2=1; } // Z X Y order\n\t }\n\t else { // x0<y0\n\t if(y0<z0) { i1=0; j1=0; k1=1; i2=0; j2=1; k2=1; } // Z Y X order\n\t else if(x0<z0) { i1=0; j1=1; k1=0; i2=0; j2=1; k2=1; } // Y Z X order\n\t else { i1=0; j1=1; k1=0; i2=1; j2=1; k2=0; } // Y X Z order\n\t }\n\t // A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),\n\t // a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z), and\n\t // a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z), where\n\t // c = 1/6.\n\t double x1 = x0 - i1 + G3; // Offsets for second corner in (x,y,z) coords\n\t double y1 = y0 - j1 + G3;\n\t double z1 = z0 - k1 + G3;\n\t double x2 = x0 - i2 + 2.0*G3; // Offsets for third corner in (x,y,z) coords\n\t double y2 = y0 - j2 + 2.0*G3;\n\t double z2 = z0 - k2 + 2.0*G3;\n\t double x3 = x0 - 1.0 + 3.0*G3; // Offsets for last corner in (x,y,z) coords\n\t double y3 = y0 - 1.0 + 3.0*G3;\n\t double z3 = z0 - 1.0 + 3.0*G3;\n\t // Work out the hashed gradient indices of the four simplex corners\n\t int ii = i & 255;\n\t int jj = j & 255;\n\t int kk = k & 255;\n\t int gi0 = perm[ii+perm[jj+perm[kk]]] % 12;\n\t int gi1 = perm[ii+i1+perm[jj+j1+perm[kk+k1]]] % 12;\n\t int gi2 = perm[ii+i2+perm[jj+j2+perm[kk+k2]]] % 12;\n\t int gi3 = perm[ii+1+perm[jj+1+perm[kk+1]]] % 12;\n\t // Calculate the contribution from the four corners\n\t double t0 = 0.6 - x0*x0 - y0*y0 - z0*z0;\n\t if(t0<0) n0 = 0.0;\n\t else {\n\t t0 *= t0;\n\t n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);\n\t }\n\t double t1 = 0.6 - x1*x1 - y1*y1 - z1*z1;\n\t if(t1<0) n1 = 0.0;\n\t else {\n\t t1 *= t1;\n\t n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);\n\t }\n\t double t2 = 0.6 - x2*x2 - y2*y2 - z2*z2;\n\t if(t2<0) n2 = 0.0;\n\t else {\n\t t2 *= t2;\n\t n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);\n\t }\n\t double t3 = 0.6 - x3*x3 - y3*y3 - z3*z3;\n\t if(t3<0) n3 = 0.0;\n\t else {\n\t t3 *= t3;\n\t n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);\n\t }\n\t // Add contributions from each corner to get the final noise value.\n\t // The result is scaled to stay just inside [-1,1]\n\t return 32.0*(n0 + n1 + n2 + n3);\n\t }","skip":false,"length":81,"comment":{"description":"generates a noise value for a given 3D coordinate, based on the hashed gradient indices of the four simplex corners and the distances between the coordinate and each corner. The resulting value is scaled to stay within [-1,1].","params":[{"name":"xin","type":"double","description":"3D noise position along the x-axis."},{"name":"yin","type":"double","description":"2D coordinate of the current point in the noise simulation, which is used to skew the input space and determine which simplex cell the point is in."},{"name":"zin","type":"double","description":"3D noise sample position and is used to calculate the contributions from the four simplex corners in the hashed gradient method."}],"returns":{"type":"double","description":"a scaled noise value between [-1, 1]."}}},{"id":"fb5c3305-8f6b-4852-8c69-86d6db4f9bb2","ancestors":[],"type":"function","name":"noise","location":{"offset":"\t","indent":2,"insert":190,"start":190},"returns":"double","params":[{"name":"x","type":"double"},{"name":"y","type":"double"},{"name":"z","type":"double"},{"name":"w","type":"double"}],"code":"double noise(double x, double y, double z, double w) {\n\n\t // The skewing and unskewing factors are hairy again for the 4D case\n\t final double F4 = (Math.sqrt(5.0)-1.0)/4.0;\n\t final double G4 = (5.0-Math.sqrt(5.0))/20.0;\n\t double n0, n1, n2, n3, n4; // Noise contributions from the five corners\n\t // Skew the (x,y,z,w) space to determine which cell of 24 simplices we're in\n\t double s = (x + y + z + w) * F4; // Factor for 4D skewing\n\t int i = fastfloor(x + s);\n\t int j = fastfloor(y + s);\n\t int k = fastfloor(z + s);\n\t int l = fastfloor(w + s);\n\t double t = (i + j + k + l) * G4; // Factor for 4D unskewing\n\t double X0 = i - t; // Unskew the cell origin back to (x,y,z,w) space\n\t double Y0 = j - t;\n\t double Z0 = k - t;\n\t double W0 = l - t;\n\t double x0 = x - X0; // The x,y,z,w distances from the cell origin\n\t double y0 = y - Y0;\n\t double z0 = z - Z0;\n\t double w0 = w - W0;\n\t // For the 4D case, the simplex is a 4D shape I won't even try to describe.\n\t // To find out which of the 24 possible simplices we're in, we need to\n\t // determine the magnitude ordering of x0, y0, z0 and w0.\n\t // The method below is a good way of finding the ordering of x,y,z,w and\n\t // then find the correct traversal order for the simplex were in.\n\t // First, six pair-wise comparisons are performed between each possible pair\n\t // of the four coordinates, and the results are used to add up binary bits\n\t // for an integer index.\n\t int c1 = (x0 > y0) ? 32 : 0;\n\t int c2 = (x0 > z0) ? 16 : 0;\n\t int c3 = (y0 > z0) ? 8 : 0;\n\t int c4 = (x0 > w0) ? 4 : 0;\n\t int c5 = (y0 > w0) ? 2 : 0;\n\t int c6 = (z0 > w0) ? 1 : 0;\n\t int c = c1 + c2 + c3 + c4 + c5 + c6;\n\t int i1, j1, k1, l1; // The integer offsets for the second simplex corner\n\t int i2, j2, k2, l2; // The integer offsets for the third simplex corner\n\t int i3, j3, k3, l3; // The integer offsets for the fourth simplex corner\n\t // simplex[c] is a 4-vector with the numbers 0, 1, 2 and 3 in some order.\n\t // Many values of c will never occur, since e.g. x>y>z>w makes x<z, y<w and x<w\n\t // impossible. Only the 24 indices which have non-zero entries make any sense.\n\t // We use a thresholding to set the coordinates in turn from the largest magnitude.\n\t // The number 3 in the \"simplex\" array is at the position of the largest coordinate.\n\t i1 = simplex[c][0]>=3 ? 1 : 0;\n\t j1 = simplex[c][1]>=3 ? 1 : 0;\n\t k1 = simplex[c][2]>=3 ? 1 : 0;\n\t l1 = simplex[c][3]>=3 ? 1 : 0;\n\t // The number 2 in the \"simplex\" array is at the second largest coordinate.\n\t i2 = simplex[c][0]>=2 ? 1 : 0;\n\t j2 = simplex[c][1]>=2 ? 1 : 0;\n\t k2 = simplex[c][2]>=2 ? 1 : 0;\n\t l2 = simplex[c][3]>=2 ? 1 : 0;\n\t // The number 1 in the \"simplex\" array is at the second smallest coordinate.\n\t i3 = simplex[c][0]>=1 ? 1 : 0;\n\t j3 = simplex[c][1]>=1 ? 1 : 0;\n\t k3 = simplex[c][2]>=1 ? 1 : 0;\n\t l3 = simplex[c][3]>=1 ? 1 : 0;\n\t // The fifth corner has all coordinate offsets = 1, so no need to look that up.\n\t double x1 = x0 - i1 + G4; // Offsets for second corner in (x,y,z,w) coords\n\t double y1 = y0 - j1 + G4;\n\t double z1 = z0 - k1 + G4;\n\t double w1 = w0 - l1 + G4;\n\t double x2 = x0 - i2 + 2.0*G4; // Offsets for third corner in (x,y,z,w) coords\n\t double y2 = y0 - j2 + 2.0*G4;\n\t double z2 = z0 - k2 + 2.0*G4;\n\t double w2 = w0 - l2 + 2.0*G4;\n\t double x3 = x0 - i3 + 3.0*G4; // Offsets for fourth corner in (x,y,z,w) coords\n\t double y3 = y0 - j3 + 3.0*G4;\n\t double z3 = z0 - k3 + 3.0*G4;\n\t double w3 = w0 - l3 + 3.0*G4;\n\t double x4 = x0 - 1.0 + 4.0*G4; // Offsets for last corner in (x,y,z,w) coords\n\t double y4 = y0 - 1.0 + 4.0*G4;\n\t double z4 = z0 - 1.0 + 4.0*G4;\n\t double w4 = w0 - 1.0 + 4.0*G4;\n\t // Work out the hashed gradient indices of the five simplex corners\n\t int ii = i & 255;\n\t int jj = j & 255;\n\t int kk = k & 255;\n\t int ll = l & 255;\n\t int gi0 = perm[ii+perm[jj+perm[kk+perm[ll]]]] % 32;\n\t int gi1 = perm[ii+i1+perm[jj+j1+perm[kk+k1+perm[ll+l1]]]] % 32;\n\t int gi2 = perm[ii+i2+perm[jj+j2+perm[kk+k2+perm[ll+l2]]]] % 32;\n\t int gi3 = perm[ii+i3+perm[jj+j3+perm[kk+k3+perm[ll+l3]]]] % 32;\n\t int gi4 = perm[ii+1+perm[jj+1+perm[kk+1+perm[ll+1]]]] % 32;\n\t // Calculate the contribution from the five corners\n\t double t0 = 0.6 - x0*x0 - y0*y0 - z0*z0 - w0*w0;\n\t if(t0<0) n0 = 0.0;\n\t else {\n\t t0 *= t0;\n\t n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0);\n\t }\n\t double t1 = 0.6 - x1*x1 - y1*y1 - z1*z1 - w1*w1;\n\t if(t1<0) n1 = 0.0;\n\t else {\n\t t1 *= t1;\n\t n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1);\n\t }\n\t double t2 = 0.6 - x2*x2 - y2*y2 - z2*z2 - w2*w2;\n\t if(t2<0) n2 = 0.0;\n\t else {\n\t t2 *= t2;\n\t n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2);\n\t }\n\t double t3 = 0.6 - x3*x3 - y3*y3 - z3*z3 - w3*w3;\n\t if(t3<0) n3 = 0.0;\n\t else {\n\t t3 *= t3;\n\t n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3);\n\t }\n\t double t4 = 0.6 - x4*x4 - y4*y4 - z4*z4 - w4*w4;\n\t if(t4<0) n4 = 0.0;\n\t else {\n\t t4 *= t4;\n\t n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4);\n\t }\n\t // Sum up and scale the result to cover the range [-1,1]\n\t return 27.0 * (n0 + n1 + n2 + n3 + n4);\n\t }","skip":false,"length":119,"comment":{"description":"calculates a 5D noise value based on a combination of coordinates and gradients, using a hashed gradient method for increased resolution.","params":[{"name":"x","type":"double","description":"3D coordinates of the point in the grid, which are used to calculate the contribution from each simplex corner to the total gradient value."},{"name":"y","type":"double","description":"2D position of the simplex corner in the output image, and it is used to compute the contribution from each corner to the final hashed gradient signal."},{"name":"z","type":"double","description":"3D position of the point in the grid, which is used to calculate the dot product with the gradient vectors and contribute to the overall signal strength."},{"name":"w","type":"double","description":"4th coordinate of the simplex cells, which is used to compute the dot product with the gradient vector in each corner and contribute to the final output value."}],"returns":{"type":"double","description":"a scalar value between -1 and 1 that represents the randomized gradient of a smoothed function."}}}]}}}