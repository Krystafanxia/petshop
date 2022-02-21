package com.example.petshop.service;

import com.example.petshop.bean.AccountBean;


public interface AccountService {
    //getMyAccount
    AccountBean getMyAccount(String id);

    int payPorPet(AccountBean account);
}
