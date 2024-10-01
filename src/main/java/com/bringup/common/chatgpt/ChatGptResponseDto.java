package com.bringup.common.chatgpt;

import lombok.Builder;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ChatGptResponseDto implements Serializable {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    @Builder
    public ChatGptResponseDto(String id, String object,
                              LocalDate created, String model,
                              List<Choice> choices) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }
}
