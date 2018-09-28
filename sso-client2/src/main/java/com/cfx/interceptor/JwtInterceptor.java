package com.cfx.interceptor;

import com.alibaba.fastjson.JSON;
import com.cfx.exception.BusinessException;
import com.cfx.util.CookieUtil;
import com.cfx.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: chenfeixiang
 * @Date: Created in 15:30 2018/9/28
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value(value = "${TTL}")
    private String TTL;

    @Value(value = "${refreshTTL}")
    private String refreshTTL;

    @Value(value = "${authServer}")
    private String authAddress;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = CookieUtil.getCookie(request, "token");
        String refreshToken = CookieUtil.getCookie(request, "refreshToken");
        try {
            JwtUtil.parseJWT(token);
            try {
                JwtUtil.parseJWT(refreshToken);
            } catch (ExpiredJwtException e) {
                log.info("refreshToken过期，重新构造token和refreshToken");
                String userStr = JSON.toJSONString(JwtUtil.getJwtUser(token));
                token = JwtUtil.createJWT(JwtUtil.getJwtUser(token).getId(), userStr, Long.parseLong(TTL) * 60000);
                refreshToken = JwtUtil.createJWT(JwtUtil.getJwtUser(token).getId(), userStr,
                        Long.parseLong(refreshTTL) * 60000);
                CookieUtil.addCookie(response, "token", token);
                CookieUtil.addCookie(response, "refreshToken", refreshToken);
                return true;
            }
        } catch (Exception e) {
            log.info("token过期, 需要重新登录");
            log.info(request.getRequestURI());
            response.sendRedirect(authAddress + "?redirect=" + request.getRequestURL());
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }
}

