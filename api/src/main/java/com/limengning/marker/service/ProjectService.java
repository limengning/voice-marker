package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.ProjectEntity;
import com.limengning.marker.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectService extends ServiceImpl<ProjectMapper, ProjectEntity> {
    public boolean exist(Integer id) {
        return lambdaQuery().eq(ProjectEntity::getId, id).oneOpt().isPresent();
    }
}
