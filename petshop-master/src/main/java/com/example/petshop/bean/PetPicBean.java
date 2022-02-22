package com.example.petshop.bean;

import java.util.List;

public class PetPicBean extends PetBean{
    private List<String> pics;

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
