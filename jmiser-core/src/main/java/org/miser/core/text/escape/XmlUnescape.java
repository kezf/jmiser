package org.miser.core.text.escape;

import org.miser.core.text.replacer.LookupReplacer;
import org.miser.core.text.replacer.ReplacerChain;

/**
 * XML的UNESCAPE
 *
 * @author Oliver
 * 
 */
public class XmlUnescape extends ReplacerChain {
	private static final long serialVersionUID = 1L;

	protected static final String[][] BASIC_UNESCAPE = InternalEscapeUtil.invert(XmlEscape.BASIC_ESCAPE);
	// issue#1118
	protected static final String[][] OTHER_UNESCAPE = new String[][] { new String[] { "&apos;", "'" } };

	/**
	 * 构造
	 */
	public XmlUnescape() {
		addChain(new LookupReplacer(BASIC_UNESCAPE));
		addChain(new NumericEntityUnescaper());
		addChain(new LookupReplacer(OTHER_UNESCAPE));
	}
}
