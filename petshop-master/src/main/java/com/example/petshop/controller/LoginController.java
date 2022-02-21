package com.example.petshop.controller;

import com.example.petshop.bean.UserBean;
import com.example.petshop.service.UserService;
import com.example.petshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.petshop.utils.Const.LOGIN_KEY;

@RestController
public class LoginController extends BaseController {

    //将Service注入Web层
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(HttpServletRequest request, UserBean user){
        UserBean userBean = userService.loginIn(user.getName(),user.getPassword());
        if(userBean!=null){
            request.getSession().setAttribute(LOGIN_KEY, userBean);
            return Result.success(userBean);
        }else {
            return Result.error("用户名或密码错误！");
        }
    }



}
