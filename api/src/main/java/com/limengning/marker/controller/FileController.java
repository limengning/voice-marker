package com.limengning.marker.controller;

import com.limengning.marker.entity.FileEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/")
    public Mono<List<FileEntity>> get(@RequestParam Integer projectId) {
        return null;
    }

    @GetMapping("/{id}")
    public Mono<FileEntity> getOne(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/")
    public Mono<FileEntity> save(@RequestPart("file") FilePart filePart) {
        return null;
    }

    @DeleteMapping("/{id}")
    public Mono<?> delete(@PathVariable Integer id) {
        return null;
    }
}
