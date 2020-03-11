package cn.mxsic.amap;


import com.google.common.collect.Maps;

import org.springframework.http.HttpMethod;

import java.util.Map;

import cn.mxsic.amap.entity.AMapRequest;
import cn.mxsic.amap.entity.v3.DistanceRequest;
import cn.mxsic.amap.entity.v3.GeoCodeRequest;
import cn.mxsic.amap.entity.v4.CheckGeoFenceRequest;
import cn.mxsic.amap.entity.v4.CreateGeoFenceRequest;
import cn.mxsic.amap.entity.v4.DeleteGeoFenceRequest;
import cn.mxsic.amap.entity.v4.QueryGeoFenceRequest;

/**
 * Function: GeoCodeEntity <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 11:12:00
 */
public class ApiUrls {

    private static Map<Class, Api> urlMap = Maps.newHashMap();

    static {
        urlMap.put(GeoCodeRequest.class, new Api(HttpMethod.GET, "https://restapi.amap.com/v3/geocode/geo"));
        urlMap.put(CreateGeoFenceRequest.class, new Api(HttpMethod.POST, "https://restapi.amap.com/v4/geofence/meta"));
        urlMap.put(DeleteGeoFenceRequest.class, new Api(HttpMethod.DELETE, "https://restapi.amap.com/v4/geofence/meta"));
        urlMap.put(QueryGeoFenceRequest.class, new Api(HttpMethod.GET, "https://restapi.amap.com/v4/geofence/meta"));
        urlMap.put(CheckGeoFenceRequest.class, new Api(HttpMethod.GET, "https://restapi.amap.com/v4/geofence/status"));
        urlMap.put(DistanceRequest.class, new Api(HttpMethod.GET, "https://restapi.amap.com/v3/distance"));
    }

    public static Api getUrl(AMapRequest request) {
        if (urlMap.containsKey(request.getClass())) {
            return urlMap.get(request.getClass());
        }
        throw new IllegalArgumentException("url not config for request:" + request.getClass());
    }

}
