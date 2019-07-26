package cn.siqishangshu.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.siqishangshu.robot.StatsGovCnGetter;

/**
 * Function: HtmlParser <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-25 17:05:00
 */


public class HtmlParser {
    protected List<List<String>> data = new LinkedList<List<String>>();

    /**
     * 获取value值
     */
    public static String getValue(Element e) {
        return e.attr("value");
    }

    /**
     * 获取
     * <tr>
     * 和
     * </tr>
     * 之间的文本
     */
    public static String getText(Element e) {
        return e.text();
    }

    /**
     * 识别属性id的标签,一般一个html页面id唯一
     */
    public static Element getID(String body, String id) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        Elements elements = doc.select("#" + id);
        // 返回第一个
        return elements.first();
    }

    /**
     * 识别属性class的标签
     */
    public static Elements getClassTag(String body, String classTag) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        return doc.select("." + classTag);
    }

    /**
     * 获取tr标签元素组
     */
    public static Elements getTR(Element e) {
        return e.getElementsByTag("tr");
    }

    /**
     * 获取td标签元素组
     */
    public static Elements getTD(Element e) {
        return e.getElementsByTag("td");
    }

    /**
     * 获取表元组
     */
    public static List<List<String>> getTables(Element table) {
        List<List<String>> data = new ArrayList<>();

        for (Element etr : table.select("tr")) {
            List<String> list = new ArrayList<>();
            for (Element etd : etr.select("td")) {
                String temp = etd.text();
                //增加一行中的一列
                list.add(temp);
            }
            //增加一行
            data.add(list);
        }
        return data;
    }

    /**
     * 读html文件
     */
    public static String readHtml(String fileName) {
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        List<Area> areas = new ArrayList<>();

        int level = 1;
        String parentId = "0";
        String index = "";
        goNextLevel(areas, level, parentId, index, "");

    }

    /**
     * 没有拉第居委会层数据
     */
    private static void goNextLevel(List<Area> areas, int level, String parentId, String index, String codeP) {
        String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
        String body = StatsGovCnGetter.getDataFromURL(baseUrl + index);
        Document doc = Jsoup.parse(body);
        // 获取html的标题
        String title = doc.select("title").text();
        System.out.println(title);
        int order = 0;
        Elements elements = doc.getElementsByTag("a");
        if (index == "") {
            for (Element element : elements) {
                String name = element.childNodes().get(0).toString();
                if ("京ICP备05034670号".contains(name)) {
                    continue;
                }
                String next = element.attr("href");
                String code = next.replace(".html", "");
                System.out.println("next:" + next);
                if (next.contains("www.miibeian.gov.cn")) {
                    continue;
                }
                Area area = new Area();
                area.setDistrictCode(code);
                area.setName(name);
                area.setOrdinal(order++);
                area.setParentId(parentId);
                area.setLevel(level);
                areas.add(area);
                goNextLevel(areas, level++, code, next, code);
                try {
                    File file = new File(name + "_area.sql");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    for (Area area1 : areas) {
                        fileWriter.write(area1.toString());
                        fileWriter.write("\n\r");
                    }
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    areas.clear();
                }
            }
        } else {
            for (int i = 0; i < elements.size(); i++) {
                if ("京ICP备05034670号".contains(elements.get(i).childNodes().get(0).toString())) {
                    continue;
                }
                String code = elements.get(i).childNodes().get(0).toString();
                String name = elements.get(i + 1).childNodes().get(0).toString();
                Area area = new Area();
                area.setDistrictCode(code);
                area.setName(name);
                area.setOrdinal(order++);
                area.setParentId(parentId);
                area.setLevel(level);
                areas.add(area);
                String next = elements.get(i + 1).attr("href");
                if (next != null && level < 3) {
                    if (level == 2) {
                        next = codeP + "/" + next;
                    }
                    if (next.contains("www.miibeian.gov.cn")) {
                        continue;
                    }
                    System.out.println("next:" + next);
                    goNextLevel(areas, level + 1, code, next, codeP);
                }
                i++;

            }

        }


    }

}