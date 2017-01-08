package org.eding.httpclient;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

/**
 * <ul>
 * <li>异常处理</li>
 * <li>最大时间设置</li>
 * <li>连接超时</li>
 * <li>读取超时</li>
 * <li>重试</li>
 * <li>公共请求�?</li>
 * </ul>
 */
public class HTTPConfig {
	private int maxTotal = 100;
	private int retryCount = 3;
	private int socketTimeout = 10000;
	private int connectTimeout = 10000;

	private PoolingHttpClientConnectionManager connectionManager;
	private HttpRequestRetryHandler retryHandler;

	// ConnectionKeepAliveStrategy keepAliveStrategy;
	// List<HttpRequestInterceptor> requestInterceptors = new
	// ArrayList<HttpRequestInterceptor>();

	public PoolingHttpClientConnectionManager getConnectionManager() {
		if (connectionManager != null) {
			return connectionManager;
		}
		try {
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", getHttpSocketFactory()).register("https", getHttpsSocketFactory()).build();
			connectionManager = new PoolingHttpClientConnectionManager(registry);
			connectionManager.setMaxTotal(maxTotal);
			connectionManager.setDefaultMaxPerRoute(maxTotal);
			connectionManager.closeIdleConnections(5, TimeUnit.MINUTES);
			ConnectionConfig cf = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).build();
			connectionManager.setDefaultConnectionConfig(cf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return connectionManager;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public HttpRequestRetryHandler getRetryHandler() {
		if (retryHandler != null) {
			return retryHandler;
		}

		retryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int count, HttpContext context) {
				if (count > retryCount) {
					return false;
				}

				if (exception instanceof ClientProtocolException) {
					return false;
				}

				if (exception instanceof InterruptedIOException) {
					// Timeout
					return false;
				}
				if (exception instanceof UnknownHostException) {
					// Unknown host
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					// Connection refused
					return false;
				}
				if (exception instanceof SSLException) {
					// SSL handshake exception
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		return retryHandler;
	}

	private static PlainConnectionSocketFactory getHttpSocketFactory() {
		PlainConnectionSocketFactory psf = new PlainConnectionSocketFactory();
		return psf;
	}

	private static LayeredConnectionSocketFactory getHttpsSocketFactory() throws Exception {
		// KeyStore keyStore = KeyStore.getInstance("PKCS12");
		// FileInputStream instream = new FileInputStream(new File(
		// "d:/apiclient_cert.p12"));
		// try {
		// keyStore.load(instream, Config.ID.toCharArray());
		// } finally {
		// instream.close();
		// }
		// 忽略SSL证书错误
		SSLContext sslContext = SSLContexts.custom().useProtocol("TLSv1")
				// .loadKeyMaterial(keyStore, "1228527702".toCharArray())
				.loadTrustMaterial(new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();

		LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		return sslsf;
	}
}
