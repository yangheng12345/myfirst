package fiddler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
public class Demo {
    public static void main(String[] args) {
        String url = "http://10.134.132.176:8000/prpall/business/editCitemCar.do?editType=NEW&bizType=PROPOSAL&bizNo=&riskCode=DAA&applyNo=&startDate=2018-12-22&endDate=2019-12-21&startHour=0&endHour=24&endorType=&taskID_Ppms=&prpallLinkPpmsFlag=&operateDate=2018-12-21&motorFastTrack=&operatorProjectCode=KBDAA201841010000000139,KBDAA201841010000000203&reload=&rnd320=Fri%20Dec%2021%2021:00:58%20UTC+0800%202018";
        NameValuePair Accept = new BasicNameValuePair("Accept", "Accept: */*");
        NameValuePair Accept_Language = new BasicNameValuePair("Accept-Language", "Accept-Language: zh-cn");
        NameValuePair Referer = new BasicNameValuePair("Referer", "Referer: http://10.134.132.176:8000/prpall/business/prepareEdit.do?bizType=PROPOSAL&editType=NEW&isEnterPrjectFlag=N&operatorProjectCode=KBDAA201841010000000139,KBDAA201841010000000203");
        NameValuePair x_requested_with = new BasicNameValuePair("x-requested-with", "x-requested-with: XMLHttpRequest");
        NameValuePair Accept_Encoding = new BasicNameValuePair("Accept-Encoding", "Accept-Encoding: gzip, deflate");
        NameValuePair User_Agent = new BasicNameValuePair("User-Agent", "User-Agent: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E; Tablet PC 2.0)");
        NameValuePair Host = new BasicNameValuePair("Host", "Host: 10.134.132.176:8000");
        NameValuePair Proxy_Connection = new BasicNameValuePair("Proxy-Connection", "Proxy-Connection: Keep-Alive");


    }
}

