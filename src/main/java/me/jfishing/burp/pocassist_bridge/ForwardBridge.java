package me.jfishing.burp.pocassist_bridge;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ForwardBridge {

    private int contentLength = 482;
    private final String templateRequestPrefix =
            "POST /api/v1/scan/passiveraw/ HTTP/1.1\n" +
                    "Host: 127.0.0.1:1231\n" +
                    "Content-Length: {{length}}\n" +
                    "Accept: application/json, text/plain, */*\n" +
                    "Authorization: JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiJDaGVuankwODEyIiwiZXhwIjoxNjQ5Mzk3MTQ1LCJpc3MiOiJwb2Nhc3Npc3QifQ.NDL376CsKCtvU2EwK3dRNFLU7ROWXAZbyL-PBH0eHyU\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36\n" +
                    "Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryHiRhNJmdch7uHr93\n" +
                    "Origin: http://127.0.0.1:1231\n" +
                    "Referer: http://127.0.0.1:1231/ui/\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                    "Cookie: _secretKey=3101dd471a1f475d8008b82d2b28a0d4\n" +
                    "Connection: close\n" +
                    "\n" +
                    "------WebKitFormBoundaryHiRhNJmdch7uHr93\n" +
                    "Content-Disposition: form-data; name=\"target\"; filename=\"test.raw\"\n" +
                    "Content-Type: image/RAW\n" +
                    "\n";
    private final String templateRequestSuffix =
                    "\n" +
                    "------WebKitFormBoundaryHiRhNJmdch7uHr93\n" +
                    "Content-Disposition: form-data; name=\"type\"\n" +
                    "\n" +
                    "all\n" +
                    "------WebKitFormBoundaryHiRhNJmdch7uHr93\n" +
                    "Content-Disposition: form-data; name=\"vul_list\"\n" +
                    "\n" +
                    "undefined\n" +
                    "------WebKitFormBoundaryHiRhNJmdch7uHr93\n" +
                    "Content-Disposition: form-data; name=\"remarks\"\n" +
                    "\n" +
                    "burpsuite\n" +
                    "------WebKitFormBoundaryHiRhNJmdch7uHr93--\n";

    public ForwardBridge() {

    }

    public String forward(byte[] request) throws Exception {
        String requestRaw = new String(request);
        contentLength += requestRaw.length();
        String requestPrefix = templateRequestPrefix.replace("{{length}}", ""+contentLength);
        String data = requestPrefix + requestRaw + templateRequestSuffix;
        SocketHTTPRequest httpRequest = new SocketHTTPRequest("127.0.0.1", 1231);
        return httpRequest.sendPost(data);
    }

}
