package edu.hdu.modules.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.hdu.commom.core.domain.Result;
import edu.hdu.commom.core.enums.ResultCode;
import edu.hdu.commom.core.enums.UserIdentity;
import edu.hdu.common.redis.service.RedisService;
import edu.hdu.common.security.exception.ServiceException;
import edu.hdu.common.security.service.TokenService;
import edu.hdu.modules.system.domain.SysUser;
import edu.hdu.modules.system.domain.DTO.SysUserSaveDTO;
import edu.hdu.modules.system.mapper.SysUserMapper;
import edu.hdu.modules.system.utils.BCryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RefreshScope
public class SysUserService {

    @Value("${jwt.secret}")
    String jwtSecret;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;

    public Result<String> login(String userAccount, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.lambda()
                .select(SysUser::getUserId,SysUser::getPassword)
                .eq(SysUser::getUserAccount, userAccount);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            return Result.failed(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        if (BCryptUtils.matchesPassword(password, sysUser.getPassword())) {
            String token=tokenService.createToken(sysUser.getUserId(),jwtSecret,UserIdentity.ADMIN.getValue());
            return Result.success(token);
        }
        return Result.failed(ResultCode.FAILED_LOGIN);
    }

    public Result<Integer> addSysUser(SysUserSaveDTO sysUserSaveDTO) {
        List<SysUser> sysUserList = sysUserMapper.selectList(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserAccount, sysUserSaveDTO.getUserAccount()));
        if (CollectionUtil.isNotEmpty(sysUserList)) {
            throw new ServiceException(ResultCode.AILED_USER_EXISTS);
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserAccount(sysUserSaveDTO.getUserAccount());
        sysUser.setPassword(BCryptUtils.encryptPassword(sysUserSaveDTO.getPassword()));
        int isInsert=sysUserMapper.insert(sysUser);
        return isInsert==0?Result.error():Result.success();
    }
}
