package com.example.petshop.serviceImpl;

import com.example.petshop.bean.ViewBean;
import com.example.petshop.mapper.ViewMapper;
import com.example.petshop.service.ViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class ViewServiceImp implements ViewService {
    @Resource
    ViewMapper viewMapper;

    @Override
    public int viewPet(ViewBean view) {
        List<ViewBean> views=viewMapper.findViewsByWhere(view);
        if(views==null||views.size()<1){
            String id= UUID.randomUUID().toString().replace("-","");
            view.setViewid(id);
            view.setViews(1);
            return viewMapper.addViews(view);
        }else
            return viewMapper.viewPet(view);
    }

    @Override
    public List<ViewBean> getViews(String userid) {
        return viewMapper.getViews(userid);
    }
}
