package org.example.fileuproad.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileUploadUtils {

    /*
        야밀파일에 맞춰서 나올 것.
     */

    @Value("${file.upload.windows.path}")
    private String windowPath;

    @Value("${file.upload.mac.path}")
    private String macPath;

    public String getUploadPath(String subdirectory) {

        String osName = System.getProperty("os.name").toLowerCase();
        String basePath;

        if (osName.contains("win")) {
            basePath = windowPath;
        } else {
            basePath = macPath;
        }

        String uploadPath = basePath + "/" + subdirectory;
        File directory = new File(uploadPath);

        if (!directory.exists()){
            System.out.println("파일 업로드를 위한 디렉토리 생성!");

            boolean created = directory.mkdirs();

            if(!created) {
                throw new RuntimeException("파일 업로드 디렉토리 생성 실패 : " + uploadPath);
            }
        }

        return uploadPath;
    }
}
