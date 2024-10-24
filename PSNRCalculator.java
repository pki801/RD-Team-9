import java.awt.image.BufferedImage;

public class PSNRCalculator {

    public static double calculatePSNR(BufferedImage img1, BufferedImage img2) {
        int width1 = img1.getWidth();
        int height1 = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();

        // The images must have the same width and height to compute PSNR.
        if (width1 != width2 || height1 != height2) {
            throw new IllegalArgumentException("Incompatible images: width/height does not match.");
        }
        
        double mse = 0.0;
        // Iterates over each pixel calculate the difference RBG value at every pixel, and accumulates it.
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                // Gets the RGB value of the pixel at index (x,y) for the two images we want to compare.
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                // Split the RGB value from each image to an int for each color
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;

                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;

                // Accumulate the difference of each pixel for each color squared.
                mse += Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2);
            }
        }

        // Final step of calculating MSE. 3 is here because of the three values R, G, and B.
        mse /= (width1 * height1 * 3);

        // The images are identical.
        if (mse == 0) {
            return Double.POSITIVE_INFINITY;
        }

        int maxPixelValue = 255;
        // PSNR formula.
        return 10 * Math.log10(maxPixelValue * maxPixelValue / mse);
    }
    
}