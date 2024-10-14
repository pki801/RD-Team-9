import java.awt.image.BufferedImage;

public class LSBDecoder {

    public static String extractMessage(BufferedImage image) {
        StringBuilder binaryMessage = new StringBuilder();
        int width = image.getWidth();
        int height = image.getHeight();

        outerLoop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                int red = (pixel >> 16) & 1;
                int green = (pixel >> 8) & 1;
                int blue = pixel & 1;

                binaryMessage.append(red);
                binaryMessage.append(green);
                binaryMessage.append(blue);

                if (binaryMessage.length() % 8 == 0) {
                    String byteString = binaryMessage.substring(binaryMessage.length() - 8);
                    int charCode = Integer.parseInt(byteString, 2);

                    if (charCode == 0) {
                        break outerLoop;
                    }
                }
            }
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < binaryMessage.length(); i += 8) {
            String byteString = binaryMessage.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);
            if (charCode == 0) {
                break;
            }
            message.append((char) charCode);
        }

        return message.toString();
    }
}
