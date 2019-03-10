package spiders.proxy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
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
 * <p>
 * 方案一：
 * -10 进行重试机制
 * 快代理会存在访问太快封ip的情况，这个地方建议拿到ip后，用他的ip去进行访问，
 * 要是自己把自己封了就厉害了
 * 这个地方尽量不要调用的太快了，快代理有2000多页，为我们免费提供的ip的服务器考虑一下
 * <p>
 * <p>
 * 方案二：爬去的就是ip池子，你跟我说封ip，开玩笑塞
 * 从第一页开始记录下每页的数据，放入存储集合a中，如果ip被封或者不通的情况，从存储集合中倒叙的取出数据，进行接口重试操作。
 * 重试过程中遇到的假ip记得直接清除掉即可
 * 可优化地方：有的ip是暂时被禁用哦
 * <p>
 * <p>
 * 情景一：正常返回
 * <p>
 * 情景二：返回 -10，表示ip被暂时禁用
 * <p>
 * 情景三：连接拒绝，表示ip被封
 * <p>
 * 情景四：请求参数异常，实际上请求参数是正常的，应该是服务器内部出问题了，一定要注意控制自己的请求频次，适当的sleep
 * 返回
 * <html>
 * <head><title>400 Bad Request</title></head>
 * <body bgcolor="white">
 * <center><h1>400 Bad Request</h1></center>
 * <hr><center>nginx/1.7.10</center>
 * </body>
 * </html>
 * <p>
 * 情景五：对方服务器资源被大量耗用，这个时候一定要注意自己的请求频次，适当的sleep，返回
 * Maximum number of open connections reached.
 *
 * 情景六： The target server failed to respond
 */
public class SpiderQuickProxy {

    private static List<ProxyResponseDto> realUsedList = new ArrayList<>();
    /**
     * 网站首页，这里只尝试进行解析了匿名的url
     */
    public final static String query_url = "https://www.kuaidaili.com/free/inha/";

    private static List<ProxyResponseDto> getList(String url, List<ProxyResponseDto> proxyResponseDtoList) {
        try {
            HttpHost proxy = new HttpHost("122.114.236.226", 65534);
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, HeaderUtils.makeCommonRequestHeaders(), null, proxy);
            String content = httpClientResult.getContent();
            if (StringUtils.isNotEmpty(content)) {
                Document parse = Jsoup.parse(content);
                Elements elements = parse.getElementById("listnav").getElementsByTag("a");
                if (CollectionUtils.isNotEmpty(elements)) {
                    //首先解析第一页
                    System.out.println("开始第一页");
                    List<ProxyResponseDto> responseDtos = parseResult(parse);
                    ProxyResponseDto userProxy = null;
                    //代理检查
                    if (CollectionUtils.isNotEmpty(responseDtos)) {
                        for (ProxyResponseDto proxyResponseDto : responseDtos) {
                            HttpClientResult result = null;
                            try {
                                HttpHost checkProxy = new HttpHost(proxyResponseDto.getIp(), Integer.parseInt(proxyResponseDto.getPort()));
                                result = HttpClientUtils.doGet("https://www.kuaidaili.com/free/inha/", HeaderUtils.makeCommonRequestHeaders(), null, checkProxy);
                            } catch (Exception e) {
                                continue;
                            }
                            String resultContent = result.getContent();
                            if (resultContent.contains("listnav")) {
                                userProxy = proxyResponseDto;
                                realUsedList.add(userProxy);
                            }
                            break;
                        }
                        proxyResponseDtoList.addAll(responseDtos);
                    }
                    String text = elements.get(elements.size() - 1).text();
                    int number = Integer.parseInt(text);
                    //按照总页数进行翻页即可
                    HttpHost checkProxy = null;
                    for (int i = 2; i <= number; i++) {
                        if (userProxy != null) {
                            checkProxy = new HttpHost(userProxy.getIp(), Integer.parseInt(userProxy.getPort()));
                        } else {
                            checkProxy = new HttpHost("122.114.236.226", 65534);
                        }
                        String nextUrl = query_url + i ;
                        System.out.println(nextUrl + ":" + checkProxy.getHostName() + ":" + checkProxy.getPort());
                        int flag = 0;
                        try {
                            long startTime = System.currentTimeMillis();
                            httpClientResult = HttpClientUtils.doGet(nextUrl, HeaderUtils.makeCommonRequestHeaders(), null, checkProxy);
                            long entTime = System.currentTimeMillis();
                            content = httpClientResult.getContent();
                            if (!content.contains("listnav")) {
                                flag = 1;
                            }
//                            //时间超过2秒的接口直接拿掉，太慢了
//                            if((entTime - startTime) > 4000){
//                                System.out.println("超时的接口进行移除操作，ip："+checkProxy.getHostName() + "端口号："+checkProxy.getPort());
//                                flag = 1;
//                            }
                        } catch (Exception e) {
                            flag = 1;
                        }
                        //如果代理坏了，然后进行代理切换
                        if (flag == 1) {
                            List<ProxyResponseDto> removeList = new ArrayList<>();
                            System.out.println("集合中一共有" + proxyResponseDtoList.size() + "条数据,正在进行尝试代理切换");
                            for (int j = proxyResponseDtoList.size() - 1; j > 0; j--) {
                                ProxyResponseDto proxyResponseDto = proxyResponseDtoList.get(j);
                                HttpHost newCheckProxy = null;
                                HttpClientResult result = null;
                                //进行重试
                                System.out.println("进行第" + (proxyResponseDtoList.size() - j) + "次重试，重试ip：" + proxyResponseDto.getIp() + "，重试端口：" + proxyResponseDto.getPort());
                                try {
                                    newCheckProxy = new HttpHost(proxyResponseDto.getIp(), Integer.parseInt(proxyResponseDto.getPort()));
                                    result = HttpClientUtils.doGet(nextUrl, HeaderUtils.makeCommonRequestHeaders(), null, newCheckProxy);
                                } catch (Exception e) {
                                    removeList.add(proxyResponseDto);
                                    continue;
                                }
                                content = result.getContent();
                                if(content.contains("Maximum number of open connections reached")){
                                    System.out.println(content);
                                    //Thread.sleep(5000L);
                                }
                                if (content.contains("-10")) {
                                    //ip短暂被封，这里先不进行移除操作哦
                                    continue;
                                }
                                if (content.contains("listnav")) {
                                    userProxy = proxyResponseDto;
                                    break;
                                }
                            }
                            System.out.println("无效代理清除");
                            proxyResponseDtoList.removeAll(removeList);
                        }
                        if (StringUtils.isNotEmpty(content)) {
                            parse = Jsoup.parse(content);
                            responseDtos = parseResult(parse);
                            if (CollectionUtils.isNotEmpty(responseDtos)) {
                                proxyResponseDtoList.addAll(responseDtos);
                            } else {
                                System.out.println("content内容" + content);
                                System.out.println("---第" + i + "页---------失败了，请及时检查代码逻辑哦-------------------");
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
