package com.example.petshop.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.petshop.utils.Result;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.petshop.utils.Const.LOGIN_KEY;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name()) || request.getSession().getAttribute(LOGIN_KEY) != null) {
            return true;
        } else {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(401);
            response.getWriter().write(JSON.toJSONString(Result.error("请登录！")));
            return false;
        }
    }
}
