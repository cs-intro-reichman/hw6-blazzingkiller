    import java.awt.Color;

/**
 * Editor4 morphs an image into its grayscale version. 
 * The program receives two command-line arguments: 
 * - A string representing the name of the PPM file of the source (colored) image
 * - An integer representing the number of morphing steps (n).
 * 
 * For example: java Editor4 thor.ppm 50
 * This will morph the colored Thor image into a greyscaled Thor image in 50 steps.
 */
public class Editor4 {
    public static void main(String[] args) {
        String sourceFile = args[0];
        int n = Integer.parseInt(args[1]);
        
        // Read the source image
        Color[][] sourceImage = Runigram.read(sourceFile);
        
        // Create the grayscale version of the image
        Color[][] grayImage = Runigram.grayScaled(sourceImage);
        
        // Set up the canvas to display the morphing
        Runigram.setCanvas(sourceImage);
        
        // Morph from the source image to the grayscale image over n steps
        Runigram.morph(sourceImage, grayImage, n);
    }
}


