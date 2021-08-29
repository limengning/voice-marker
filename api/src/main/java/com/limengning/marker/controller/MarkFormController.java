package com.limengning.marker.controller;

import com.limengning.marker.entity.TplMarkFieldEntity;
import com.limengning.marker.entity.TplMarkFormEntity;
import com.limengning.marker.service.TplMarkFieldService;
import com.limengning.marker.service.TplMarkFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "标注表单")
@RestController
@RequestMapping("/markForm")
public class MarkFormController extends BaseController {
    private final TplMarkFormService tplMarkFormService;
    private final TplMarkFieldService tplMarkFieldService;

    public MarkFormController(TplMarkFormService tplMarkFormService, TplMarkFieldService tplMarkFieldService) {
        this.tplMarkFormService = tplMarkFormService;
        this.tplMarkFieldService = tplMarkFieldService;
    }

    @ApiOperation("获取标注表单设置列表")
    @GetMapping("")
    public List<TplMarkFormEntity> get() {
        return tplMarkFormService.getNamedForms();
    }

    @ApiOperation("获取标注表单字段设置")
    @GetMapping("/{formId}/field")
    public List<TplMarkFieldEntity> get(
            @ApiParam("表单id")
            @PathVariable Integer formId) {
        return tplMarkFieldService.getFields(formId);
    }

    @ApiOperation(value = "保存标注表单设置", notes = "根据表单id保存表单字段设置")
    @PostMapping("/{formId}/field")
    public void saveByForm(@RequestBody List<TplMarkFieldEntity> req, @PathVariable Integer formId) {
        tplMarkFieldService.save(req, formId);
    }

}
