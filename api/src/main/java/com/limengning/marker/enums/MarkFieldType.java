package com.limengning.marker.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarkFieldType implements IEnum<Integer> {
    INPUT("文本框", 1),
    TEXT("文本域", 2),
    CHECKBOX("勾选", 3),
    SELECT("下拉", 4),
    TAG("标签", 5);
    MarkFieldType(String text, Integer value) {
        this.text = text;
        this.value = value;
    }
    private String text;
    private Integer value;

    @Override
    public Integer getValue() {
        return Integer.valueOf(value);
    }
}
