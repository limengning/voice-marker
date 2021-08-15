package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkFieldEntity;
import com.limengning.marker.entity.MarkFormEntity;
import com.limengning.marker.mapper.MarkFieldMapper;
import com.limengning.marker.mapper.MarkFormMapper;
import com.limengning.marker.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MarkFieldService extends ServiceImpl<MarkFieldMapper, MarkFieldEntity> {

    private final ProjectService projectService;
    private final MarkFormService markFormService;


    public MarkFieldService(ProjectService projectService, MarkFormService markFormService) {
        this.projectService = projectService;
        this.markFormService = markFormService;
    }

    public List<MarkFieldEntity> getFields(Integer formId) {
        return lambdaQuery().eq(MarkFieldEntity::getFormId, formId).list();
    }

    public Integer saveByProject(Collection<MarkFieldEntity> entities, Integer projectId) {
        var formId = projectService.getMarkFormId(projectId);
        if (formId != null) {
            saveByForm(entities, formId);
        }
        return formId;
    }

    public void saveByForm(Collection<MarkFieldEntity> entities, Integer formId) {
        removeFields(formId);
        if (!entities.isEmpty()) {
            entities.forEach(x -> x.setFormId(formId));
            saveBatch(entities);
        }
    }

    boolean removeFields(Integer formId) {
        return lambdaUpdate()
                .eq(MarkFieldEntity::getFormId, formId)
                .remove();
    }
}
