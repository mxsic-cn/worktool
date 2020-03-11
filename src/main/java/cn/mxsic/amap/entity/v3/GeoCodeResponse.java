package cn.mxsic.amap.entity.v3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Data;

/**
 * Function: GeoCodeResponse <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 10:51:00
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodeResponse extends V3Response {
    private List<GeoCode> geocodes;
}
