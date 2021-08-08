package com.limengning.marker.controller;

import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.service.MarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "标注模块")
@RestController
@RequestMapping("/mark")
public class MarkController extends BaseController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @ApiOperation("获取标注")
    @GetMapping("/")
    public List<MarkEntity> get(
            @ApiParam("文件id")
            @RequestParam Integer fileId) {
        return markService.list(fileId);
    }

    @ApiOperation("保存标注")
    @PostMapping("/")
    public void save(List<MarkEntity> req) {
        markService.saveBatch(req);
    }
}
