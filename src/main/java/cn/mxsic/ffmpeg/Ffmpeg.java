package cn.mxsic.ffmpeg;

import java.util.List;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-05-08 15:08:00
 */
public class Ffmpeg {

    public static void main(String[] args) {

//视频文件

//        String videoRealPath = "/Users/siqishangshu/Downloads/6.mp4";
        String videoRealPath = "/Users/siqishangshu/Downloads/wKgAHlryU-mAWz1GAENjeorzUhs047.flv";
//        String videoRealPath = "http://dev.3zang.net:91/files/M00/00/1B/rBAJd1rxUvaAJIKQAAV_2tY99dQ118.mp4";

        //截图的路径（输出路径）

        String imageRealPath = "/Users/siqishangshu/Downloads/NEW.jpg";

        //调用批处理文件
//            Runtime.getRuntime().exec("cmd /c start F://ffmpeg.bat " + videoRealPath + " " + imageRealPath);
//        ffmpeg -i 6.mp4 -ss 00:10:00 -f image2 output.jpg
//        ffmpeg -i 6.mp4 -r 1 -s 300x300 -f image2 foo-03d.jpeg
        List<String> commend = new java.util.ArrayList<>();
        commend.add("ffmpeg"); // 视频提取工具的位置
        commend.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        commend.add(videoRealPath); // 视频文件路径
        commend.add("-y");
        commend.add("-f");
        commend.add("image2");
        commend.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        commend.add("1"); // 添加起始时间为第1秒
        commend.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        commend.add("0.001"); // 添加持续时间为1毫秒
//        commend.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
//        commend.add("400*600"); // 添加截取的图片大小为800*600
        commend.add(imageRealPath); // 添加截取的图片的保存路径
////

//        commend.add("ffmpeg");
//        commend.add("-i");
//        commend.add(videoRealPath);
//        commend.add("-r");
//        commend.add("1");
//        commend.add("-s");
//        commend.add("-s");
//        commend.add("300x300");
//        commend.add("-f");
//        commend.add("image2");
//        commend.add(imageRealPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            System.out.println(commend.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
