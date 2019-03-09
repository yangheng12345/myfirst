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
import java.util.List;

/**
 *  89免费代理
 *  网址：http://www.89ip.cn/
 */
public class SpiderEightNineProxy {

    public final static String query_url = "http://www.89ip.cn/";
    private static List<ProxyResponseDto> getList(String url,List<ProxyResponseDto> proxyResponseDtoList){
        try {
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, HeaderUtils.makeCommonRequestHeaders());
            String content = httpClientResult.getContent();
            if(StringUtils.isNotEmpty(content)){
                Document parse = Jsoup.parse(content);
                Elements table = parse.getElementsByClass("layui-table");
                if(CollectionUtils.isNotEmpty(table)){
                    Element element = table.get(0);
                    Elements tbodys = element.getElementsByTag("tbody");
                    Element tbody = tbodys.get(0);
                    Elements trs = tbody.getElementsByTag("tr");

                    if(CollectionUtils.isNotEmpty(trs)){
                        proxyResponseDtoList.addAll(parseResult(trs));
                        Elements elementsByClass = parse.getElementsByClass("layui-laypage-next");
                        String href = elementsByClass.get(0).attr("href");
//                        elementsByClass.get(0).get
                        String nextUrl = query_url +"/"+href ;
                        System.out.println(href);
                        getList(nextUrl,proxyResponseDtoList);
                        //
                    }else {
                        return proxyResponseDtoList;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyResponseDtoList;
    }

    private static List<ProxyResponseDto> parseResult(Elements trs) {
        List<ProxyResponseDto> proxyResponseDtoList = new ArrayList<>();
        for(Element element : trs){
            Elements tds = element.getElementsByTag("td");
            ProxyResponseDto proxyResponseDto = new ProxyResponseDto();
            proxyResponseDto.setIp(tds.get(0).text().trim());
            proxyResponseDto.setPort(tds.get(1).text().trim());
            proxyResponseDto.setAdderssName(tds.get(2).text().trim());
            proxyResponseDto.setOperators(tds.get(3).text().trim());
            proxyResponseDto.setLastCheckTime(tds.get(4).text().trim());
            proxyResponseDtoList.add(proxyResponseDto);
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
