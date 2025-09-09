package edu.hdu.modules.system.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String userAccount;
    @NotBlank
    private String password;
}
