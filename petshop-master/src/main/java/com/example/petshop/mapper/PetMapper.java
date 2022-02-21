package com.example.petshop.mapper;

import com.example.petshop.bean.PetBean;

import java.util.List;

public interface PetMapper {

    //queryPets
    List<PetBean> queryPets(PetBean pet);

    //addPet
    int addPet(PetBean pet);

    //dropPet
    int dropPet(String pet);

    //modifyUser
    int modifyPet(PetBean pet);

    //findPetById
    PetBean findPetById(String id);

}
