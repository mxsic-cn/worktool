package cn.mxsic.amap.util;


import java.util.UUID;

import cn.mxsic.amap.Coordinate;
import cn.mxsic.amap.ResponseType;
import cn.mxsic.amap.constants.DistanceType;
import cn.mxsic.amap.entity.v3.DistanceRequest;
import cn.mxsic.amap.entity.v3.DistanceResponse;
import cn.mxsic.amap.entity.v3.GeoCode;
import cn.mxsic.amap.entity.v3.GeoCodeRequest;
import cn.mxsic.amap.entity.v3.GeoCodeResponse;
import cn.mxsic.amap.entity.v4.CheckGeoFenceRequest;
import cn.mxsic.amap.entity.v4.CheckGeoFenceResponse;
import cn.mxsic.amap.entity.v4.DeleteGeoFenceRequest;
import cn.mxsic.amap.entity.v4.DeleteGeoFenceResponse;
import cn.mxsic.amap.entity.v4.QueryGeoFenceRequest;
import cn.mxsic.amap.entity.v4.QueryGeoFenceResponse;
import cn.mxsic.amap.entity.v4.V4Response;
import cn.mxsic.amap.service.AMapService;
import cn.mxsic.amap.service.impl.AMapServiceImpl;


/**
 * Function: Test <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 14:53:00
 */
public class Test {

    public static void main(String[] args) throws Exception{
        AMapService aMapService = new AMapServiceImpl();
//        geoCode(aMapService);
//
//        CreateGeoFenceRequest request = new CreateGeoFenceRequest();
//        //分区方案的id+SPLIT+分区块的ID
//        request.setName("mxsic_2320");
//        request.setCenter(new Coordinate(108.850684,34.312203).toString());
//        request.setRadius(4000.23);
//        V4Response<CreateGeoFenceResponse> esr = aMapService.execute(request, new ResponseType<V4Response<CreateGeoFenceResponse>>() {
//        });
//        System.out.println(esr);

//        query(aMapService,"748825c3-3d45-46fc-a668-fdbbefff561b");
//
        delete(aMapService,"75d7192f-a5f0-45a9-95f5-954e409eecd6");
        delete(aMapService,"b2363717-6923-434d-bc02-e998a249f46c");
        delete(aMapService,"b52dbf0d-de35-4b79-8d29-24722fe31d90");
//
//        check(aMapService, new Coordinate(108.872834, 34.300706));

//        check2(aMapService, "中国&陕西&西安市&雁塔区&锦业公寓&*");
//        Coordinate start = new Coordinate(116.345243,39.934507);
//        Coordinate end = new Coordinate(108.951444,34.297523);
//        distance(aMapService, start, end);
    }

    private static void distance(AMapService aMapService, Coordinate start, Coordinate end) throws Exception  {
        DistanceRequest request = new DistanceRequest();
        request.setOrigins(start.toString());
        request.setDestination(end.toString());
        request.setType(DistanceType.DRIVE.get());
        DistanceResponse response = aMapService.execute(request,new ResponseType<DistanceResponse>() {});
        System.out.println(response);
        if (!response.getResults().isEmpty()) {
            System.out.println(Integer.parseInt(response.getResults().get(0).getDistance()) / 1000 +"km");
        }
    }

    private static void check2(AMapService aMapService, String address)  throws Exception {
        GeoCodeRequest request = new GeoCodeRequest();
        request.setAddress(address.replaceAll("/", ""));
        GeoCodeResponse response = aMapService.execute(request, new ResponseType<GeoCodeResponse>() {
        });
        if (!response.isSucceed()) {
            System.out.println(response.getInfo());
        }
        if (response.getGeocodes().isEmpty()) {
            System.out.println(address + "定位失败");
        }
        if (response.getGeocodes().size() > 1) {
            System.out.println(address + "定位不准确");
        }
        CheckGeoFenceRequest checkGeoFenceRequest = new CheckGeoFenceRequest();
        checkGeoFenceRequest.setLocations(response.getGeocodes().get(0).getCoordinate().toStringAtNow());
        V4Response<CheckGeoFenceResponse> checkGeoFenceResponseV4Response = aMapService.execute(checkGeoFenceRequest, new ResponseType<V4Response<CheckGeoFenceResponse>>() {
        });
        CheckGeoFenceResponse checkGeoFenceResponse = checkGeoFenceResponseV4Response.getData();
        if (!checkGeoFenceResponse.isSucceed()) {
            System.out.println(checkGeoFenceResponse.getMessage());
        }
        System.out.println(checkGeoFenceResponse);
    }

    private static void geoCode(AMapService aMapService) throws Exception  {
        GeoCodeRequest request = new GeoCodeRequest();
        request.setAddress("中国&陕西省&西安市&雁塔区&丈八沟街道&宝德云谷大厦1005A".replaceAll("/", ""));
        GeoCodeResponse response = aMapService.execute(request, new ResponseType<GeoCodeResponse>() {
        });
        for (GeoCode geoCode : response.getGeocodes()) {
            System.out.println("经度：" + geoCode.getCoordinate().getLongitude());
            System.out.println("纬度：" + geoCode.getCoordinate().getLatitude());
        }
    }

    private static void check(AMapService aMapService, Coordinate coordinate) throws Exception {
        CheckGeoFenceRequest checkGeoFenceRequest = new CheckGeoFenceRequest();
        checkGeoFenceRequest.setLocations(coordinate.toStringAtNow());
        checkGeoFenceRequest.setUid(UUID.randomUUID().toString());
        V4Response<CheckGeoFenceResponse> responseV4Response = aMapService.execute(checkGeoFenceRequest, new ResponseType<V4Response<CheckGeoFenceResponse>>() {
        });
        System.out.println(responseV4Response);
    }

    private static void query(AMapService aMapService, String gid) throws Exception  {
        QueryGeoFenceRequest queryGeoFenceRequest = new QueryGeoFenceRequest();
        queryGeoFenceRequest.setGid(gid);
//        queryGeoFenceRequest.setName("mxsic_001");
        V4Response<QueryGeoFenceResponse> responseV4Response = aMapService.execute(queryGeoFenceRequest, new ResponseType<V4Response<QueryGeoFenceResponse>>() {
        });
        System.out.println(responseV4Response);
    }

    private static void delete(AMapService aMapService, String gid) throws Exception  {
        DeleteGeoFenceRequest queryGeoFenceRequest = new DeleteGeoFenceRequest();
        queryGeoFenceRequest.setGid(gid);
        V4Response<DeleteGeoFenceResponse> responseV4Response = aMapService.execute(queryGeoFenceRequest, new ResponseType<V4Response<DeleteGeoFenceResponse>>() {
        });
        System.out.println(responseV4Response);
    }
}
