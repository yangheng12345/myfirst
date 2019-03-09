package spiders.proxy.impl;

import spiders.model.request.ProxyRequestDto;
import spiders.model.response.ProxyResponseDto;
import spiders.proxy.IReptileProxy;

import java.util.List;

public class ReptileProxy implements IReptileProxy {
    @Override
    public List<ProxyResponseDto> getProxyList(ProxyRequestDto requestDto) {
        //直接从数据库中获取即可
        //按照指定的策略，排序后返回
        return null;
    }
}
