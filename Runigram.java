// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;
import java.util.Arrays;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		Color[][] iron = read("thor.ppm");
		Color[][] cake = read("cake.ppm");
		print(tinypic);
		// iron = scaled(iron, cake[0].length, cake.length);
		// blend(iron, cake, 0.25);
		// morph(iron, cake, 3);

		// Color c1 = new Color(100, 40, 100);
		// Color c2 = new Color(200, 20, 40);

		// System.out.println(blend(c1, c2, 0.25));

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		imageOut = blend(iron,cake,0.25);
		setCanvas(iron);
		display(iron);
		
		System.out.println();
		// print(imageOut);
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for(int i=0;i<numRows;i++){
			for(int j=0;j<numCols;j++){
				int R = in.readInt();
				int G = in.readInt();
				int B = in.readInt();
				Color c = new Color(R,G,B);
				image[i][j] = c;
			}
		}
		// Reads the RGB values from the file, into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {

		for(int i=0;i<image.length;i++){
			for(int j=0;j<image[0].length;j++){
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {

		int numCols = image[0].length;
		for(int i=0;i<image.length;i++){
			for(int j=0;j<numCols/2;j++){
				Color temp = image[i][j];
				image[i][j]=image[i][numCols-1-j];
				image[i][numCols-1-j]=temp;
			}
		}
		return image;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){

		int numRows = image.length;
		for(int i=0;i<numRows/2;i++){
			Color [] temp = image[i];
			image[i] = image[numRows-1-i];
			image[numRows-1-i] = temp;
		}
		return image;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		//// Replace the following statement with your code
		int R = (int) (pixel.getRed());
		int G = (int) (pixel.getGreen());
		int B = (int) (pixel.getBlue());
		int grayPixel = (int) (.299*R + 0.587*G + 0.114*B);
		Color newPx = new Color(grayPixel,grayPixel,grayPixel);

		return newPx;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {

		for(int i=0;i<image.length;i++){
			for(int j=0;j<image[0].length;j++){
				image[i][j] = luminance(image[i][j]);
			}
		}
		return image;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		//// Replace the following statement with your code
		int h0 = image.length;
		int w0 = image[0].length;
		Color [][] newImg = new Color[height][width];
		int h = newImg.length;
		int w = newImg[0].length;
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				newImg[i][j]=image[i*h0/h][j*w0/w];
			}
		}
		return newImg;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		//// Replace the following statement with your code
		int blendR =(int) (alpha*c1.getRed() + (1-alpha)*c2.getRed());
		int blendG =(int) (alpha*c1.getGreen() + (1-alpha)*c2.getGreen());
		int blendB =(int) (alpha*c1.getBlue() + (1-alpha)*c2.getBlue());
		Color blendClr = new Color(blendR, blendG, blendB);

		return blendClr;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {

		int h = image1.length;
		int w = image1[0].length;
		Color [][] newImg = new Color[h][w];
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				newImg[i][j] = blend(image1[i][j],image2[i][j],alpha);
			}
		}
		return newImg;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		//// Replace this comment with your code
		int hSrc = source.length; 
		int wSrc = source[0].length; 
		target = scaled(target, wSrc, hSrc);
		Color[][] img = new Color[hSrc][wSrc];
		for(int i=0;i<=n;i++){
			double alpha = (double)(n - i)/(double)n;
			img = blend(source, target, alpha);
			display(img);
			StdDraw.pause(500);
			
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

