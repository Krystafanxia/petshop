package com.example.petshop.serviceImpl;

import com.example.petshop.bean.FriendBean;
import com.example.petshop.mapper.FriendMapper;
import com.example.petshop.service.FriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class FriendServiceImp implements FriendService {

    @Resource
    FriendMapper friendMapper;

    @Override
    public int addFriend(FriendBean friend) {
        String id= UUID.randomUUID().toString().replace("-","");
        friend.setId(id);
        return friendMapper.addFriend(friend);
    }

    @Override
    public int delFriend(FriendBean friend) {
        return friendMapper.delFriend(friend);
    }

    @Override
    public List<FriendBean> getFriends(String userid) {
        return friendMapper.getFriends(userid);
    }

    @Override
    public FriendBean getFriend(FriendBean friend) {
        return friendMapper.getFriend(friend);
    }
}
