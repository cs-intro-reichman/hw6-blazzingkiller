import java.awt.Color;

/**
 * Demonstrates the scaling (resizing) operation featured by Runigram.java. 
 * The program receives three command-line arguments: a string representing the name
 * of the PPM file of a source image, and two integers that specify the width and the
 * height of the scaled, output image. For example, to scale ironman.ppm to width=100 and
 * height=900, use: java Editor2 ironman.ppm 100 900
 */
public class Editor2 {
    public static void main (String[] args) {
        // Parse command-line arguments
        String sourceFile = args[0];
        int newWidth = Integer.parseInt(args[1]);
        int newHeight = Integer.parseInt(args[2]);
        
        // Read the source image
        Color[][] sourceImage = Runigram.read(sourceFile);
        
        // Create the scaled image
        Color[][] scaledImage = Runigram.scaled(sourceImage, newWidth, newHeight);
        
        // Display the original image
        Runigram.setCanvas(sourceImage);
        Runigram.display(sourceImage);
        
        // Pause for a while so we can see the original image
        StdDraw.pause(1000);
        
        // Display the scaled image
        Runigram.setCanvas(scaledImage);
        Runigram.display(scaledImage);
    }
}
