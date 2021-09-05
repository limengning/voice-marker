package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkFieldEntity;
import com.limengning.marker.mapper.MarkFieldMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MarkFieldService extends ServiceImpl<MarkFieldMapper, MarkFieldEntity> {
    public List<MarkFieldEntity> getFields(Integer projectId) {
        return lambdaQuery().eq(MarkFieldEntity::getProjectId, projectId).list();
    }

    public void save(Collection<MarkFieldEntity> entities, Integer projectId) {
        clearFields(projectId);
        if (!entities.isEmpty()) {
            entities.forEach(x -> {
                x.setProjectId(projectId);
                x.setId(null);
            });
            saveBatch(entities);
        }
    }

    boolean clearFields(Integer projectId) {
        return lambdaUpdate()
                .eq(MarkFieldEntity::getProjectId, projectId)
                .remove();
    }
}
