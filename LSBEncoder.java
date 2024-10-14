import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LSBEncoder {

    public static void encodeMessage(BufferedImage image, String message, String outputPath) {
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

        outerLoop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // Modify LSBs based on the message
                if (messageIndex < binaryMessage.length()) {
                    red = (red & 0xFE) | (binaryMessage.charAt(messageIndex++) - '0');
                }
                if (messageIndex < binaryMessage.length()) {
                    green = (green & 0xFE) | (binaryMessage.charAt(messageIndex++) - '0');
                }
                if (messageIndex < binaryMessage.length()) {
                    blue = (blue & 0xFE) | (binaryMessage.charAt(messageIndex++) - '0');
                }

                // Combine new RGB values and set pixel
                int newPixel = (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newPixel);

                // Stop if we've encoded the whole message
                if (messageIndex >= binaryMessage.length()) {
                    break outerLoop;
                }
            }
        }

        // Write the modified image to a file
        try {
            ImageIO.write(image, "png", new File(outputPath));
            System.out.println("Message encoded successfully to " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}