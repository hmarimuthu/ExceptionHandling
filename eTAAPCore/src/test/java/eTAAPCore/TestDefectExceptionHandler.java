package eTAAPCore;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.etaap.common.util.LogUtil;
import com.etaap.core.config.TestBedManagerConfiguration;
import com.etaap.core.exception.DefectException;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;
import com.etaap.manage.defect.resources.SupportedDefectTools;
import com.etaap.webui.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class TestDefectExceptionHandler implements ExceptionListener {

	static Log log = LogUtil.getLog(TestDefectExceptionHandler.class);
	private static Properties prop = null;

	@Before
	public void setUp() {
		prop = TestUtil.loadProps(TestUtil.propFilePath);
		TestUtil.initialize();
	}

	@HandleException(expected = { DefectException.class })
	public static boolean isDefectToolSupported() {

		boolean result = false;
		String defectTool = TestBedManagerConfiguration.getInstance().getDefectConfig().getDefectManagementTool();

		// instantiate the appropriate defect tracking tool to be used
		if (defectTool != null) {
			if (SupportedDefectTools.isSupported(defectTool)) {
				log.error("The defect '" + defectTool + "' is not supported.");
				ExceptionHandler ex = new ExceptionHandler();

				ex.handleit(TestDefectExceptionHandler.class,
						new DefectException("The defect tool '" + defectTool + "' is not supported."));

			} else {
				result = true;
			}
		}
		return result;
	}

	@Test
	public void testStaticHandle() {

		boolean result = isDefectToolSupported();
		assertTrue(result == false);

	}

}
