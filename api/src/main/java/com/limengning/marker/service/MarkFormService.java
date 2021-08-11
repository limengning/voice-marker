package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.MarkFormEntity;
import com.limengning.marker.mapper.MarkFormMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkFormService extends ServiceImpl<MarkFormMapper, MarkFormEntity> {
    public Integer create() {
        var entity = new MarkFormEntity();
        save(entity);
        return entity.getId();
    }

    public List<MarkFormEntity> getNamedForms() {
        return lambdaQuery().isNotNull(MarkFormEntity::getName).list();
    }

    public boolean removeUnNamed(Integer id) {
        if (id == null) {
            return true;
        }
        return lambdaUpdate()
                .isNull(MarkFormEntity::getName)
                .eq(MarkFormEntity::getId, id)
                .remove();
    }
}
