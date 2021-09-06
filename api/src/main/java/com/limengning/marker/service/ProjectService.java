package com.limengning.marker.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.limengning.marker.entity.FileEntity;
import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.entity.MarkFieldEntity;
import com.limengning.marker.entity.ProjectEntity;
import com.limengning.marker.mapper.ProjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@Service
public class ProjectService extends ServiceImpl<ProjectMapper, ProjectEntity> {
    private final FileService fileService;
    private final MarkService markService;
    private final MarkFieldService markFieldService;

    public ProjectService(FileService fileService, MarkService markService, MarkFieldService markFieldService) {
        this.fileService = fileService;
        this.markService = markService;
        this.markFieldService = markFieldService;
    }

    public boolean exist(Integer id) {
        return getById(id) != null;
    }

    @Override
    public boolean removeById(Serializable id) {
        final var project = getById(id);
        if (project == null) {
            return true;
        }
        return super.removeById(id);
    }

    public void export(Integer projectId, HttpServletResponse response) throws IOException {
        ProjectEntity project = getById(projectId);
        response.addHeader("Content-Type", "application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(project.getName(), StandardCharsets.UTF_8) + ".csv");
        List<MarkFieldEntity> markFields = markFieldService.getFields(projectId);
        long pageIndex = 0L;
        long pageSize = 100L;
        PrintWriter writer = response.getWriter();
        StringBuilder builder = new StringBuilder("项目名称,文件名称,开始,结束");
        for (MarkFieldEntity field : markFields) {
            builder.append(",");
            builder.append(field.getFieldDisplayText());
        }
        builder.append("\n");
        writer.write(builder.toString());
        while (true) {
            IPage<FileEntity> page = fileService.page(pageIndex, pageSize, projectId);
            if (page.getRecords().size() == 0) {
                break;
            }
            for (FileEntity file : page.getRecords()) {
                List<MarkEntity> marks = markService.list(file.getId());
                for (MarkEntity mark : marks) {
                    builder = new StringBuilder(project.getName());
                    builder.append(",");
                    builder.append(file.getName());
                    builder.append(",");
                    builder.append(mark.getStart());
                    builder.append(",");
                    builder.append(mark.getEnd());
                    String comment = mark.getComment();
                    if (StringUtils.isBlank(comment)) {
                        comment = "{}";
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    final JsonNode jsonNode = mapper.readTree(comment);
                    for (MarkFieldEntity field : markFields) {
                        builder.append(",");
                        String fieldName = field.getFieldName();
                        if (jsonNode.has(fieldName)) {
                            builder.append(jsonNode.get(fieldName).asText());
                        }
                    }
                    builder.append("\n");
                    writer.write(builder.toString());
                    response.flushBuffer();
                }
            }
            pageIndex += pageSize;
        }
    }
}
