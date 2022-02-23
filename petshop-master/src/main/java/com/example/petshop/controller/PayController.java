package com.example.petshop.controller;

import com.example.petshop.bean.AccountBean;
import com.example.petshop.bean.PetBean;
import com.example.petshop.bean.PetPicBean;
import com.example.petshop.service.AccountService;
import com.example.petshop.service.PetService;
import com.example.petshop.utils.Result;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PayController extends BaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    PetService petService;


    /**
     *
     * @return
     */
    @RequestMapping("/getMyAccount")
    public Result getMyAccount(){
        try {
            AccountBean account = accountService.getMyAccount(getLoginUser().getId() + "");
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
    public Result payForPet(@RequestBody ModelMap modelMap) {
        try {
            List<PetBean> payList = new ArrayList<PetBean>();
            ((List) modelMap.get("payList")).forEach(item -> {
                PetBean bean = new PetBean();
                try { BeanUtils.copyProperties(item, bean); } catch (Exception e) {}
                payList.add(bean);
            });
            double money = payList.stream().mapToDouble(PetBean::getPrice).reduce((x, y) -> x += y).getAsDouble();
            // double money =Double.parseDouble(modelMap.get("price").toString());
            AccountBean account= accountService.getMyAccount(getLoginUser().getId() + "");
            double banlance=account.getMoney();
            if(banlance >= money) {
                // String petId=modelMap.get("petId").toString();
                money = banlance - money;
                account.setMoney(money);
                int flag = accountService.payPorPet(account);
                payList.forEach(item -> {
                    PetBean pet = new PetBean();
                    pet.setId(item.getId());
                    pet.setStatus("01");
                    petService.modifyPet(pet);
                });
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
