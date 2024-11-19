package com.example.demo;

import java.awt.image.BufferedImage;

public class TwoLSBDecoder {
    //Method retrieves the secret message from image
    public static String extractMessage(BufferedImage image) {
        StringBuilder binaryMessage = new StringBuilder(); //Stores secret message's binary representation
        int width = image.getWidth();
        int height = image.getHeight();

        // Goes through each pixel in the image
        outerLoop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y); // Gets RGB values of current pixel

                // Retrieves the LSB from each RGB channel
                int red = (pixel >> 16) & 0x3;
                int green = (pixel >> 8) & 0x3;
                int blue = pixel & 0x3;

                // Appends extracted bits to binary message
                binaryMessage.append(String.format("%2s",Integer.toBinaryString(red)).replace(' ', '0'));
                binaryMessage.append(String.format("%2s",Integer.toBinaryString(green)).replace(' ', '0'));
                binaryMessage.append(String.format("%2s",Integer.toBinaryString(blue)).replace(' ', '0'));

                // If binary message has 8 bits, determine if it is equivalent to the null terminator (i.e., 0)
                if (binaryMessage.length() % 8 == 0) {
                    String byteString = binaryMessage.substring(binaryMessage.length() - 8);
                    int charCode = Integer.parseInt(byteString, 2);

                    // If charCode is equivalent to null terminator (i.e., 0), exit outer loop
                    if (charCode == 0) {
                        break outerLoop;
                    }
                }
            }
        }

        // Converts binary message into characters to create decoded message
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < binaryMessage.length(); i += 8) {
            String byteString = binaryMessage.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);
            if (charCode == 0) {
                break;
            }
            message.append((char) charCode); // Appends decoded character to message
        }

        return message.toString(); // Returns decoded message
    }
}
