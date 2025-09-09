package edu.hdu.modules.system.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysUserSaveDTO {
    @NotBlank   //此处注解用于参数校验  值不能为null
    private String userAccount;
    @NotBlank
    private String password;
}
