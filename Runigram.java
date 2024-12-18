import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		// Test vertical flipping
		Color[][] flippedV = flippedVertically(tinypic);
		display(flippedV);
	
		// Test grayscale conversion
		Color[][] grayscale = grayScaled(tinypic);
		display(grayscale);
	
		// Test scaling
		Color[][] scaledImage = scaled(tinypic, 100, 100);
		display(scaledImage);
	
		// Test blending
		Color[][] blendedImage = blend(tinypic, grayscale, 0.5);
		display(blendedImage);
	
		// Test morphing
		morph(tinypic, grayscale, 50);

		//// You can continue using the image array.
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
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int r = in.readInt(); // Read red value
				int g = in.readInt(); // Read green value
				int b = in.readInt(); // Read blue value
				image[i][j] = new Color(r, g, b); // Create Color object and assign it to the array
			}
		}
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

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++){
				print(image[i][j]);
				System.out.println(" ");
			}
			System.out.println();
		}
	
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {

		Color[][] flippedImage = new Color[image.length][image[0].length];

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++){	
				flippedImage[i][j] = image[i][image[i].length - 1 - j];
			}
		}
	return flippedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){

		Color[][] flippedImageV = new Color[image.length][image[0].length];

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++){	
				flippedImageV[i][j] = image[image.length - 1 - i][j];
			}
		}
	return flippedImageV;
	}
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {

		
		int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());

		Color greyscalePixel = new Color(lum, lum, lum);

		return greyscalePixel;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {

		Color[][] grayImage = new Color[image.length][image[0].length];

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++){	
			grayImage[i][j] = luminance(image[i][j]);
			}
		}
	return (grayImage);
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {

		Color[][] scaledImage = new Color[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++){	

				int originalRow = (i * image.length) / height;
				int originalCol = (j * image[0].length) / width;

				scaledImage[i][j] = image[originalRow][originalCol];
			}
		}
				return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		
		int newR = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
		int newB = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
		int newG = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());

		Color blendedColor = new Color(newR, newG, newB);

		return blendedColor;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		
		if (image1.length != image2.length || image1[0].length != image2[0].length){
			System.out.println("Error, images do not have the same dimensions");
			return null;
		}

		Color[][] blended = new Color[image1.length][image1[0].length];

		for (int i = 0; i < image1.length; i++) {
			for (int j = 0; j < image1[i].length; j++){	
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}

		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {

		Color[][] scaledTarget = scaled(target, source[0].length, source.length);

		for (int step = 0; step < n; step++) {
			double alpha = (double)(n - step) / n; // Blending factor
			Color[][] blendedImage = blend(source, scaledTarget, alpha); // Blend the images
			display(blendedImage); 
			StdDraw.pause(500); 
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
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

