package cn.mxsic.amap.service.impl;


import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mxsic.amap.Api;
import cn.mxsic.amap.ApiUrls;
import cn.mxsic.amap.ResponseType;
import cn.mxsic.amap.entity.AMapRequest;
import cn.mxsic.amap.entity.AMapResponse;
import cn.mxsic.amap.entity.v3.V3Request;
import cn.mxsic.amap.entity.v4.V4Request;
import cn.mxsic.amap.service.AMapService;
import cn.mxsic.amap.util.AMapHttpClientUtils;



/**
 * Function: AMapServiceImpl <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 11:52:00
 */
@Service
public class AMapServiceImpl implements AMapService {

    /**
     * 高德Key
     *
     * 用户在高德地图官网申请Web服务API类型Key
     *
     * 必填
     * default :b07bfe50251125cd26b9377f85977ba3
     * 无
     */
    @Value("${amap.app.key:'b07bfe50251125cd26b9377f85977ba3'}")
    private String appKey="b07bfe50251125cd26b9377f85977ba3";


    @Override
    public <T extends AMapResponse> T execute(AMapRequest request, ResponseType<T> response) throws Exception {
        Api api = ApiUrls.getUrl(request);
        String responseBody = executeDiffVersion(api, appKey, request);
        T mapResponse = new Gson().fromJson(responseBody, response.getType());
        if (mapResponse == null || !mapResponse.isSucceed()) {
            System.out.println("ERROR Map Request Failed :" + responseBody);
            throw new Exception("ERROR Map Request Failed :" + responseBody);
        }
        return mapResponse;
    }


    /**
     * diff version request use diff request function
     * @param api
     * @param key
     * @param aMapRequest
     * @return
     */
    private String executeDiffVersion(Api api, String key, AMapRequest aMapRequest) {
        if (aMapRequest instanceof V3Request) {
            V3Request v3Request = (V3Request) aMapRequest;
            return AMapHttpClientUtils.execute(api, key, v3Request);
        } else if (aMapRequest instanceof V4Request) {
            V4Request v4Request = (V4Request) aMapRequest;
            return AMapHttpClientUtils.execute(api, key, v4Request);
        }
        throw new UnsupportedOperationException("Unsupported Request: " + aMapRequest.getClass());
    }
}
