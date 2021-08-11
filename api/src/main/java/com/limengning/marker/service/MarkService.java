package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.mapper.MarkMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MarkService extends ServiceImpl<MarkMapper, MarkEntity> {
    public List<MarkEntity> list(Integer fileId) {
        return lambdaQuery().eq(MarkEntity::getFileId, fileId).list();
    }

    public boolean saveBatch(Collection<MarkEntity> entityList, Integer fileId) {
        removeByFileId(fileId);
        if (!entityList.isEmpty()) {
            return super.saveBatch(entityList);
        }
        return true;
    }

    private boolean removeByFileId(Integer id) {
        return lambdaUpdate().eq(MarkEntity::getFileId, id).remove();
    }
}
