package com.example.demo;
import java.awt.image.BufferedImage;

public class Normalization {
    public static BufferedImage to24Bit(BufferedImage image) {
        if (image != null) {
            // Create a new BufferedImage with 24-bit (RGB) format
            BufferedImage newImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            // Iterate through each pixel
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);

                    // Extract RGB values
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Combine into ARGB format
                    int newrgb = (red << 16) | (green << 8) | blue;

                    // Set the pixel in the new image
                    newImage.setRGB(x, y, newrgb);
                }
            }
            return newImage;
        }
        return null;
    }
}