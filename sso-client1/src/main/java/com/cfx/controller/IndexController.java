package com.cfx.controller;

import com.cfx.entity.User;
import com.cfx.util.CookieUtil;
import com.cfx.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: chenfeixiang
 * @Date: Created in 15:21 2018/9/28
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        User user = JwtUtil.getJwtUser(CookieUtil.getCookie(request, "token"));
        return "欢迎登录Taobao:" + user.getName();
    }
}
