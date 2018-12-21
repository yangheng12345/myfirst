package fiddler;

public enum Fiddler {
    GET("GET "),
    POST("POST"),
    ACCEPT("Accept"),
    ACCEPT_LANGUAGE("Accept-Language"),
    REFERER("Referer"),
    X_REQUESTED_WITH("x-requested-with"),
    CONTENT_TYPE("Content-Type"),
    UA_CPU("UA-CPU"),
    ACCEPT_ENCODING("Accept-Encoding"),
    USER_AGENT("User-Agent"),
    HOST("Host"),
    CONTENT_LENGTH("Content-Length"),
    PROXY_CONNECTION("Proxy-Connection"),
    PRAGMA("Pragma"),
    COOKIE("Cookie"),
    X_PROTOTYPE_VERSION("x-prototype-version")
    ;
    private String headName;
    Fiddler(String headName){
        this.headName = headName;
    }
}
