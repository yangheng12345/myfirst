package fiddler;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类的目标是，通过fiddler请求的Raw自动生成我们java代码
 */
public class FiddlerForJava {

    //请求结果放入类中
    private static StringBuilder result = new StringBuilder();

    private static List<NameValuePair> headers = new ArrayList<>();

    private static List<NameValuePair> bodys = Lists.newArrayList();

    private static String className = "Demo";

    private static String url = "";

    public static void main(String[] args) {
        System.out.println("hello is my first");
        System.out.println("------start-----------");
        //第一步，读取Raw文件
        try {
            FileReader fr = new FileReader("D:\\testproject\\nettyTest\\myfirst\\src\\main\\java\\fiddler\\raw.txt");
            BufferedReader bf = new BufferedReader(fr);

            String str = "";
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                byRowAddResult(str);
                System.out.println(str);
            }

        } catch (Exception e) {

        }
        System.out.println("------end-----------");
    }

    private static void byRowAddResult(String str) {
        String heard = getHeard(str);
        if (StringUtils.isNotEmpty(heard)) {
            switch (heard) {
                case "GET":
                case "POST":
                    appendPostOrGet(str);
                    break;
                case "Accept-Language:":
                case "Referer:":
                case "x-requested-with:":
                case "Content-Type:":
                case "Accept-Encoding:":
                case "User-Agent:":
                case "Host:":
                case "Proxy-Connection:":
                case "Pragma:":
                case "x-prototype-version:":
                case "Accept:":
                    appendHeader(heard, str);
                    break;
                case "Cookie:":
                case "Content-Length:":
                case "UA-CPU:":
                    break;
                default:
                    appendBody(heard, str);
            }
        }
    }

    private static void appendBody(String heard, String str) {
        if (StringUtils.isNotEmpty(str) && str.contains("=")) {
            String[] split = str.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    if (StringUtils.isNotEmpty(s)) {
                        String[] strings = s.split("=");
                        if (strings != null && strings.length > 1) {
                            NameValuePair nameValuePair = new BasicNameValuePair(strings[0], strings[1]);
                            bodys.add(nameValuePair);
                        } else if (strings != null && strings.length > 0) {
                            NameValuePair nameValuePair = new BasicNameValuePair(strings[0], "");
                            bodys.add(nameValuePair);
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加头部代码
     *
     * @param heard
     * @param str
     */
    private static void appendHeader(String heard, String str) {
        String substring = str.substring(str.indexOf(heard));
        NameValuePair nameValuePair = new BasicNameValuePair(heard, substring);
        headers.add(nameValuePair);
    }

    /**
     * 获取头部信息
     *
     * @param lineStr
     * @return
     */
    private static String getHeard(String lineStr) {
        if (lineStr != null && StringUtils.isNotEmpty(lineStr) && StringUtils.isNotEmpty(lineStr.trim())) {
            String[] split = lineStr.split(" ");
            if (split != null && split.length > 0) {
                return split[0];
            }
        }
        return null;
    }

    /**
     * 添加post or get代码
     *
     * @param lineStr
     */
    private static void appendPostOrGet(String lineStr) {
//        System.out.println("hello POST");
        String[] split = lineStr.split(" ");
        if (split != null && split.length > 1) {
            url = split[1];
        }
    }

}
