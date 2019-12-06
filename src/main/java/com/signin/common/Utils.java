package com.signin.common;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Auther: engow
 * @Date: 2019/4/29 20:42
 * @Description:
 */
public class Utils {
    public static String handleFileUpload(MultipartFile file, String dirName, String type) {
        try {
            String dirPath;
            if (type.equals("image") || type.equals("voice")) {
                dirPath = type + "/" + dirName + "/";
            } else {
                return null;
            }
            String filePath = dirPath + file.getOriginalFilename();
            String absolutePath = Constants.RESOURCE_DIR + filePath;
            File newFile = new File(absolutePath);
            if (!newFile.getParentFile().exists()) {
                boolean hasDirs = newFile.getParentFile().mkdirs();
                if (!hasDirs) {
                    return null;
                }
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newFile));

            out.write(file.getBytes());
            out.flush();
            out.close();
            return Constants.RESOURCE_URL + filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
