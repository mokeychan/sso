package com.cfx.controller;

import com.alibaba.fastjson.JSON;
import com.cfx.entity.User;
import com.cfx.util.CookieUtil;
import com.cfx.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: chenfeixiang
 * @Date: Created in 13:55 2018/9/28
 */
@Controller
@Slf4j
public class LoginController {

    @Value(value = "${TTL}")
    private String TTL;

    @Value(value = "${refreshTTL}")
    private String refreshTTL;

    private static String token;
    private static String refreshToken;

    @PostMapping("/login")
    public void login(HttpServletResponse response, String password) throws Exception {
        if ("123456".equals(password)) {
            User user = new User();
            user.setId("1");
            user.setName("mokeychan");

            String userStr = JSON.toJSONString(user);
            token = JwtUtil.createJWT(user.getId(), userStr, Long.parseLong(TTL) * 60000);
            refreshToken = JwtUtil.createJWT(user.getId(), userStr, Long.parseLong(refreshTTL) * 60000);
            log.info("token is :" + token);
            log.info("refreshToken is :" + refreshToken);

            // 也可以将认证信息存入Header之中

            CookieUtil.addCookie(response, "token", token);
            CookieUtil.addCookie(response, "refreshToken", refreshToken);

            response.getWriter().print("ok");
        } else {
            response.getWriter().print("error");
        }
    }
}
