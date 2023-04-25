package com.project.smallshop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class OrderForm {

    @NotEmpty(message = "필수 입력 사항입니다")
    private String receiverName;

    @NotEmpty(message = "필수 입력 사항입니다")
    private String receiverPhone;

    @NotEmpty(message = "필수 입력 사항입니다")
    private String receiverAddress;
}
