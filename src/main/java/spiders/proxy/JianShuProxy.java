package spiders.proxy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import spiders.util.HeaderUtils;
import spiders.util.HttpClientResult;
import spiders.util.HttpClientUtils;

import java.util.HashMap;

/**
 * 简书深度爬去，先爬取首页数据，然后按照返回页面的url进行深度访问
 */
public class JianShuProxy {
    /**
     * 把所有url放入map中，避免重复爬去
     */
    private static HashMap<String, String> hashMap = new HashMap<>();
    /**
     * 简书首页
     */
    public final static String query_url = "https://www.jianshu.com";

    private static void getList(String url) {
        try {
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, HeaderUtils.makeCommonRequestHeaders());
            System.out.println(url);
            String content = httpClientResult.getContent();
            if (StringUtils.isNotEmpty(content)) {
                Elements elementsByTag = Jsoup.parse(content).getElementsByTag("a");
                if (CollectionUtils.isNotEmpty(elementsByTag)) {
                    for (int i = 0; i < elementsByTag.size(); i++) {
                        //获取到深度url
                        //访问url例如：https://www.jianshu.com/p/384954e2caef?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
                        String href = elementsByTag.get(i).attr("href");
                        if (href.startsWith("/p/")) {
                            String s = hashMap.get(href);
                            if (StringUtils.isEmpty(s)) {
                                hashMap.put(href, href);
                                //递归的方式进行爬去，尝试运行了一个小时，大约每秒三次访问，发现根本停不下来
                                getList(query_url + href);
                            }
                        }
                    }
                } else {
                    System.out.println(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        getList(query_url);
    }
}
