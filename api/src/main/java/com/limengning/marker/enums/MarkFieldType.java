package com.limengning.marker.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarkFieldType implements IEnum<Integer> {
    INPUT("文本框", 1),
    TEXT("文本域", 2),
    TAG("标签", 3),
    SWITCH("开关", 4),
    RADIO("单选", 5),
    CHECKBOX("多选", 6),
    SELECT("下拉", 7),
    RATE("打分", 8);
    MarkFieldType(String text, Integer value) {
        this.text = text;
        this.value = value.toString();
    }
    private String text;
    @JsonValue
    private String value;

    @Override
    public Integer getValue() {
        return Integer.valueOf(value);
    }
}
