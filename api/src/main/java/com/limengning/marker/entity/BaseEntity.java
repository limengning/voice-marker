package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateBy;
}
