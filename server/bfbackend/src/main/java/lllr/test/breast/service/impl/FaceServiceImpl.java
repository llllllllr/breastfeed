package lllr.test.breast.service.impl;

import lllr.test.breast.service.inter.FaceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FaceServiceImpl implements FaceService {

    /*
    1.前提：face++ 创建了 脸集
    2.用 Detect 方法 分析用户face_token并且查询用户是否注册过 是-》 抛出异常 返回错误消息
    3. 调用 addFace 将用户 人脸识别的 face_token 添加到脸集
    4.返回成功消息
     */
    @Override
    public void FaceSign(File image) {

    }

    /*
    1.用 Search 方法 查询 脸集中是否有这个 人
    2.验证成功 进入主页面
    3.验证失败，重新登录
     */
    @Override
    public void FaceRegister(File image) {

    }
}
