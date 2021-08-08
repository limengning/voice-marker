package com.limengning.marker.entity;

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
    private Integer start;
    private Integer end;
    private String comment;
}
