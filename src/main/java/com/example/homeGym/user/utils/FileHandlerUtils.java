package com.example.homeGym.user.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
public class FileHandlerUtils {
    public String saveFile(
            String path,
            String filenameBase,
            MultipartFile file
    ){
        // 파일을 어디에 업로드 할건지 결정
        String pathDir = String.format("media/%s", path);
        // 폴더가 없으면 생성
        try {
            Files.createDirectories(Path.of(pathDir));
        }catch (IOException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //파일의 원래 이름
        String originalFilename = file.getOriginalFilename();
        //확장자를 얻기 위해 . 기준으로 자름
        String[] filenameSplit = originalFilename.split("\\.");
        //확장자
        String extension = filenameSplit[filenameSplit.length - 1];
        //저장할 filename
        String filename = filenameBase + "." + extension;
        String filePath = pathDir + filename;

        try{
            file.transferTo(Path.of(filePath));
        }catch (IOException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return String.format("/static/%s", path + filename);

    }
}
