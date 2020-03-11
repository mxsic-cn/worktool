package cn.mxsic.amap;


import com.google.common.reflect.TypeToken;

import cn.mxsic.amap.entity.AMapResponse;


/**
 * Function: ResponseType <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-20 10:56:00
 */
public class ResponseType<T extends AMapResponse> extends TypeToken<T> {

    public ResponseType() {
        super();
    }
}
