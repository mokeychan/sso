package com.cfx.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: chenfeixiang
 * @Date: Created in 14:35 2018/9/28
 */
@Slf4j
public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false); // 设置是否只能通过https来传递此条cookie,默认是false
        cookie.setHttpOnly(true);// 防御XSS攻击
        cookie.setMaxAge(-1);// 规定cookie多长时间之后过期.负值（默认值）表明cookie仅仅用于当前浏览会话,并不存储到磁盘上.
        cookie.setDomain("localhost");// 可以访问此cookie的域名
        cookie.setPath("/");// 可在同一应用服务器内共享cookie
        response.addCookie(cookie);
    }


    public static String getCookie(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    public static void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
