package org.miser.extra.tokenizer.engine.ikanalyzer;

import org.wltea.analyzer.core.Lexeme;

import org.miser.extra.tokenizer.Word;

/**
 * IKAnalyzer分词中的一个单词包装
 * 
 * @author Oliver
 *
 */
public class IKAnalyzerWord implements Word {
	private static final long serialVersionUID = 1L;
	
	private final Lexeme word;

	/**
	 * 构造
	 * 
	 * @param word {@link Lexeme}
	 */
	public IKAnalyzerWord(Lexeme word) {
		this.word = word;
	}

	@Override
	public String getText() {
		return word.getLexemeText();
	}
	
	@Override
	public int getStartOffset() {
		return word.getBeginPosition();
	}
	
	@Override
	public int getEndOffset() {
		return word.getEndPosition();
	}

	@Override
	public String toString() {
		return getText();
	}
}
