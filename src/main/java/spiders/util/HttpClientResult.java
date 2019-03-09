package spiders.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class HttpClientResult implements Serializable {
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(){

    }

    public HttpClientResult(int code){
        this.code = code;
    }
    public HttpClientResult(int code,String content){
        this.code = code;
        this.content = content;
    }
}
