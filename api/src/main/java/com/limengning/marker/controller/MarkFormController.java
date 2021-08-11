package com.limengning.marker.controller;

import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.entity.MarkFormEntity;
import com.limengning.marker.service.MarkFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "标注表单")
@RestController
@RequestMapping("/markForm")
public class MarkFormController extends BaseController {
    private final MarkFormService markFormService;

    public MarkFormController(MarkFormService markFormService) {
        this.markFormService = markFormService;
    }
    @ApiOperation("获取标注表单设置")
    @GetMapping("/")
    public List<MarkFormEntity> get(
            @ApiParam("项目id")
            @RequestParam Integer projectId) {
        return markFormService.list(projectId);
    }

    @ApiOperation("保存标注表单设置")
    @PostMapping("/")
    public void save(@RequestBody List<MarkFormEntity> req, @RequestParam Integer projectId) {
        markFormService.save(req, projectId );
    }
}
