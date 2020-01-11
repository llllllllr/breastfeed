package lllr.test.breast.controller;

import lllr.test.breast.service.Inte.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@Controller
public class UserFaceController {

    //存储人脸识别图片的位置
    @Value("${face.imageTempLocation}")
    private String imageTempLocation;

    @Autowired
    private FaceService faceService;

    //人脸注册
    @ResponseBody
    @RequestMapping("/face")
    public String faceRegister(HttpServletRequest request, @RequestParam("file") MultipartFile file){

        if(file != null) {

            System.out.println(file.getOriginalFilename() + file.getSize());

            //获得图片的名称
            String fileName = file.getOriginalFilename();
            //将图片作为文件储存起来
            File outFile = new File(imageTempLocation + UUID.randomUUID().toString() + fileName);
            try {
                file.transferTo(outFile);

                //调用 事务层 方法
                faceService.FaceRegister(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "receive";
    }

}