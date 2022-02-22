package com.example.petshop.controller;

import com.example.petshop.bean.FriendBean;
import com.example.petshop.bean.UserBean;
import com.example.petshop.service.FriendService;
import com.example.petshop.service.UserService;
import com.example.petshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ModifyUserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    /**
     * 查询所有用户信息
     * @param modelMap
     * @return
     */
    @RequestMapping("/users")
    public String showUsers(ModelMap modelMap){
        List<UserBean> userList = userService.queryAllUser();
        modelMap.addAttribute("userList",userList);
        return "users";
    }

    /**
     * 新增用户
     * remark：这里未做用户名是否重复校验
     * @param userBean
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map addUser(UserBean userBean){
        int flag = userService.addUser(userBean);
        Map<String,Object> map = new HashMap<String,Object>();
        if(flag == 1){
            map.put("msg","新增用户成功");
            return map;
        }else {
            map.put("msg","新增用户失败");
            return map;
        }
    }


    /**
     * 根据用户ID删除用户信息
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/deleteUser+{id}")
    public String dropUser(@PathVariable("id") String id,ModelMap modelMap){
        int flag = userService.dropUser(id);
        List<UserBean> userList = userService.queryAllUser();
        modelMap.addAttribute("userList",userList);
        if(flag == 1){
            return "users";
        }else {
            return "error";
        }
    }

    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    @RequestMapping("/user+{id}")
    public String queryUser(@PathVariable("id") String id,ModelMap modelMap){
        UserBean userBean = userService.queryUserById(id);
        modelMap.addAttribute("user",userBean);
        return "userInfo";
    }

    /**
     * 根据用户ID修改用户信息
     * remark：这里未做用户名是否重复校验
     * @param userBean
     * @return
     */
    @RequestMapping("/modifyUser")
    @ResponseBody
    public Map modifyUser(UserBean userBean){
        int flag = userService.modifyUser(userBean);
        Map<String,Object> map = new HashMap<>();
        if(flag == 1){
            map.put("msg","修改用户信息成功");
            return map;
        }else {
            map.put("msg","修改用户信息失败");
            return map;
        }
    }

    /**
     *
     * @param friend
     * @return
     */
    @RequestMapping("/addFriend")
    public Result addFriend(@RequestBody FriendBean friend) {
        try {
            FriendBean friendNew = friendService.getFriend(friend);
            if (friendNew == null) {
                int flag = friendService.addFriend(friend);
                if (flag == 1) {
                    return Result.success("success");
                } else {
                    return Result.error("fail");
                }
            } else {
               return Result.error("Have been friends");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("fail");
        }
    }

    /**
     *
     * @param friend
     * @return
     */
    @RequestMapping("/delFriend")
    public Result delFriend(@RequestBody FriendBean friend) {
        try {
            friend = friendService.getFriend(friend);
            int flag = friendService.delFriend(friend);
            if (flag == 1) {
                return Result.success("success");
            } else {
                return Result.error("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("fail");
        }
    }

    /**
     *
     * @param userid
     * @return
     */
    @RequestMapping("/getFriends")
    public Result getFriends(@RequestParam String userid) {
        try {
            List<FriendBean> fridens= friendService.getFriends(userid);
            List<UserBean> users=new ArrayList<>();
            for(FriendBean friend:fridens){
                UserBean user=userService.queryUserById(String.valueOf(friend.getFriendid()));
                users.add(user);
            }
            return Result.success(users,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("fail");
        }
    }


}
