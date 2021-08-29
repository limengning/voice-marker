package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.limengning.marker.enums.MarkFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mark_field")
public class MarkFieldEntity extends BaseEntity {
    private Integer projectId;
    private String fieldName;
    private String fieldDisplayText;
    private MarkFieldType fieldType;
    private boolean required;
    private String dataSource;
    private Integer sort;
}
