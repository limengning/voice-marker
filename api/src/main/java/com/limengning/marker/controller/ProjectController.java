package com.limengning.marker.controller;

import com.limengning.marker.entity.File;
import com.limengning.marker.entity.Project;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/")
    public Mono<List<Project>> get() {
        return null;
    }

    @GetMapping("/{id}")
    public Mono<File> getOne(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/")
    public Mono<File> add(@RequestBody Project req) {
        return null;
    }

    @PutMapping("/{id}")
    public Mono<File> edit(@PathVariable Integer id, @RequestBody Project req) {
        return null;
    }

    @DeleteMapping("/{id}")
    public Mono<?> delete(@PathVariable Integer id) {
        return null;
    }
}
