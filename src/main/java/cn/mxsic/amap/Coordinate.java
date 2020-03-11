package cn.mxsic.amap;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Function: Coordinate <br>
 * 地图坐标经纬度
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 17:07:00
 */
@Data
@NoArgsConstructor
public class Coordinate {

    private static final int LONGITUDE_LATITUDE_STRING_LEN = 2;

    public static final String DATA_SEPARATOR = ",";
    public static final String COORDINATE_SEPARATOR = ";";
    /**
     * 地理经度
     */
    public BigDecimal longitude;
    /**
     * 地理纬度
     */
    public BigDecimal latitude;


    public Coordinate(String location) {
        if (location != null) {
            String[] arr = StringUtils.split(location, DATA_SEPARATOR);
            if (arr.length == LONGITUDE_LATITUDE_STRING_LEN) {
                longitude = new BigDecimal(arr[0]);
                latitude = new BigDecimal(arr[1]);
            }
        }
    }

    public Coordinate(double longitude, double latitude) {
        this.longitude = new BigDecimal(longitude);
        this.latitude = new BigDecimal(latitude);
    }

    public Coordinate(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private static String format(BigDecimal decimal) {
        return decimal.setScale(6, BigDecimal.ROUND_CEILING).toString();
    }


    @Override
    public String toString() {
        return format(longitude) + DATA_SEPARATOR + format(latitude);
    }

    public String toStringAtNow() {
        return toString() + DATA_SEPARATOR + String.valueOf(System.currentTimeMillis() / 1000);
    }

    public String toStringAtTime(Date date) {
        return toString() + DATA_SEPARATOR + String.valueOf(date.getTime() / 1000);
    }

    public static String toString(Coordinate coordinate) {
        if (coordinate == null) {
            return null;
        }
        return coordinate.toString();
    }

    public static String toString(List<Coordinate> list) {
        if (list.isEmpty()) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        list.stream().forEach(coordinate -> {
            stringBuffer.append(coordinate.toString());
            stringBuffer.append(COORDINATE_SEPARATOR);
        });
        return stringBuffer.substring(0, stringBuffer.length() - 1).toString();
    }

    public static List<Coordinate> toList(String str) {
        List<Coordinate> coordinates = Lists.newArrayList();
        String[] arr = StringUtils.split(str, COORDINATE_SEPARATOR);
        for (String s : arr) {
            coordinates.add(new Coordinate(s));
        }
        return coordinates;
    }

    public static Coordinate toCenter(String str) {
        String[] arr = StringUtils.split(str, COORDINATE_SEPARATOR);
        if (arr.length != 1) {
            throw new IllegalArgumentException("length of coordinate len error:" + str);
        }
        return new Coordinate(arr[0]);
    }
}

