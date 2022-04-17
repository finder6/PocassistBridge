package me.jfishing.burp.pocassist_bridge;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ScannedCache {

    private Map<String, String> cache;
    private MessageDigest md5Digest;

    public ScannedCache() throws NoSuchAlgorithmException {
        cache = new HashMap<String, String>(1000);
        md5Digest = MessageDigest.getInstance("MD5");
    }

    public boolean add(String url) throws UnsupportedEncodingException {
        byte[] digest = md5Digest.digest(url.getBytes("UTF-8"));
        String hash = bytesToHexString(digest);
        if(cache.get(hash) ==  null) {
            cache.put(hash, url);
            return true;
        }
        return false;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
