import java.awt.image.BufferedImage;

public class ImageToBinary {
    public static StringBuilder binaryStringBuilder = new StringBuilder();
    public static byte[] getBinaryData(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = new int[width * height];

        img.getRGB(0, 0, width, height, pixels,0, width);

        byte[] binaryData = new byte[pixels.length * 4];
        for(int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            binaryData[i * 4] = (byte) ((pixel >> 24) & 0xFF);      // Extract Alpha
            binaryData[i * 4 + 1] = (byte) ((pixel >> 16) & 0xFF);  // Extract Red
            binaryData[i * 4 + 2] = (byte) ((pixel >> 8) & 0xFF);   // Extract Green
            binaryData[i * 4 + 3] = (byte) (pixel & 0xFF);          // Extract Blue
        }
        for(int pixel : pixels) {
            // Convert each pixel component to its binary representation and append to the string builder
            String alpha = String.format("%8s", Integer.toBinaryString((pixel >> 24) & 0xFF)).replace(' ', '0');
            String red   = String.format("%8s", Integer.toBinaryString((pixel >> 16) & 0xFF)).replace(' ', '0');
            String green = String.format("%8s", Integer.toBinaryString((pixel >> 8) & 0xFF)).replace(' ', '0');
            String blue  = String.format("%8s", Integer.toBinaryString(pixel & 0xFF)).replace(' ', '0');

            binaryStringBuilder.append("A: ").append(alpha)
                    .append(" R: ").append(red)
                    .append(" G: ").append(green)
                    .append(" B: ").append(blue)
                    .append("\n");
        }
        System.out.println(binaryStringBuilder.toString());
        return binaryData;
    }
}
