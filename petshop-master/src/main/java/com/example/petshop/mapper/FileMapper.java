package com.example.petshop.mapper;

import com.example.petshop.bean.FileBean;

import java.util.List;

public interface FileMapper {

    FileBean getFile(FileBean file);

    int insertFile(FileBean file);

    int deleteFile(String fileid);

    List<FileBean> findFile(FileBean file);

    List<String> getPics(String id);
}
