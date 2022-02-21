package com.example.petshop.serviceImpl;

import com.example.petshop.bean.FileBean;
import com.example.petshop.mapper.FileMapper;
import com.example.petshop.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    @Resource
    FileMapper fileMapper;

    /**
     *
     * @param file
     * @return
     */
    @Override
    public FileBean getFile(FileBean file) {
        return fileMapper.getFile(file);
    }

    /**
     *
     * @param file
     * @return
     */
    @Override
    public int insertFile(FileBean file) {
        String id= UUID.randomUUID().toString().replace("-","");
        FileBean oldFile=fileMapper.getFile(file);
        if(oldFile!=null){
            fileMapper.deleteFile(oldFile);
        }
        file.setFileid(id);
        return fileMapper.insertFile(file);
    }

}
