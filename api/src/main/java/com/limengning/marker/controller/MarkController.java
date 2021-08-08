package com.limengning.marker.controller;

import com.limengning.marker.entity.Mark;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/mark")
public class MarkController {

    @GetMapping("/")
    public Mono<List<Mark>> get(@RequestParam Integer fileId) {
        return null;
    }

    @PostMapping("/")
    public Mono<?> save(List<Mark> req) {
        return null;
    }
}
