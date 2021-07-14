package org.miser.poi.excel.sax;

import org.miser.core.io.IORuntimeException;
import org.miser.core.io.IOUtil;
import org.miser.core.util.StringUtil;
import org.miser.poi.excel.sax.handler.RowHandler;
import org.miser.poi.exceptions.POIException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Sax方式读取Excel文件<br>
 * Excel2007格式说明见：http://www.cnblogs.com/wangmingshun/p/6654143.html
 *
 * @author Oliver
 * 
 */
public class Excel07SaxReader implements ExcelSaxReader<Excel07SaxReader> {

	private final SheetDataSaxHandler handler;

	/**
	 * 构造
	 *
	 * @param rowHandler 行处理器
	 */
	public Excel07SaxReader(RowHandler rowHandler) {
		this.handler = new SheetDataSaxHandler(rowHandler);
	}

	/**
	 * 设置行处理器
	 *
	 * @param rowHandler 行处理器
	 * @return this
	 */
	public Excel07SaxReader setRowHandler(RowHandler rowHandler) {
		this.handler.setRowHandler(rowHandler);
		return this;
	}

	// ------------------------------------------------------------------------------ Read start
	@Override
	public Excel07SaxReader read(File file, int rid) throws POIException {
		return read(file, RID_PREFIX + rid);
	}

	@Override
	public Excel07SaxReader read(File file, String idOrRidOrSheetName) throws POIException {
		try {
			return read(OPCPackage.open(file), idOrRidOrSheetName);
		} catch (InvalidFormatException e) {
			throw new POIException(e);
		}
	}

	@Override
	public Excel07SaxReader read(InputStream in, int rid) throws POIException {
		return read(in, RID_PREFIX + rid);
	}

	@Override
	public Excel07SaxReader read(InputStream in, String idOrRidOrSheetName) throws POIException {
		try (final OPCPackage opcPackage = OPCPackage.open(in)) {
			return read(opcPackage, idOrRidOrSheetName);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} catch (InvalidFormatException e) {
			throw new POIException(e);
		}
	}

	/**
	 * 开始读取Excel，Sheet编号从0开始计数
	 *
	 * @param opcPackage {@link OPCPackage}，Excel包，读取后不关闭
	 * @param rid        Excel中的sheet rid编号，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	public Excel07SaxReader read(OPCPackage opcPackage, int rid) throws POIException {
		return read(opcPackage, RID_PREFIX + rid);
	}

	/**
	 * 开始读取Excel，Sheet编号从0开始计数
	 *
	 * @param opcPackage {@link OPCPackage}，Excel包，读取后不关闭
	 * @param idOrRidOrSheetName    Excel中的sheet id或者rid编号或sheet名，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	public Excel07SaxReader read(OPCPackage opcPackage, String idOrRidOrSheetName) throws POIException {
		try {
			return read(new XSSFReader(opcPackage), idOrRidOrSheetName);
		} catch (OpenXML4JException e) {
			throw new POIException(e);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * 开始读取Excel，Sheet编号从0开始计数
	 *
	 * @param xssfReader {@link XSSFReader}，Excel读取器
	 * @param idOrRidOrSheetName    Excel中的sheet id或者rid编号或sheet名，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 * 
	 */
	public Excel07SaxReader read(XSSFReader xssfReader, String idOrRidOrSheetName) throws POIException {
		// 获取共享样式表，样式非必须
		try {
			this.handler.stylesTable = xssfReader.getStylesTable();
		} catch (IOException | InvalidFormatException ignore) {
			// ignore
		}

		// 获取共享字符串表
		try {
			this.handler.sharedStringsTable = xssfReader.getSharedStringsTable();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} catch (InvalidFormatException e) {
			throw new POIException(e);
		}

		return readSheets(xssfReader, idOrRidOrSheetName);
	}
	// ------------------------------------------------------------------------------ Read end

	// --------------------------------------------------------------------------------------- Private method start

	/**
	 * 开始读取Excel，Sheet编号从0开始计数
	 *
	 * @param xssfReader         {@link XSSFReader}，Excel读取器
	 * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名，从0开始，rid必须加rId前缀，例如rId0，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 * 
	 */
	private Excel07SaxReader readSheets(XSSFReader xssfReader, String idOrRidOrSheetName) throws POIException {
		this.handler.sheetIndex = getSheetIndex(xssfReader, idOrRidOrSheetName);
		InputStream sheetInputStream = null;
		try {
			if (this.handler.sheetIndex > -1) {
				// 根据 rId# 或 rSheet# 查找sheet
				sheetInputStream = xssfReader.getSheet(RID_PREFIX + (this.handler.sheetIndex + 1));
				ExcelSaxUtil.readFrom(sheetInputStream, this.handler);
				this.handler.rowHandler.doAfterAllAnalysed();
			} else {
				this.handler.sheetIndex = -1;
				// 遍历所有sheet
				final Iterator<InputStream> sheetInputStreams = xssfReader.getSheetsData();
				while (sheetInputStreams.hasNext()) {
					// 重新读取一个sheet时行归零
					this.handler.index = 0;
					this.handler.sheetIndex++;
					sheetInputStream = sheetInputStreams.next();
					ExcelSaxUtil.readFrom(sheetInputStream, this.handler);
					this.handler.rowHandler.doAfterAllAnalysed();
				}
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new POIException(e);
		} finally {
			IOUtil.close(sheetInputStream);
		}
		return this;
	}

	/**
	 * 获取sheet索引，从0开始
	 * <ul>
	 *     <li>传入'rId'开头，直接去除rId前缀</li>
	 *     <li>传入纯数字，表示sheetIndex，通过{@link SheetRidReader}转换为rId</li>
	 *     <li>传入其它字符串，表示sheetName，通过{@link SheetRidReader}转换为rId</li>
	 * </ul>
	 *
	 * @param xssfReader         {@link XSSFReader}，Excel读取器
	 * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名称，从0开始，rid必须加rId前缀，例如rId0，如果为-1处理所有编号的sheet
	 * @return sheet索引，从0开始
	 * 
	 */
	private int getSheetIndex(XSSFReader xssfReader, String idOrRidOrSheetName) {
		// rid直接处理
		if (StringUtil.startWithIgnoreCase(idOrRidOrSheetName, RID_PREFIX)) {
			return Integer.parseInt(StringUtil.removePrefixIgnoreCase(idOrRidOrSheetName, RID_PREFIX));
		}

		// sheetIndex需转换为rid
		final SheetRidReader ridReader = new SheetRidReader().read(xssfReader);

		final int sheetIndex;
		Integer rid;
		try {
			sheetIndex = Integer.parseInt(idOrRidOrSheetName);
			rid = ridReader.getRidBySheetIdBase0(sheetIndex);
			return (null != rid) ? rid : sheetIndex;
		} catch (NumberFormatException ignore) {
			// 非数字，可能为sheet名称
			rid = ridReader.getRidByNameBase0(idOrRidOrSheetName);
			if (null != rid) {
				return rid;
			}
		}

		throw new IllegalArgumentException("Invalid rId or id or sheetName: " + idOrRidOrSheetName);
	}
	// --------------------------------------------------------------------------------------- Private method end
}