package edu.hdu.commom.core.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
}
