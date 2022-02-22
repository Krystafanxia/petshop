package com.example.petshop.service;

import com.example.petshop.bean.PetBean;
import com.example.petshop.bean.PetPicBean;

import java.util.List;

public interface PetService {

    //queryPets
    List<PetBean> queryPets(PetBean pet);


    /**
     *
     * @param pet
     * @return
     */
    int addPet(PetBean pet);

    /**
     *
     * @param id
     * @return
     */
    int dropPet(PetBean pet);

    /**
     *
     * @param pet
     * @return
     */
    int modifyPet(PetBean pet);

    /**
     *
     * @param id
     * @return
     */
    PetBean findPetById(String id);
}
