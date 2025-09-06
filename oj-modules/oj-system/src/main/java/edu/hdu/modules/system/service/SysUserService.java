package edu.hdu.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.hdu.commom.core.domain.Result;
import edu.hdu.commom.core.enums.ResultCode;
import edu.hdu.modules.system.domain.SysUser;
import edu.hdu.modules.system.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    public Result<Void> login(String userAccount, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.lambda()
                .select(SysUser::getPassword)
                .eq(SysUser::getUserAccount, userAccount);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            return Result.failed(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        if (sysUser.getPassword().equals(password)) {
            return Result.success();
        }
        return Result.failed(ResultCode.FAILED_LOGIN);
    }
}
