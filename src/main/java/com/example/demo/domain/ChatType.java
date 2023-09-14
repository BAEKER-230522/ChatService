package com.example.demo.domain;

import lombok.Getter;

@Getter
public enum ChatType {
    IN("IN"), OUT("OUT"), MESSAGE("MESSAGE")
    ;
    private final String type;

    ChatType(String type) {
        this.type = type;
    }
}
