package com.limengning.marker.controller;

import com.limengning.marker.entity.MarkFieldEntity;
import com.limengning.marker.entity.MarkFormEntity;
import com.limengning.marker.service.MarkFieldService;
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
    private final MarkFieldService markFieldService;

    public MarkFormController(MarkFormService markFormService, MarkFieldService markFieldService) {
        this.markFormService = markFormService;
        this.markFieldService = markFieldService;
    }

    @ApiOperation("获取标注表单设置列表")
    @GetMapping("")
    public List<MarkFormEntity> get() {
        return markFormService.getNamedForms();
    }

    @ApiOperation("获取标注表单字段设置")
    @GetMapping("/{formId}/field")
    public List<MarkFieldEntity> get(
            @ApiParam("表单id")
            @PathVariable Integer formId) {
        return markFieldService.getFields(formId);
    }

    @ApiOperation(value = "保存标注表单设置", notes = "根据表单id保存表单字段设置")
    @PostMapping("/{formId}/field")
    public void saveByForm(@RequestBody List<MarkFieldEntity> req, @PathVariable Integer formId) {
        markFieldService.saveByForm(req, formId);
    }

    @ApiOperation(value = "保存标注表单设置", notes = "根据项目id保存表单字段设置")
    @PostMapping("/field")
    public void saveByProject(@RequestBody List<MarkFieldEntity> req, @RequestParam Integer projectId) {
        markFieldService.saveByProject(req, projectId);
    }
}
