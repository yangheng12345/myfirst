package fiddler;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 这个类的目标是，通过fiddler请求的Raw自动生成我们java代码
 */
public class FiddlerForJava {
    //    GET("GET "),
//    POST("POST"),
//    ACCEPT("Accept"),
//    ACCEPT_LANGUAGE("Accept-Language"),
//    REFERER("Referer"),
//    X_REQUESTED_WITH("x-requested-with"),
//    CONTENT_TYPE("Content-Type"),
//    UA_CPU("UA-CPU"),
//    ACCEPT_ENCODING("Accept-Encoding"),
//    USER_AGENT("User-Agent"),
//    HOST("Host"),
//    CONTENT_LENGTH("Content-Length"),
//    PROXY_CONNECTION("Proxy-Connection"),
//    PRAGMA("Pragma"),
//    COOKIE("Cookie"),
//    X_PROTOTYPE_VERSION("x-prototype-version")
    public static void main(String[] args) {
        System.out.println("hello is my first");
        System.out.println("------start-----------");
        //第一步，读取Raw文件
        try {
//            InputStream is = new FileInputStream("D:\\testproject\\nettyTest\\myfirst\\src\\main\\java\\fiddler\\raw.txt");
            FileReader fr = new FileReader("D:\\testproject\\nettyTest\\myfirst\\src\\main\\java\\fiddler\\raw.txt");
            BufferedReader bf = new BufferedReader(fr);
            StringBuilder result = new StringBuilder();
            String str = "";
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                byRowAddResult(result, str);
                System.out.println(str);
            }

        } catch (Exception e) {

        }
        System.out.println("------end-----------");
    }

    private static void byRowAddResult(StringBuilder result, String str) {
        switch (str) {
            case "GET":
                appendGet(result);
                break;
            case "POST":
                appendPost(result);
                break;
        }
    }

    /**
     * 添加post代码
     * @param result
     */
    private static void appendPost(StringBuilder result) {
    }

    /**
     * 添加get代码
     * @param result
     */
    private static void appendGet(StringBuilder result) {

    }
}
