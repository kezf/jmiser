package org.miser.http.ssl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.miser.http.ssl.SSLSocketFactoryBuilder.SSLv3;
import static org.miser.http.ssl.SSLSocketFactoryBuilder.TLSv1;
import static org.miser.http.ssl.SSLSocketFactoryBuilder.TLSv11;
import static org.miser.http.ssl.SSLSocketFactoryBuilder.TLSv12;

/**
 * 兼容android低版本SSL连接<br>
 * 在测试HttpUrlConnection的时候，发现一部分手机无法连接[GithubPage]
 *
 * <p>
 * 最后发现原来是某些SSL协议没有开启
 *
 * @author Oliver
 */
public class AndroidSupportSSLFactory extends CustomProtocolsSSLFactory {

	// Android低版本不重置的话某些SSL访问就会失败
	private static final String[] protocols = {SSLv3, TLSv1, TLSv11, TLSv12};

	public AndroidSupportSSLFactory() throws KeyManagementException, NoSuchAlgorithmException {
		super(protocols);
	}

}