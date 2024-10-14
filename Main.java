import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("""
                1. Encode
                2. Decode
                Choose an option:\s""");

        int option = keyboard.nextInt();
        keyboard.nextLine();

        if (option == 1) {
            encoder();
        } else if (option == 2) {
            decoder();
        } else {
            System.out.println("Invalid option!");
        }
    }

    private static void encoder() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the image name: ");
        String path = keyboard.nextLine();

        System.out.println("Enter the message to hide: ");
        String message = keyboard.nextLine();

        System.out.println("Enter the output image name: ");
        String outputPath = keyboard.nextLine();

        try{
            BufferedImage image = ImageIO.read(new File(path));

            LSBEncoder.encodeMessage(image, message, outputPath);
        } catch (Exception e){
            e.printStackTrace();
        }
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
