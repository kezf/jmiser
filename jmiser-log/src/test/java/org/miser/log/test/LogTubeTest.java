package org.miser.log.test;

import org.miser.log.Log;
import org.miser.log.LogFactory;
import org.miser.log.dialect.logtube.LogTubeLogFactory;
import org.junit.Test;

public class LogTubeTest {

	@Test
	public void logTest(){
		LogFactory factory = new LogTubeLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		log.debug("LogTube debug test.");
	}
}
