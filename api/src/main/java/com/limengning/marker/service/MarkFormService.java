package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkFormEntity;
import com.limengning.marker.mapper.MarkFormMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MarkFormService extends ServiceImpl<MarkFormMapper, MarkFormEntity> {

    public List<MarkFormEntity> list(Integer projectId) {
        return lambdaQuery().eq(MarkFormEntity::getProjectId, projectId).list();
    }

    public void save(Collection<MarkFormEntity> entities, Integer projectId) {
        removeByProjectId(projectId);
        if (!entities.isEmpty()) {
            saveBatch(entities);
        }
    }

    boolean removeByProjectId(Integer projectId) {
        return lambdaUpdate()
                .eq(MarkFormEntity::getProjectId, projectId)
                .remove();
    }
}
