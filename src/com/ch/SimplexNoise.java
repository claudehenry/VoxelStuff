package com.ch;

public class SimplexNoise { // Simplex noise in 2D, 3D and 4D
	 private static int grad3[][] = {{1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
	 {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
	 {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1}};
	 private static int grad4[][]= {{0,1,1,1}, {0,1,1,-1}, {0,1,-1,1}, {0,1,-1,-1},
	 {0,-1,1,1}, {0,-1,1,-1}, {0,-1,-1,1}, {0,-1,-1,-1},
	 {1,0,1,1}, {1,0,1,-1}, {1,0,-1,1}, {1,0,-1,-1},
	 {-1,0,1,1}, {-1,0,1,-1}, {-1,0,-1,1}, {-1,0,-1,-1},
	 {1,1,0,1}, {1,1,0,-1}, {1,-1,0,1}, {1,-1,0,-1},
	 {-1,1,0,1}, {-1,1,0,-1}, {-1,-1,0,1}, {-1,-1,0,-1},
	 {1,1,1,0}, {1,1,-1,0}, {1,-1,1,0}, {1,-1,-1,0},
	 {-1,1,1,0}, {-1,1,-1,0}, {-1,-1,1,0}, {-1,-1,-1,0}};
	 private static int p[] = {151,160,137,91,90,15,
	 131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
	 190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
	 88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
	 77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
	 102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
	 135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
	 5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
	 223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
	 129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
	 251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
	 49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
	 138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180};
	 // To remove the need for index wrapping, double the permutation table length
	 private static int perm[] = new int[512];
	 static { for(int i=0; i<512; i++) perm[i]=p[i & 255]; }
	 // A lookup table to traverse the simplex around a given point in 4D.
	 // Details can be found where this table is used, in the 4D noise method.
	 private static int simplex[][] = {
	 {0,1,2,3},{0,1,3,2},{0,0,0,0},{0,2,3,1},{0,0,0,0},{0,0,0,0},{0,0,0,0},{1,2,3,0},
	 {0,2,1,3},{0,0,0,0},{0,3,1,2},{0,3,2,1},{0,0,0,0},{0,0,0,0},{0,0,0,0},{1,3,2,0},
	 {0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},
	 {1,2,0,3},{0,0,0,0},{1,3,0,2},{0,0,0,0},{0,0,0,0},{0,0,0,0},{2,3,0,1},{2,3,1,0},
	 {1,0,2,3},{1,0,3,2},{0,0,0,0},{0,0,0,0},{0,0,0,0},{2,0,3,1},{0,0,0,0},{2,1,3,0},
	 {0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},
	 {2,0,1,3},{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,0,1,2},{3,0,2,1},{0,0,0,0},{3,1,2,0},
	 {2,1,0,3},{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,1,0,2},{0,0,0,0},{3,2,0,1},{3,2,1,0}};
	 // This method is a *lot* faster than using (int)Math.floor(x)
  /**
   * This function returns the nearest integer to a double value x rounded down if x
   * is positive and rounded up if x is negative.
   * 
   * @param x The `x` input parameter is passed by value and is only used to determine
   * the sign of `x` before casting it to an integer using `((int)x)` or `(int)(x-1)`.
   * It does not have any other effect on the function's behavior.
   * 
   * @returns The function `fastfloor` takes a `double` parameter `x` and returns an
   * `int`. If `x` is positive (i.e., greater than zero), the function simply casts `x`
   * to an integer using `((int)x)`. If `x` is non-positive (i.e., equal to or less
   * than zero), the function subtracts 1 from `x` before casting it to an integer.
   * 
   * In other words:
   * 
   * 	- If x is positive: fastfloor(x) = x
   * 	- If x is non-positive: fastfloor(x) = x - 1
   * 
   * Therefore the output returned by this function is either x (if x is positive) or
   * x-1 (if x is non-positive).
   */
	 private static int fastfloor(double x) {
	 return x>0 ? (int)x : (int)x-1;
	 }
  /**
   * The function `dot(int[], double x)`, given an array of integers `g` and two doubles
   * `x` and `y`, returns the dot product of the two vectors `g` and `[x]`, i.e., it
   * returns `g[0]*x + g[1]*y`.
   * 
   * @param g The `g` input parameter is an array of integers that represents the
   * gradient of the function with respect to the inputs `x` and `y`.
   * 
   * @param x The `x` input parameter multiplies each element of the `g` array by the
   * corresponding coefficient of the linear combination.
   * 
   * @param y The `y` input parameter is not used at all inside the function. It is an
   * unused variable and therefore has no effect on the function's behavior or output.
   * 
   * @returns The function `dot` takes an integer array `g`, a double `x`, and a double
   * `y`, and returns their dot product (the sum of the products of the corresponding
   * elements).
   * 
   * Therefore the output of the function is a double value representing the dot product
   * of `g`, `x`, and `y`.
   */
	 private static double dot(int g[], double x, double y) {
	 return g[0]*x + g[1]*y; }
  /**
   * This function calculates the dot product of three vectors represented as arrays
   * of double values (x), (y), and (z), using the coefficients storedin an array g[],
   * and returns the result as a double value.
   * 
   * @param g The `g` input parameter is an array of three integers (`int[] g = new
   * int[3]`), and it represents the coefficients of the dot product. The function
   * calculates the dot product of the vectors represented by `x`, `y`, and `z` with
   * the vectors represented by the elements of the `g` array.
   * 
   * @param x The `x` input parameter is ignored. The function doesn't use it at all.
   * 
   * @param y The `y` input parameter is not used anywhere inside the function `dot()`.
   * Therefore it is optional and can be removed.
   * 
   * @param z The `z` parameter is not used anywhere inside the function `dot(int g[].,
   * double x`, so it has no effect and can be safely removed from the function definition.
   * 
   * @returns The output of this function is a double value calculated as the dot product
   * of the values of the elements of the `g` array and the input `x`, `y`, and `z` values.
   * 
   * Concisely: The function takes an integer array `g`, doubles `x`, `y`, and `z`, and
   * returns the dot product of the corresponding elements of `g` and the input values.
   */
	 private static double dot(int g[], double x, double y, double z) {
	 return g[0]*x + g[1]*y + g[2]*z; }
  /**
   * This function calculates the dot product of a set of four doubles (x y z w) with
   * a set of four integers (g0 g1 g2 g3).
   * 
   * @param g The `g` input parameter is an array of four doubles that represents a
   * vector. The function takes these values and uses them to compute the dot product
   * with the input parameters `x`, `y`, `z`, and `w`.
   * 
   * @param x In the function `dot(int g[], double x)`, the `x` parameter is ignored.
   * The function takes four double values as parameters `g`, but the first parameter
   * `x` is of type `int` and is not used anywhere within the function. Therefore the
   * input `x` does not have any effect on the behavior of the function.
   * 
   * @param y The `y` input parameter is not used at all inside the `dot` function. It
   * is not referred to anywhere within the code of the function.
   * 
   * @param z The `z` input parameter is not used at all within the `dot()` function.
   * Therefore it is superfluous and can be removed.
   * 
   * @param w The `w` input parameter is not used anywhere within the function and is
   * therefore redundant or unnecessary. It can be removed without affecting the
   * functionality of the code.
   * 
   * @returns The function `dot` takes six input parameters: an integer array `g`, and
   * five double parameters `x`, `y`, `z`, `w`. The function returns a double value
   * computed as the dot product of the elements of `g` with `x`, `y`, `z`, and `w`.
   * In other words:
   * 
   * The output returned by this function is a dot product of two vectors represented
   * as arrays of integers.
   */
	 private static double dot(int g[], double x, double y, double z, double w) {
	 return g[0]*x + g[1]*y + g[2]*z + g[3]*w; }
	 // 2D simplex noise
  /**
   * This function implements a 2D simulated annealing noise algorithm. It takes two
   * inputs x and y and computes a noise value based on the position of each point
   * within a predefined simplex. The output is scaled to return values between -1 and
   * 1.
   * 
   * @param xin The `xin` input parameter is a double value that is used as one of the
   * two inputs to determine which simplex cell the current point belongs to. It is
   * skewed and then unskewed along with the other input `yin` to determine the corner
   * of the simplex that the point belongs to.
   * 
   * @param yin The `yin` input parameter is ignored. The function only uses `xin` to
   * determine the simplex cell that the point belongs to.
   * 
   * @returns The output returned by the `noise()` function is a double value between
   * -1 and 1. It is generated using a simulation of a random walk on a simplex lattice
   * to create a Perlin noise pattern. The function takes two double parameters
   * representing the x and y coordinates of a point on the grid and returns a smoothed
   * version of the noise pattern at that point.
   */
	 public static double noise(double xin, double yin) {
	 double n0, n1, n2; // Noise contributions from the three corners
	 // Skew the input space to determine which simplex cell we're in
	 final double F2 = 0.5*(Math.sqrt(3.0)-1.0);
	 double s = (xin+yin)*F2; // Hairy factor for 2D
	 int i = fastfloor(xin+s);
	 int j = fastfloor(yin+s);
	 final double G2 = (3.0-Math.sqrt(3.0))/6.0;
	 double t = (i+j)*G2;
	 double X0 = i-t; // Unskew the cell origin back to (x,y) space
	 double Y0 = j-t;
	 double x0 = xin-X0; // The x,y distances from the cell origin
	 double y0 = yin-Y0;
	 // For the 2D case, the simplex shape is an equilateral triangle.
	 // Determine which simplex we are in.
	 int i1, j1; // Offsets for second (middle) corner of simplex in (i,j) coords
	 if(x0>y0) {i1=1; j1=0;} // lower triangle, XY order: (0,0)->(1,0)->(1,1)
	 else {i1=0; j1=1;} // upper triangle, YX order: (0,0)->(0,1)->(1,1)
	 // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
	 // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
	 // c = (3-sqrt(3))/6
	 double x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) unskewed coords
	 double y1 = y0 - j1 + G2;
	 double x2 = x0 - 1.0 + 2.0 * G2; // Offsets for last corner in (x,y) unskewed coords
	 double y2 = y0 - 1.0 + 2.0 * G2;
	 // Work out the hashed gradient indices of the three simplex corners
	 int ii = i & 255;
	 int jj = j & 255;
	 int gi0 = perm[ii+perm[jj]] % 12;
	 int gi1 = perm[ii+i1+perm[jj+j1]] % 12;
	 int gi2 = perm[ii+1+perm[jj+1]] % 12;
	 // Calculate the contribution from the three corners
	 double t0 = 0.5 - x0*x0-y0*y0;
	 if(t0<0) n0 = 0.0;
	 else {
	 t0 *= t0;
	 n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient
	 }
	 double t1 = 0.5 - x1*x1-y1*y1;
	 if(t1<0) n1 = 0.0;
	 else {
	 t1 *= t1;
	 n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
	 }
	 double t2 = 0.5 - x2*x2-y2*y2;
	 if(t2<0) n2 = 0.0;
	 else {
	 t2 *= t2;
	 n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
	 }
	 // Add contributions from each corner to get the final noise value.
	 // The result is scaled to return values in the interval [-1,1].
	 return 70.0 * (n0 + n1 + n2);
	 }
	 // 3D simplex noise
  /**
   * This function implements a 3D Simplex noise algorithm for generating random values
   * within a given space. It takes three input coordinates (xin. yin. zin) and calculates
   * the noise value based on the location of the point within one of four simplified
   * tetrahedral zones defined by the input coordinates and four corner points.
   * 
   * @param xin The `xin` input parameter is one of the three doubles that are passed
   * to the `noise()` function and represents the input value for the x-coordinate of
   * the point being generated using Perlin noise.
   * 
   * @param yin The `yin` input parameter is not used anywhere within the noise()
   * function. Therefore it has no effect on its output. The unused inputs do have a
   * default value however that gets triggered with debug builds if you set either yin
   * and cin accordingly or neither one and just leave only cin without any assignement
   * or initilization to default values such as zeroes like below (c/++ mainly targets
   * academu programs not games since gamedev almost only c targets etc..
   * void main() {cin.sync_with_stdio(false);  cout.tie(0,"/>");  int cinBuffy =0;cin
   * >> cinBuffy ;  noise(3.5678941840332E-5 ,4 ,4) return 0;
   * }
   * 
   * @param zin The `zin` input parameter is not used or referenced anywhere inside the
   * noise function code. It's effectively an unused and optional parameter that could
   * be removed without changing the functionality of the noise function.
   * 
   * @returns This function computes noise from four corners of a Simplex noise
   * computation. The output returned by the function is a double value representing
   * the final noise value between -1 and 1 after being scaled by 32. The noise values
   * are computed from contributions made by each of the four corners using dot products
   * and values like t0 and t1 that determine the degree to which each corner contributes
   * to the overall output based on their distance from the origin and inside/outside
   * the simplex boundaries.
   */
	 public static double noise(double xin, double yin, double zin) {
	 double n0, n1, n2, n3; // Noise contributions from the four corners
	 // Skew the input space to determine which simplex cell we're in
	 final double F3 = 1.0/3.0;
	 double s = (xin+yin+zin)*F3; // Very nice and simple skew factor for 3D
	 int i = fastfloor(xin+s);
	 int j = fastfloor(yin+s);
	 int k = fastfloor(zin+s);
	 final double G3 = 1.0/6.0; // Very nice and simple unskew factor, too
	 double t = (i+j+k)*G3;
	 double X0 = i-t; // Unskew the cell origin back to (x,y,z) space
	 double Y0 = j-t;
	 double Z0 = k-t;
	 double x0 = xin-X0; // The x,y,z distances from the cell origin
	 double y0 = yin-Y0;
	 double z0 = zin-Z0;
	 // For the 3D case, the simplex shape is a slightly irregular tetrahedron.
	 // Determine which simplex we are in.
	 int i1, j1, k1; // Offsets for second corner of simplex in (i,j,k) coords
	 int i2, j2, k2; // Offsets for third corner of simplex in (i,j,k) coords
	 if(x0>=y0) {
	 if(y0>=z0)
	 { i1=1; j1=0; k1=0; i2=1; j2=1; k2=0; } // X Y Z order
	 else if(x0>=z0) { i1=1; j1=0; k1=0; i2=1; j2=0; k2=1; } // X Z Y order
	 else { i1=0; j1=0; k1=1; i2=1; j2=0; k2=1; } // Z X Y order
	 }
	 else { // x0<y0
	 if(y0<z0) { i1=0; j1=0; k1=1; i2=0; j2=1; k2=1; } // Z Y X order
	 else if(x0<z0) { i1=0; j1=1; k1=0; i2=0; j2=1; k2=1; } // Y Z X order
	 else { i1=0; j1=1; k1=0; i2=1; j2=1; k2=0; } // Y X Z order
	 }
	 // A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
	 // a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z), and
	 // a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z), where
	 // c = 1/6.
	 double x1 = x0 - i1 + G3; // Offsets for second corner in (x,y,z) coords
	 double y1 = y0 - j1 + G3;
	 double z1 = z0 - k1 + G3;
	 double x2 = x0 - i2 + 2.0*G3; // Offsets for third corner in (x,y,z) coords
	 double y2 = y0 - j2 + 2.0*G3;
	 double z2 = z0 - k2 + 2.0*G3;
	 double x3 = x0 - 1.0 + 3.0*G3; // Offsets for last corner in (x,y,z) coords
	 double y3 = y0 - 1.0 + 3.0*G3;
	 double z3 = z0 - 1.0 + 3.0*G3;
	 // Work out the hashed gradient indices of the four simplex corners
	 int ii = i & 255;
	 int jj = j & 255;
	 int kk = k & 255;
	 int gi0 = perm[ii+perm[jj+perm[kk]]] % 12;
	 int gi1 = perm[ii+i1+perm[jj+j1+perm[kk+k1]]] % 12;
	 int gi2 = perm[ii+i2+perm[jj+j2+perm[kk+k2]]] % 12;
	 int gi3 = perm[ii+1+perm[jj+1+perm[kk+1]]] % 12;
	 // Calculate the contribution from the four corners
	 double t0 = 0.6 - x0*x0 - y0*y0 - z0*z0;
	 if(t0<0) n0 = 0.0;
	 else {
	 t0 *= t0;
	 n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
	 }
	 double t1 = 0.6 - x1*x1 - y1*y1 - z1*z1;
	 if(t1<0) n1 = 0.0;
	 else {
	 t1 *= t1;
	 n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
	 }
	 double t2 = 0.6 - x2*x2 - y2*y2 - z2*z2;
	 if(t2<0) n2 = 0.0;
	 else {
	 t2 *= t2;
	 n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
	 }
	 double t3 = 0.6 - x3*x3 - y3*y3 - z3*z3;
	 if(t3<0) n3 = 0.0;
	 else {
	 t3 *= t3;
	 n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
	 }
	 // Add contributions from each corner to get the final noise value.
	 // The result is scaled to stay just inside [-1,1]
	 return 32.0*(n0 + n1 + n2 + n3);
	 }
	 // 4D simplex noise
  /**
   * The provided code is a G4 mesh simulation routine that calculates the dot product
   * of gradient vectors and position values for five vertices (corners) of a 3D hexagonal
   * mesh element. It uses a hashed gradient index technique to distribute the computation
   * among the 16 vertices of the hexahedron and return a single value covering the
   * range [-1;1].
   * 
   * @param x The `x` parameter is an index into the vertices of a quadrilateral mesh.
   * It is used to select one of the four corners of the mesh as the current corner to
   * work with.
   * 
   * @param y The `y` parameter is the fourth input to the Simplex noise function and
   * serves as a generic "parameter" that can be used to scale or modify the output of
   * the function. In other words: it's a sort of bias for the Simplex function that
   * lets you alter the shape and distribution of the noise.
   * 
   * @param z The `z` parameter is unused and can be ignored. The function ignores it
   * and only uses the other three coordinates (`x`, `y`, and `w`).
   * 
   * @param w The `w` input parameter is a scalator used to scale the result of the
   * hashed gradient norm calculation from the range `[0.6,-0.6]` to the range `[-1.0001
   * 1.0001]`.
   * 
   * @returns The output returned by the `hashed_gradient` function is a scalar value
   * that represents the gradient of a quadrilateral object at a given point. Specifically:
   * 
   * 	- The output value ranges from -1 to 1.
   * 	- It is computed as the sum of the contributions from the five corners of the
   * quadrilateral object (which are determined by hashing the gradient indices), each
   * weighted by a power of the distance between the current point and the corresponding
   * corner.
   * 
   * The function takes as input:
   * 
   * 	- `x0`, `y0`, `z0`, `w0` : coordinates of the current point.
   * 	- `G4` : length of one edge of the gradient grid.
   * 	- `simplex[c][i]`: coefficients of the Bernstein basis for the polynomial of
   * degree 2 that describes the piecewise-defined function.
   * 	- `perm`: a list of permutations used to construct the hashed gradient indices.
   */
	 double noise(double x, double y, double z, double w) {

	 // The skewing and unskewing factors are hairy again for the 4D case
	 final double F4 = (Math.sqrt(5.0)-1.0)/4.0;
	 final double G4 = (5.0-Math.sqrt(5.0))/20.0;
	 double n0, n1, n2, n3, n4; // Noise contributions from the five corners
	 // Skew the (x,y,z,w) space to determine which cell of 24 simplices we're in
	 double s = (x + y + z + w) * F4; // Factor for 4D skewing
	 int i = fastfloor(x + s);
	 int j = fastfloor(y + s);
	 int k = fastfloor(z + s);
	 int l = fastfloor(w + s);
	 double t = (i + j + k + l) * G4; // Factor for 4D unskewing
	 double X0 = i - t; // Unskew the cell origin back to (x,y,z,w) space
	 double Y0 = j - t;
	 double Z0 = k - t;
	 double W0 = l - t;
	 double x0 = x - X0; // The x,y,z,w distances from the cell origin
	 double y0 = y - Y0;
	 double z0 = z - Z0;
	 double w0 = w - W0;
	 // For the 4D case, the simplex is a 4D shape I won't even try to describe.
	 // To find out which of the 24 possible simplices we're in, we need to
	 // determine the magnitude ordering of x0, y0, z0 and w0.
	 // The method below is a good way of finding the ordering of x,y,z,w and
	 // then find the correct traversal order for the simplex were in.
	 // First, six pair-wise comparisons are performed between each possible pair
	 // of the four coordinates, and the results are used to add up binary bits
	 // for an integer index.
	 int c1 = (x0 > y0) ? 32 : 0;
	 int c2 = (x0 > z0) ? 16 : 0;
	 int c3 = (y0 > z0) ? 8 : 0;
	 int c4 = (x0 > w0) ? 4 : 0;
	 int c5 = (y0 > w0) ? 2 : 0;
	 int c6 = (z0 > w0) ? 1 : 0;
	 int c = c1 + c2 + c3 + c4 + c5 + c6;
	 int i1, j1, k1, l1; // The integer offsets for the second simplex corner
	 int i2, j2, k2, l2; // The integer offsets for the third simplex corner
	 int i3, j3, k3, l3; // The integer offsets for the fourth simplex corner
	 // simplex[c] is a 4-vector with the numbers 0, 1, 2 and 3 in some order.
	 // Many values of c will never occur, since e.g. x>y>z>w makes x<z, y<w and x<w
	 // impossible. Only the 24 indices which have non-zero entries make any sense.
	 // We use a thresholding to set the coordinates in turn from the largest magnitude.
	 // The number 3 in the "simplex" array is at the position of the largest coordinate.
	 i1 = simplex[c][0]>=3 ? 1 : 0;
	 j1 = simplex[c][1]>=3 ? 1 : 0;
	 k1 = simplex[c][2]>=3 ? 1 : 0;
	 l1 = simplex[c][3]>=3 ? 1 : 0;
	 // The number 2 in the "simplex" array is at the second largest coordinate.
	 i2 = simplex[c][0]>=2 ? 1 : 0;
	 j2 = simplex[c][1]>=2 ? 1 : 0;
	 k2 = simplex[c][2]>=2 ? 1 : 0;
	 l2 = simplex[c][3]>=2 ? 1 : 0;
	 // The number 1 in the "simplex" array is at the second smallest coordinate.
	 i3 = simplex[c][0]>=1 ? 1 : 0;
	 j3 = simplex[c][1]>=1 ? 1 : 0;
	 k3 = simplex[c][2]>=1 ? 1 : 0;
	 l3 = simplex[c][3]>=1 ? 1 : 0;
	 // The fifth corner has all coordinate offsets = 1, so no need to look that up.
	 double x1 = x0 - i1 + G4; // Offsets for second corner in (x,y,z,w) coords
	 double y1 = y0 - j1 + G4;
	 double z1 = z0 - k1 + G4;
	 double w1 = w0 - l1 + G4;
	 double x2 = x0 - i2 + 2.0*G4; // Offsets for third corner in (x,y,z,w) coords
	 double y2 = y0 - j2 + 2.0*G4;
	 double z2 = z0 - k2 + 2.0*G4;
	 double w2 = w0 - l2 + 2.0*G4;
	 double x3 = x0 - i3 + 3.0*G4; // Offsets for fourth corner in (x,y,z,w) coords
	 double y3 = y0 - j3 + 3.0*G4;
	 double z3 = z0 - k3 + 3.0*G4;
	 double w3 = w0 - l3 + 3.0*G4;
	 double x4 = x0 - 1.0 + 4.0*G4; // Offsets for last corner in (x,y,z,w) coords
	 double y4 = y0 - 1.0 + 4.0*G4;
	 double z4 = z0 - 1.0 + 4.0*G4;
	 double w4 = w0 - 1.0 + 4.0*G4;
	 // Work out the hashed gradient indices of the five simplex corners
	 int ii = i & 255;
	 int jj = j & 255;
	 int kk = k & 255;
	 int ll = l & 255;
	 int gi0 = perm[ii+perm[jj+perm[kk+perm[ll]]]] % 32;
	 int gi1 = perm[ii+i1+perm[jj+j1+perm[kk+k1+perm[ll+l1]]]] % 32;
	 int gi2 = perm[ii+i2+perm[jj+j2+perm[kk+k2+perm[ll+l2]]]] % 32;
	 int gi3 = perm[ii+i3+perm[jj+j3+perm[kk+k3+perm[ll+l3]]]] % 32;
	 int gi4 = perm[ii+1+perm[jj+1+perm[kk+1+perm[ll+1]]]] % 32;
	 // Calculate the contribution from the five corners
	 double t0 = 0.6 - x0*x0 - y0*y0 - z0*z0 - w0*w0;
	 if(t0<0) n0 = 0.0;
	 else {
	 t0 *= t0;
	 n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0);
	 }
	 double t1 = 0.6 - x1*x1 - y1*y1 - z1*z1 - w1*w1;
	 if(t1<0) n1 = 0.0;
	 else {
	 t1 *= t1;
	 n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1);
	 }
	 double t2 = 0.6 - x2*x2 - y2*y2 - z2*z2 - w2*w2;
	 if(t2<0) n2 = 0.0;
	 else {
	 t2 *= t2;
	 n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2);
	 }
	 double t3 = 0.6 - x3*x3 - y3*y3 - z3*z3 - w3*w3;
	 if(t3<0) n3 = 0.0;
	 else {
	 t3 *= t3;
	 n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3);
	 }
	 double t4 = 0.6 - x4*x4 - y4*y4 - z4*z4 - w4*w4;
	 if(t4<0) n4 = 0.0;
	 else {
	 t4 *= t4;
	 n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4);
	 }
	 // Sum up and scale the result to cover the range [-1,1]
	 return 27.0 * (n0 + n1 + n2 + n3 + n4);
	 }
	}