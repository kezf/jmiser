package org.miser.crypto.asymmetric;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.miser.core.codec.BCD;
import org.miser.core.codec.Base64;
import org.miser.core.io.IORuntimeException;
import org.miser.core.io.IOUtil;
import org.miser.core.lang.Assert;
import org.miser.core.util.CharsetUtil;
import org.miser.core.util.HexUtil;
import org.miser.core.util.StringUtil;
import org.miser.crypto.SecureUtil;

/**
 * 抽象的非对称加密对象，包装了加密和解密为Hex和Base64的封装
 *
 * @param <T> 返回自身类型
 * @author Oliver
 */
public abstract class AbstractAsymmetricCrypto<T extends AbstractAsymmetricCrypto<T>> extends BaseAsymmetric<T> {
	// ------------------------------------------------------------------
	// Constructor start

	/**
	 * 构造
	 * <p>
	 * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
	 * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
	 *
	 * @param algorithm  算法
	 * @param privateKey 私钥
	 * @param publicKey  公钥
	 * 
	 */
	public AbstractAsymmetricCrypto(String algorithm, PrivateKey privateKey, PublicKey publicKey) {
		super(algorithm, privateKey, publicKey);
	}
	// ------------------------------------------------------------------
	// Constructor end

	// ---------------------------------------------------------------------------------
	// Encrypt

	/**
	 * 加密
	 *
	 * @param data    被加密的bytes
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 加密后的bytes
	 */
	public abstract byte[] encrypt(byte[] data, KeyType keyType);

	/**
	 * 编码为Hex字符串
	 *
	 * @param data    被加密的bytes
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Hex字符串
	 */
	public String encryptHex(byte[] data, KeyType keyType) {
		return HexUtil.encodeHexStr(encrypt(data, keyType));
	}

	/**
	 * 编码为Base64字符串
	 *
	 * @param data    被加密的bytes
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Base64字符串
	 * 
	 */
	public String encryptBase64(byte[] data, KeyType keyType) {
		return Base64.encode(encrypt(data, keyType));
	}

	/**
	 * 加密
	 *
	 * @param data    被加密的字符串
	 * @param charset 编码
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 加密后的bytes
	 */
	public byte[] encrypt(String data, String charset, KeyType keyType) {
		return encrypt(StringUtil.bytes(data, charset), keyType);
	}

	/**
	 * 加密
	 *
	 * @param data    被加密的字符串
	 * @param charset 编码
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 加密后的bytes
	 */
	public byte[] encrypt(String data, Charset charset, KeyType keyType) {
		return encrypt(StringUtil.bytes(data, charset), keyType);
	}

	/**
	 * 加密，使用UTF-8编码
	 *
	 * @param data    被加密的字符串
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 加密后的bytes
	 */
	public byte[] encrypt(String data, KeyType keyType) {
		return encrypt(StringUtil.bytes(data, CharsetUtil.CHARSET_UTF_8), keyType);
	}

	/**
	 * 编码为Hex字符串
	 *
	 * @param data    被加密的字符串
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Hex字符串
	 * 
	 */
	public String encryptHex(String data, KeyType keyType) {
		return HexUtil.encodeHexStr(encrypt(data, keyType));
	}

	/**
	 * 编码为Hex字符串
	 *
	 * @param data    被加密的bytes
	 * @param charset 编码
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Hex字符串
	 * 
	 */
	public String encryptHex(String data, Charset charset, KeyType keyType) {
		return HexUtil.encodeHexStr(encrypt(data, charset, keyType));
	}

	/**
	 * 编码为Base64字符串，使用UTF-8编码
	 *
	 * @param data    被加密的字符串
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Base64字符串
	 * 
	 */
	public String encryptBase64(String data, KeyType keyType) {
		return Base64.encode(encrypt(data, keyType));
	}

	/**
	 * 编码为Base64字符串
	 *
	 * @param data    被加密的字符串
	 * @param charset 编码
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Base64字符串
	 * 
	 */
	public String encryptBase64(String data, Charset charset, KeyType keyType) {
		return Base64.encode(encrypt(data, charset, keyType));
	}

	/**
	 * 加密
	 *
	 * @param data    被加密的数据流
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 加密后的bytes
	 * @throws IORuntimeException IO异常
	 */
	public byte[] encrypt(InputStream data, KeyType keyType) throws IORuntimeException {
		return encrypt(IOUtil.readBytes(data), keyType);
	}

