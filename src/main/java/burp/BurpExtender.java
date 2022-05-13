package burp;

import burp.*;
import me.jfishing.burp.pocassist_bridge.ForwardBridge;
import me.jfishing.burp.pocassist_bridge.ScannedCache;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class BurpExtender implements IScannerCheck,IBurpExtender {

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;
    private PrintWriter stderr;
    private ScannedCache scannedCache;
    private ForwardBridge forwardBridge;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.callbacks.setExtensionName("PocassistBridge");//插件名字
        this.callbacks.setProxyInterceptionEnabled(false);
        callbacks.registerScannerCheck(this);
        helpers = callbacks.getHelpers();
        stdout = new PrintWriter(callbacks.getStdout(), true);
        stderr = new PrintWriter(callbacks.getStderr(), true);
        try {
            scannedCache = new ScannedCache();
            forwardBridge = new ForwardBridge();
        } catch (NoSuchAlgorithmException e) {
            stderr.println(e.getClass() + ":" +e.getMessage());
        }
        stdout.println("PocassistBridge Plugin is working");
    }

    public List<IScanIssue> doPassiveScan(IHttpRequestResponse baseRequestResponse) {
        IResponseInfo responseInfo = helpers.analyzeResponse(baseRequestResponse.getResponse());
        // 静态文件过滤
        for(String header : responseInfo.getHeaders()) {
            if(header.startsWith("Last-Modified:") || header.startsWith("last-modified") || (header.indexOf("max-age=") > 0 && header.indexOf("max-age=0") == -1))
                return null;
        }
        IRequestInfo requestInfo =  helpers.analyzeRequest(baseRequestResponse);
        String method = requestInfo.getMethod();
        URL url = requestInfo.getUrl();
        String urlFinger = method + url.getHost() + ":" + url.getPort() + url.getPath() + "^" + requestInfo.getParameters().size();
        try {
            stdout.println("receive " + method + " " + url);
            if(scannedCache.add(urlFinger)) {
                stdout.println("send " + method + " " + url);
                byte[] requestRaw = baseRequestResponse.getRequest();
                try {
                    String result = forwardBridge.forward(requestRaw);
                    stdout.println(result);
                } catch (Exception e) {
                    stderr.println(e.getClass() + ":" +e.getMessage());
                }
            }
        } catch (UnsupportedEncodingException e) {
            stderr.println(e.getClass() + ":" +e.getMessage());
        }

        return null;
    }

    public List<IScanIssue> doActiveScan(IHttpRequestResponse baseRequestResponse, IScannerInsertionPoint insertionPoint) {
        return null;
    }

    public int consolidateDuplicateIssues(IScanIssue existingIssue, IScanIssue newIssue) {
        return 0;
    }
}
