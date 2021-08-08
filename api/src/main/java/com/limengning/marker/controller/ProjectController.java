package com.limengning.marker.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limengning.marker.entity.ProjectEntity;
import com.limengning.marker.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "项目模块")
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {

        this.projectService = projectService;
    }

    @ApiOperation("获取项目列表")
    @GetMapping("/")
    public IPage<ProjectEntity> get(
            @RequestParam(defaultValue = "1") long pageIndex,
            @RequestParam(defaultValue = "10") long pageSize) {
        var page = new Page<ProjectEntity>(pageIndex, pageSize);
        return projectService.page(page);
    }

    @ApiOperation("获取单个项目")
    @GetMapping("/{id}")
    public ProjectEntity getOne(
            @ApiParam(name = "id", value = "项目id", required = true)
            @PathVariable Integer id) {
        return projectService.getById(id);
    }

    @ApiOperation("添加项目")
    @PostMapping("/")
    public Integer add(
            @Valid @RequestBody ProjectEntity req) {
        projectService.save(req);
        return req.getId();
    }

    @ApiOperation("修改项目")
    @PutMapping("/{id}")
    public void edit(
            @ApiParam(name = "id", value = "项目id", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ProjectEntity req) {
        req.setId(id);
        projectService.updateById(req);
    }

    @ApiOperation("删除项目")
    @DeleteMapping("/{id}")
    public void delete(
            @ApiParam(name = "id", value = "项目id", required = true)
            @PathVariable Integer id) {
        projectService.removeById(id);
    }
}
