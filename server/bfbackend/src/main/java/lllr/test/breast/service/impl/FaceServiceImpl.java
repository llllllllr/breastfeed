package lllr.test.breast.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.service.inter.FaceService;
import lllr.test.breast.util.exception.FaceException;
import lllr.test.breast.util.exception.ImageException;
import lllr.test.breast.util.face.FaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class FaceServiceImpl implements FaceService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(FaceServiceImpl.class);


    @Autowired
    private FaceUtil faceUtil;

    //调用 SearchFace 返回相似人脸的数量
    @Value("${face.RETURN_RESULT_COUNT}")
    private String RETURN_RESULT_COUNT;

    /*
    与 face++ api 交互

    判断返回的数据中  是否包含错误消息，有则抛出异常
                    是否为空
     */
    private void IsErrorMessage(Map responseData) throws FaceException {
        //如果包含错误消息  抛出错误信息
        if(responseData == null || responseData.containsKey("error_message")) {
            LOGGER.debug("=== IsErrorMessage: " + "返回数据为空 或者 error_message" + responseData.get("error_message") + "===");
            throw new FaceException(responseData == null ? "face++ 返回数据为空!" : ("face++ error_message:" + responseData.get("error_message")));

        }
    }

    /*
    调用 SearchFace 方法 返回的数据
    判断 该人 是否注册过
    */
    private boolean isReadyRegister(Map<String,Object> searchResponseData) throws FaceException {
        LOGGER.debug("=== SearchFace： " + searchResponseData.toString() + "===");

        //如果包含错误消息  抛出错误信息
        IsErrorMessage(searchResponseData);

        /*
        返回字段:results  类型：Array
        搜索结果对象数组
        注：如果传入图片但图片中未检测到人脸，则无法进行人脸搜索，本字段不返回。

        默认用该数组第一个数据的 confidence值 判断 是否是同一个人
         */
        JSONArray results = (JSONArray) searchResponseData.get("results");
        if(results == null || results.size() == 0)
            return false;

        Float confidence = results.getJSONObject(0).getFloat("confidence");
        Float similar_score = ((JSONObject) searchResponseData.get("thresholds")).getFloat("1e-5");

        //脸集中已经有该人的数据  说明已经注册过
        if(similar_score.compareTo(confidence) == -1){
            LOGGER.debug("=== 该用户在系统注册过" + "===");
            return true;
        }

        return false;
    }


    /////////////////////////////////////////////////////////////////////////////////

    /*
    1.用 Search 方法 查询 脸集中是否有这个 人
    2.验证成功 进入主页面
    3.验证失败，重新登录
    返回值 表示 查询的状态 1.登录成功 2. 用户还未登录
     */
    @Override
    public ServerResponse FaceSign(File image) throws FaceException {
        Map<String,Object> searchResponseData = null;

        try {
            searchResponseData = faceUtil.SearchFace(image);
        } catch (ImageException | IOException e) {
            LOGGER.error("=== FaceSign: " + e.getMessage() + "===");
            throw  new FaceException(e.getMessage());
        }

        if(isReadyRegister(searchResponseData))
        {
            LOGGER.debug("=== FaceSign: 人脸验证成功！");
            return ServerResponse.createBysuccessMsg("人脸验证成功！");
        }
        else{
            LOGGER.debug("=== FaceSign: 人脸验证失败：该用户未注册！");
            return ServerResponse.createByErrorMsg("人脸验证失败：该用户未注册！");
        }


    }


    /*
    1.前提：face++ 创建了 脸集
    2.用 Detect 方法 分析用户face_token并且查询用户是否注册过 是-》 抛出异常 返回错误消息
    3. 调用 addFace 将用户 人脸识别的 face_token 添加到脸集
    4.返回成功消息
   */
    @Override
    public ServerResponse FaceRegister(File image) throws FaceException{
        //上传图片返回 人物的 face_token
        Map<String,Object> detectResponseData = null;
        try {
            detectResponseData = faceUtil.DetectFace(image);
            LOGGER.debug("=== DetectFace： " + detectResponseData.toString() + "===");
        } catch (ImageException | IOException e) {
            LOGGER.debug("=== DetectFace: " + e.getMessage() + "===");
            throw new FaceException(e.getMessage());
        }

        //如果包含错误消息  抛出错误信息
        IsErrorMessage(detectResponseData);

        JSONArray faces = (JSONArray) detectResponseData.get("faces");        //获得图片中人物的识别信息

        /*
        因为 人脸识别拍摄得到的图片中可能存在多个人物，这样要求只能有一个人
        默认 faces 数组 取 第一个进行处理
        如果 检测不到人或者有多个人则不做处理 ，直接返回
         */
        if(faces != null && faces.size() ==1) {

            JSONObject face = ((JSONArray) detectResponseData.get("faces")).getJSONObject(0);
            String face_token = (String) face.get("face_token");

            //查询 该人是否已经注册过
            Map<String, Object> searchResponseData = null;
            try {
                searchResponseData = faceUtil.SearchFace(face_token);
            } catch (IOException e) {
                LOGGER.debug("=== DetectFace: " + e.getMessage() + "===");
                throw new FaceException(e.getMessage());
            }

            LOGGER.debug("=== SearchFace： " + searchResponseData.toString() + "===");

            //成功添加的人脸集合
            int face_added = 0;
            //脸集中已经有该人的数据  说明已经注册过
            if (isReadyRegister(searchResponseData)) {
                LOGGER.debug("=== 已注册过，无效注册！" + "===");
                return ServerResponse.createByErrorMsg("已注册过，无效注册！");
            } else {
                //该人没有注册过账号，将人脸的数据放入脸集
                Map<String, Object> addFaceResponseData = null;
                try {
                    addFaceResponseData = faceUtil.AddFace(face_token);
                } catch (IOException e) {
                    LOGGER.debug("=== DetectFace: " + e.getMessage() + "===");
                    throw new FaceException(e.getMessage());
                }

                LOGGER.debug("=== AddFace： " + addFaceResponseData.toString() + "===");

                //检查数据
                IsErrorMessage(addFaceResponseData);
                //获取成功添加的人脸个数
                face_added = (int) addFaceResponseData.get("face_added");

            }
            //添加成功
            if (face_added > 0) {
                LOGGER.debug("=== 人脸识别注册成功！ face_token： " + face_token + "===");
                return ServerResponse.createBysuccessMsg("人脸识别注册成功！");
            }

            LOGGER.debug("=== 人脸识别注册失败！ face_token： " + face_token + "===");
        }
        //添加失败
        return ServerResponse.createByErrorMsg("人脸识别注册失败！");
    }
}
