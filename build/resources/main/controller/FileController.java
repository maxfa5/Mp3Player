package org.example.Mp3Player.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {

    private final Path rootLocation = Paths.get("src/main/resources/music");
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                System.out.println("Song play: " + resource.getFilename());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                        .body(resource);
            } else {
                System.out.println(("Файл не найден: " + filename));
//                  throw new RuntimeException("Файл не найден: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + filename, e);
        }
        return ResponseEntity.notFound().build();
    }
}