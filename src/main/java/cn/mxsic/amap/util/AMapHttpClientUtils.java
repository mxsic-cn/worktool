package cn.mxsic.amap.util;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import cn.mxsic.amap.Api;
import cn.mxsic.amap.entity.AMapRequest;
import cn.mxsic.amap.entity.v3.V3Request;
import cn.mxsic.amap.entity.v4.V4Request;
import cn.mxsic.json.JsonUtil;


/**
 * Function: HttpClientUtils <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-17 15:14:00
 */
public class AMapHttpClientUtils {
    /**
     * 连接池最大连接数
     */
    private static final int MAX_TOTAL_CONNECTIONS = 4000;

    /**
     * 设置每个路由上的默认连接个数
     */
    private static final int DEFAULT_MAX_PER_ROUTE = 200;

    /**
     * 请求的请求超时时间 单位：毫秒
     */
    private static final int REQUEST_CONNECTION_TIMEOUT = 8 * 1000;

    /**
     * 请求的等待数据超时时间 单位：毫秒
     */
    private static final int REQUEST_SOCKET_TIMEOUT = 8 * 1000;

    /**
     * 请求的连接超时时间 单位：毫秒
     */
    private static final int REQUEST_CONNECTION_REQUEST_TIMEOUT = 5 * 1000;

    /**
     * 连接闲置多久后需要重新检测 单位：毫秒
     */
    private static final int VALIDATE_AFTER_IN_ACTIVITY = 2 * 1000;

    /**
     * 关闭Socket时，要么发送完所有数据，要么等待多少秒后，就关闭连接，此时socket.close()是阻塞的　单位秒
     */
    private static final int SOCKET_CONFIG_SO_LINGER = 60;

    /**
     * 接收数据的等待超时时间,即读超时时间，单位ms
     */
    private static final int SOCKET_CONFIG_SO_TIMEOUT = 5 * 1000;
    /**
     * 重试次数
     */
    private static int RETRY_COUNT = 5;
    /**
     * 声明为 static volatile,会迫使线程每次读取时作为一个全局变量读取
     */
    private static volatile CloseableHttpClient httpClient = null;

    /**
     * 返回数据格式类型
     *
     * 可选输入内容包括：JSON，XML。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     *
     * 可选
     *
     * JSON
     *
     * @return string
     * @description data format is JSON  带map参数get请求, 此方法会将map参数拼接到连接地址上。
     */
    public static String execute(Api api, String key, V3Request v3Request) {
        String output = "JSON";
        switch (api.getMethod()) {
            case GET:
                return doV3Get(api.getUrl(), key, output, v3Request);
            case PUT:
                break;
            case PATCH:
                break;
            case HEAD:
                break;
            case POST:
                return doV3Post(api.getUrl(), key, output, v3Request);
            case DELETE:
                break;
            case OPTIONS:
                break;
            case TRACE:
                break;
            default:
                throw new UnsupportedOperationException("Unsupported method: " + api.getMethod().name());
        }
        throw new UnsupportedOperationException("Unsupported method: " + api.getMethod().name());
    }

    /**
     * 返回数据格式类型
     * 可选输入内容包括：JSON，XML。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     * 可选
     * JSON
     *
     * @return string
     * @description data format is ContentType.APPLICATION_JSON  带map参数get请求, 此方法会将map参数拼接到连接地址上。
     */
    public static String execute(Api api, String key, V4Request v4Request) {

        ContentType contentType = ContentType.APPLICATION_JSON;

        switch (api.getMethod()) {
            case GET:
                return doV4Get(api.getUrl(), key, contentType, v4Request);
            case PUT:
                return doV4Put(api.getUrl(), key, contentType, v4Request);
            case PATCH:
                return doV4Patch(api.getUrl(), key, contentType, v4Request);
            case HEAD:
                break;
            case POST:
                return doV4Post(api.getUrl(), key, contentType, v4Request);
            case DELETE:
                return doV4Delete(api.getUrl(), key, contentType, v4Request);
            case OPTIONS:
                break;
            case TRACE:
                break;
            default:
                throw new UnsupportedOperationException("Unsupported method: " + api.getMethod().name());
        }
        throw new UnsupportedOperationException("Unsupported method: " + api.getMethod().name());
    }


