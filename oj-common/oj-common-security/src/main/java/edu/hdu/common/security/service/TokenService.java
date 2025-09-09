package edu.hdu.common.security.service;

import edu.hdu.commom.core.constants.CacheConstants;
import edu.hdu.commom.core.constants.JwtConstants;
import edu.hdu.commom.core.domain.LoginUser;
import edu.hdu.commom.core.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import edu.hdu.common.redis.service.RedisService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String createToken(long userId,String jwtSecret,int identify) {
        //JWT 生成token
        Map<String, Object> claims = new HashMap<>();
        String userKey = UUID.randomUUID().toString();
        claims.put(JwtConstants.LOGIN_USER_ID,userId);
        claims.put(JwtConstants.LOGIN_USER_KEY,userKey);
        String token= JwtUtils.createToken(claims, jwtSecret);

        // 搭配redis 解决JWT存在的问题
        String key = CacheConstants.LOGIN_TOKEN_KEY +userKey;
        LoginUser loginUser = new LoginUser();
        loginUser.setIdentity(identify);
        redisService.setCacheObject(key, loginUser,CacheConstants.EXP, TimeUnit.MINUTES);
        return token;
    }

    public void extendToken(String token,String jwtSecret) {
        Claims claims;
        try {
            claims=JwtUtils.parseToken(token,jwtSecret);
            if(claims==null){
                log.error("解析token：{}，出现异常",token);
                return;
            }
        }catch (Exception e){
            log.error("解析token：{}，出现异常",token,e);
            return;
        }

        String userKey = JwtUtils.getUserKey(claims);
        String key = CacheConstants.LOGIN_TOKEN_KEY +userKey;

        Long expire = redisService.getExpire(key,TimeUnit.MINUTES);
        if(expire!=null&&expire<CacheConstants.REFRESH_TIME){
            redisService.expire(key,CacheConstants.EXP,TimeUnit.MINUTES);
        }

    }
}
