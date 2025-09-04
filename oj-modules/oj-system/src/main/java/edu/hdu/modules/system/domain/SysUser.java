package edu.hdu.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.hdu.commom.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;


@Data
@TableName("tb_sys_user")
public class SysUser extends BaseEntity {
    @TableId(type= IdType.ASSIGN_ID) //引入雪花算法生成分布式的主键ID
    private Long userId;
    private String userAccount;
    private String password;
}
