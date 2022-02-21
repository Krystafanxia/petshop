package com.example.petshop.service;

import com.example.petshop.bean.FileBean;

import java.io.File;

public interface FileService {

    FileBean getFile(FileBean file);

    int insertFile(FileBean file);

}
