package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("project")
public class ProjectEntity extends BaseEntity {
    private String name;
    private String description;
}