    /**
     * @param contentType 根据具体请求情况指定,比如json可以是 ContentType.APPLICATION_JSON
     * @return String
     * @description 带单string参数执行post方法
     */
    private static String doV4Delete(String uri, String key, ContentType contentType, V4Request v4Request) {
        String responseBody;
        Map<String, String> params = getParams(v4Request);
        HttpDelete httpDelete = new HttpDelete(jointUrl(params,null,key,uri));
        try {
            httpDelete.setConfig(getRequestConfig());
            responseBody = executeRequest(httpDelete);
        } catch (IOException e) {
            throw new RuntimeException("http client doPost方法异常 ", e);
        } finally {
            httpDelete.releaseConnection();
        }
        return responseBody;
    }
    /**
     * @param contentType 根据具体请求情况指定,比如json可以是 ContentType.APPLICATION_JSON
     * @return String
     * @description 带单string参数执行post方法
     */
    private static String doV4Post(String uri, String key, ContentType contentType, V4Request v4Request) {
        String responseBody;
        HttpPost httpPost = new HttpPost( uri + "?key=" + key);
        try {
            StringEntity reqEntity = new StringEntity(JsonUtil.toJSONString(v4Request), contentType);
            httpPost.setEntity(reqEntity);
            httpPost.setConfig(getRequestConfig());
            responseBody = executeRequest(httpPost);
        } catch (IOException e) {
            throw new RuntimeException("http client doPost方法异常 ", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseBody;
    }

    /**
     * @param contentType 根据具体请求情况指定,比如json可以是 ContentType.APPLICATION_JSON
     * @return String
     * @description 带单string参数执行put方法
     */
    private static String doV4Put(String uri, String key, ContentType contentType, V4Request v4Request) {
        String responseBody;
        HttpPut httpPut = new HttpPut( uri + "?key=" + key);
        try {
            StringEntity reqEntity = new StringEntity(JsonUtil.toJSONString(v4Request), contentType);
            httpPut.setEntity(reqEntity);
            httpPut.setConfig(getRequestConfig());
            responseBody = executeRequest(httpPut);
        } catch (IOException e) {
            throw new RuntimeException("http client doPost方法异常 ", e);
        } finally {
            httpPut.releaseConnection();
        }
        return responseBody;
    }

    /**
     * @param contentType 根据具体请求情况指定,比如json可以是 ContentType.APPLICATION_JSON
     * @return String
     * @description 带单string参数执行get方法
     */
    private static String doV4Get(String uri, String key, ContentType contentType, V4Request v4Request) {
        String responseBody;
        Map<String, String> params = getParams(v4Request);
        HttpGet httpGet = new HttpGet(jointUrl(params, null, key, uri));
        try {
            httpGet.setConfig(getRequestConfig());
            responseBody = executeRequest(httpGet);
        } catch (IOException e) {
            throw new RuntimeException("http client doPost方法异常 ", e);
        } finally {
            httpGet.releaseConnection();
        }
        return responseBody;
    }

    /**
     * @param contentType 根据具体请求情况指定,比如json可以是 ContentType.APPLICATION_JSON
     * @return String
     * @description 带单string参数执行Patch方法
     */
    private static String doV4Patch(String uri, String key, ContentType contentType, V4Request v4Request) {
        String responseBody;
        HttpPatch httpPatch = new HttpPatch(uri + "?key=" + key);
        try {
            StringEntity reqEntity = new StringEntity(JsonUtil.toJSONString(v4Request), contentType);
            httpPatch.setEntity(reqEntity);
            httpPatch.setConfig(getRequestConfig());
            responseBody = executeRequest(httpPatch);
        } catch (IOException e) {
            throw new RuntimeException("http client doPost方法异常 ", e);
        } finally {
            httpPatch.releaseConnection();
        }
        return responseBody;
    }

    /**
     * @return string
     * @description 带map参数get请求, 此方法会将map参数拼接到连接地址上。
     */
    private static String doV3Get(String uri, String key, String output, V3Request v3Request) {
        String responseBody;
        Map<String, String> params = getParams(v3Request);
        HttpGet httpGet = new HttpGet(jointUrl(params, output, key, uri));
        try {
            httpGet.setConfig(getRequestConfig());
            responseBody = executeRequest(httpGet);
        } catch (IOException e) {
            throw new RuntimeException("http client doGet方法异常 ", e);
        } finally {
            httpGet.releaseConnection();
        }
        return responseBody;
    }

    /**
     * get params form object
     */
    private static Map<String, String> getParams(AMapRequest object) {
        Map<String, String> hashMap = new HashMap<>();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object) != null) {
                    if (StringUtils.isNotEmpty(field.get(object).toString())) {
                        hashMap.put(field.getName(), URLEncoder.encode(field.get(object).toString(), Charsets.UTF_8.name()));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("object to map error ", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("object to map error ", e);
        }
        return hashMap;
    }

    /**
     * @return String
     * @description 带map参数的post请求方法
     */
    private static String doV3Post(String uri, String key, String output, V3Request v3Request) {
        String responseBody;
        Map<String, String> params = getParams(v3Request);
        HttpPost httpPost = new HttpPost(jointUrl(params, output, key, uri));
        try {
            List<NameValuePair> nameValuePairs = Lists.newArrayList();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String entryKey = entry.getKey();
                String entryValue = entry.getValue();
                nameValuePairs.add(new BasicNameValuePair(entryKey, entryValue));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charsets.UTF_8));
            httpPost.setConfig(getRequestConfig());
            responseBody = executeRequest(httpPost);
        } catch (Exception e) {
            throw new RuntimeException("http client doPost方法异常 ", e);
        } finally {
            httpPost.releaseConnection();
        }

        return responseBody;

    }


