package lllr.test.face;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class FaceTest {
    private OkHttpClient client = new OkHttpClient();

    @Test
    public void test() throws IOException {
            Request request = new Request.Builder()
                    .url("https://www.baidu.com/")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println(response.body().string());
            }


    }


}
