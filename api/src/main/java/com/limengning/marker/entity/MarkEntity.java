package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@TableName("mark")
public class MarkEntity extends BaseEntity {
    @NotNull
    private Integer fileId;
    private String regionId;
    @TableField("start_point")
    private Float start;
    @TableField("end_point")
    private Float end;
    private String comment;
    private boolean locked;
}
