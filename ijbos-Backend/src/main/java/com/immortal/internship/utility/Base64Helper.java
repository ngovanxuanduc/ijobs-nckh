package com.immortal.internship.utility;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

/**
 * https://www.baeldung.com/java-base64-image-string
 * https://stackoverflow.com/questions/27886677/javascript-get-extension-from-base64-image
 * */
public class Base64Helper {

    private static String getFileNameExtension(char c){
        if (c=='/') return ".jpg";
        if (c=='i') return ".png";
        if (c=='R') return ".gif";
        return ".webp";
    }

    private static String generateFileName(char fileNameExtension){
        return RandomIDUtil.getGeneratedString()
                .concat("_"+new Date().getTime())
                .concat(getFileNameExtension(fileNameExtension));
    }

    public static File base64ToFile(String encodedString) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        File file = new File(generateFileName(encodedString.charAt(0)));
        FileUtils.writeByteArrayToFile(file, decodedBytes);
        return file;
    }


//    public static void main(String[] args) throws IOException {
//        String pathIn = "D:\\test\\in.txt";
//
//        String source = new String(Files.readAllBytes(Paths.get(pathIn)));
//
//        File file = base64ToFile(source);
//        System.out.println(file.getName());
////        file.delete();
////        System.out.println(file.getPath());
////        FileOutputStream fileOutputStream = new FileOutputStream(file);
//    }
}
