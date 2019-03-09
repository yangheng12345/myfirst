package spiders.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HeaderUtils {
    /**
     * URL scheme 指示符
     */
    public static final String URL_SECHEME_SUFFIX = "://";
    /**
     * URL 目录分隔符
     */
    public static final String URL_PATH_SEPARATOR = "/";
    /**
     * URL 参数标识符
     */
    public static final String URL_PARAM_FLAG = "?";
    public static final NameValuePair REQ_HEADER_ACCEPT_ALL = new BasicNameValuePair("Accept", "*/*");
    public static final NameValuePair REQ_HEADER_ACCEPT_XHTML = new BasicNameValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    public static final NameValuePair REQ_HEADER_ACCEPT_JSON = new BasicNameValuePair("Accept", "application/json");
    public static final NameValuePair REQ_HEADER_ACCEPT_JS = new BasicNameValuePair("Accept", "text/javascript, */*; q=0.01Z");
    public static final NameValuePair REQ_HEADER_ACCEPT_IMG = new BasicNameValuePair("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
    public static final NameValuePair REQ_HEADER_ACCEPT_TEXT = new BasicNameValuePair("Accept", "text/plain, */*; q=0.01");

    public static final NameValuePair REQ_HEADER_ACCEPT_ENCODING_GZIP = new BasicNameValuePair("Accept-Encoding", "gzip, deflate, br");
    public static final NameValuePair REQ_HEADER_ACCEPT_LANGUAGE_ALL = new BasicNameValuePair("Accept-Language", "zh-CN,zh;q=0.8,en-us;q=0.5,en;q=0.3,*;q=0.2");
    public static final NameValuePair REQ_HEADER_ACCEPT_LANGUAGE_ZH = new BasicNameValuePair("Accept-Language", "zh-CN,zh;q=0.9");
    public static final NameValuePair REQ_HEADER_ACCEPT_LANGUAGE_EN = new BasicNameValuePair("Accept-Language", "en-US,en;q=0.5");

    public static final NameValuePair REQ_HEADER_CONTENT_TYPE_FORM = new BasicNameValuePair("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
    public static final NameValuePair REQ_HEADER_CONTENT_TYPE_JSON = new BasicNameValuePair("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
    public static final NameValuePair REQ_HEADER_CONTENT_TYPE_XML = new BasicNameValuePair("Content-Type", ContentType.APPLICATION_XML.getMimeType());
    public static final NameValuePair REQ_HEADER_CONTENT_TYPE_PLAIN = new BasicNameValuePair("Content-Type", ContentType.TEXT_PLAIN.getMimeType());

    public static final NameValuePair REQ_HEADER_CACHE_CONTROL_NO_CACHE = new BasicNameValuePair("Cache-Control", "no-cache");
    public static final NameValuePair REQ_HEADER_PRAGMA_NO_CACHE = new BasicNameValuePair("Pragma", "no-cache");
    public static final NameValuePair REQ_HEADER_CONNECTION_KEEP_ALIVE = new BasicNameValuePair("Connection", "keep-alive");
    public static final NameValuePair REQ_HEADER_CONNECTION_CLOSE = new BasicNameValuePair("Connection", "close");

    public static final NameValuePair REQ_HEADER_USER_AGENT_CHROME = new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
    public static final NameValuePair REQ_HEADER_USER_AGENT_FIREFOX = new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
    public static final NameValuePair REQ_HEADER_USER_AGENT_IE = new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
    public static final NameValuePair REQ_HEADER_USER_AGENT_DEFAULT = REQ_HEADER_USER_AGENT_CHROME;

    public static final NameValuePair REQ_HEADER_X_REQUESTED_WITH = new BasicNameValuePair("X-Requested-With", "XMLHttpRequest");

    public static final List<NameValuePair> makeCommonRequestHeaders() {
        return makeCommonRequestHeaders(null, null, null);
    }

    public static final List<NameValuePair> makeCommonRequestHeaders(String accept, String userAgent, String host) {
        List<NameValuePair> headers = new ArrayList<>(10);

        if (StringUtils.isNotEmpty(accept)) {
            headers.add(new BasicNameValuePair("Accept", accept));
        } else {
            headers.add(REQ_HEADER_ACCEPT_ALL);
        }
        if (StringUtils.isNotEmpty(userAgent)) {
            headers.add(new BasicNameValuePair("User-Agent", userAgent));
        } else {
            headers.add(REQ_HEADER_USER_AGENT_DEFAULT);
        }

        headers.add(REQ_HEADER_ACCEPT_ENCODING_GZIP);
        headers.add(REQ_HEADER_ACCEPT_LANGUAGE_ALL);
        headers.add(REQ_HEADER_CACHE_CONTROL_NO_CACHE);
        headers.add(REQ_HEADER_PRAGMA_NO_CACHE);
        headers.add(REQ_HEADER_CONNECTION_KEEP_ALIVE);

        if (StringUtils.isNotEmpty(host)){
            headers.add(makeRequestHeaderHost(host));
        }

        return headers;
    }

    public static final NameValuePair makeRequestHeaderHost(String url) {
        return new BasicNameValuePair("Host", getUrlHost(url));
    }

    /**
     * 获取 URL 地址的主机段
     */
    public static final String getUrlHost(String url) {
        int p1 = url.indexOf(URL_SECHEME_SUFFIX);
        if (p1 != -1) {
            url = url.substring(p1 + 3);
            return getUrlHost(url);
        } else {
            int p2 = url.indexOf(URL_PATH_SEPARATOR);
            if (p2 != -1) {
                return url.substring(0, p2);
            } else {
                int p3 = url.indexOf(URL_PARAM_FLAG);
                if (p3 != -1) {
                    return url.substring(0, p3);
                } else {
                    return url;
                }
            }
        }
    }

}
