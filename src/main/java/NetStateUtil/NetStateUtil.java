package NetStateUtil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class NetStateUtil {
    static HostnameVerifier hv = new HostnameVerifier() {
        @Override
        public boolean verify(String urlHostName, SSLSession session) {
            return true;
        }
    };

    public String connectingAddress(String remoteInetAddr) {
        boolean flag = false;
        String tempUrl = remoteInetAddr.substring(0, 5);//取出地址前5位
        if (tempUrl.contains("http")) {//判断传过来的地址中是否有http
            if (tempUrl.equals("https")) {//判断服务器是否是https协议
                try {
                    trustAllHttpsCertificates();//当协议是https时
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HttpsURLConnection.setDefaultHostnameVerifier(hv);//当协议是https时
            }
            flag = isConnServerByHttp(remoteInetAddr);
        } else {//传过来的是IP地址
            flag = isReachable(remoteInetAddr);
        }
        if (flag) {
            return "正常";
        } else {
            return "异常";
        }
    }

    public static void main(String[] args) {
        boolean reachable = isReachable("127.0.0.1");
        reachable = isReachable("221.229.166.112");
        System.out.println(reachable);
        System.out.println("---------------------");
//        boolean hostConnectable = isHostConnectable("58.218.200.13", 10818);
//        System.out.println(hostConnectable);
        url();
    }

    public static void url(){
        try {
            URL url = new URL("https://blog.csdn.net/wenxingchen/article/details/78530204");
            InetSocketAddress addr = new InetSocketAddress("58.218.213.58",10218);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
            URLConnection connection = url.openConnection(proxy);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) >= 0) {
                System.out.println(new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    /**
     * 传入需要连接的IP，返回是否连接成功
     *
     * @param remoteInetAddr
     * @return
     */
    public static boolean isReachable(String remoteInetAddr) {// IP地址是否可达，相当于Ping命令
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(remoteInetAddr);
            reachable = address.isReachable(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reachable;
    }

    public static boolean isConnServerByHttp(String serverUrl) {// 服务器是否开启
        boolean connFlag = false;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(serverUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            if (conn.getResponseCode() == 200) {// 如果连接成功则设置为true
                connFlag = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return connFlag;
    }

    /*以下是Https适用*/
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }
}
