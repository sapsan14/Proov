package ee.bcs.valiit.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class FileService {

    public static final String FILE_FOLDER = "C:/Pictures/";

    public static byte[] readFileBytes(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File(FILE_FOLDER + fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String formatName = fileName.split("\\.")[1];
            ImageIO.write(image, formatName, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String writeFileBytes(InputStream file, FormDataContentDisposition fileMedadata) {
        if (fileMedadata.getFileName().length() == 0) {
            return null;
        }
        try {
            String extension = fileMedadata.getFileName().split("\\.")[1];
            String fileName = UUID.randomUUID().toString();
            String fileNameWithExtension = fileName + "." + extension;
            FileOutputStream fos = new FileOutputStream(FileService.FILE_FOLDER + fileNameWithExtension);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = file.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            fos.flush();
            fos.close();
            return fileNameWithExtension;

        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again !!");
        }
    }

}
