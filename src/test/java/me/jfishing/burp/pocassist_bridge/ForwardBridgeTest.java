package me.jfishing.burp.pocassist_bridge;

import java.io.IOException;

public class ForwardBridgeTest {

    public static void main(String[] args) {
        ForwardBridge forwardBridge = new ForwardBridge();
        try {
            String result = forwardBridge.forward(("POST /ruoyi-admin/tool/gen/list HTTP/1.1\n" +
                    "Host: 192.168.31.42:8081\n" +
                    "Content-Length: 126\n" +
                    "Accept: application/json, text/javascript, */*; q=0.01\n" +
                    "X-Requested-With: XMLHttpRequest\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Origin: http://192.168.31.42:8081\n" +
                    "Referer: http://192.168.31.42:8081/ruoyi-admin/tool/gen\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                    "Cookie: JSESSIONID=99478933-7790-46cf-9b0d-f74ec5c8ecd5; _secretKey=3101dd471a1f475d8008b82d2b28a0d4\n" +
                    "Connection: close\n" +
                    "\n" +
                    "pageSize=10&pageNum=1&orderByColumn=createTime&isAsc=desc&tableName=&tableComment=&params%5BbeginTime%5D=&params%5BendTime%5D=\n").getBytes());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
