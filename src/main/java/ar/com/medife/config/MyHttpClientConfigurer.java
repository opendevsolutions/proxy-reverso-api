package ar.com.medife.config;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.camel.component.http4.HttpClientConfigurer;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class MyHttpClientConfigurer implements HttpClientConfigurer {
    
    public void configureHttpClient(HttpClientBuilder client) {
            try {
                    SSLContext ctx = SSLContext.getInstance("SSL");
                    X509TrustManager tm = new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[0];
                            }
							@Override
							public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
									throws CertificateException {
								
							}
							@Override
							public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
									throws CertificateException {
								
							}
                    };
                    ctx.init(null, new TrustManager[] { tm }, null);
                    SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    CloseableHttpClient cc = client.build();
                    cc.getConnectionManager().getSchemeRegistry().register(new Scheme("https4", 443, ssf));
			        cc.getConnectionManager().getSchemeRegistry().register(new Scheme("http", 80,
			        PlainSocketFactory.getSocketFactory()));
			        cc.getConnectionManager().getSchemeRegistry().register(new Scheme("http4", 80,
			        PlainSocketFactory.getSocketFactory()));

            } catch (Exception e) {
                    throw new RuntimeException(e);
            }
    }

}
