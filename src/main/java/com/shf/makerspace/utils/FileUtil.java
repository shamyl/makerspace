package com.shf.makerspace.utils;

import com.shf.makerspace.exception.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static void dumpFile(InputStream inputStream, String path, String fileName) {
        String qualifiedFileName = path + "/" + fileName;
//        createDirectoryIfNotExist(path);
        try {
            Path targetPath = Paths.get(qualifiedFileName);

            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath.getParent());
                Files.createFile(targetPath);
            }
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

//            Files.copy(inputStream, Paths.get(qualifiedFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Error while dump file....... {}", fileName);
            throw new DocumentException("Error while dump file " + fileName);
        }
    }

    public static boolean createDirectory(String path) {
        File directory = new File(path);
        return directory.mkdir();
    }

    public static void createDirectoryIfNotExist(String path) {
        if (!Files.exists(Paths.get(path))) {
            createDirectory(path);
        }
    }

}
