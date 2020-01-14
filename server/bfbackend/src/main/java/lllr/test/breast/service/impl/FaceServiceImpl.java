package lllr.test.breast.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.service.inter.FaceService;
import lllr.test.breast.util.exception.FaceRegisterException;
import lllr.test.breast.util.exception.ImageException;
import lllr.test.breast.util.face.FaceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class FaceServiceImpl implements FaceService {

    @Autowired
    private FaceUtil faceUtil;



    /*
    1.用 Search 方法 查询 脸集中是否有这个 人
    2.验证成功 进入主页面
    3.验证失败，重新登录
     */
    @Override
    public void FaceSign(File image) throws ImageException {

    }

    /*
    1.前提：face++ 创建了 脸集
    2.用 Detect 方法 分析用户face_token并且查询用户是否注册过 是-》 抛出异常 返回错误消息
    3. 调用 addFace 将用户 人脸识别的 face_token 添加到脸集
    4.返回成功消息
   */
    @Override
    public void FaceRegister(File image) throws FaceRegisterException {
        //上传图片返回 人物的 face_token
        Map<String,Object> responseData = null;
        try {
            responseData = faceUtil.DetectFace(image);
        } catch (ImageException e) {
            e.printStackTrace();
            throw new FaceRegisterException(e.getMessage());
        }

        //如果包含错误消息  抛出错误信息
        if(responseData.containsKey("error_message"))
            throw new FaceRegisterException("face++ error_message:" + responseData.get("error_message"));

        JSONArray faces = (JSONArray) responseData.get("faces");        //获得图片中人物的识别信息

        /*
        因为 人脸识别拍摄得到的图片中可能存在多个人物，这样要求只能有一个人
        默认 faces 数组 取 第一个进行处理
        如果 检测不到人或者有多个人则不做处理 ，直接返回
         */
        if(faces == null || faces.size() > 1)
            return;

        JSONObject face = ((JSONArray)responseData.get("faces")).getJSONObject(0);
        String face_token = (String) face.get("face_token");

    }
}
