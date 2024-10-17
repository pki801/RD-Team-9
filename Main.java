import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        
    // Displays options to user for if they want to encode, decode, or exit
        while (option != 3) {
            System.out.println(
                    "1. Encode\n" +
                    "2. Decode\n" +
                    "3. Exit\n" +
                    "Choose an option:"
            );

            option = keyboard.nextInt();
            keyboard.nextLine();

         // Based on user's input (1 for encode, 2 for decode, 3 for exit), either encoder() or decode() is called or program exits 
            if (option == 1) {
                encoder();
            } else if (option == 2) {
                decoder();
            } else if(option == 3) {
                System.exit(0);
            } else {
                System.out.println("Invalid option!");
            }
        }

    }

    // Method for encoding
    private static void encoder() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the image name: ");
        String path = keyboard.nextLine(); // Gets the path of the image to be encoded

        System.out.println("Enter the message to hide: ");
        String message = keyboard.nextLine(); // Gets secret message

        System.out.println("Enter the output image name: ");
        String outputPath = keyboard.nextLine(); // Gets output image's path

        try{
            BufferedImage image = ImageIO.read(new File(path)); // reads image
            BufferedImage newImage = Normalization.to32Bit(image); // converts/normalizes image to 32 bit format
            LSBEncoder.encodeMessage(newImage, message, outputPath); // encodes secret message
        } catch (Exception e){
            e.printStackTrace();
        }
    }

// Method for decoding
    private static void decoder() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the image name: ");
        String path = keyboard.next(); // Gets path of the image to be decoded

        try {
            BufferedImage image = ImageIO.read(new File(path)); // reads image
            System.out.println("Hidden Message: " + LSBDecoder.extractMessage(image)); // decodes message and displays it to the user
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
