package edu.zhoujw.lesson.okhttp;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: zhoujw
 * @Description:
 * @Date: 2020/4/26 18:10
 */
public class RequestDemoTest {

    @Test
    public void getA() throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();


        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url("http://47.100.66.220:8088/tjl_test/security")
                .build();

        Response execute = okHttpClient
                .newCall(request)
                .execute();

        ResponseBody body = execute.body();

        String string = body.string();
        System.out.println(string);
        System.out.println(body.toString());
    }

    @Test
    public void postA() throws IOException {
        MediaType JSON
                = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");

        RequestBody requestBody = RequestBody.create("api=apivalue&security=security_value", JSON);


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://47.100.66.220:8088/tjl_test/security")
                .addHeader("token", "request_token")
                .post(requestBody)
                .build();



        Response execute = okHttpClient
                .newCall(request)
                .execute();

        ResponseBody body = execute.body();

        String string = body.string();
        System.out.println(string);

    }

    @Test
    public void postB() throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl buildHttpUrl = HttpUrl.get("http://47.100.66.220:8088/tjl_test/security")
                .newBuilder()
                .addQueryParameter("qeuryA", "queryA-value")
                .addQueryParameter("qeuryB", "queryB-value")
                .build();

        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create("{}", JSON);


        Request request = new Request.Builder()
                .url(buildHttpUrl)
                .addHeader("head1", "head1Value")
                .post(requestBody)
                .build();


        final Response execute = okHttpClient.newCall(request).execute();
        ResponseBody body = execute.body();
        String string = body.string();
        System.out.println(string);
    }

}
