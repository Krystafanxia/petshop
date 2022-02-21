package com.example.petshop.controller;

import com.example.petshop.bean.FileBean;
import com.example.petshop.service.FileService;
import com.example.petshop.utils.Result;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class FileController extends HttpServlet {

    public static String FILEPATH="D:\\file";
    @Autowired
    FileService fileService;

    /**
     *
     * @param file
     * @param id
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, String id){
        if(file.isEmpty()){
            return Result.error("The file is null");
        }
        try{
            String fileName= file.getOriginalFilename();
            String destFileName=FILEPATH+File.separator+fileName;
            File destFile=new File(destFileName);
            file.transferTo(destFile);
            //insert vedio into database
            FileBean vedio=new FileBean();
            vedio.setId(Integer.parseInt(id));
            vedio.setFilename(fileName);
            fileService.insertFile(vedio);
            return Result.success(destFileName,"success");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("upload fail");
        }
    }

    @RequestMapping("/download")
    public Result download (HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        String fileName=request.getParameter("fileName");
        String path=FILEPATH+File.separator+fileName;
        File file=new File(path);
        FileInputStream in=new FileInputStream(file);
        String type=request.getServletContext().getMimeType(fileName);
        response.setHeader("Content-type",type);
        response.setHeader("Content-Disposition","attachment;filename="+(URLEncoder.encode(fileName,"utf-8")));
        ServletOutputStream out=response.getOutputStream();
        IOUtils.copy(in,out);
        if(in!=null){
            in.close();
        }
        return Result.success(fileName,"success");
    }

    @RequestMapping("/getPetFile")
    public Result getPetFile(@RequestParam String id){
        try{
        FileBean file=new FileBean();
        file.setId(Integer.parseInt(id));
        file=fileService.getFile(file);
        return Result.success(file,"success");
    }catch (Exception e){
        e.printStackTrace();
        return Result.error("file");
        }
    }
}
