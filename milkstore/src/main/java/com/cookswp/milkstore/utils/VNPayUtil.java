package com.cookswp.milkstore.utils;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static java.net.URLEncoder.encode;

public class VNPayUtil {

    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }


    public static String getPaymentURL(Map<String, String> paramsMap, boolean encodeKey) {
        return paramsMap.entrySet().stream()
                .filter(entry -> isNotEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> buildQueryParam(entry, encodeKey))
                .collect(Collectors.joining("&"));
    }

    //Kiểm tra xem giá trị không rỗng và không phải null
    private static boolean isNotEmpty(String value){
        return value != null && !value.isEmpty();
    }

    //Tạo cặp key=value từ Map gọi phương thức encode để mã hóa khóa
    private static String buildQueryParam(Map.Entry<String, String> entry, boolean encodeKey){
        String key = encodeKey ? encode(entry.getKey()) : entry.getKey();
        String value = encode(entry.getValue());
        return key + "=" + value;
    }

    //Mã hóa một chuỗi sử dụng URLEncoder với bộ ký tự US_ASCII
    private static String encode(String value){
        return URLEncoder.encode(value, StandardCharsets.US_ASCII);
    }
}
