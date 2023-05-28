package com.itshow.demo.controller;

import com.itshow.demo.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ImageController {
    // 파일 저장 위치 지정
    private final static String basicPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\";

    private final ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public ResponseEntity<Result> upload(@RequestParam("file") MultipartFile file) {
        try {
            // 파일이 겹치지 않도록 UUID 지정
            String[] split = file.getOriginalFilename().split("\\.");
            String type = split[split.length - 1];
            if (!Objects.equals(type, "jpg") &&
                    !Objects.equals(type, "jpeg") &&
                    !Objects.equals(type, "png")) {
                return ResponseEntity
                        .badRequest()
                        .body(new Result(
                                "file is not the image",
                                true));
            }

            String uniquePath = UUID.randomUUID() +"."+ type;
            Path imagePath = Paths.get(basicPath, uniquePath);

            // 파일 생성
            Files.write(imagePath, file.getBytes());

            return ResponseEntity.ok()
                    .body(new Result(uniquePath, false));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new Result(e.getMessage(), true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new Result(null, true));
        }
    }

    @GetMapping("/image/{path}")
    public ResponseEntity image(@PathVariable("path") String path) {
        try {
            Resource resource = new FileSystemResource(basicPath + path);
            File file = resource.getFile();

            return ResponseEntity.ok()
                    .headers(headers -> headers.add("Content-Type", "image/jpeg"))
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new Result<>(null, true));
        }
    }
}
