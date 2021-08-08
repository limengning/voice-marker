package com.limengning.marker.controller;

import com.limengning.marker.entity.FileEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/")
    public List<FileEntity> get(@RequestParam Integer projectId) {
        return null;
    }

    @GetMapping("/{id}")
    public FileEntity getOne(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/")
    public void save(@RequestPart("file") FilePart filePart) {

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    }
}
