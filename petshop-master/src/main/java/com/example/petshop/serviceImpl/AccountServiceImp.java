package com.example.petshop.serviceImpl;

import com.example.petshop.bean.AccountBean;
import com.example.petshop.mapper.AccountMapper;
import com.example.petshop.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImp implements AccountService {

    //将DAO注入Service层
    @Resource
    AccountMapper accountMapper;

    @Override
    public AccountBean getMyAccount(String id) {
        return accountMapper.getMyAccount(id);
    }

    @Override
    public int payPorPet(AccountBean account) {
        return accountMapper.payForPet(account);
    }
}
