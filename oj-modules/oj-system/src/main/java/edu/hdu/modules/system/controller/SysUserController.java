package edu.hdu.modules.system.controller;


import edu.hdu.commom.core.domain.Result;
import edu.hdu.modules.system.domain.DTO.LoginDTO;
import edu.hdu.modules.system.domain.DTO.SysUserSaveDTO;
import edu.hdu.modules.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        return sysUserService.login(loginDTO.getUserAccount(), loginDTO.getPassword());
    }

    @PostMapping("/addSysUser")
    public Result<Integer> addSysUser(@RequestBody SysUserSaveDTO sysUserSaveDTO) {
        return  sysUserService.addSysUser(sysUserSaveDTO);
    }
}
