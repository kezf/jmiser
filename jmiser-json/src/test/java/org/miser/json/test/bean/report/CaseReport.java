package org.miser.json.test.bean.report;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例报告
 * @author Oliver
 * @version 20181012
 *
 */
public class CaseReport {
	
	/**
	 * 包含的测试步骤报告
	 */
	private List<StepReport> stepReports = new ArrayList<>();
	
	public List<StepReport> getStepReports() {
		return stepReports;
	}

	public void setStepReports(List<StepReport> stepReports) {
		this.stepReports = stepReports;
	}

	@Override
	public String toString() {
		return "CaseReport [stepReports=" + stepReports + "]";
	}
}
