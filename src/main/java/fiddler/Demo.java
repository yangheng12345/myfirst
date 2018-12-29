package fiddler;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
public class Demo {
    public static void main(String[] args) {
        String url = "http://10.134.132.176:8000/prpall/business/selectRenewal.do?pageSize=10&pageNo=1";
        NameValuePair Accept = new BasicNameValuePair("Accept", "Accept: */*");
        NameValuePair Accept_Language = new BasicNameValuePair("Accept-Language", "Accept-Language: zh-cn");
        NameValuePair Referer = new BasicNameValuePair("Referer", "Referer: http://10.134.132.176:8000/prpall/business/editRenewalSearch.do");
        NameValuePair x_requested_with = new BasicNameValuePair("x-requested-with", "x-requested-with: XMLHttpRequest");
        NameValuePair Content_Type = new BasicNameValuePair("Content-Type", "Content-Type: application/x-www-form-urlencoded; charset=UTF-8");
        NameValuePair Accept_Encoding = new BasicNameValuePair("Accept-Encoding", "Accept-Encoding: gzip, deflate");
        NameValuePair User_Agent = new BasicNameValuePair("User-Agent", "User-Agent: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E; Tablet PC 2.0)");
        NameValuePair Host = new BasicNameValuePair("Host", "Host: 10.134.132.176:8000");
        NameValuePair Proxy_Connection = new BasicNameValuePair("Proxy-Connection", "Proxy-Connection: Keep-Alive");
        NameValuePair Pragma = new BasicNameValuePair("Pragma", "Pragma: no-cache");

        NameValuePair prpCrenewalVo_policyNo = new BasicNameValuePair("prpCrenewalVo.policyNo", "");
        NameValuePair prpCrenewalVo_othFlag = new BasicNameValuePair("prpCrenewalVo.othFlag", "");
        NameValuePair prpCrenewalVo_engineNo = new BasicNameValuePair("prpCrenewalVo.engineNo", "");
        NameValuePair prpCrenewalVo_frameNo = new BasicNameValuePair("prpCrenewalVo.frameNo", "");
        NameValuePair prpCrenewalVo_vinNo = new BasicNameValuePair("prpCrenewalVo.vinNo", "");
        NameValuePair prpCrenewalVo_licenseNo = new BasicNameValuePair("prpCrenewalVo.licenseNo", "%D4%A5A00U38");
        NameValuePair prpCrenewalVo_licenseColorCode = new BasicNameValuePair("prpCrenewalVo.licenseColorCode", "");
        NameValuePair prpCrenewalVo_licenseType = new BasicNameValuePair("prpCrenewalVo.licenseType", "02");
        NameValuePair validateCodeInput = new BasicNameValuePair("validateCodeInput", "");

    }
}
