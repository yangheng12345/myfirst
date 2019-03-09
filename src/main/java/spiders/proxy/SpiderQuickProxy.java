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
 * 快代理
 * 网址：https://www.kuaidaili.com/free/intr/
 */
public class SpiderQuickProxy {

    /**
     * 网站首页，这里只尝试进行解析了匿名的url
     */
    public final static String query_url = "https://www.kuaidaili.com/free/inha/";

    private static List<ProxyResponseDto> getList(String url, List<ProxyResponseDto> proxyResponseDtoList) {
        try {
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, HeaderUtils.makeCommonRequestHeaders());
            String content = httpClientResult.getContent();
            if (StringUtils.isNotEmpty(content)) {
                Document parse = Jsoup.parse(content);
                Elements elements = parse.getElementById("listnav").getElementsByTag("a");
                if (CollectionUtils.isNotEmpty(elements)) {
                    //首先解析第一页
                    List<ProxyResponseDto> responseDtos = parseResult(parse);
                    if (CollectionUtils.isNotEmpty(responseDtos)) {
                        proxyResponseDtoList.addAll(responseDtos);
                    }
                    String text = elements.get(elements.size() - 1).text();
                    int number = Integer.parseInt(text);
                    //按照总页数进行翻页即可
                    for (int i = 2; i <= number; i++) {
                        String nextUrl = query_url + i + "/";
                        httpClientResult = HttpClientUtils.doGet(nextUrl, HeaderUtils.makeCommonRequestHeaders());
                        content = httpClientResult.getContent();
                        if (StringUtils.isNotEmpty(content)) {
                            parse = Jsoup.parse(content);
                            responseDtos = parseResult(parse);
                            if (CollectionUtils.isNotEmpty(responseDtos)) {
                                proxyResponseDtoList.addAll(responseDtos);
                            } else {
                                //-10 进行重试机制
                                // 快代理会存在访问太快封ip的情况，这个地方建议拿到ip后，用他的ip去进行访问，
                                // 要是自己把自己封了就厉害了
                                //这个地方尽量不要调用的太快了，快代理有2000多页，为我们免费提供的ip的服务器考虑一下
                                Thread.sleep(5000L);
                                System.out.println(content);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyResponseDtoList;
    }

    /**
     * 代理内容解析
     *
     * @param parse
     * @return
     */
    private static List<ProxyResponseDto> parseResult(Document parse) {
        List<ProxyResponseDto> proxyResponseDtoList = new ArrayList<>();
        Element list = parse.getElementById("list");
        if (list != null) {
            Elements tbodys = list.getElementsByTag("tbody");
            if (CollectionUtils.isNotEmpty(tbodys)) {
                Element tbody = tbodys.get(0);
                Elements trs = tbody.getElementsByTag("tr");
                if (CollectionUtils.isNotEmpty(trs)) {
                    for (Element tr : trs) {
                        Elements tds = tr.getElementsByTag("td");
                        ProxyResponseDto proxyResponseDto = new ProxyResponseDto();
                        proxyResponseDto.setIp(tds.get(0).text().trim());
                        proxyResponseDto.setPort(tds.get(1).text().trim());
                        proxyResponseDto.setAdderssName(tds.get(4).text().trim());
                        proxyResponseDto.setLastCheckTime(tds.get(6).text().trim());
                        proxyResponseDtoList.add(proxyResponseDto);
                    }
                }
            }
        }
        return proxyResponseDtoList;
    }

    public static void main(String[] args) {
        List<ProxyResponseDto> list = new ArrayList<>();
        getList(query_url, list);
        System.out.println("代理总数量" + list.size());
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProxyResponseDto proxyResponseDto : list) {
                System.out.println(proxyResponseDto);
            }
        }
    }
}
