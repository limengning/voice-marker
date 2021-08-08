package com.limengning.marker.controller;

import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.service.MarkService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "标注模块")
@RestController
@RequestMapping("/mark")
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping("/")
    public List<MarkEntity> get(@RequestParam Integer fileId) {
        return markService.list(fileId);
    }

    @PostMapping("/")
    public void save(List<MarkEntity> req) {
        markService.saveBatch(req);
    }
}
