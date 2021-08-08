package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.mapper.MarkMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarkService extends ServiceImpl<MarkMapper, MarkEntity> {
    public List<MarkEntity> list(Integer fileId) {
        return lambdaQuery().eq(MarkEntity::getFileId, fileId).list();
    }

    @Override
    public boolean saveBatch(Collection<MarkEntity> entityList) {
        var fileIds = entityList.stream().map(MarkEntity::getFileId).collect(Collectors.toSet());
        removeByFileIds(fileIds);
        return super.saveBatch(entityList);
    }

    private boolean removeByFileIds(Collection<Integer> ids) {
        return lambdaUpdate().in(MarkEntity::getFileId, ids).remove();
    }
}
