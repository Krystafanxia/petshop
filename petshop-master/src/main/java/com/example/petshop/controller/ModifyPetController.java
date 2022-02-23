package com.example.petshop.controller;

import com.example.petshop.bean.*;
import com.example.petshop.service.FileService;
import com.example.petshop.service.PetService;
import com.example.petshop.service.ViewService;
import com.example.petshop.utils.Const;
import com.example.petshop.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ModifyPetController extends BaseController {

    @Autowired
    PetService petService;

    @Autowired
    ViewService viewService;

    @Autowired
    FileService fileService;

    /**
     *
     * @param pet
     * @return
     */
    @RequestMapping("/queryPets")
    public Result queryPets(@RequestBody PetBean pet){
        try {
            UserBean loginUser = getLoginUser();
            if (loginUser != null && loginUser.getRole() != Const.UserType.ADMIN.getKey()) {
                pet.setUserid(loginUser.getId());
            }
            List<PetBean> petList = petService.queryPets(pet);
            List<PetPicBean> petPicList=new ArrayList<>();
            for(PetBean petNew:petList) {
                PetPicBean petpic=new PetPicBean();
                BeanUtils.copyProperties(petNew,petpic);
                FileBean fileBean = new FileBean();
                fileBean.setId(petNew.getId());
                List<FileBean> files = fileService.findFile(fileBean);
                petpic.setFiles(files);
                petPicList.add(petpic);
            }
            return Result.success(petPicList,"success");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }

    }

    /**
     *
     * @param pet
     * @return
     */
    @RequestMapping("/addPet")
    @ResponseBody
    public Result addUser(@RequestBody PetBean pet){
        try {
            pet.setUserid(getLoginUser().getId());
            petService.addPet(pet);
            Map<String,Object> map = new HashMap<String,Object>();
            return Result.success(pet.getId(),"success");
        }catch (Exception e){
            e.printStackTrace();
           return Result.error("fail");
        }
    }


    /**
     *
     * @param pet
     * @return
     */
    @RequestMapping(value = "/dropPet")
    public Result dropPet(@RequestBody PetBean pet){
        try {
            int flag = petService.dropPet(pet);
            if(flag == 1){
                return Result.success(flag,"success");
            }else {
                return Result.error("fail");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }

    }


    /**
     *
     * @param pet
     * @return
     */
    @RequestMapping("/modifyPet")
    public Result modifyPet(@RequestBody PetBean pet){
        try {
            int flag = petService.modifyPet(pet);
            Map<String,Object> map = new HashMap<>();
            if(flag == 1){
                return Result.success(flag,"success");
            }else {
                return Result.error("fail");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }

    }
    @RequestMapping("/viewPet")
    public Result viewPet(@RequestBody ViewBean view){
        try {
            viewService.viewPet(view);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }
        return Result.success("success");
    }
    @RequestMapping("/getViewPets")
    public Result getViewPet(@RequestBody ViewBean view){
        try {
            List<ViewBean> views = viewService.getViews(String.valueOf(view.getUserid()));
            List<PetBean> pets=new ArrayList<>();
            for(ViewBean viewpet :views){
                PetBean pet=petService.findPetById(String.valueOf(viewpet.getPetid()));
                pets.add(pet);
            }
            return Result.success(pets,"success");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }
    }

}
