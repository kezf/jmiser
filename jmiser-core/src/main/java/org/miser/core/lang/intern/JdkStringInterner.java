package org.miser.core.lang.intern;

/**
 * JDK中默认的字符串规范化实现
 *
 * @author Oliver
 * 
 */
public class JdkStringInterner implements Interner<String> {
	@Override
	public String intern(String sample) {
		if (null == sample) {
			return null;
		}
		return sample.intern();
	}
}
