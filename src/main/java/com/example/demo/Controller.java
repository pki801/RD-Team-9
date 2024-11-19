package com.example.demo;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/steganography")
public class Controller {
    @PostMapping("/encode/1bit")
    public ResponseEntity<Resource> encodeOneBitMessage(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("message") String message
    ){
        try {
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            BufferedImage embeddedImage = OneLSBEncoder.encodeMessage(Normalization.to24Bit(originalImage), message);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(embeddedImage, "png", outputStream);

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"embedded-image.png\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/decode/1bit")
    public ResponseEntity<String> decodeOneBitMessage(
            @RequestParam("image") MultipartFile imageFile
    ){
        try{
            BufferedImage encodedImage = ImageIO.read(imageFile.getInputStream());
            String message = OneLSBDecoder.extractMessage(encodedImage);

            return ResponseEntity.ok(message);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/encode/2bit")
    public ResponseEntity<Resource> encodeTwoBitMessage(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("message") String message
    ){
        try {
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            BufferedImage embeddedImage = TwoLSBEncoder.encodeMessage(Normalization.to24Bit(originalImage), message);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(embeddedImage, "png", outputStream);

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"embedded-image.png\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/decode/2bit")
    public ResponseEntity<String> decodeTwoBitMessage(
            @RequestParam("image") MultipartFile imageFile
    ){
        try{
            BufferedImage encodedImage = ImageIO.read(imageFile.getInputStream());
            String message = TwoLSBDecoder.extractMessage(encodedImage);

            return ResponseEntity.ok(message);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

