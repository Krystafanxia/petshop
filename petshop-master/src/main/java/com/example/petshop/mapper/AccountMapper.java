package com.example.petshop.mapper;

import com.example.petshop.bean.AccountBean;

public interface AccountMapper {

    AccountBean getMyAccount(String id);

    int payForPet(AccountBean account);

}
