package edu.hdu.commom.core.domain;

import lombok.Data;

@Data
public class LoginUser {
    // 1 表示普通用户 & 2 表示管理员用户
    private int identity;
}
