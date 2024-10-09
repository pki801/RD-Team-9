import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        decoder();
    }

    private static void decoder() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the image name: ");
        String path = keyboard.next();

        try {
            BufferedImage image = ImageIO.read(new File(path));

            System.out.println("Hidden Message: " + LSBDecoder.extractMessage(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

