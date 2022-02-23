package com.example.petshop.bean;

import java.util.List;

public class PetPicBean extends PetBean{
    private List<FileBean> files;

    public List<FileBean> getFiles() {
        return files;
    }

    public void setFiles(List<FileBean> pics) {
        this.files = pics;
    }
}
