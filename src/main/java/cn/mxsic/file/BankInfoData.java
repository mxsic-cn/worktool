package cn.mxsic.file;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import cn.mxsic.amap.service.AMapService;
import cn.mxsic.amap.service.impl.AMapServiceImpl;
import cn.mxsic.file.dto.AmapResponse;
import cn.mxsic.http.HttpUtil;
import cn.mxsic.json.JsonUtil;
import cn.mxsic.util.ZHConverter;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-27 15:18:00
 */
public class BankInfoData {

    private static void readData(String filePath) throws Exception {
        File file = new File(filePath);
        String encoding = "UTF-8";
        File filew = new File("/Users/siqishangshu/Downloads/station_name.sql");
        if (filew.exists()) {
            filew.delete();
        }
        filew.createNewFile();
        FileWriter oos = new FileWriter(filew);
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), encoding
        );
        List<String> reDo = Lists.newArrayList();
        try {
            if (file.isFile() && file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                ZHConverter.convert("中国", ZHConverter.TRADITIONAL);
//                INSERT INTO `node_railway_station` (`id`, `name`, `code`, `enName`, `hkName`, `country`, `geoRange1`, `geoRange2`, `geoRange3`, `geoRange4`, `address`, `phone`, `path`, `detail`, `keywords`, `ordinal`, `status`, `description`, `createTime`, `updateTime`)
//                VALUES
//                        ('', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP');
//                System.out.println("INSERT INTO `node_railway_station` (`id`,  `code`, `name`, `enName`, `hkName`, `country`,  `keywords`, `ordinal`)\n" +
//                        "                VALUES");
                Map<String, String> code = Maps.newHashMap();

//                oos.write("INSERT INTO `node_railway_station` (`id`,  `code`, `name`, `enName`, `hkName`, `country`, `geoRange1`, `geoRange2`, `geoRange3`, `geoRange4`, `path`, `detail`, `keywords`, `ordinal`)\n" +
//                        "                VALUES");
//                oos.write("\n");
                AMapService aMapService = new AMapServiceImpl();
//                while ((lineTxt = bufferedReader.readLine()) != null) {
//                    String aString = lineTxt.replace("var station_names =", "");
//                    String bString = aString.replace("'", "");
//                    String newString = bString.replace(";", "");
//                    String[] arr = newString.split("@");
//                    int i = 50;
                List<String> list = Lists.newArrayList();

//                list.add("古浪");
//                list.add("工农湖");
//                list.add("广宁寺南");
//                list.add("红安");
//                list.add("海北");
//                list.add("怀宁");
//                list.add("虎石台");
//                list.add("红兴隆");
//                list.add("鸡冠山");
//                list.add("金马村");
//                list.add("建三江");
//                list.add("济源");
//                list.add("蓟州");
//                list.add("莲江口");
//                list.add("兰棱");
//                list.add("龙泉寺");
//                list.add("陵水");
//                list.add("龙山镇");
//                list.add("莱芜西");
//                list.add("莱西南");
//                list.add("木里图");
//                list.add("明水河");
//                list.add("南关岭");
//                list.add("南华北");
//                list.add("南朗");
//                list.add("南头");
//                list.add("平河口");
//                list.add("祁东北");
//                list.add("琼海");
//                list.add("清河");
//                list.add("潜江");
//                list.add("青龙山");
//                list.add("清水");
//                list.add("戚墅堰");
//                list.add("山城镇");
//                list.add("双河镇");
//                list.add("石河子");
//                list.add("三江口");
//                list.add("疏勒河");
//                list.add("石龙");
//                list.add("天门");
//                list.add("天门南");
//                list.add("天桥岭");
//                list.add("汤山城");
//                list.add("通榆");
//                list.add("天柱山");
//                list.add("文昌");
//                list.add("万宁");
//                list.add("渭南南");
//                list.add("五五");
//                list.add("下城子");
//                list.add("新城子");
//                list.add("斜河涧");
//                list.add("新华屯");
//                list.add("新华");
//                list.add("小榄");
//                list.add("兴隆店");
//                list.add("孝南");
//                list.add("犀浦东");
//                list.add("仙桃西");
//                list.add("襄州");
//                list.add("永定");
//                list.add("永丰营");
//                list.add("沿河城");
//                list.add("英吉沙");
//                list.add("阳明堡");
//                list.add("榆树屯");
//                list.add("榆树台");
//                list.add("泽普");
//                list.add("中山北");
//                list.add("中山");
//                list.add("博尔塔拉");
//                list.add("八方山");
//                list.add("毕节");
//                list.add("北票");
//                list.add("宝清");
//                list.add("白沙铺");
//                list.add("白云机场北");
//                list.add("彬州东");
//                list.add("彬州");
//                list.add("长宁");
//                list.add("东二道河");
//                list.add("东莞港");
//                list.add("东莞");
//                list.add("东莞西");
//                list.add("道滘");
//                list.add("丹霞山");
//                list.add("大邑");
//                list.add("额敏");
//                list.add("福海西");
//                list.add("佛坪");
//                list.add("凤台南");
//                list.add("富蕴");
//                list.add("谷城北");
//                list.add("灌云");
//                list.add("怀安");
//                list.add("霍城");
//                list.add("珲春");
//                list.add("厚街");
//                list.add("怀来");
//                list.add("黄流");
//                list.add("虎门北");
//                list.add("虎门");
//                list.add("淮南南");
//                list.add("含山南");
//                list.add("黑山寺");
//                list.add("花山镇");
//                list.add("槐荫");
//                list.add("鄠邑");
//                list.add("建德");
//                list.add("莒南北");
//                list.add("吉水西");
//                list.add("嘉峪关南");
//                list.add("金银潭");
//                list.add("焦作西");
//                list.add("昆玉");
//                list.add("寮步");
//                list.add("临沧");
//                list.add("乐东");
//                list.add("临高南");
//                list.add("麓谷");
//                list.add("庐江西");
//                list.add("六盘水东");
//                list.add("礼泉南");
//                list.add("郎溪南");
//                list.add("阆中");
//                list.add("帽儿山西");
//                list.add("米易东");
//                list.add("宁城");
//                list.add("南江口");
//                list.add("南阳寨");
//                list.add("蒲江");
//                list.add("屏山");
//                list.add("清城");
//                list.add("曲江");
//                list.add("清水北");
//                list.add("庆阳");
//                list.add("肃北");
//                list.add("双河市");
//                list.add("双龙湖");
//                list.add("商丘东");
//                list.add("松山湖北");
//                list.add("松阳");
//                list.add("桑植");
//                list.add("塔城");
//                list.add("太谷东");
//                list.add("天河机场");
//                list.add("托克托东");
//                list.add("图木舒克");
//                list.add("武当山西");
//                list.add("芜湖北");
//                list.add("乌龙泉南");
//                list.add("乌兰木图");
//                list.add("吴忠");
//                list.add("雄安");
//                list.add("新干东");
//                list.add("小金口");
//                list.add("西平西");
//                list.add("仙桃");
//                list.add("兴文");
//                list.add("襄垣东");
//                list.add("盐边");
//                list.add("宜宾西");
//                list.add("阳高南");
//                list.add("尉犁");
//                list.add("鄢陵");
//                list.add("元谋西");
//                list.add("云南驿");
//                list.add("延平东");
//                list.add("延平");
//                list.add("银瓶");
//                list.add("延平西");
//                list.add("阳泉东");
//                list.add("永仁");
//                list.add("颍上北");
//                list.add("榆社西");
//                list.add("扬州东");
//                list.add("榆中");
//                list.add("准东");
//                list.add("准格尔");
//                list.add("政和");
//                list.add("珠海长隆");
                list.add("中堂");
                list.add("支提山");
                list.add("中卫南");
                list.add("镇雄");
                for (int i1 = 0; i1 < list.size(); i1++) {
                    String s = list.get(i1);

                 String str=  HttpUtil.sendGet(
                         "https://amap.com/service/poiTipslite?&city=370900&geoobj=117.028183%7C36.170088%7C117.042991%7C36.17685&words="+( s + "站"),"");

                    AmapResponse response = JsonUtil.toObject(str, AmapResponse.class);

                    System.out.println(response);

                    //amap
//                    GeoCode geoCode = null;
//                    GeoCodeRequest request = new GeoCodeRequest();
//                    request.setAddress(s + "高铁站");
//                    try {
//                        GeoCodeResponse response = aMapService.execute(request, new ResponseType<GeoCodeResponse>() {
//                        });
//                        if (!response.getGeocodes().isEmpty()) {
//                            geoCode = response.getGeocodes().get(0);
//                        }
//                    } catch (Exception e) {
//                        e.getLocalizedMessage();
//                    }


//                                if (geoCode != null) {
//                                    oos.write("('" + i1 + "','" + arrs[2] + "','" + arrs[1] + "','" + arrs[3].toUpperCase() + "','" + ZHConverter.convert(arrs[1], ZHConverter.TRADITIONAL)
//                                            + "' ,'CN','" + geoCode.getProvince() + "', '" + geoCode.getCity() + "', '" + geoCode.getDistrict() + "', 'geoRange4', 'CN/', '中国/', '" + arrs[3].toUpperCase() + "|" + arrs[0].toUpperCase() + "|" + arrs[4].toUpperCase() + "', " + i1 + "),");
//                                    oos.write("\n");
//                                } else {
//                                    oos.write("('" + i1 + "','" + arrs[2] + "','" + arrs[1] + "','" + arrs[3].toUpperCase() + "','" + ZHConverter.convert(arrs[1], ZHConverter.TRADITIONAL)
//                                            + "' ,'CN', 'geoRange1', 'geoRange2', 'geoRange3', 'geoRange4', 'CN/', '中国/', '" + arrs[3].toUpperCase() + "|" + arrs[0].toUpperCase() + "|" + arrs[4].toUpperCase() + "', " + i1 + "),");
//                                    oos.write("\n");
//                                }
//                    if (geoCode != null) {
//                        oos.write("update node_railway_station set geoRange1='" + geoCode.getProvince() + "', geoRange2='" + geoCode.getCity() + "', geoRange3='"
//                                + geoCode.getDistrict() + "' where name='" + s + "';");
//                        oos.write("\n");
//                    } else {
//                        reDo.add(s);
////                                   "nothing"
//                    }
//                                code.put(arrs[2], arrs[2]);
//                    }

                    //amap
                }

            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            read.close();
            oos.flush();
            oos.close();
            for (String s : reDo) {
                System.out.println("list.add(\"" + s + "\");");
            }
        }
    }


    public static void main(String[] args) throws Exception {
//        https://kyfw.12306.cn/otn/resources/js/framework/station_name.js
        BankInfoData.readData("/Users/siqishangshu/Downloads/station_name.js");
    }
}
