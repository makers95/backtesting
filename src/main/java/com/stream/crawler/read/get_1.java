package com.stream.crawler.read;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Description get方式爬蟲
 * @Param $ url 網址
 * @return $ body
 **/
public class get_1 {
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String doGet(String url) throws Exception {
        get_1 get_1 = new get_1();
        OkHttpClient client = get_1.getUnsafeOkHttpClient();

        String result = "";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            result = response.body().string();
            return result;
        }
    }

    public static List<String> getParmaList(Integer start) {
        Calendar cal = Calendar.getInstance();
        List<String> dateList = new ArrayList<>();
        for (int i = start; i <= cal.get(Calendar.YEAR); i++) {
            for (int j = 1; j <= 12; j++) {

                if (j < 10) {
                    dateList.add(i + "0" + j +"01");
                } else {
                    dateList.add(i + "" + j+"01");
                }
                if (cal.get(Calendar.MONTH) + 1 == j&&cal.get(Calendar.YEAR)==i) {
                    break;
                }
            }
        }
        return dateList;
    }


}

