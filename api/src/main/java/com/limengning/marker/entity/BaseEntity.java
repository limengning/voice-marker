package com.limengning.marker.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseEntity {
    private Integer id;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private Date updateBy;
}
