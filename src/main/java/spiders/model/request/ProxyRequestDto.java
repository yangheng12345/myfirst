package spiders.model.request;

import lombok.Data;

@Data
public class ProxyRequestDto {
    private Integer number;
    private String cityName;
    private String provinceName;
    private String country;
    private String proxyType;
}
