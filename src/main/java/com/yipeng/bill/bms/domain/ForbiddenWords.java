package com.yipeng.bill.bms.domain;

import java.io.Serializable;

public class ForbiddenWords implements Serializable {
    private Long id;

    private String words;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words == null ? null : words.trim();
    }
}