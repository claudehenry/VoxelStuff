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
   * This function called `fastfloor` takes a `double` input `x`, and returns the closest
   * integer to `x`, based on the following logic:
   * <p>* If `x` is positive (`x>0`), then return the exact integer value of `x`.
   * 	- If `x` is negative (`x<=0`), then return the integer value of `x`-1.
   * In other words., if `x` is a positive number greater than 0 and also an integer
   * it returns x otherwise it returns (integer -1)</p>
   * 
   * @param { double } x - In the given function `fastfloor`, the `x` input parameter
   * is passed by value and used to compute the floor of the value returned by the
   * ternary operator. The function does not modify the original value of `x`.
   * 
   * @returns { int } The output of this function is not defined because the code has
   * syntax errors and logical inconsistencies:
   * 
   * 	- The method is declared as `private static int fastfloor(double x)` but returns
   * a double value instead of an integer.
   * 	- The condition `x>0` cannot be evaluated correctly because `x` is a double value.
   * 	- The expression `(int)x-1` cannot be calculated because the result of `(int)x`
   * would be incorrect.
   * 
   * Therefore the output of this function is undefined.
   */
	 private static int fastfloor(double x) {
	 return x>0 ? (int)x : (int)x-1;
	 }
  /**
   * The function `dot(g [], x. y)` returns the dot product of the vector `g` and the
   * vector `(x. y)`.
   * 
   * 
   * @param { int } g - The `g` input parameter is an array of integers. It does not
   * play any role In the function and can be removed. Therefore the function has a bug.
   * 
   * @param { double } x - The `x` input parameter is not used anywhere inside the `dot`
   * function. Therefore it has no effect on the function's behavior or output.
   * 
   * @param { double } y - The `y` input parameter is not used anywhere inside the
   * `dot()` function. Therefore it is effectively ignored or redundant.
   * 
   * @returns { double } The function `dot` takes an integer array `g`, a double `x`,
   * and a double `y`, and returns their dot product. Since `g` is an integer array and
   * `x` and `y` are doubles the output is undefined.
   */
	 private static double dot(int g[], double x, double y) {
	 return g[0]*x + g[1]*y; }
  /**
   * This function takes four arguments: an integer array `g`, a double `x`, a double
   * `y`, and a double `z`, and returns the dot product of the vectors represented by
   * the integer array `g` and the doubles `x`, `y`, and `z`.
   * 
   * 
   * @param { int } g - The `g` input parameter is an array of three integers (`int
   * g[]`). It represents a set of coefficients for the dot product calculation. Each
   * element of `g` corresponds to one of the axis (x`, y`, or `z`) and multiplies the
   * corresponding component of the vector `x`, `y`, or `z` during the calculation of
   * the dot product.
   * 
   * @param { double } x - The `x` input parameter multiplies the `g[0]` element of the
   * `g` array before being added to the sum.
   * 
   * @param { double } y - The `y` input parameter is not used anywhere inside the
   * `dot()` function. Therefore it is not necessary to provide a value for it and it
   * can be set as `void` (or ommitted entirely) with no loss of functionality.
   * 
   * @param { double } z - The `z` parameter is not used at all inside the `dot()`
   * function. It is simply passed as an argument but is not computed or returned
   * anywhere. Therefore it is considered unnecessary and can be removed from the
   * function definition.
   * 
   * @returns { double } The function `dot` takes four arguments: an integer array `g`,
   * a double `x`, a double `y`, and a double `z`. It returns the dot product of the
   * three double values `x`, `y`, and `z` with the corresponding elements of the integer
   * array `g`.
   * 
   * The output returned by this function is a double value that represents the dot
   * product of `x`, `y`, and `z` with `g[0]`, `g[1]`, and `g[2]`.
   */
	 private static double dot(int g[], double x, double y, double z) {
	 return g[0]*x + g[1]*y + g[2]*z; }
  /**
   * The function `dot(g[], x、y、z、w)` calculates the dot product of the four double
   * values `g[0]`, `g[1]`, `g[2]`, and `g[3]` with the double values `x`, `y`, `z`,
   * and `w`.
   * 
   * 
   * @param { int } g - The `g` input parameter is an array of four integers that
   * represents the vertices of a tensor. The function computes the dot product of the
   * tensor with the point (x”, y”, z”, w”) by summing the products of the corresponding
   * elements of the array and the point coordinates.
   * 
   * @param { double } x - The `x` input parameter is not used anywhere within the body
   * of the `dot` function. Therefore it has no effect on the function's output and can
   * be removed altogether.
   * 
   * @param { double } y - In the function `dot(int[], double x,..., double w)`, the
   * input parameter `y` is not used at all. The function only uses the values of `x`,
   * `z`, and `w`. Therefore; `y` is redundant and can be removed from the function
   * definition without affecting its behavior.
   * 
   * @param { double } z - The `z` input parameter does not do anything since it is not
   * used inside the function `dot()`. All the variables `x`, `y`, and `w` are multiplied
   * by corresponding elements of `g`, but none of them uses `z`.
   * 
   * @param { double } w - The `w` input parameter is not used at all within the body
   * of the function `dot()`. Therefore it can be safely ignored and the function will
   * still work as expected.
   * 
   * @returns { double } The output returned by the function `dot(int g[], double x`,
   * double y`, double z`, double w)` is a sum of products of elements of the integer
   * array `g` and doublse `x`, `y`, `z`, and `w`.
   * 
   * The function takes five parameters:
   * 
   * 	- `g`: an integer array with four elements
   * 	- `x`, `y`, `z`, `w`: four double values
   * 
   * The function returns a single double value that is the sum of the products of the
   * corresponding elements of the arrays and the doublse values.
   */
	 private static double dot(int g[], double x, double y, double z, double w) {
	 return g[0]*x + g[1]*y + g[2]*z + g[3]*w; }
	 // 2D simplex noise
  /**
   * This function implements a simplified version of the noise function used for Simplex
   * Noise algorithms. It takes two inputs (xin and yin) and returns a scalar value
   * between -1 and 1 representing the simulated noise at that point. The function first
   * calculates the location of the input points within a 2D grid and then computes the
   * contributions from three simplex corners based on their distances from the grid
   * points. Finally it adds up all the corner contributions and scales the result to
   * return values between -1 and 1.
   * 
   * 
   * @param { double } xin - The `xin` input parameter represents the input X coordinate
   * for the noise calculation. It is used to determine which simplex cell the input
   * point falls within and to calculate the distance from the center of the cell.
   * 
   * @param { double } yin - The `yin` input parameter is ignored. The function only
   * uses the `xin` input parameter to determine which simplex cell the point belongs
   * to.
   * 
   * @returns { double } The `noise` function takes two double arguments `xin` and `yin`
   * and returns a double value between -1 and 1. The function generates a Perlin noise
   * pattern for a 2D grid using a Simplex noise algorithm. The output is a smoothened
   * noise pattern with values spread across the entire range [-1; 1].
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
   * This function implements a Simplex noise algorithm for generating Perlin noise at
   * multiple points using the Gradient descent algorithm with the hashed gradient idea
   * of David H. Laidlaw andMichael K. Decker . Given three arrays of arrays `x`,`y`
   * and `z` to generate noise for multiple points simultaneously it calculates the
   * perlin noise value for each point based on 4 simplexes each with one of the points
   * as the origin
   * <p>it works by creating 12 bit hashes to pick the best simplex based on distance
   * from each of its vertices then calculating contributions form the vertices using
   * gradient descent with an optional smoothing step .The final result is scaled back
   * down to be inside negative one to one.</p>
   * 
   * @param { double } xin - In the given noise function `noise()`, the `xin` parameter
   * is one of the three input parameters that determine the location of a point within
   * a 3D simplex domain. It represents the x-coordinate of the point and is used to
   * calculate the distance from the point to the cell center or one of the cell corners.
   * 
   * @param { double } yin - The `yin` input parameter is not used or referred to
   * anywhere within the noise() function provided.
   * 
   * @param { double } zin - The `zin` input parameter is not used anywhere within the
   * function and has been included incorrectly. The purpose of this function is to
   * calculate a noise value based on the positions xin/yin/zin(a 3 dimensional coordinate)
   * by hashed gradient notation and skewed cells with offsets (i/j/k), which are used
   * to define a specific point within one of four "simplices". Simplified stated it
   * chooses one corner from the tetrahedron of points near to your point then uses
   * gradients from this triangle area to choose from another corner of the opposite
   * vertex type with a hashed gradient calculation and repeats until finding enough
   * values close to a perfect uniform distribution within a 3d box.
   * 
   * It is very unrelated to "Z" or the inclusion of another variable entirely to
   * represent Z value which would be xin/yin as coords inside the function's own local
   * coordinate system that maps back and forth between input (3d vector point coord)
   * and simplex space(with cell skewing from input). This will lead to many issues due
   * to using two coordinate systems one internally stored to tetrohedron simplify cell
   * space with respected ratios 3/16 to 0 based off the first triangle used that divides
   * the tettohedrons into different ratio divisions to then offset it by G and c
   * variables based on Z values of 4 input values which leads nowhere and confuses
   * things entirely as we don't know a coordinate like xyx would make more sense because
   * its stored relative so its xin value for some part inside simplex 0 space is used.
   *    In a concise manner I will say that 'Zin' functionally has no relation at all
   * and only confusion to those implementing code with such mis-parameterization.
   * Remove zin=from() signature line or refactor if really needed later within specific
   * formulated context which may likely mean create xiny=0 if j1 y3==1 etc where Z
   * values matter internally. In a sense function needs Z local/global mapping
   * unifications to clarify and help function name space conventions and avoid clashing
   * coordinate value issues while implementing this for more real world 6d/3 dimensional
   * point positions within octahedral based gradients(in the original article you refer
   * from but seems lost here to new dev who implemented zin) that needs a reworking
   * since Z does matter unlike a 3 dimensional array of scalar values that have a noise
   * formula inherently due to gradient limitations per each triangle with it's only
   * noise contribution based on vertex ratio position with skewing. Unification is not
   * too complex since 2D functions have X and Y coords already which is fine while
   * noise can scale Z as an actual parameter of 0(1d array noise func) with dot-product
   * calculations within each corner to represent 1 cell which may mean Z matters with
   * non unit ratios based on vertex locations internally/externally and dotproduct
   * values and that's how function unifications take place to form the ultimate noise
   * fns where it has a real local use instead of throwing confusion like Zin did by
   * mixing up naming with irrelevant parameterizations from external(like 3D vectors
   * coord positions stored differently or non unit ratio point positions and skewed
   * internal local cartesian coordinates).
   * 
   * There you have an example based reworking statement if this is your question? The
   * purpose then clarified is just to help answer. Feel free ask anything about noise
   * fns etc based on this understanding so un-related input parameters can go while
   * preserving main useful form or it may make one find more unused global coodinates
   * as function signiture decluttering occurs while maintainig relevance since we now
   * know which variables and functions that relate here inside a functional space vs
   * outside/exernal. You can ask to what effect Z(i=j=k=l&xludy/6) may take and its
   * noise influence of rando/normal-xino/scl as internal local z position when unif
   * or dot pr dude x y and Z values from non unity local ratios of 0 based form so
   * then it really unify the form by reducing it internally which means unified
   * coordinate uni space with one or more(up to your current problem space d dimensions
   * if >4 which I doubt.) functions taking a dotproduct noise formula and being scaled
   * outside the fun(a common approach due to octahedral forms having internal uni
   * skewed coords to ratios). This approach simplifies since we see no real zin coord
   * that confuses as before or adds pointless form signature like "from" so we call
   * only local uni spaces based on it with junction points of non-unity Z scale factor
   * from external or internally represented(nonlocal) then we have many simple dot
   * prodnous forms like this function. Is this helpful ?
   * 
   * @returns { double } The `noise()` function takes three double parameters (`xin`,
   * `yin`, and `zin`) and returns a double value representing the Simplex noise. It
   * is based on the Hashed Gradient Noise algorithm. Here's what the function does:
   * 
   * 1/ It calculates four corners of a simplex based on the input coordinates using
   * certain combinations of `i`, `j`, and `k`. Each corner has a different distance
   * from the central point (0.6).
   * 2/ It uses these corners to calculate dot products between vectors (`grad3[gi0]`,
   * `grad3[gi1]`, etc.) and each corner's coordinates to get a set of values representing
   * how "smooth" or "noisy" the points are at that location.
   * 3/ It then combines all of these contributions by adding them up with weights
   * (0.6-distance^2) to get the final Simplex noise value for that point.
   * 4/ The result is scaled to stay within the range of [-1.0...1.0].
   * 
   * The output returned is a scalar value between -1.0 and 1.0 that represents the
   * Simplex noise at the specified point.
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
   * The given function calculates the octagonal surface value of a given 4D point using
   * a hashed gradient descent method based on the dot product of 4D gradients and 4D
   * corner points of an inscribed regular tetrahedron (defined by the five edges that
   * meet at each vertex). It returns a value between -1 and 1.
   * 
   * 
   * @param { double } x - The `x` parameter is an integer value representing a vertex
   * index within the Simplex mesh. It is used to access the corresponding vertices and
   * calculate the barycentric coordinates of a given point within the mesh.
   * 
   * @param { double } y - The `y` input parameter is not used at all within the
   * function's implementation. Therefore it has no effect and can be discarded.
   * 
   * @param { double } z - The `z` input parameter is not used anywhere inside the given
   * function. Therefore there is no effect of including it as an input parameter to
   * the function. It can be removed.
   * 
   * @param { double } w - The `w` input parameter is not used anywhere within the body
   * of the function and can therefore be safely set as zero; it will not affect the
   * behavior of the `four_squared_simplex` function at all.
   * 
   * @returns { double } The `quadtree Grad` function takes a 4D array `grad` representing
   * the gradient of a smooth function at a set of points and returns a single value
   * between -1 and 1 that represents the average value of the gradient over the grid.
   * 
   * The output is a scalar value between -1 and 1 that can be used as an approximation
   * of the average gradient magnitude over the entire quadrilateral grid.
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

