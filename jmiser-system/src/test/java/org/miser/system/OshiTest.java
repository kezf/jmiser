package org.miser.system;

import org.miser.system.oshi.CpuInfo;
import org.miser.system.oshi.OshiUtil;
import org.junit.Assert;
import org.junit.Test;

public class OshiTest {

	@Test
	public void getMemoryTest() {
		long total = OshiUtil.getMemory().getTotal();
		Assert.assertTrue(total > 0);
	}

	@Test
	public void getCupInfo() {
		CpuInfo cpuInfo = OshiUtil.getCpuInfo();
		Assert.assertNotNull(cpuInfo);
	}
}
