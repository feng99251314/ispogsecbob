package com.ispogsecbob.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * --发送get请求的api
 * CloseableHttpClient类 ，client实现类
 * HttpClients类 ，client工具类，用于创建客户端对象。
 * CloseableHttpResponse接口，请求的响应对象
 * URIBuilder类 ：url构建类，用于设置get请求的路径变量
 * HttpGet类 ：get请求的发送对象
 * EntityUtils类 实体处理类
 *
 * --发送post 请求使用的api
 * CloseableHttpClient类
 * HttpClientBuilder client构建对象，用于创建客户端对象。
 * LaxRedirectStrategy类，post请求重定向的策略
 * CloseableHttpResponse 请求的响应对象
 * HttpPost post请求的发送对象
 * NameValuePair 类，用于设置参数值
 * UrlEncodedFormEntity：用于设置表单参数给发送对象HttpPost
 *
 * @author ranger
 *
 */
public class HttpClientUtils {


    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志记录

    private static RequestConfig requestConfig = null;

    static
    {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    }



    public static String doGet(String url,Map<String, Object> params){

        //获取httpclient客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";

        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);

            if(null!=params){
                for (String key:params.keySet()) {
                    builder.setParameter(key, params.get(key).toString());
                }
            }

            HttpGet get = new HttpGet(builder.build());


            response = httpclient.execute(get);

            System.out.println(response.getStatusLine());

            if(200==response.getStatusLine().getStatusCode()){
                HttpEntity entity = response.getEntity();
                resultString = EntityUtils.toString(entity, "utf-8");
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if(null!=response){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=httpclient){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultString;
    }

    public static String doGet(String url){
        return doGet(url, null);
    }

    public static String doPost(String url,Map<String,String> params){
        /**
         * 在4.0及以上httpclient版本中，post需要指定重定向的策略，如果不指定则按默认的重定向策略。
         *
         * 获取httpclient客户端
         */
        CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy( new LaxRedirectStrategy()).build();

        String resultString = "";

        CloseableHttpResponse response = null;

        try {


            HttpPost post = new HttpPost(url);

            List<NameValuePair> paramaters = new ArrayList<>();

            if(null!=params){
                for (String key : params.keySet()) {
                    paramaters.add(new BasicNameValuePair(key,params.get(key)));
                }

                UrlEncodedFormEntity  formEntity = new UrlEncodedFormEntity (paramaters);

                post.setEntity(formEntity);
            }


            /**
             * HTTP/1.1 403 Forbidden
             *   原因：
             *      有些网站，设置了反爬虫机制
             *   解决的办法：
             *      设置请求头，伪装浏览器
             */
            post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

            response = httpclient.execute(post);

            System.out.println(response.getStatusLine());

            if(200==response.getStatusLine().getStatusCode()){
                HttpEntity entity = response.getEntity();
                resultString = EntityUtils.toString(entity, "utf-8");
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if(null!=response){
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(null!=httpclient){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return resultString;
    }

    public static String doPost(String url){
        return doPost(url, null);
    }

    public static JSONObject Post(String url, JSONObject jsonParam)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try
        {
            if (null != jsonParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String str = "";
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                }
                catch (Exception e)
                {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("post请求提交失败:" + url, e);
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

}