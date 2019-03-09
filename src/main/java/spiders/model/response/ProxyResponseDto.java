package spiders.model.response;

import lombok.Data;

@Data
public class ProxyResponseDto {
    /**
     * ip
     */
    private String ip;

    /**
     * 端口号
     */
    private String port;

    /**
     * 代理类型 http,https,未知
     */
    private String proxyType;

    /**
     * 国家名称
     */
    private String country;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 详情地址
     */
    private String adderssName;

    /**
     * 该代理检测时间
     */
    private Integer responseTime;

    /**
     * 运营商
     */
    private String operators;

    /**
     * 最后检测
     */
    private String lastCheckTime;

    @Override
    public String toString() {
        return "代理信息{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", 代理类型='" + proxyType + '\'' +
                ", 国家='" + country + '\'' +
                ", 省份='" + provinceName + '\'' +
                ", 城市名称='" + cityName + '\'' +
                ", 详细地址='" + adderssName + '\'' +
                ", 响应时间=" + responseTime +
                ", 运营商='" + operators + '\'' +
                ", 最后检查时间='" + lastCheckTime + '\'' +
                '}';
    }
}
