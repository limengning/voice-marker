package com.limengning.marker.controller;

import com.limengning.marker.entity.MarkEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mark")
public class MarkController {

    @GetMapping("/")
    public List<MarkEntity> get(@RequestParam Integer fileId) {
        return null;
    }

    @PostMapping("/")
    public void save(List<MarkEntity> req) {
    }
}
