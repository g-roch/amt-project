/**
 * Manage the upload of files in order to upload an image of an article
 *
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */
package com.amt.app.utils;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;


public class FileUploadUtil {

    /**
     * Save a file
     * @param uploadDir directory in which the file is saved
     * @param fileName filename of the file to be saved
     * @param multipartFile inputstream
     */
    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
