package com.project.smallshop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class reviewForm {

    @NotEmpty(message = "필수 입력 사항입니다")
    private String title;

    @NotEmpty(message = "필수 입력 사항입니다")
    private String content;
}
