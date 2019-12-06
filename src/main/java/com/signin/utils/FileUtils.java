package com.signin.utils;

import com.signin.common.Constants;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Auther: engow
 * @Date: 2019-02-28 19:20
 * @Description:
 */
class FileUtils {
    static void saveByteToMp3(String fileName, byte[] data) throws Exception {
        if (data != null) {
            String filePath = Constants.FILE_DIR + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data, 0, data.length);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }
}
