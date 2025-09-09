package edu.hdu.commom.core.utils;

import edu.hdu.commom.core.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * ⽣成令牌
     *
     * @param claims 数据
     * @param secret 密钥
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, String secret)
    {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,secret).compact();
        return token;
    }
    /**
     * 从令牌中获取数据
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 数据
     */
    public static Claims parseToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static String getUserKey(Claims claims) {
        Object userKey = claims.get(JwtConstants.LOGIN_USER_KEY);
        if(userKey==null){
            return "";
        }
        return String.valueOf(userKey);
    }


    public static String getUserId(Claims claims) {
        Object userId = claims.get(JwtConstants.LOGIN_USER_ID);
        if(userId==null){
            return "";
        }
        return String.valueOf(userId);
    }
}
