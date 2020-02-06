package lllr.test.breast.util.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lllr.test.breast.util.exception.ImageException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//和 face++ 平台交互的 api
@Service
public final class FaceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceUtil.class);

    //face API 密匙
    @Value("${face.API_KEY}")
    private String FACE_API_KEY;
    @Value("${face.API_SECRET}")
    private String FACE_API_SECRET;
    //face++ 脸集的 全局唯一标识符
    @Value("${face.OUTER_ID}")
    private String OUTER_ID;
    //face++ 脸集的名称
    @Value("${face.DISPLAY_NAME}")
    private String DISPLAY_NAME;


    //face++ 接收的图片类型
    private final String[]  IMAGE_TYPE = {"JPG","JPEG","PNG"};

    //face++ 可接受图片最大大小  2MB
    private final int IMAGE_MAX_SIZE = 1024 * 1024 * 2;

    /*
    表单 POST 访问 外部api
    url -> 要访问的外部api
    formData - > 表单数据
    */
    private Map<String,Object> FormPostHttp(@NotNull String url,Map<String,String> formData) throws IOException {
        //创建表单体内容
        ////设置请求参数   表单格式
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        //利用遍历 依次 将表单数据 添加到 表单体
        for(Map.Entry<String,String> entry:formData.entrySet())
            formBodyBuilder.add(entry.getKey(),entry.getValue());

        //因为 访问 Face++ api 必须要求 密匙 所以在这里直接添加
        //避免不必要的代码
        formBodyBuilder.add("api_key",FACE_API_KEY);
        formBodyBuilder.add("api_secret",FACE_API_SECRET);

        RequestBody requestBody = formBodyBuilder.build();   //完成 表单信息的录入

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Map<String,Object> dataMap = null;

        /*
        这里通过 Builder 对 OkHttpClient 进行以下设置：
        * retryOnConnectionFailure - 当连接失败，尝试重连。
        * connectTimeout - 设置连接超时时间，这里设置为 30 s。
         */
//        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
//                .connectTimeout(0, TimeUnit.SECONDS).build();
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            //将请求得到的 参数字符串 放入 Map中

            String bodyContent = response.body().string();
            if(bodyContent != null)
                dataMap = JSON.parseObject(bodyContent,Map.class);
        } catch (IOException e) {
            LOGGER.error("=== FormPostHttp: " + e.getMessage() + "===");
            throw new IOException("网络连接错误!");
        }

        //返回 请求 得到的 参数
        return dataMap;

    }


    /*
    face++ 要求上传的图片为  jpg（jpeg），png
    判别图片的类型是否为 jpg（jpeg），png，不是抛出异常
     */
    private String isCorrectType(@NotNull File imageFile) throws ImageException {
        String fileName = imageFile.getName();    //文件的名字
        int index = fileName.lastIndexOf(".");   //计算从后面开始 . 的位置
        String fileType = fileName.substring(index + 1);  //截取图片的类型

        if(fileType.equalsIgnoreCase(IMAGE_TYPE[0])||fileType.equalsIgnoreCase(IMAGE_TYPE[1])||fileType.equalsIgnoreCase(IMAGE_TYPE[2]))
            return fileType.toLowerCase();
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
    private String isCorrectImage(@NotNull File imageFile) throws ImageException {
        isBeyondMaxSize(imageFile);
        return isCorrectType(imageFile);
    }
    /*
     上传File POST 访问 外部api
     url -> 要访问的外部api
     requestData - > 表单数据
     */
    private Map<String,Object> FilePostHttp(@NotNull File imageFile,@NotNull String url,Map<String,String> requestData) throws ImageException, IOException {
        //检查图片的类型
        String fileType = isCorrectImage(imageFile);

        //1.创建对应的MediaType
        final MediaType MEDIA_TYPE = MediaType.parse("image/" + fileType);

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE, imageFile);

        //3.构建MultipartBody
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file",imageFile.getName() , fileBody)
                .addFormDataPart("api_key",FACE_API_KEY)
                .addFormDataPart("api_secret",FACE_API_SECRET);

        //利用遍历 依次 将表单数据 添加到 表单体
        if(requestData != null)
            for(Map.Entry<String,String> entry:requestData.entrySet())
                multipartBodyBuilder.addFormDataPart(entry.getKey(),entry.getValue());

        RequestBody requestBody = multipartBodyBuilder.build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(url)
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
            LOGGER.error("=== FilePostHttp: " + e.getMessage() + "===");
            throw new IOException("网络连接错误!");
        }

        return dataMap;
    }


    /////////////////////////////////////////////////////////////////////////////////////

    /*
    查询人物的 face_token

    传入图片进行人脸检测和人脸分析。可以检测图片内的所有人脸，对于每个检测出的人脸，会给出其唯一标识 face_token，

    imageFile -> 人脸识别上传的图片
     */
    public Map<String,Object> DetectFace(@NotNull File imageFile) throws ImageException, IOException {

        return FilePostHttp(imageFile,"https://api-cn.faceplusplus.com/facepp/v3/detect",null);

//
//        //检查图片的类型
//        isCorrectImage(imageFile);
//
//        //1.创建对应的MediaType
//        final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
//
//        //2.创建RequestBody
//        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPG, imageFile);
//
//        //3.构建MultipartBody
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("image_file",imageFile.getName() , fileBody)
//                .addFormDataPart("api_key",FACE_API_KEY)
//                .addFormDataPart("api_secret",FACE_API_SECRET)
//                .build();
//
//        //4.构建请求
//        Request request = new Request.Builder()
//                .url("https://api-cn.faceplusplus.com/facepp/v3/detect")
//                .post(requestBody)
//                .build();
//
//        //5.发送请求
//        //  *** 异常没有处理
//        Response response = null;
//        OkHttpClient okHttpClient = null;
//        Map<String,Object> dataMap = null;
//        try {
//            okHttpClient = new OkHttpClient();
//            response = okHttpClient.newCall(request).execute();
//
//            //返回调用 face++ 查询得到的参数
//            dataMap = JSON.parseObject(response.body().string(),Map.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return dataMap;
    }



    /*
    为一个已经创建的 FaceSet 添加人脸标识 face_token
     */

    public Map<String,Object> AddFace(@NotNull String face_token) throws IOException {


        Map<String,String> dataForm = new HashMap<>();
        dataForm.put("outer_id",OUTER_ID);
        dataForm.put("face_tokens",face_token);

        return FormPostHttp("https://api-cn.faceplusplus.com/facepp/v3/faceset/addface",dataForm);

//        //设置请求参数   表单格式
//        RequestBody requestBody = new FormBody.Builder()
//                .add("api_key",FACE_API_KEY)
//                .add("api_secret",FACE_API_SECRET)
//                .add("outer_id","test")
//                .add("face_tokens",face_token)
//                .build();
//
//        Request request = new Request.Builder()
//                    .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/addface")
//                    .post(requestBody)
//                    .build();
//
//        Map<String,Object> dataMap = null;
//        OkHttpClient client = new OkHttpClient();
//
//        try (Response response = client.newCall(request).execute()) {
//
//            //将请求得到的 参数字符串 放入 Map中
//                dataMap = JSON.parseObject(response.body().string());
//
//        }catch (IOException e){
//                e.printStackTrace();
//        }
//
//        //返回 请求 得到的 参数
//            return dataMap;

        }



    /*
    创建脸集
    创建一个人脸的集合 FaceSet，用于存储人脸标识 face_token。一个 FaceSet 能够存储10000个 face_token。
     */
    private Map<String,Object> CreateFaceSet() throws IOException {
        Map<String,String> dataForm = new HashMap<>();
        dataForm.put("outer_id",OUTER_ID);
        dataForm.put("display_name",DISPLAY_NAME);

        return FormPostHttp("https://api-cn.faceplusplus.com/facepp/v3/faceset/create",dataForm);


//        //设置请求参数   表单格式
//        RequestBody requestBody = new FormBody.Builder()
//                .add("api_key",FACE_API_KEY)
//                .add("api_secret",FACE_API_SECRET)
//                .add("outer_id",OUTER_ID)
//                .add("display_name",DISPLAY_NAME)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/create")
//                .post(requestBody)
//                .build();
//
//        Map<String,Object> dataMap = null;
//        OkHttpClient client = new OkHttpClient();
//
//        try (Response response = client.newCall(request).execute()) {
//
//            //将请求得到的 参数字符串 放入 Map中
//            dataMap = JSON.parseObject(response.body().string());
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        //返回 请求 得到的 参数
//        return dataMap;

    }

    /*
    删除一个脸集
     */
    private Map<String,Object> DeleteFaceSet() throws IOException {
        Map<String,String> dataForm = new HashMap<>();
        dataForm.put("outer_id",OUTER_ID);

        return FormPostHttp("https://api-cn.faceplusplus.com/facepp/v3/faceset/delete",dataForm);


//        //设置请求参数   表单格式
//        RequestBody requestBody = new FormBody.Builder()
//                .add("api_key",FACE_API_KEY)
//                .add("api_secret",FACE_API_SECRET)
//                .add("outer_id",OUTER_ID)
//                .add("display_name",DISPLAY_NAME)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/create")
//                .post(requestBody)
//                .build();
//
//        Map<String,Object> dataMap = null;
//        OkHttpClient client = new OkHttpClient();
//
//        try (Response response = client.newCall(request).execute()) {
//
//            //将请求得到的 参数字符串 放入 Map中
//            dataMap = JSON.parseObject(response.body().string());
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        //返回 请求 得到的 参数
//        return dataMap;

    }


    /*
    查询 脸集 中是否有该人脸
    参数为 face_token
    在一个已有的 FaceSet 中找出与目标人脸最相似的一张或多张人脸，返回置信度和不同误识率下的阈值。
     */
    public Map<String,Object> SearchFace(@NotNull String face_token) throws IOException {

        Map<String,String> dataForm = new HashMap<>();
        dataForm.put("outer_id",OUTER_ID);
        dataForm.put("face_token",face_token);

        return FormPostHttp("https://api-cn.faceplusplus.com/facepp/v3/search",dataForm);
//
//        //设置请求参数   表单格式
//        RequestBody requestBody = new FormBody.Builder()
//                .add("api_key",FACE_API_KEY)
//                .add("api_secret",FACE_API_SECRET)
//                .add("outer_id",OUTER_ID)
//                .add("face_token",face_token)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://api-cn.faceplusplus.com/facepp/v3/search")
//                .post(requestBody)
//                .build();
//
//        Map<String,Object> dataMap = null;
//        OkHttpClient client = new OkHttpClient();
//
//        try (Response response = client.newCall(request).execute()) {
//
//            //将请求得到的 参数字符串 放入 Map中
//            dataMap = JSON.parseObject(response.body().string());
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        //返回 请求 得到的 参数
//        return dataMap;

    }

    /*
    查询 脸集 中是否有该人脸
    参数为 File(上传的图片)
     */
    public Map<String,Object> SearchFace(@NotNull File imageFile) throws ImageException, IOException {
        Map<String,String> requestData = new HashMap<>();
        requestData.put("outer_id",OUTER_ID);

        return FilePostHttp(imageFile,"https://api-cn.faceplusplus.com/facepp/v3/search",requestData);

    }
}
