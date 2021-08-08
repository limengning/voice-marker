package com.limengning.marker.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.limengning.marker.entity.FileEntity;
import com.limengning.marker.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "文件模块")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @ApiOperation("获取文件列表")
    @GetMapping("/")
    public IPage<FileEntity> get(
            @ApiParam("项目id")
            @RequestParam Integer projectId,
            @RequestParam(defaultValue = "1") long pageIndex,
            @RequestParam(defaultValue = "10") long pageSize) {
        return fileService.page(pageIndex, pageSize, projectId);
    }

    @ApiOperation("获取单个文件")
    @GetMapping("/{id}")
    public FileEntity getOne(@PathVariable Integer id) {
        return fileService.getById(id);
    }

    @ApiOperation("上传文件")
    @PostMapping("/")
    public FileEntity save(@RequestParam("file") MultipartFile file, @RequestParam Integer projectId) throws IOException {
        return fileService.save(file.getInputStream(), file.getContentType(), file.getOriginalFilename() ,projectId);
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        fileService.removeById(id);
    }
}
