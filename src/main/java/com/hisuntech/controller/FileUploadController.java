package com.hisuntech.controller;


import com.hisuntech.utils.ReadExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
public class FileUploadController {

    @RequestMapping("/ajax/upload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
        if (file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int)file.getSize();
        System.out.println(fileName + "-->" +size);

        String path = "F:/readExcel/  test";
        File dest = new File(path + "/" +fileName);
        if (!dest.getParentFile().exists()){    //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try{
            file.transferTo(dest);      //保存文件
            ReadExcelUtil.readExcel(dest);  //转化SQL
            return "true";
        }catch (IllegalStateException  e){
            e.printStackTrace();
            return "false";
        }catch (IOException e){
            e.printStackTrace();
            return "false";
        }
    }
}
