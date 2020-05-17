package edu.zhoujw.lesson.httpclient.factory;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhoujw
 * @version: 1.0
 * @date: 2020/5/17 11:07
 * @description:
 */
public class MyHttpClientFactoryTest {

    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager manager;

    @Test
    public void getClient(){

        // 注册访问协议相关的socket工厂
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
                .build();

        // httpConnection工厂，配置写请求/解析响应处理器
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory
                = new ManagedHttpClientConnectionFactory(
                        DefaultHttpRequestWriterFactory.INSTANCE
                        , DefaultHttpResponseParserFactory.INSTANCE);

        // DNS 解析器
        SystemDefaultDnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

        // 创建池化连接欸管理器
        manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry,connectionFactory,dnsResolver);

        SocketConfig defaultSocketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();

        manager.setDefaultSocketConfig(defaultSocketConfig);

        manager.setMaxTotal(300);

        manager.setDefaultMaxPerRoute(200);

        manager.setValidateAfterInactivity(5 * 1000);

        // 默认请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(2 * 1000)
                .setSocketTimeout(5 * 1000)
                .setConnectionRequestTimeout(2000)
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(manager)
                .setConnectionManagerShared(false)
                .evictIdleConnections(60, TimeUnit.SECONDS)
                .setDefaultRequestConfig(defaultRequestConfig)
                .evictExpiredConnections()
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                // 不进行重试
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try{
                    httpClient.close();
                }catch (IOException ioe){}
            }
        });
    }

}
