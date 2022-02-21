package com.example.petshop.mapper;

import com.example.petshop.bean.FriendBean;

import java.util.List;

public interface FriendMapper {

    List<FriendBean> getFriends(String userid);

    int addFriend(FriendBean friend);

    int delFriend(FriendBean friend);

    FriendBean getFriend(FriendBean friend);
}
