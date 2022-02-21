package com.example.petshop.service;

import com.example.petshop.bean.FriendBean;

import java.util.List;

public interface FriendService {

    int addFriend(FriendBean friend);

    int delFriend(FriendBean friend);

    List<FriendBean> getFriends(String userid);

    FriendBean getFriend(FriendBean friend);

}
