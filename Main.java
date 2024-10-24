import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        int option = 0;

    // Displays options to user for if they want to encode, decode, or exit
        while (option != 4) {
            System.out.println(
                    "1. Encode\n" +
                    "2. Decode\n" +
                    "3. PSNR\n" +
                    "4. Exit\n" +
                    "Choose an option:"
            );

            option = keyboard.nextInt();
            keyboard.nextLine();

         // Based on user's input (1 for encode, 2 for decode, 3 for exit), either encoder() or decode() is called or program exits
            if (option == 1) {
                encoder();
            } else if (option == 2) {
                decoder();
            } else if (option == 3) {
                psnr();
            } else if (option == 4) {
                System.exit(0);
            } else {
                System.out.println("Invalid option!");
            }
        }

    }

    private static void encoder() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the image name: ");
        String path = keyboard.nextLine();

        System.out.println("Enter the txt file to hide: ");
        String filePath = keyboard.nextLine();
        String message = new String(Files.readAllBytes(Paths.get(filePath)));

        System.out.println("Enter the output image name: ");
        String outputPath = keyboard.nextLine();

        try{
            BufferedImage image = ImageIO.read(new File(path));
            BufferedImage newImage = Normalization.to24Bit(image);
            LSBEncoder.encodeMessage(newImage, message, outputPath);
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

    private static void psnr() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the first image name: ");
        String path1 = keyboard.next();
        System.out.println("Enter the second image name: ");
        String path2 = keyboard.next();

        try {
            BufferedImage img1 = ImageIO.read(new File(path1));
            BufferedImage img2 = ImageIO.read(new File(path2));
            double psnr = PSNRCalculator.calculatePSNR(img1, img2);
            System.out.println("PSNR: " + psnr + " dB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
