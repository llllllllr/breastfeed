package lllr.test.breast.controller;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.service.inter.FaceService;
import lllr.test.breast.util.exception.FaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.UUID;

@RequestMapping("/face")
@ResponseBody
@Controller
public class UserFaceController {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserFaceController.class);

    //存储人脸识别图片的位置
    @Value("${face.imageTempLocation}")
    private String imageTempLocation;

    @Autowired
    private FaceService faceService;

    //人脸注册
    @ResponseBody
    @RequestMapping("/register")
    public ServerResponse faceRegister(HttpServletRequest request, @RequestParam("file") @NotNull MultipartFile multipartFile) {

        File image = ChangeMultipartFileToFile(multipartFile);
        try {
            return faceService.FaceRegister(image);
        } catch (FaceException | IOException e) {
            LOGGER.error("=== faceRegisterController:" + e.getMessage() + "===");
            return ServerResponse.createByError();
        }
    }


    //人脸登录
    @ResponseBody
    @RequestMapping("/sign")
    public ServerResponse faceSign(HttpServletRequest request, @RequestParam("file") @NotNull  MultipartFile multipartFile) {

        File image = ChangeMultipartFileToFile(multipartFile);
        try {
            return faceService.FaceSign(image);
        } catch (FaceException e) {
            LOGGER.error("=== faceSignController:" + e.getMessage() + "===");
            return ServerResponse.createByErrorMsg("系统错误！");
        }
    }

    /*
    将 MultipartFile 转化为 File
     */
    private File ChangeMultipartFileToFile(@NotNull MultipartFile multipartFile) {
        LOGGER.info("===人脸注册图片名称：" + multipartFile.getOriginalFilename() + "  大小：" + multipartFile.getSize() + "=== ");

        //获得图片的名称
        String fileName = multipartFile.getOriginalFilename();
        //将图片作为文件储存起来
        File outFile = new File(imageTempLocation + UUID.randomUUID().toString() + fileName);
        try {
            multipartFile.transferTo(outFile);

        } catch (IOException e) {
            LOGGER.error("=== faceSignController:" + e.getMessage() + "===");
        }

        return outFile;
    }


}