package com.example.demo;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TwoLSBEncoder {

    public static BufferedImage encodeMessage(BufferedImage image, String message) {
        StringBuilder binaryMessage = new StringBuilder();

        // Convert the message to binary, adding a null terminator (char code 0)
        for (char c : message.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0");
            binaryMessage.append(binaryChar);
        }
        // Append a null character at the end of the message to mark the end
        binaryMessage.append("00000000");

        int width = image.getWidth();
        int height = image.getHeight();
        int messageIndex = 0;

        // Goes through each pixel in the image
        outerLoop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                // retrieves each RGB value
                int red = (pixel >> 16) & 0xFC;
                int green = (pixel >> 8) & 0xFC;
                int blue = pixel & 0xFC;

                // Modify LSBs based on the message
                if (messageIndex + 1 < binaryMessage.length()) {
                    red = red | (((binaryMessage.charAt(messageIndex++) - '0') << 1) | (binaryMessage.charAt(messageIndex++) - '0'));
                }
                if (messageIndex + 1 < binaryMessage.length()) {
                    green = green | (((binaryMessage.charAt(messageIndex++) - '0') << 1) | (binaryMessage.charAt(messageIndex++) - '0'));
                }
                if (messageIndex + 1 < binaryMessage.length()) {
                    blue = blue | (((binaryMessage.charAt(messageIndex++) - '0') << 1) | (binaryMessage.charAt(messageIndex++) - '0'));
                }

                // Combine new RGB values and set pixel
                // Reconstructs pixel using modified LSBs and sets it in the image
                int newPixel = (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newPixel);

                // Stop if we've encoded the whole message
                if (messageIndex >= binaryMessage.length()) {
                    break outerLoop;
                }
            }
        }
        return image;
    }
}
