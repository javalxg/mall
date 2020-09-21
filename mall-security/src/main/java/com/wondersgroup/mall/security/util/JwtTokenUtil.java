package com.wondersgroup.mall.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxg
 * @Description: jwt工具类
 * @date 2020/9/1915:44
 *
 *   JwtToken生成的工具类
 *   JWT token的格式：header.payload.signature
 *   header的格式（算法、token的类型）：
 *   {"alg": "HS512","typ": "JWT"}
 *   payload的格式（用户名、创建时间、生成时间）：
 *   {"sub":"wang","created":1489079981393,"exp":1489684781}
 *   signature的生成算法：
 *  HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 *
 */
public class JwtTokenUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.expiration}")
    private Long expiration;
    private String generateToken(Map<String,Object> claims){
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512,secret)
                    .compact();

    }
    private Claims getClaimsFromToken(String token){
        Claims claims=null;
        try {
            claims=Jwts.parser()
                .setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            LOGGER.info("jwt验证失败", e.getMessage());
        }
        return claims;
    }
    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isExpireToken(token);
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        String username=null;
        try {
            Claims claims=getClaimsFromToken(token);
            username=claims.getSubject();
        }catch (Exception e){
            LOGGER.info("从token中获取用户名失败", e.getMessage());
        }
        return username;
    }



    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    private boolean isExpireToken(String token){
        Date date=getExpiredDateFromToken(token);
        return date.before(new Date());
    }
    private Date getExpiredDateFromToken(String token){
        Claims claims=getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成用户token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> map=new HashMap<>();
        map.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        map.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(map);
    }

    /**
     * 当原来的token没有过期 可以刷新
     * @param oldToken
     * @return
     */
    public String refreshToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //当token已经过期不支持刷新
        if (isExpireToken(token)) {
            return null;
        }
        //如果token在30分钟之内刷新过，返回原token
        if (tokenRefreshJustBefore(token, 30*60)){
            return token;
        }else{
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }

    }
    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if(refreshDate.after(created)&&refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }
}
