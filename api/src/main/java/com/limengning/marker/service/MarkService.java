package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkEntity;
import com.limengning.marker.mapper.MarkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService extends ServiceImpl<MarkMapper, MarkEntity> {
    public List<MarkEntity> list(Integer fileId) {
        return lambdaQuery().eq(MarkEntity::getFileId, fileId).list();
    }
}
