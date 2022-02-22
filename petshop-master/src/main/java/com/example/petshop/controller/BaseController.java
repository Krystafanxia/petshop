package com.example.petshop.controller;

import com.example.petshop.bean.UserBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;

import static com.example.petshop.utils.Const.LOGIN_KEY;

public class BaseController extends HttpServlet {
    UserBean getLoginUser() {
        try{
            return (UserBean) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(LOGIN_KEY);
        }catch (Exception e) {
            return null;
        }
    }
}
