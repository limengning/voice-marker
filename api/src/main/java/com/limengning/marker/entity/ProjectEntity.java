package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@TableName("project")
public class ProjectEntity extends BaseEntity {
    @NotBlank
    private String name;
    private String description;
}
