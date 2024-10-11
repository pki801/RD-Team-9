import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LSBEncoder {

    // Number of bits to store the length of the message
    private static final int LENGTH_BITS = 32;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Input paths
            System.out.print("Enter path to the cover image (PNG format): ");
            String coverImagePath = scanner.nextLine();

            System.out.print("Enter path to save the stego image (PNG format recommended): ");
            String stegoImagePath = scanner.nextLine();

            // Input message
            System.out.print("Enter the secret message to hide: ");
            String message = scanner.nextLine();

            // Load cover image
            BufferedImage coverImage = ImageIO.read(new File(coverImagePath));

            // Encode the message into the image
            BufferedImage stegoImage = encodeMessage(coverImage, message);

            // Save stego image
            ImageIO.write(stegoImage, "png", new File(stegoImagePath));
            System.out.println("Message successfully hidden in " + stegoImagePath);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Encoding Error: " + e.getMessage());
        }
    }

    /**
     * Encodes a secret message into an image using LSB steganography.
     *
     * @param coverImage The original image.
     * @param message    The secret message to hide.
     * @return The image with the hidden message.
     * @throws IllegalArgumentException If the message is too long to hide in the image.
     */
    private static BufferedImage encodeMessage(BufferedImage coverImage, String message) {
        int imgWidth = coverImage.getWidth();
        int imgHeight = coverImage.getHeight();

        // Create a copy of the cover image to preserve the original
        BufferedImage stegoImage = new BufferedImage(imgWidth, imgHeight, coverImage.getType());
        Graphics2D graphics = stegoImage.createGraphics();
        graphics.drawImage(coverImage, 0, 0, null);
        graphics.dispose();

        // Convert message to binary
        byte[] messageBytes = message.getBytes();
        int messageLength = messageBytes.length;
        StringBuilder binaryMessage = new StringBuilder();

        // First, store the length of the message (32 bits)
        String lengthBinary = String.format("%32s", Integer.toBinaryString(messageLength)).replace(' ', '0');
        binaryMessage.append(lengthBinary);

        // Then, store the message itself
        for (byte b : messageBytes) {
            String byteBinary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryMessage.append(byteBinary);
        }

        // Calculate total bits to embed
        int totalBits = binaryMessage.length();

        // Calculate available bits in the image
        int availableBits = imgWidth * imgHeight * 3; // 3 bits per pixel (R, G, B)

        if (totalBits > availableBits) {
            throw new IllegalArgumentException("Message is too long to be hidden in this image.");
        }

        int bitIndex = 0;

        // Iterate through each pixel to embed the message
        outerLoop:
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                if (bitIndex >= totalBits) {
                    break outerLoop;
                }

                int rgb = stegoImage.getRGB(x, y);

                // Extract color components
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Modify the least significant bit of Red, Green, and Blue
                if (bitIndex < totalBits) {
                    red = (red & 0xFE) | (binaryMessage.charAt(bitIndex++) - '0');
                }
                if (bitIndex < totalBits) {
                    green = (green & 0xFE) | (binaryMessage.charAt(bitIndex++) - '0');
                }
                if (bitIndex < totalBits) {
                    blue = (blue & 0xFE) | (binaryMessage.charAt(bitIndex++) - '0');
                }

                // Reconstruct the RGB value, preserving the alpha channel
                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                stegoImage.setRGB(x, y, newRgb);
            }
        }

        return stegoImage;
    }
}
