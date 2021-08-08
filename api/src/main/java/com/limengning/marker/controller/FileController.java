package com.limengning.marker.controller;

import com.limengning.marker.entity.File;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncWebRequest;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/")
    public Mono<List<File>> get(@RequestParam Integer projectId) {
        return null;
    }

    @GetMapping("/{id}")
    public Mono<File> getOne(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/")
    public Mono<File> save(@RequestPart("file") FilePart filePart) {
        return null;
    }

    @DeleteMapping("/{id}")
    public Mono<?> delete(@PathVariable Integer id) {
        return null;
    }
}
