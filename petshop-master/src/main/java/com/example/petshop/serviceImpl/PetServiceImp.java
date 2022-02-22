package com.example.petshop.serviceImpl;

import com.example.petshop.bean.PetBean;
import com.example.petshop.bean.PetPicBean;
import com.example.petshop.mapper.PetMapper;
import com.example.petshop.service.PetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PetServiceImp implements PetService {
    @Resource
    PetMapper petMapper;

    @Override
    public List<PetBean> queryPets(PetBean pet) {
        return petMapper.queryPets(pet);
    }


    @Override
    public int addPet(PetBean pet) {
        return petMapper.addPet(pet);
    }

    @Override
    public int dropPet(PetBean pet) {
        String id=String.valueOf(pet.getId());
        return petMapper.dropPet(id);
    }

    @Override
    public int modifyPet(PetBean pet) {
        return petMapper.modifyPet(pet);
    }

    @Override
    public PetBean findPetById(String id) {
        return petMapper.findPetById(id);
    }
}
