package com.example.petshop.service;

import com.example.petshop.bean.ViewBean;

import java.util.List;

public interface ViewService {

    int viewPet(ViewBean view);

    List<ViewBean> getViews(String userid);

}
