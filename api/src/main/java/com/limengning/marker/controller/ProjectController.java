package com.limengning.marker.controller;

import com.limengning.marker.entity.FileEntity;
import com.limengning.marker.entity.ProjectEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/")
    public Mono<List<ProjectEntity>> get() {
        return null;
    }

    @GetMapping("/{id}")
    public Mono<FileEntity> getOne(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/")
    public Mono<FileEntity> add(@RequestBody ProjectEntity req) {
        return null;
    }

    @PutMapping("/{id}")
    public Mono<FileEntity> edit(@PathVariable Integer id, @RequestBody ProjectEntity req) {
        return null;
    }

    @DeleteMapping("/{id}")
    public Mono<?> delete(@PathVariable Integer id) {
        return null;
    }
}
