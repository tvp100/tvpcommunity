package com.tvp100.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.tvp100.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by tvp100 on 2020/4/4.
 */
@Controller
public class FileController {

    @ResponseBody
    @RequestMapping(value = "/file/upload")
    public FileDTO upload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {

        FileDTO fileDTO = new FileDTO();
        fileDTO.setMessage("上传成功");
        fileDTO.setSuccess(1);
        try {
            request.setCharacterEncoding("utf-8");
            /*这里返回格式是html/txt才能上传*/
            response.setHeader("Content-Type", "application/json");
            Calendar date = Calendar.getInstance();
            File dateFile = new File(date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + (date.get(Calendar.DATE)));
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID()+fileName;
            File pathpp = new File("src/main/resources/uploadFiles/article");
            File newFile = new File(pathpp.getAbsolutePath()+File.separator+dateFile+File.separator+fileName);
            System.out.println(newFile.getParentFile());
            /*文件不存在就创建*/
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            file.transferTo(newFile);
            fileDTO.setUrl("/uploadFiles/article/"+date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + (date.get(Calendar.DATE))+"/"+fileName);
        } catch (Exception e) {
            fileDTO.setSuccess(0);
            fileDTO.setMessage("文件太大啦");
            return fileDTO;
        }
        return fileDTO;
    }
}
