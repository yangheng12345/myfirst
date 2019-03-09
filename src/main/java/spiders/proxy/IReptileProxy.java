package spiders.proxy;

import spiders.model.response.ProxyResponseDto;
import spiders.model.request.ProxyRequestDto;
import java.util.List;

/**
 * 总出口，获取所有的代理信息
 */
public interface IReptileProxy {
    List<ProxyResponseDto> getProxyList(ProxyRequestDto requestDto);
}
