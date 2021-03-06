package com.example.petshop.controller;

import com.example.petshop.bean.FileBean;
import com.example.petshop.service.FileService;
import com.example.petshop.utils.Result;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController extends HttpServlet {

    public static String FILEPATH="uploads";
    @Autowired
    FileService fileService;

    /**
     *
     * @param file
     * @param id
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, String id,String type){
        if(file.isEmpty()){
            return Result.error("The file is null");
        }
        try{
            String fileName= UUID.randomUUID() + file.getOriginalFilename();
            FileBean attachment=new FileBean();
            attachment.setId(Integer.parseInt(id));
            attachment.setFilename(fileName);
            attachment.setFiletype(type);
            attachment.setContent(file.getBytes());
            fileService.insertFile(attachment);
            return Result.success(fileName,"success");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("upload fail");
        }
    }

    @RequestMapping("/download")
    public Result download (HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        String fileName=request.getParameter("fileName");
        FileBean fileBean = new FileBean();
        fileBean.setFilename(fileName);
        FileBean file = fileService.getFile(fileBean);
        String type=request.getServletContext().getMimeType(fileName);
        response.setHeader("Content-type",type);
        response.setHeader("Content-Disposition","attachment;filename="+(URLEncoder.encode(fileName,"utf-8")));
        if (file != null) {
            response.getOutputStream().write(file.getContent());
        }
        return Result.success(fileName,"success");
    }

    @RequestMapping("/getPetFiles")
    public Result getPetFile(@RequestParam String id,String type){
        try{
            List<FileBean> files=new ArrayList<>();
            FileBean file=new FileBean();
            file.setId(Integer.parseInt(id));
            file.setFiletype(type);
            files=fileService.findFile(file);
            return Result.success(files,"success");
    }catch (Exception e){
        e.printStackTrace();
        return Result.error("file");
        }
    }

    @RequestMapping("/delPetFile")
    public Result getPetFile(@RequestParam String fileid){
        try{
            int flag=fileService.deleteFile(fileid);
            return Result.success("success");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("file");
        }
    }

    String getFilePath() {
        ApplicationHome ah = new ApplicationHome(FileController.class);
        String classPath;
        try {
            classPath = ah.getSource().getParentFile().toString();
        } catch (Exception e) {
            classPath = ah.getDir().getAbsolutePath();
        }
        // String classPath = "/";
        // try {
        //     classPath = ResourceUtils.getURL("classpath:").getPath();
        // } catch (FileNotFoundException e) {
        // }

        // classPath = "D:\\";
        classPath += File.separator + "static" + File.separator + "static" + File.separator + FILEPATH;
        File file = new File(classPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        System.out.println("------path" + classPath);
        return classPath;
    }
}
