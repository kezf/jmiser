package org.miser.extra.tokenizer.engine.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;

import org.miser.core.util.StringUtil;
import org.miser.extra.tokenizer.TokenizerEngine;
import org.miser.extra.tokenizer.Result;

/**
 * HanLP分词引擎实现<br>
 * 项目地址：https://github.com/hankcs/HanLP
 * 
 * @author Oliver
 *
 */
public class HanLPEngine implements TokenizerEngine {

	private final Segment seg;
	
	/**
	 * 构造
	 * 
	 */
	public HanLPEngine() {
		this(HanLP.newSegment());
	}
	
	/**
	 * 构造
	 * 
	 * @param seg {@link Segment}
	 */
	public HanLPEngine(Segment seg) {
		this.seg = seg;
	}

	@Override
	public Result parse(CharSequence text) {
		return new HanLPResult(this.seg.seg(StringUtil.str(text)));
	}
	
}