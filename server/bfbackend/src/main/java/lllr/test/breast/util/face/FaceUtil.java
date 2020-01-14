package lllr.test.breast.util.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.x.protobuf.Mysqlx;
import lllr.test.breast.util.exception.ImageException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Map;

//和 face++ 平台交互的 api
@Service
public final class FaceUtil {

    //face API 密匙
    @Value("${face.API_KEY}")
    private String FACE_API_KEY;
    @Value("${face.API_SECRET}")
    private String FACE_API_SECRET;

    //face++ 接收的图片类型
    private final String[]  IMAGE_TYPE = {"JPG","JPEG","PNG"};

    //face++ 可接受图片最大大小  2MB
    private final int IMAGE_MAX_SIZE = 1024 * 1024 * 2;

    /*
    face++ 要求上传的图片为  jpg（jpeg），png
    判别图片的类型是否为 jpg（jpeg），png，不是抛出异常
     */
    private void isCorrectType(@NotNull File imageFile) throws ImageException {
        String fileName = imageFile.getName();    //文件的名字
        int index = fileName.lastIndexOf(".");   //计算从后面开始 . 的位置
        String fileType = fileName.substring(index + 1);  //截取图片的类型

        if(fileType.equalsIgnoreCase(IMAGE_TYPE[0])||fileType.equalsIgnoreCase(IMAGE_TYPE[1])||fileType.equalsIgnoreCase(IMAGE_TYPE[2]))
            return ;
        throw new ImageException("图片的格式不正确！");

    }

    /*
    face++ 要求 上传图片的大小 不大于2MB
     */
    private void isBeyondMaxSize(@NotNull File imageFile) throws ImageException {
        long ImageLength = imageFile.length();
        if(ImageLength > 0 && ImageLength < IMAGE_MAX_SIZE)
            return;
        throw new ImageException("图片大小超出" + IMAGE_MAX_SIZE);
    }

    //检查图片的 类型 和 大小 是否合适
    private void isCorrectImage(@NotNull File imageFile) throws ImageException {
        isBeyondMaxSize(imageFile);
        isCorrectType(imageFile);
    }

    /*
    传入图片进行人脸检测和人脸分析。可以检测图片内的所有人脸，对于每个检测出的人脸，会给出其唯一标识 face_token，

    imageFile -> 人脸识别上传的图片
     */
    public Map<String,Object> DetectFace(@NotNull File imageFile) throws ImageException {
        //检查图片的类型
        isCorrectImage(imageFile);

        //1.创建对应的MediaType
        final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPG, imageFile);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file",imageFile.getName() , fileBody)
                .addFormDataPart("api_key",FACE_API_KEY)
                .addFormDataPart("api_secret",FACE_API_SECRET)
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/detect")
                .post(requestBody)
                .build();

        //5.发送请求
        //  *** 异常没有处理
        Response response = null;
        OkHttpClient okHttpClient = null;
        Map<String,Object> dataMap = null;
        try {
            okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();

            //返回调用 face++ 查询得到的参数
            dataMap = JSON.parseObject(response.body().string(),Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }

    /*
    为一个已经创建的 FaceSet 添加人脸标识 face_token。一个 FaceSet 最多存储1,000个 face_token。
     */

    public Map<String,Object> AddFace(@NotNull String face_token){
        //设置请求参数   表单格式
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id","test")
                .add("face_tokens",face_token)
                .build();

        Request request = new Request.Builder()
                    .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/addface")
                    .post(requestBody)
                    .build();

        Map<String,Object> dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            //将请求得到的 参数字符串 放入 Map中
                dataMap = JSON.parseObject(response.body().string());

        }catch (IOException e){
                e.printStackTrace();
        }

        //返回 请求 得到的 参数
            return dataMap;

        }



    /*

     */
    public Map<String,Object> CreateFaceSet(@NotNull String face_token){
        //设置请求参数   表单格式
        RequestBody requestBody = new FormBody.Builder()
                .add("api_key",FACE_API_KEY)
                .add("api_secret",FACE_API_SECRET)
                .add("outer_id","test")
                .add("face_tokens",face_token)
                .build();

        Request request = new Request.Builder()
                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/addface")
                .post(requestBody)
                .build();

        Map<String,Object> dataMap = null;
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            //将请求得到的 参数字符串 放入 Map中
            dataMap = JSON.parseObject(response.body().string());

        }catch (IOException e){
            e.printStackTrace();
        }

        //返回 请求 得到的 参数
        return dataMap;

    }

}
