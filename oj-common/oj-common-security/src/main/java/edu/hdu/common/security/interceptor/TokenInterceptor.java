package edu.hdu.common.security.interceptor;

import cn.hutool.core.util.StrUtil;
import edu.hdu.commom.core.constants.HttpConstants;
import edu.hdu.common.security.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Value("${jwt.secret}")
    String jwtSecret; //从哪个服务配置文件中读取，取决于这个bean对象交给了那个服务的spring容器管理

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中获取 token
        String token = getToken(request);
        tokenService.extendToken(token,jwtSecret);
        return true;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.substring(HttpConstants.PREFIX.length());
        }
        return token;
    }
}

