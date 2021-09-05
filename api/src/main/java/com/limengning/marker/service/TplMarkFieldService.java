package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.TplMarkFieldEntity;
import com.limengning.marker.mapper.TplMarkFieldMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TplMarkFieldService extends ServiceImpl<TplMarkFieldMapper, TplMarkFieldEntity> {

    public List<TplMarkFieldEntity> getFields(Integer formId) {
        return lambdaQuery().eq(TplMarkFieldEntity::getFormId, formId).list();
    }

    public void save(Collection<TplMarkFieldEntity> entities, Integer formId) {
        clearFields(formId);
        if (!entities.isEmpty()) {
            entities.forEach(x -> {
                x.setFormId(formId);
                x.setId(null);
            });
            saveBatch(entities);
        }
    }

    boolean clearFields(Integer formId) {
        return lambdaUpdate()
                .eq(TplMarkFieldEntity::getFormId, formId)
                .remove();
    }
}
