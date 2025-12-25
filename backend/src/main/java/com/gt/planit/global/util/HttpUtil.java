package com.gt.planit.global.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    public static String get(String apiUrl, Map<String, String> headers) {
        HttpURLConnection con = null;

        try {
            URL url = new URL(apiUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            InputStream is = (con.getResponseCode() == 200)
                    ? con.getInputStream()
                    : con.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("HTTP GET 요청 실패: " + apiUrl, e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}