	/**
	 * 编码为Hex字符串
	 *
	 * @param data    被加密的数据流
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Hex字符串
	 * 
	 */
	public String encryptHex(InputStream data, KeyType keyType) {
		return HexUtil.encodeHexStr(encrypt(data, keyType));
	}

	/**
	 * 编码为Base64字符串
	 *
	 * @param data    被加密的数据流
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return Base64字符串
	 * 
	 */
	public String encryptBase64(InputStream data, KeyType keyType) {
		return Base64.encode(encrypt(data, keyType));
	}

	/**
	 * 分组加密
	 *
	 * @param data    数据
	 * @param keyType 密钥类型
	 * @return 加密后的密文
	 * 
	 */
	public String encryptBcd(String data, KeyType keyType) {
		return encryptBcd(data, keyType, CharsetUtil.CHARSET_UTF_8);
	}

	/**
	 * 分组加密
	 *
	 * @param data    数据
	 * @param keyType 密钥类型
	 * @param charset 加密前编码
	 * @return 加密后的密文
	 * 
	 */
	public String encryptBcd(String data, KeyType keyType, Charset charset) {
		return BCD.bcdToStr(encrypt(data, charset, keyType));
	}

	// ---------------------------------------------------------------------------------
	// Decrypt

	/**
	 * 解密
	 *
	 * @param bytes   被解密的bytes
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 解密后的bytes
	 */
	public abstract byte[] decrypt(byte[] bytes, KeyType keyType);

	/**
	 * 解密
	 *
	 * @param data    被解密的bytes
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 解密后的bytes
	 * @throws IORuntimeException IO异常
	 */
	public byte[] decrypt(InputStream data, KeyType keyType) throws IORuntimeException {
		return decrypt(IOUtil.readBytes(data), keyType);
	}

	/**
	 * 从Hex或Base64字符串解密，编码为UTF-8格式
	 *
	 * @param data    Hex（16进制）或Base64字符串
	 * @param keyType 私钥或公钥 {@link KeyType}
	 * @return 解密后的bytes
	 * 
	 */
	public byte[] decrypt(String data, KeyType keyType) {
		return decrypt(SecureUtil.decode(data), keyType);
	}

	/**
	 * 解密为字符串，密文需为Hex（16进制）或Base64字符串
	 *
	 * @param data    数据，Hex（16进制）或Base64字符串
	 * @param keyType 密钥类型
	 * @param charset 加密前编码
	 * @return 解密后的密文
	 * 
	 */
	public String decryptStr(String data, KeyType keyType, Charset charset) {
		return StringUtil.str(decrypt(data, keyType), charset);
	}

	/**
	 * 解密为字符串，密文需为Hex（16进制）或Base64字符串
	 *
	 * @param data    数据，Hex（16进制）或Base64字符串
	 * @param keyType 密钥类型
	 * @return 解密后的密文
	 * 
	 */
	public String decryptStr(String data, KeyType keyType) {
		return decryptStr(data, keyType, CharsetUtil.CHARSET_UTF_8);
	}

	/**
	 * 解密BCD
	 *
	 * @param data    数据
	 * @param keyType 密钥类型
	 * @return 解密后的密文
	 * 
	 */
	public byte[] decryptFromBcd(String data, KeyType keyType) {
		return decryptFromBcd(data, keyType, CharsetUtil.CHARSET_UTF_8);
	}

	/**
	 * 分组解密
	 *
	 * @param data    数据
	 * @param keyType 密钥类型
	 * @param charset 加密前编码
	 * @return 解密后的密文
	 * 
	 */
	public byte[] decryptFromBcd(String data, KeyType keyType, Charset charset) {
		Assert.notNull(data, "Bcd string must be not null!");
		final byte[] dataBytes = BCD.ascToBcd(StringUtil.bytes(data, charset));
		return decrypt(dataBytes, keyType);
	}

	/**
	 * 解密为字符串，密文需为BCD格式
	 *
	 * @param data    数据，BCD格式
	 * @param keyType 密钥类型
	 * @param charset 加密前编码
	 * @return 解密后的密文
	 * 
	 */
	public String decryptStrFromBcd(String data, KeyType keyType, Charset charset) {
		return StringUtil.str(decryptFromBcd(data, keyType, charset), charset);
	}

	/**
	 * 解密为字符串，密文需为BCD格式，编码为UTF-8格式
	 *
	 * @param data    数据，BCD格式
	 * @param keyType 密钥类型
	 * @return 解密后的密文
	 * 
	 */
	public String decryptStrFromBcd(String data, KeyType keyType) {
		return decryptStrFromBcd(data, keyType, CharsetUtil.CHARSET_UTF_8);
	}
}
