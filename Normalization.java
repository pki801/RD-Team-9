import java.awt.image.BufferedImage;

public class Normalization {
    public static BufferedImage to32Bit(BufferedImage image) {
        if (image != null) {
            // Create a new BufferedImage with 32-bit (ARGB) format
            BufferedImage newImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            // Iterate through each pixel
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int alpha = 0xFF; // Default to fully opaque

                    // Check if the image has alpha
                    if (image.getColorModel().hasAlpha()) {
                        alpha = (rgb >> 24) & 0xFF;
                    }

                    // Extract RGB values
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Combine into ARGB format
                    int argb = (alpha << 24) | (red << 16) | (green << 8) | blue;

                    // Set the pixel in the new image
                    newImage.setRGB(x, y, argb);
                }
            }
            return newImage;
        }
        return null;
    }
}