    /**
     * 拼接请求字符串
     * @param params 参数
     * @param output 返回数据类型，JSON 是v3 用法，V4不在这儿设置
     * @param key 密钥
     * @param url 原始url
     * @return 新url
     */
    private static String jointUrl(Map<String, String> params, String output, String key, String url) {
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(url);
        boolean haveBeAppend = false;
        if (StringUtils.isEmpty(output)) {
            haveBeAppend = true;
            baseUrl.append("?key=").append(key);
        }
        Set<Map.Entry<String, String>> entries = params.entrySet();
        try {
            for (Map.Entry<String, String> param : entries) {
                if (haveBeAppend) {
                    baseUrl.append("&");
                } else {
                    baseUrl.append("?");
                    haveBeAppend = true;
                }
                baseUrl.append(param.getKey()).append("=").append(param.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException("http jointUrl 方法异常 ", e);
        }
        if (StringUtils.isNotEmpty(output)) {
            if (haveBeAppend) {
                baseUrl.append("&");
            } else {
                baseUrl.append("?");
            }
            baseUrl.append("output=").append(output).append("&key=").append(key);
        }
        return baseUrl.toString();
    }

    /**
     * @return RequestConfig
     * @description: 获得请求配置信息
     */
    private static RequestConfig getRequestConfig() {

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                //.setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                //.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                //.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();

        return RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(REQUEST_CONNECTION_TIMEOUT)
                .setConnectTimeout(REQUEST_SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_CONNECTION_REQUEST_TIMEOUT)
                .build();

    }


    /**
     * @return String
     * @description 通用执行请求方法
     */
    private static String executeRequest(HttpUriRequest method) throws IOException {
        ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
            int status = response.getStatusLine().getStatusCode();
            String result;
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                HttpEntity entity = response.getEntity();
                result = entity != null ? EntityUtils.toString(entity) : null;
                EntityUtils.consume(entity);
                return result;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        return getHttpClientInstance().execute(method, responseHandler);
    }


    /**
     * @return CloseableHttpClient
     * @description 单例获取httpclient实例
     */
    private static CloseableHttpClient getHttpClientInstance() {
        if (httpClient == null) {
            synchronized (CloseableHttpClient.class) {
                if (httpClient == null) {
                    httpClient = HttpClients.custom().setConnectionManager(initConfig()).setRetryHandler(getRetryHandler()).build();
                }
            }
        }
        return httpClient;

    }

    /**
     * @return HttpRequestRetryHandler
     * @description :获取重试handler
     */
    private static HttpRequestRetryHandler getRetryHandler() {

        // 请求重试处理
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= RETRY_COUNT) {
                    // 假设已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 假设server丢掉了连接。那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // 目标server不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL握手异常
                    return false;
                }

                HttpRequest request = HttpClientContext.adapt(context).getRequest();
                // 假设请求是幂等的，就再次尝试
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };

    }


    /**
     * @return PoolingHttpClientConnectionManager
     * @description 初始化连接池等配置信息
     */
    private static PoolingHttpClientConnectionManager initConfig() {

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        /**
         * 以下参数设置含义分别为:
         * 1 是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
         * 2 是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
         * 3 接收数据的等待超时时间，单位ms
         * 4 关闭Socket时，要么发送完所有数据，要么等待多少秒后，就关闭连接，此时socket.close()是阻塞的
         * 5 开启监视TCP连接是否有效
         * 其中setTcpNoDelay(true)设置是否启用Nagle算法，设置true后禁用Nagle算法，默认为false（即默认启用Nagle算法）。
         * Nagle算法试图通过减少分片的数量来节省带宽。当应用程序希望降低网络延迟并提高性能时，
         * 它们可以关闭Nagle算法，这样数据将会更早地发 送，但是增加了网络消耗。 单位为：毫秒
         */

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoReuseAddress(true)
                .setSoTimeout(SOCKET_CONFIG_SO_TIMEOUT)
                //.setSoLinger(SOCKET_CONFIG_SO_LINGER)
                //.setSoKeepAlive(true)
                .build();

        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setValidateAfterInactivity(VALIDATE_AFTER_IN_ACTIVITY);

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Charsets.UTF_8)
                .build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        return connManager;

    }

}