package org.miser.core.text.csv;

/**
 * CSV读取配置项
 *
 * @author Oliver
 *
 */
public class CsvReadConfig extends CsvConfig {
	private static final long serialVersionUID = 5396453565371560052L;

	/** 是否首行做为标题行，默认false */
	protected boolean containsHeader;
	/** 是否跳过空白行，默认true */
	protected boolean skipEmptyRows = true;
	/** 每行字段个数不同时是否抛出异常，默认false */
	protected boolean errorOnDifferentFieldCount;

	/**
	 * 默认配置
	 *
	 * @return 默认配置
	 */
	public static CsvReadConfig defaultConfig() {
		return new CsvReadConfig();
	}

	/**
	 * 设置是否首行做为标题行，默认false
	 *
	 * @param containsHeader 是否首行做为标题行，默认false
	 * @return this
	 */
	public CsvReadConfig setContainsHeader(boolean containsHeader) {
		this.containsHeader = containsHeader;
		return this;
	}

	/**
	 * 设置是否跳过空白行，默认true
	 *
	 * @param skipEmptyRows 是否跳过空白行，默认true
	 * @return this
	 */
	public CsvReadConfig setSkipEmptyRows(boolean skipEmptyRows) {
		this.skipEmptyRows = skipEmptyRows;
		return this;
	}

	/**
	 * 设置每行字段个数不同时是否抛出异常，默认false
	 *
	 * @param errorOnDifferentFieldCount 每行字段个数不同时是否抛出异常，默认false
	 * @return this
	 */
	public CsvReadConfig setErrorOnDifferentFieldCount(boolean errorOnDifferentFieldCount) {
		this.errorOnDifferentFieldCount = errorOnDifferentFieldCount;
		return this;
	}
}
