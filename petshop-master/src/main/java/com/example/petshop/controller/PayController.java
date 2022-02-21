package com.example.petshop.controller;

import com.example.petshop.bean.AccountBean;
import com.example.petshop.bean.PetBean;
import com.example.petshop.service.AccountService;
import com.example.petshop.service.PetService;
import com.example.petshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController extends BaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    PetService petService;


    /**
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/getMyAccount")
    public Result getMyAccount(@RequestBody ModelMap modelMap){
        try {
            String userId=modelMap.get("userId").toString();
            AccountBean account = accountService.getMyAccount(userId);
            return Result.success(account,"success");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }

    }

    /**
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/payForPet")
    public Result payForPet(@RequestBody ModelMap modelMap){
        try {
            String userId=modelMap.get("userId").toString();
            double money =Double.parseDouble(modelMap.get("price").toString());
            String petId=modelMap.get("petId").toString();
            AccountBean account= accountService.getMyAccount(userId);
            double banlance=account.getMoney();
            if(banlance>=money) {
                money = banlance - money;
                account.setMoney(money);
                int flag = accountService.payPorPet(account);
                PetBean pet=petService.findPetById(petId);
                pet.setStatus("01");
                petService.modifyPet(pet);
                return Result.success(account, "交易成功");
            }else{
                return Result.error("余额不足");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("fail");
        }
    }

}
