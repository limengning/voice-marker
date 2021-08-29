package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.ProjectEntity;
import com.limengning.marker.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class ProjectService extends ServiceImpl<ProjectMapper, ProjectEntity> {

    private final TplMarkFormService tplMarkFormService;

    public ProjectService(TplMarkFormService tplMarkFormService) {
        this.tplMarkFormService = tplMarkFormService;
    }

    public boolean exist(Integer id) {
        return getById(id) != null;
    }
    public Integer getMarkFormId(Integer id) {
        final var project = getById(id);
        if (project == null) {
            return null;
        }
        var markFormId = project.getMarkFormId();
        if (markFormId == null) {
            markFormId = tplMarkFormService.create();
            lambdaUpdate()
                    .eq(ProjectEntity::getId, id)
                    .set(ProjectEntity::getMarkFormId, markFormId)
                    .update();
        }
        return markFormId;
    }

    @Override
    public boolean removeById(Serializable id) {
        final var project = getById(id);
        if (project == null) {
            return true;
        }
        tplMarkFormService.removeUnNamed(project.getMarkFormId());
        return super.removeById(id);
    }
}
