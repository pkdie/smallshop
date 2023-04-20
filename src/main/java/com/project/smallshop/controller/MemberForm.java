package com.project.smallshop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String email;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String pwd;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String name;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String phone;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String address;
}
