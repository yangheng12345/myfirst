package spiders.proxy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spiders.model.response.ProxyResponseDto;
import spiders.util.HeaderUtils;
import spiders.util.HttpClientResult;
import spiders.util.HttpClientUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JianShuProxy {
    private static HashMap<String,String> hashMap = new HashMap<>();
    public final static String query_url = "https://www.jianshu.com";
    private static List<ProxyResponseDto> getList(String url, List<ProxyResponseDto> proxyResponseDtoList){
        try {
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, HeaderUtils.makeCommonRequestHeaders());
            System.out.println(url);
            String content = httpClientResult.getContent();
            if(StringUtils.isNotEmpty(content)){
                Elements elementsByTag = Jsoup.parse(content).getElementsByTag("a");
                if(CollectionUtils.isNotEmpty(elementsByTag)){
                    for (int i = 0 ; i < elementsByTag.size() ; i++){

                        String href = elementsByTag.get(i).attr("href");
                        if(href.startsWith("/p/")){

                            String s = hashMap.get(href);
                            if(StringUtils.isEmpty(s)) {
                                hashMap.put(href,href);
                                getList(query_url + href, null);

                            }
                        }
                    }
                }else {
                    System.out.println(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyResponseDtoList;
    }


    public static void main(String[] args) {
        List<ProxyResponseDto> list = new ArrayList<>();
        getList(query_url,list);
        System.out.println("代理总数量"+list.size());
        if(CollectionUtils.isNotEmpty(list)){
            for (ProxyResponseDto proxyResponseDto : list){
                System.out.println(proxyResponseDto);
            }
        }
    }
}
