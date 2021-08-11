package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.limengning.marker.enums.MarkFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mark_form")
public class MarkFormEntity extends BaseEntity {
    private Integer projectId;
    private String fieldName;
    private String fieldDisplayText;
    private MarkFieldType fieldType;
    private Boolean required;
    private String dataSource;
    private Integer sort;
}
