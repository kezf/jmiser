package org.miser.jwt.signers;

import org.miser.core.util.CharsetUtil;
import org.miser.core.util.StringUtil;
import org.miser.crypto.digest.HMac;

import java.nio.charset.Charset;
import java.security.Key;

/**
 * HMac算法签名实现
 *
 * @author Oliver
 * 
 */
public class HMacJWTSigner implements JWTSigner {

	private Charset charset = CharsetUtil.CHARSET_UTF_8;
	private final HMac hMac;

	/**
	 * 构造
	 *
	 * @param algorithm HMAC签名算法
	 * @param key       密钥
	 */
	public HMacJWTSigner(String algorithm, byte[] key) {
		this.hMac = new HMac(algorithm, key);
	}

	/**
	 * 构造
	 *
	 * @param algorithm HMAC签名算法
	 * @param key       密钥
	 */
	public HMacJWTSigner(String algorithm, Key key) {
		this.hMac = new HMac(algorithm, key);
	}

	/**
	 * 设置编码
	 *
	 * @param charset 编码
	 * @return 编码
	 */
	public HMacJWTSigner setCharset(Charset charset) {
		this.charset = charset;
		return this;
	}

	@Override
	public String sign(String headerBase64, String payloadBase64) {
		return hMac.digestBase64(StringUtil.format("{}.{}", headerBase64, payloadBase64), charset, true);
	}

	@Override
	public boolean verify(String headerBase64, String payloadBase64, String signBase64) {
		final String sign = sign(headerBase64, payloadBase64);
		return hMac.verify(
				StringUtil.bytes(sign, charset),
				StringUtil.bytes(signBase64, charset));
	}

	@Override
	public String getAlgorithm() {
		return this.hMac.getAlgorithm();
	}
}
