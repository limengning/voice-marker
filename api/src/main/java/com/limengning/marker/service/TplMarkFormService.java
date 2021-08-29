package com.limengning.marker.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.entity.TplMarkFormEntity;
import com.limengning.marker.mapper.TplMarkFormMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TplMarkFormService extends ServiceImpl<TplMarkFormMapper, TplMarkFormEntity> {
    public Integer create() {
        var entity = new TplMarkFormEntity();
        save(entity);
        return entity.getId();
    }

    public List<TplMarkFormEntity> getNamedForms() {
        return lambdaQuery().isNotNull(TplMarkFormEntity::getName).list();
    }

    public boolean removeUnNamed(Integer id) {
        if (id == null) {
            return true;
        }
        return lambdaUpdate()
                .isNull(TplMarkFormEntity::getName)
                .eq(TplMarkFormEntity::getId, id)
                .remove();
    }
}
