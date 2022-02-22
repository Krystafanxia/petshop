package com.example.petshop.service;

import com.example.petshop.bean.FileBean;

import java.util.List;

public interface FileService {

    FileBean getFile(FileBean file);

    int insertFile(FileBean file);

    List<FileBean> findFile(FileBean file);

}
