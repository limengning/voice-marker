package com.limengning.marker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("file")
public class FileEntity extends BaseEntity {
    private Integer projectId;
    private String name;
    private String src;
    private String localPath;
    private Long size;
    private Integer duration;
}
