package cn.mxsic.amap.service;


import cn.mxsic.amap.ResponseType;
import cn.mxsic.amap.entity.AMapRequest;
import cn.mxsic.amap.entity.AMapResponse;

/**
 * Function: AMapService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 09:49:00
 */
public interface AMapService {


    /**
     *  使用样例：
     * 样例一：
     *
     * GeoCodeRequest request = new GeoCodeRequest();
     * request.setAddress("中国&陕西省&西安市&雁塔区&丈八沟街道&宝德云谷大厦1005A".replaceAll(Separator.CHAR,""));
     * GeoCodeResponse response = aMapService.execute(request,new ResponseType<GeoCodeResponse>(){});
     * for (GeoCode geoCode : response.getGeocodes()) {
     *      System.out.println("经度："+geoCode.getCoordinate().getLongitude());
     *      System.out.println("纬度："+geoCode.getCoordinate().getLatitude());
     * }
     *
     * 样例二：
     *
     * CreateGeoFenceRequest request = new CreateGeoFenceRequest();
     * request.setName("MXSIC测试围栏名称");
     * request.setCenter(new Coordinate(108.872834,34.190706).toString());
     * request.setRadius("1000");
     * V4Response<CreateGeoFenceResponse> response =  aMapService.execute(request, new ResponseType<V4Response<CreateGeoFenceResponse>>(){});
     * System.out.println(response2);
     *
     * @param request base request
     * @param response response for all
     * @param <T> return sub response type
     * @return
     */
    <T extends AMapResponse> T execute(AMapRequest request, ResponseType<T> response) throws Exception;

}
