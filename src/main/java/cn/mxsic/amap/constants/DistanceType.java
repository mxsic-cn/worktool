package cn.mxsic.amap.constants;


import cn.mxsic.inter.IType;

/**
 * Function: DistanceType <br>
 *
 * * 0：直线距离
 *
 * 1：驾车导航距离（仅支持国内坐标）。
 *
 * 必须指出，当为1时会考虑路况，故在不同时间请求返回结果可能不同。
 *
 * 此策略和驾车路径规划接口的 strategy=4策略基本一致，策略为“ 躲避拥堵的路线，但是可能会存在绕路的情况，耗时可能较长 ”
 *
 * 若需要实现高德地图客户端效果，可以考虑使用驾车路径规划接口
 *
 * 3：步行规划距离（仅支持5km之间的距离）
 *
 * @author: siqishangshu <br>
 * @date: 2020-03-05 14:59:00
 */
public enum  DistanceType implements IType<Integer> {
    STRAIGHT(0),
    DRIVE(1),
    WALK(3),
    STRATEGY(4);

    private Integer type;
    DistanceType(Integer type){
        this.type = type;
    }

    @Override
    public Integer get() {
        return type;
    }
}
