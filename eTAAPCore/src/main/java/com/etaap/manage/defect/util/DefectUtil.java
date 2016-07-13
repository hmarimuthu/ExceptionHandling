package com.etaap.manage.defect.util;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.config.util.ConfigUtil;
import com.etaap.core.exception.DefectException;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;
import com.etaap.manage.defect.resources.SupportedDefectTools;

/**
 * This class has utility method regarding defect module
 * 
 * @author rThangavelu
 *
 */
public class DefectUtil {

	private static Log log = LogUtil.getLog(DefectUtil.class);

	/**
	 * created constructor for sonar purpose Otherwise this class has to be
	 * defined as Static
	 */
	DefectUtil() {

	}

	/**
	 * Verifies weather the given defect tool is Supported by eTaap or not.
	 *
	 * @return boolean
	 * @throws DefectException
	 *             the defect exception
	 */
	@HandleException(expected = { DefectException.class })
	public static boolean isDefectToolSupported() throws DefectException {

		boolean result = false;

		if (ConfigUtil.isManageDefectOn()) {

			String defectTool = TestBedManagerConfiguration.getInstance().getDefectConfig().getDefectManagementTool();
			// instantiate the appropriate defect tracking tool to be used
			if (defectTool != null) {
				if (!SupportedDefectTools.isSupported(defectTool)) {
					log.error("The defect '" + defectTool + "' is not supported.");
					ExceptionHandler ex = new ExceptionHandler();

					ex.handleit(ConfigUtil.class,
							new DefectException("The defect '" + defectTool + "' is not supported."));
				} else {
					result = true;
				}
			}
		}
		return result;
	}

}
