package org.miser.log.test;

import org.miser.log.Log;
import org.miser.log.LogFactory;
import org.miser.log.dialect.commons.ApacheCommonsLogFactory;
import org.miser.log.dialect.console.ConsoleLogFactory;
import org.miser.log.dialect.jboss.JbossLogFactory;
import org.miser.log.dialect.jdk.JdkLogFactory;
import org.miser.log.dialect.log4j.Log4jLogFactory;
import org.miser.log.dialect.log4j2.Log4j2LogFactory;
import org.miser.log.dialect.slf4j.Slf4jLogFactory;
import org.miser.log.dialect.tinylog.TinyLog2Factory;
import org.miser.log.dialect.tinylog.TinyLogFactory;
import org.junit.Test;

/**
 * 日志门面单元测试
 * @author Oliver
 *
 */
public class CustomLogTest {
	
	private static final String LINE = "----------------------------------------------------------------------";
	
	@Test
	public void consoleLogTest(){
		LogFactory factory = new ConsoleLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}

	@Test
	public void consoleLogNullTest(){
		LogFactory factory = new ConsoleLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();

		log.info(null);
		log.info((String)null);
	}
	
	@Test
	public void commonsLogTest(){
		LogFactory factory = new ApacheCommonsLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void tinyLogTest(){
		LogFactory factory = new TinyLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}

	@Test
	public void tinyLog2Test(){
		LogFactory factory = new TinyLog2Factory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();

		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void log4j2LogTest(){
		LogFactory factory = new Log4j2LogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();

		log.debug(null);
		log.debug("This is custom '{}' log\n{}", factory.getName(), LINE);
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void log4jLogTest(){
		LogFactory factory = new Log4jLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
		
	}
	
	@Test
	public void jbossLogTest(){
		LogFactory factory = new JbossLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void jdkLogTest(){
		LogFactory factory = new JdkLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void slf4jTest(){
		LogFactory factory = new Slf4jLogFactory(false);
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info((String)null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
}
