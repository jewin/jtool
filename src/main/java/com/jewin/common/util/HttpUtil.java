package com.jewin.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  HTTP 请求工具类
 * Created by jianyang on 17/8/2.
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static SSLConnectionSocketFactory sslsf;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    static {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            sslsf = new SSLConnectionSocketFactory( sslContext, new String[] { "TLSv1" }, null, NoopHostnameVerifier.INSTANCE);

            Registry registry = RegistryBuilder
                    . create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf).build();

            // 设置连接池
            connMgr = new PoolingHttpClientConnectionManager(registry);
            // 设置连接池大小
            connMgr.setMaxTotal(100);
            connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
            // 在提交请求之前 测试连接是否可用(setStaleConnectionCheckEnabled过期方法已被替换)
            connMgr.setValidateAfterInactivity(300);
        }catch (GeneralSecurityException e){
            logger.error("创建https连接失败。", e);
        }

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用(setStaleConnectionCheckEnabled过期方法已被替换)
//        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>(), null);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params, String charset) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpclient = null;

        if(apiUrl.startsWith("https://")){
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        }else{
            httpclient = HttpClients.createDefault();
        }

        charset = determineCharset(charset);

        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("url=【{}】 response http code is {}", apiUrl, statusCode);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 初始化编码
     * @param charset
     */
    private static String  determineCharset(String charset) {
        if(null == charset || charset.trim().length() == 0){
            return DEFAULT_CHARSET;
        }else{
            return charset;
        }
    }

    private static String determineContentType(String contentType) {
        if(null == contentType || contentType.trim().length() == 0){
            return DEFAULT_CONTENT_TYPE;
        }else{
            return contentType;
        }
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPostForm(apiUrl, new HashMap<String, Object>(), null);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostForm(String apiUrl, Map<String, Object> params, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        charset = determineCharset(charset);

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(charset)));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            logger.error("post request failed， url=" + apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("EntityUtils.consume(response.getEntity()) failed!", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * @param apiUrl
     * @param json json对象
     * @return
     */
    public static String doPost(String apiUrl, Object json, String charset, String contentType) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        charset = determineCharset(charset);
        contentType = determineContentType(contentType);

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), charset);//解决中文乱码问题
            stringEntity.setContentEncoding(charset);
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("url=【{}】 response http code is {}", apiUrl, statusCode);

            response = temporarilyMoved302(response, httpPost, stringEntity, httpClient, charset, contentType);

            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            logger.error("post request failed， url=" + apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("EntityUtils.consume(response.getEntity()) failed!", e);
                }
            }
        }

        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式, 通过表单提交
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doHttpsPostForm(String apiUrl, Map<String, Object> params, String charset) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        charset = determineCharset(charset);

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, Charset.forName(charset));
            httpPost.setEntity(urlEncodedFormEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            response = temporarilyMoved302(response, httpPost, urlEncodedFormEntity, httpClient, charset, null);

            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }

            httpStr = EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            logger.error("post request failed， url=" + apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("EntityUtils.consume(response.getEntity()) failed!", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * @param apiUrl API接口URL
     * @param json JSON对象
     * @return
     */
    public static String doHttpsPost(String apiUrl, Object json, String charset, String contentType) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        charset = determineCharset(charset);
        contentType = determineContentType(contentType);

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), charset);//解决中文乱码问题
            stringEntity.setContentEncoding(charset);
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);

            //处理302跳转
            response = temporarilyMoved302(response, httpPost,stringEntity, httpClient, charset, contentType);

            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            logger.error("post request failed， url=" + apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("EntityUtils.consume(response.getEntity()) failed!", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 302 跳转
     * @param response
     * @param httpPost
     * @param stringEntity
     * @param httpClient
     * @param charset
     * @param contentType
     * @throws IOException
     */
    private static CloseableHttpResponse temporarilyMoved302(CloseableHttpResponse response, HttpPost httpPost, StringEntity stringEntity,
                                CloseableHttpClient httpClient, String charset, String contentType) throws IOException{
        List<Integer> allowStatusCodes = new ArrayList<Integer>();
        allowStatusCodes.add(HttpStatus.SC_OK);
        allowStatusCodes.add(HttpStatus.SC_MOVED_TEMPORARILY);

        int statusCode = response.getStatusLine().getStatusCode();
        if (!allowStatusCodes.contains(statusCode)) {
            logger.warn("HTTP STATUS CODE 不在允许返回的列表中，返回的HTTP代码为【{}】,允许的HTTP代码列表为【{}】", statusCode, allowStatusCodes);
            return response;
        }

        if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
            String relocationUrl = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
            logger.debug("原请求地址被对方服务器进行了302重定向，重定向后的地址为【{}】", relocationUrl);

            if(relocationUrl.startsWith("https://")){
                httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
            }

            httpPost = new HttpPost(relocationUrl);
            httpPost.setHeader("Content-Type",  contentType + ";" + charset);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);

            statusCode = response.getStatusLine().getStatusCode();
            logger.debug("重定向后返回的的HTTP代码为{}", statusCode);
        }

        return response;
    }

    /**
     * 自动根据http或者https协议选择json数据的post的方式
     * @param apiUrl
     * @param json
     * @param charset
     * @param contentType
     * @return
     */
    public static String doSmartPost(String apiUrl, Object json, String charset, String contentType){
        String response = null;
        if(null != apiUrl && apiUrl.trim().length() > 0){
            if(apiUrl.startsWith("https://")){
                response = doHttpsPost(apiUrl, json, charset, contentType);
            }else{
                response = doPost(apiUrl, json, charset, contentType);
            }
        }else{
            logger.error("apiUrl不能为空");
        }

        return response;
    }

    /**
     * 根据http或者https协议自动选择表单提交的方式
     * @param apiUrl
     * @param params
     * @param charset
     * @param contentType
     * @return
     */
    public static String doSmartPostForm(String apiUrl, Map<String, Object> params, String charset, String contentType) {
        String response = null;
        if(null != apiUrl && apiUrl.trim().length() > 0){
            if(apiUrl.startsWith("https://")){
                response = doHttpsPostForm(apiUrl, params, charset);
            }else{
                response = doPostForm(apiUrl, params, charset);
            }
        }else{
            logger.error("apiUrl不能为空");
        }

        return response;
    }
}