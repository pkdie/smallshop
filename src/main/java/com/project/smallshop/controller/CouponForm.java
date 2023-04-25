package com.project.smallshop.controller;

import com.project.smallshop.domain.CouponType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter @Setter
public class CouponForm {

    private String name;

    @Min(value = 0, message = "0이상의 값을 입력해주세요") @Max(value = 100, message = "100이하의 값을 입력해주세요")
    private int discount;

    private CouponType type;
}
