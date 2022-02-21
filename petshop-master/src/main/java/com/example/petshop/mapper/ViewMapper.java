package com.example.petshop.mapper;

import com.example.petshop.bean.ViewBean;

import java.util.List;

public interface ViewMapper {

    int viewPet(ViewBean view);

    List<ViewBean> getViews(String userid);

    List<ViewBean> findViewsByWhere(ViewBean view);

    int addViews(ViewBean view);
}
